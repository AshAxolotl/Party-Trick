package com.ashaxolotl.partytrick.mixin.client;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.SoundStorage;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class ClientPlayerEntityMixin {
    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At("HEAD")
    )
    private void catchSound(SoundInstance sound, CallbackInfo ci) {
        PartyTrick.LOGGER.info(sound.toString());
        SoundStorage.INSTANCE.addSound(sound);
    }

//    @Inject(method = "tick(Z)V", at = @At("HEAD"))
//    private void ticking(boolean paused, CallbackInfo ci) {
//        SoundStorage.INSTANCE.reset();
//    }
}
