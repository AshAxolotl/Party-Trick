package com.ashaxolotl.partytrick.mixin.client;

import com.ashaxolotl.partytrick.cca.ModEntityComponents;
import com.ashaxolotl.partytrick.cca.MufflesComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSoundInstance.class)
public class AbstractSoundInstanceMixin {

    @Shadow @Final protected SoundCategory category;
    @Shadow @Final protected Identifier id;

    @Inject(method = "getVolume", at = @At("RETURN"), cancellable = true)
    private void muffleSound(CallbackInfoReturnable<Float> cir) {
        if (this.category != SoundCategory.VOICE) {
            var player = MinecraftClient.getInstance().player;
            if (player != null) {
                double mod = 1;
                for (MufflesComponent.Muffle muffle : player.getComponent(ModEntityComponents.MUFFLES).getMuffles()) {
                    if (muffle.sounds.isEmpty()) {
                        mod += muffle.amount;
                    } else if (muffle.sounds.get().contains(id)) {
                        mod += muffle.amount;
                    }
                }

                cir.setReturnValue(cir.getReturnValue() * (float) mod);
            }
        }
    }
}
