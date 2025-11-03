package com.ashaxolotl.partytrick.mixin.client;

import com.ashaxolotl.partytrick.PartyTrick;
import com.google.common.collect.Multimap;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Shadow @Final private Map<SoundInstance, Integer> soundEndTicks;

    @Shadow @Final private Map<SoundInstance, Integer> startTicks;

    @Shadow @Final private Multimap<SoundCategory, SoundInstance> sounds;

    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At("HEAD")
    )
    private void catchSound(SoundInstance sound, CallbackInfo ci) {
//        PartyTrick.LOGGER.info("SOUND SYSTEM START: " + startTicks.toString());
//        PartyTrick.LOGGER.info("SOUND SYSTEM END: " + soundEndTicks.toString());
//        PartyTrick.LOGGER.info("SOUND SYSTEM {}", sound.toString());
    }


}
