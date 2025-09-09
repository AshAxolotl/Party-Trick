package com.ashaxolotl.partytrick.mixin;

import dev.enjarai.trickster.spell.ItemTriggerHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(
            method = "finishUsing",
            at = @At(
                    value = "HEAD"
            )
    )
    private void triggerItemSpell(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (user instanceof ServerPlayerEntity) {
            ItemTriggerHelper.trigger((ServerPlayerEntity) user, stack, List.of());
        }
    }
}