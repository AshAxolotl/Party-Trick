package com.ashaxolotl.partytrick.mixin.client;

import com.ashaxolotl.partytrick.ClientSoundStorage;
import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundListener;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Shadow @Final private SoundListener listener;

    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundInstance;getVolume()F")
    )

    private void catchSound(SoundInstance sound, CallbackInfo ci) {
        ClientSoundStorage.INSTANCE.addSound(sound, listener.getTransform().position());
    }

//    @ModifyVariable(
//            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
//            at = @At(value = "STORE"), ordinal = 1
//    )
//    private float changeVolume(float value) {
//        PartyTrick.LOGGER.info("{}", value);
//        return value;
//    }
}
