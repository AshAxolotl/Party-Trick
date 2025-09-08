package com.ashaxolotl.partytrick.spell.trick.block;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.blunder.ColorVectorInvalidRangeBlunder;
import dev.enjarai.trickster.block.LightBlockEntity;
import dev.enjarai.trickster.block.SpellColoredBlockEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlockInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;


import java.awt.*;
import java.util.Arrays;

public class ChangeColoredLightTrick extends Trick<ChangeColoredLightTrick> {
    public ChangeColoredLightTrick() {
        super(Pattern.of(6, 8, 4, 0, 1, 2, 0), Signature.of(FragmentType.VECTOR, FragmentType.VECTOR, ChangeColoredLightTrick::change, FragmentType.VECTOR));
    }

    public VectorFragment change(SpellContext ctx, VectorFragment pos, VectorFragment colorVector) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        if (colorVector.x() < 0 || colorVector.x() > 255 || colorVector.y() < 0 || colorVector.y() > 255 || colorVector.z() < 0 || colorVector.z() > 255) {
            throw new ColorVectorInvalidRangeBlunder(this, colorVector);
        }
        int[] color = new int[]{new Color((int) colorVector.x(), (int) colorVector.y(), (int) colorVector.z()).getRGB()};

        var entity = world.getBlockEntity(blockPos);
        if (entity instanceof LightBlockEntity lightEntity) {
            lightEntity.setColors(color);
            world.updateListeners(blockPos, lightEntity.getCachedState(), lightEntity.getCachedState(), 0); // update clients
        } else {
            throw new BlockInvalidBlunder(this);
        }

        return pos;
    }
}
