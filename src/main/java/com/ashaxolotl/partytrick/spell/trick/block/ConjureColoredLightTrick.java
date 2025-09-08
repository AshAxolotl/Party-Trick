package com.ashaxolotl.partytrick.spell.trick.block;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.blunder.ColorVectorInvalidRangeBlunder;
import com.ibm.icu.text.MessagePattern;
import dev.enjarai.trickster.block.LightBlock;
import dev.enjarai.trickster.block.LightBlockEntity;
import dev.enjarai.trickster.block.ModBlocks;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlockOccupiedBlunder;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;

import java.awt.*;
import java.util.Optional;


public class ConjureColoredLightTrick extends Trick<ConjureColoredLightTrick> {
    public ConjureColoredLightTrick() {
        super(Pattern.of(8, 4, 0, 1, 2, 0), Signature.of(FragmentType.VECTOR, FragmentType.VECTOR.optionalOfArg(), ConjureColoredLightTrick::conjure, FragmentType.VECTOR));
    }

    public VectorFragment conjure(SpellContext ctx, VectorFragment pos, Optional<VectorFragment> optionalColor) throws BlunderException {

        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();
        expectCanBuild(ctx, blockPos);

        BlockState state = world.getBlockState(blockPos);
        var waterlogged = state.getFluidState().getFluid() == Fluids.WATER;
        var isWater = state.getFluidState().isIn(FluidTags.WATER);
        var dry = state.getFluidState().getFluid() == Fluids.EMPTY;

        // Blunder if block can't be replaced or if it has a fluid that isn't water
        if (!state.isReplaceable() || (!state.isAir() && !isWater && !dry)) {
            throw new BlockOccupiedBlunder(this, pos);
        }


        ctx.useMana(this, 20);
        world.setBlockState(blockPos, ModBlocks.LIGHT.getDefaultState().with(LightBlock.WATERLOGGED, waterlogged));
        if (optionalColor.isPresent()) {
            LightBlockEntity entity = (LightBlockEntity) world.getBlockEntity(blockPos);
            var colorVector = optionalColor.get();
            if (colorVector.x() < 0 || colorVector.x() > 255 || colorVector.y() < 0 || colorVector.y() > 255 || colorVector.z() < 0 || colorVector.z() > 255) {
                throw new ColorVectorInvalidRangeBlunder(this, colorVector);
            }
            int[] color = new int[]{new Color((int) colorVector.x(), (int) colorVector.y(), (int) colorVector.z()).getRGB()};
            entity.setColors(color);
        }

        return pos;
    }
}
