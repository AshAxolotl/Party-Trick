package com.ashaxolotl.partytrick.mixin.client;


import blue.endless.jankson.annotation.Nullable;
import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.PartyTrickClient;
import com.ashaxolotl.partytrick.SoundStorage;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin {
    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At("HEAD")
    )
    private void catchSound(SoundInstance sound, CallbackInfo ci) {
//        SoundStorage.INSTANCE.addSound(sound);
    }

    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V",
            at = @At("HEAD")
    )
    private void catchSound(SoundInstance sound, int delay, CallbackInfo ci) {
//        SoundStorage.INSTANCE.addSound(sound);
    }
}
