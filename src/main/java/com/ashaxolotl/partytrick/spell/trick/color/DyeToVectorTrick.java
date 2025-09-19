package com.ashaxolotl.partytrick.spell.trick.color;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.ItemInvalidBlunder;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.ItemTypeFragment;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.item.DyeItem;
import org.joml.Vector3d;

import java.awt.*;

public class DyeToVectorTrick extends Trick<DyeToVectorTrick> {
    public DyeToVectorTrick() {
        super(Pattern.of(1,6,8,1,4,7), Signature.of(FragmentType.ITEM_TYPE, DyeToVectorTrick::getDyeFromVector, FragmentType.VECTOR));
    }

    public VectorFragment getDyeFromVector(SpellContext ctx, ItemTypeFragment dyeItem) throws BlunderException {

        if (!(dyeItem.item() instanceof DyeItem dye)) {
            throw new ItemInvalidBlunder(this);
        }
        Color color = new Color(dye.getColor().getEntityColor());
        return new VectorFragment(new Vector3d(color.getRed(), color.getGreen(), color.getBlue()));
    }
}
