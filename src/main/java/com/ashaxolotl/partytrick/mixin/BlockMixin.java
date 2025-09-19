package com.ashaxolotl.partytrick.mixin;

import dev.enjarai.trickster.block.ModularSpellConstructBlock;
import dev.enjarai.trickster.block.SpellConstructBlock;
import dev.enjarai.trickster.spell.ItemTriggerHelper;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(
            method = "onPlaced",
            at = @At(
                    value = "HEAD"
            )
    )
    private void triggerItemSpell(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        // is there a better way to check if its a spell construct?
        if (placer instanceof ServerPlayerEntity && !(Block.getBlockFromItem(itemStack.getItem()) instanceof ModularSpellConstructBlock || Block.getBlockFromItem(itemStack.getItem()) instanceof SpellConstructBlock)) {
            ItemTriggerHelper.trigger((ServerPlayerEntity) placer, itemStack, List.of(VectorFragment.of(pos)));
        }
    }
}