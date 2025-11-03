package com.ashaxolotl.partytrick.mixin;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.fragment.SoundFragment;
import com.ibm.icu.text.MessagePattern;
import dev.enjarai.trickster.cca.MessageHandlerComponent.Key;
import dev.enjarai.trickster.cca.ModGlobalComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow @Final private ServerWorldProperties worldProperties;

    @Shadow public abstract ServerWorld toServerWorld();

    @Inject(
            method = "playSound",
            at = @At("HEAD")
    )
    private void playSound(@Nullable PlayerEntity source, double x, double y, double z, RegistryEntry<SoundEvent> sound, SoundCategory category, float volume, float pitch, long seed, CallbackInfo ci) {
//        PartyTrick.LOGGER.info("SERVER: " + sound.getIdAsString());
//        sendSoundMessage(toServerWorld(),
//                new Key.Broadcast(toServerWorld().getRegistryKey(), new Vector3d(x, y, z), 32*volume), // todo this shouldnt just be 32...
//                sound
//        );
    }

    @Inject(
            method = "playSoundFromEntity",
            at = @At("HEAD")
    )
    private void playSoundFromEntity(@Nullable PlayerEntity source, Entity entity, RegistryEntry<SoundEvent> sound, SoundCategory category, float volume, float pitch, long seed, CallbackInfo ci) {
//        PartyTrick.LOGGER.info("SERVER: " + sound.getIdAsString());
//        World world = entity.getWorld();
//        sendSoundMessage(world,
//                new Key.Broadcast(world.getRegistryKey(), new Vector3d(entity.getPos().x, entity.getPos().y, entity.getPos().z), 32*volume), // todo this shouldnt just be 32...
//                sound
//                );
    }
}
