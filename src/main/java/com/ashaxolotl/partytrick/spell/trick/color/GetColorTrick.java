package com.ashaxolotl.partytrick.spell.trick.color;

import com.ashaxolotl.partytrick.misc.ColorHelper;
import dev.enjarai.trickster.block.SpellColoredBlockEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.*;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import io.vavr.control.Either;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import org.joml.Vector3d;

import java.awt.*;
import java.util.Optional;

public class GetColorTrick extends Trick<GetColorTrick> {
    public GetColorTrick() {
        super(Pattern.of(0,3,6,4,0,5,6), Signature.of(FragmentType.VECTOR, GetColorTrick::getBlockColor, FragmentType.VECTOR.or(FragmentType.ITEM_TYPE).optionalOfRet()));
        overload(Signature.of(FragmentType.ENTITY, GetColorTrick::getEntityColor, FragmentType.ITEM_TYPE.optionalOfRet()));
        overload(Signature.of(FragmentType.ITEM_TYPE, GetColorTrick::getItemColor, FragmentType.ITEM_TYPE.optionalOfRet()));
    }

    public Optional<Either<VectorFragment, ItemTypeFragment>> getBlockColor(SpellContext ctx, VectorFragment pos) throws BlunderException { // Either<ItemTypeFragment, VoidFragment>
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        var entity = world.getBlockEntity(blockPos);
        if (entity instanceof SpellColoredBlockEntity blockEntity) { // allows for any SpellColoredBlockEntity. What seems to be only lights by default
            Color color = new Color(blockEntity.getColors()[0]);
            return Optional.of(Either.left(new VectorFragment(new Vector3d(color.getRed(),color.getGreen(), color.getBlue())))); // TODO this currently doesnt work because of something on tricksters. Im to lazy to fix it here
        }

        String colorString = ColorHelper.getDyeColorString(Registries.BLOCK.getId(world.getBlockState(blockPos).getBlock()));
        if (colorString.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Either.right(new ItemTypeFragment(DyeItem.byColor(DyeColor.valueOf(colorString.toUpperCase())))));
    }

    public Optional<ItemTypeFragment> getEntityColor(SpellContext ctx, EntityFragment entityFragment) throws BlunderException {
        Entity entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));

        if (entity instanceof SheepEntity sheep) {
            return Optional.of(new ItemTypeFragment(DyeItem.byColor(sheep.getColor())));
        } else if ((entity instanceof WolfEntity wolf) && wolf.isTamed()) {
            ctx.useMana(this, 20);
            return Optional.of(new ItemTypeFragment(DyeItem.byColor(wolf.getCollarColor())));
        } else if (entity instanceof CatEntity cat  && cat.isTamed()) {
            ctx.useMana(this, 20);
            return Optional.of(new ItemTypeFragment(DyeItem.byColor(cat.getCollarColor())));
        }

        return Optional.empty();
    }

    public Optional<ItemTypeFragment> getItemColor(SpellContext ctx, ItemTypeFragment itemTypeFragment) throws BlunderException {
        String colorString = ColorHelper.getDyeColorString(Registries.ITEM.getId(itemTypeFragment.item()));
        if (colorString.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new ItemTypeFragment(DyeItem.byColor(DyeColor.valueOf(colorString.toUpperCase()))));
    }
}
