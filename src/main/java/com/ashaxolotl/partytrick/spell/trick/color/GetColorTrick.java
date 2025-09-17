package com.ashaxolotl.partytrick.spell.trick.color;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.misc.ColorHelper;
import dev.enjarai.trickster.Trickster;
import dev.enjarai.trickster.block.SpellColoredBlockEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlockInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.ItemTypeFragment;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.joml.Vector3d;

import java.util.Optional;

public class GetColorTrick extends Trick<GetColorTrick> {
    public GetColorTrick() {
        // TODO this doesnt work
        super(Pattern.of(6,7,8), Signature.of(FragmentType.VECTOR, GetColorTrick::getBlockColorDye, FragmentType.ITEM_TYPE.optionalOfRet()));
        overload(Signature.of(FragmentType.VECTOR, GetColorTrick::getBlockColorVector, FragmentType.VECTOR.optionalOfRet()));
    }


    public Optional<VectorFragment> getBlockColorVector(SpellContext ctx, VectorFragment pos) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        var entity = world.getBlockEntity(blockPos);
        if (entity instanceof SpellColoredBlockEntity blockEntity) { // allows for any SpellColoredBlockEntity. What seems to be only lights by default
            return Optional.of(new VectorFragment(new Vector3d(0,0,0)));
        } else {
            return Optional.empty();
        }
    }

    // TODO make it so it can also return a color vector!
    public Optional<ItemTypeFragment> getBlockColorDye(SpellContext ctx, VectorFragment pos) throws BlunderException {
        String colorString = ColorHelper.getDyeColorString(Registries.BLOCK.getId(ctx.source().getWorld().getBlockState(pos.toBlockPos()).getBlock()));
        // TODO THIS SHOULD PROBABLY BE IMPROVED
        try {
            return Optional.of(new ItemTypeFragment(DyeItem.byColor(DyeColor.valueOf(colorString.toUpperCase()))));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
