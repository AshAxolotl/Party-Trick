package com.ashaxolotl.partytrick.cca;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import io.wispforest.owo.serialization.endec.MinecraftEndecs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MufflesComponent implements ServerTickingComponent, AutoSyncedComponent {
    public static final int STAY_FOR_TICKS = 100;
    public static final Endec<List<Muffle>> MUFFLES_ENDEC = Muffle.ENDEC.listOf();

    private final PlayerEntity player;
    private final List<Muffle> muffles = new ArrayList<>();
    private int lastBarsHashcode;
    private RegistryKey<World> lastPlayerWorld;


    public MufflesComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        for (var iterator = muffles.iterator(); iterator.hasNext();) {
            var entry = iterator.next();

            if (entry.age >= STAY_FOR_TICKS) {
                iterator.remove();
            } else {
                entry.age++;
            }
        }
        ModEntityComponents.MUFFLES.sync(player);
    }

    public List<Muffle> getMuffles() {
        return muffles;
    }

    public void addMuffle(Optional<List<Identifier>> sounds, double amount) {
        muffles.add(new Muffle(sounds, amount));
    }


    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        // No-op
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        // No-op
        // No need to permanently save this information at all
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        if (player != this.player) return false;

        var hash = muffles.hashCode();
        var worldKey = player.getWorld().getRegistryKey();
        if (hash != lastBarsHashcode || worldKey != lastPlayerWorld) {
            lastBarsHashcode = hash;
            lastPlayerWorld = worldKey;
            return true;
        }
        return false;
    }

    @Override
    public void applySyncPacket(RegistryByteBuf buf) {
        muffles.clear();
        muffles.addAll(buf.read(MUFFLES_ENDEC));
    }

    @Override
    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient) {
        buf.write(MUFFLES_ENDEC, muffles);
    }

    public static class Muffle {
        public static final Endec<Muffle> ENDEC = StructEndecBuilder.of(
                MinecraftEndecs.IDENTIFIER.listOf().optionalOf().fieldOf("sounds", m -> m.sounds),
                Endec.DOUBLE.fieldOf("amount", m -> m.amount),
                Muffle::new
        );

        public Muffle(Optional<List<Identifier>> sounds, double amount) {
            this(sounds, amount, 0);
        }

        public Muffle(Optional<List<Identifier>> sounds, double amount, int age) {
            this.sounds = sounds;
            this.amount = amount;
            this.age = age;
        }

        public Optional<List<Identifier>> sounds;
        public double amount;
        public int age;
    }

}
