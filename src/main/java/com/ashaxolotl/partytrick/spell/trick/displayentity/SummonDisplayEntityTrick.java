package com.ashaxolotl.partytrick.spell.trick.displayentity;

import com.ashaxolotl.partytrick.entity.PartyBlockDisplayEntity;
import com.ashaxolotl.partytrick.entity.PartyEntities;
import dev.enjarai.trickster.particle.ModParticles;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlockInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.MissingItemBlunder;
import dev.enjarai.trickster.spell.fragment.*;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;

import java.util.Optional;

public class SummonDisplayEntityTrick extends Trick<SummonDisplayEntityTrick> {
    public SummonDisplayEntityTrick() {
        super(Pattern.of(3, 4, 5), Signature.of(FragmentType.VECTOR, SummonDisplayEntityTrick::summon, FragmentType.ENTITY));
        overload(Signature.of(FragmentType.SLOT, FragmentType.VECTOR, SummonDisplayEntityTrick::summon, FragmentType.ENTITY));
    }

    public EntityFragment summon(SpellContext ctx, SlotFragment slot, VectorFragment pos) {
        var stack = ctx.getStack(this, Optional.of(slot), item -> !item.isEmpty()).orElseThrow(() -> new MissingItemBlunder(this));
        var world = ctx.source().getWorld();

        try {
            DisplayEntity.ItemDisplayEntity itemDisplay = EntityType.ITEM_DISPLAY.create(world);
            itemDisplay.setItemStack(stack);
            itemDisplay.setPosition(pos.x(), pos.y(), pos.z());

            world.spawnEntity(itemDisplay);
            return EntityFragment.from(itemDisplay);
        } catch (Throwable err) {
            ctx.source().offerOrDropItem(stack);
            throw err;
        }
    }

    public EntityFragment summon(SpellContext ctx, VectorFragment pos) {
        var world = ctx.source().getWorld();
        var blockPos = pos.toBlockPos();
        var state = world.getBlockState(blockPos);

        if (state.isAir() || state.getBlock() instanceof FluidBlock) { // || state.isIn(ModBlocks.CANNOT_LEVITATE) TODO TAG, TODO can it be a fluid?
            throw new BlockInvalidBlunder(this);
        }
        expectCanBuild(ctx, blockPos);

        world.setBlockState(blockPos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
        var particlePos = blockPos.toCenterPos();
        ctx.source().getWorld().spawnParticles(
                ModParticles.PROTECTED_BLOCK, particlePos.x, particlePos.y, particlePos.z,
                1, 0, 0, 0, 0
        );

        PartyBlockDisplayEntity blockDisplayEntity = PartyEntities.PARTY_BLOCK_DISPLAY.create(world);
        blockDisplayEntity.setBlockState(state);
        blockDisplayEntity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        // TODO KEEP NBT DATA??

        world.spawnEntity(blockDisplayEntity);
        return EntityFragment.from(blockDisplayEntity);
    }
}
