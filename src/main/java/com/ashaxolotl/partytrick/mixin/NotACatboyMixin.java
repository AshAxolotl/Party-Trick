package com.ashaxolotl.partytrick.mixin;

import dev.enjarai.trickster.spell.trick.entity.CatCurseTrick;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.UUID;

// I SWEAR I AM NOT A CATBOY TRUST!!!
@Mixin(CatCurseTrick.class)
public class NotACatboyMixin {
    @Inject(method = "restricted()Ljava/util/Set;", at = @At("RETURN"), cancellable = true, remap = false)
    private void notacat(CallbackInfoReturnable<Set<UUID>> cir) {
        Set<UUID> set = new java.util.HashSet<>(Set.of());
        set.addAll(cir.getReturnValue());
        set.add(UUID.fromString("1a6f36ff-070f-43a4-8de9-d9e3502d9719")); // UUID OF SOMEONE THAT ISNT A CATBOY
        cir.setReturnValue(set);
    }
}
