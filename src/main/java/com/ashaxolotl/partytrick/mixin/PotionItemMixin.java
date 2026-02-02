package com.ashaxolotl.partytrick.mixin;

import com.ashaxolotl.partytrick.misc.PartyTags;
import dev.enjarai.trickster.spell.ItemTriggerHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {

    @Inject(
            method = "finishUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/advancement/criterion/ConsumeItemCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;)V"
            )
    )
    private void triggerItemSpell(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!(stack.isIn(PartyTags.GLUTTONS_HUNGER_BLACKLIST))) {
            ItemTriggerHelper.trigger((ServerPlayerEntity) user, stack, List.of());
        }
    }
}