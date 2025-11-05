package com.ashaxolotl.partytrick.mixin.client;

import com.ashaxolotl.partytrick.SoundStorage;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At("HEAD")
    )

    // TODO this picks up sounds the player doesnt really hear at all. I dont know if there is a way go fix that
    private void catchSound(SoundInstance sound, CallbackInfo ci) {
        SoundStorage.INSTANCE.addSound(sound);
    }
}
