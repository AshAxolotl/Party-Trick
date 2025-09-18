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
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.EitherRetType;
import dev.enjarai.trickster.spell.type.RetType;
import dev.enjarai.trickster.spell.type.Signature;
import io.vavr.control.Either;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;

import java.awt.*;
import java.util.Optional;
import java.util.Vector;

public class GetColorTrick extends Trick<GetColorTrick> {
    public GetColorTrick() {
        super(Pattern.of(6,7,8), Signature.of(FragmentType.VECTOR, GetColorTrick::getBlockColor, FragmentType.ITEM_TYPE.or(FragmentType.VECTOR).optionalOfRet())); // .or(FragmentType.VOID)
    }

    public Optional<Either<ItemTypeFragment, VectorFragment>> getBlockColor(SpellContext ctx, VectorFragment pos) throws BlunderException { // Either<ItemTypeFragment, VoidFragment>
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        var entity = world.getBlockEntity(blockPos);
        if (entity instanceof SpellColoredBlockEntity blockEntity) { // allows for any SpellColoredBlockEntity. What seems to be only lights by default
            Color color = new Color(blockEntity.getColors()[0]);
            return Optional.of(Either.right(new VectorFragment(new Vector3d(color.getRed(),color.getGreen(), color.getBlue()))));
        }

        String colorString = ColorHelper.getDyeColorString(Registries.BLOCK.getId(world.getBlockState(blockPos).getBlock()));
        if (colorString.isEmpty()) {
            return Optional.empty();
        }

        Optional test = Optional.of(Either.left(new ItemTypeFragment(DyeItem.byColor(DyeColor.valueOf(colorString.toUpperCase())))));
        PartyTrick.LOGGER.info(test.get().toString());
        return test;
    }
}
