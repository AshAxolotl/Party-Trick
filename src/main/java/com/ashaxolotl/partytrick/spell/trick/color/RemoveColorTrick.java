package com.ashaxolotl.partytrick.spell.trick.color;

import com.ashaxolotl.partytrick.misc.ColorHelper;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlockInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.ItemInvalidBlunder;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.ItemTypeFragment;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeItem;
import net.minecraft.util.DyeColor;

// TODO REMOVE THIS.
public class RemoveColorTrick extends Trick<RemoveColorTrick>{
    public RemoveColorTrick() {
        super(Pattern.of(3,4,5,6), Signature.of(FragmentType.VECTOR, RemoveColorTrick::changeBlock, FragmentType.VECTOR));
    }

    public VectorFragment changeBlock(SpellContext ctx, VectorFragment pos) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();
        var blockState = world.getBlockState(blockPos);

        expectLoaded(ctx, blockPos);
        // TODO could block entities be implemented?
        if (blockState.hasBlockEntity()) {
            throw new BlockInvalidBlunder(this);
        }

        BlockState newBlockState = ColorHelper.getBlockColorVariant(blockState, null);
        if (newBlockState.isAir()) {
            throw new BlockInvalidBlunder(this);
        }

        ctx.useMana(this, 20);

        if (newBlockState != blockState) {
            world.setBlockState(blockPos, newBlockState);
        }

        return pos;
    }
}
