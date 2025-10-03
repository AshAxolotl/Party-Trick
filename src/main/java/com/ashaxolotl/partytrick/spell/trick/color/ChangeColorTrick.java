package com.ashaxolotl.partytrick.spell.trick.color;

import com.ashaxolotl.partytrick.misc.ColorHelper;
import com.ashaxolotl.partytrick.misc.Tags;
import com.ashaxolotl.partytrick.spell.blunder.ColorVectorInvalidRangeBlunder;
import dev.enjarai.trickster.block.SpellColoredBlockEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.*;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;

import java.awt.*;
import java.util.Optional;

public class ChangeColorTrick extends Trick<ChangeColorTrick> {
    private static final int manaCost = 40;

    public ChangeColorTrick() {
        super(Pattern.of(2,4,6,1,8,6), Signature.of(FragmentType.VECTOR, FragmentType.VECTOR, ChangeColorTrick::changeBlockWithVector, FragmentType.VECTOR));
        overload(Signature.of(FragmentType.VECTOR, FragmentType.ITEM_TYPE.optionalOfArg(), ChangeColorTrick::changeBlockWithDye, FragmentType.VECTOR));
        overload(Signature.of(FragmentType.SLOT, FragmentType.ITEM_TYPE.optionalOfArg(), ChangeColorTrick::changeSlot, FragmentType.SLOT));
        overload(Signature.of(FragmentType.ENTITY, FragmentType.ITEM_TYPE, ChangeColorTrick::changeEntity, FragmentType.ENTITY));

    }

    public VectorFragment changeBlockWithDye(SpellContext ctx, VectorFragment pos, Optional<ItemTypeFragment> optionalDyeItem) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();
        var blockState = world.getBlockState(blockPos);

        expectLoaded(ctx, blockPos);
        if (blockState.isIn(Tags.COLOR_BLOCK_BLACKLIST)) {
            throw new BlockInvalidBlunder(this);
        }

        DyeColor dyeColor;
        if (optionalDyeItem.isEmpty()) {
            dyeColor = null;
        } else {
            if (!(optionalDyeItem.get().item() instanceof DyeItem dye)) {
                throw new ItemInvalidBlunder(this);
            }
            dyeColor = dye.getColor();
        }


        BlockState newBlockState = ColorHelper.getBlockColorVariant(blockState, dyeColor);
        if (newBlockState.isAir()) {
            throw new BlockInvalidBlunder(this);
        }

        ctx.useMana(this, manaCost);

        NbtCompound blockEntityNbt = null;
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity != null) {
            blockEntityNbt = blockEntity.createNbt(world.getRegistryManager());
        }

        if (newBlockState != blockState) {
            world.setBlockState(blockPos, newBlockState);
        }

        if (blockEntity != null) {
            world.getBlockEntity(blockPos).read(blockEntityNbt, world.getRegistryManager());
        }

        return pos;
    }

    public SlotFragment changeSlot(SpellContext ctx, SlotFragment slot, Optional<ItemTypeFragment> optionalDyeItem) throws BlunderException {
        DyeColor dyeColor;
        if (optionalDyeItem.isEmpty()) {
            dyeColor = null;
        } else {
            if (!(optionalDyeItem.get().item() instanceof DyeItem dye)) {
                throw new ItemInvalidBlunder(this);
            }
            dyeColor = dye.getColor();
        }

        ItemStack itemStack = slot.reference(this, ctx);
        if (itemStack.isIn(Tags.COLOR_ITEM_BLACKLIST)) {
            throw new ItemInvalidBlunder(this);
        }

        ItemStack newItemStack = ColorHelper.getItemColorVariant(itemStack, dyeColor);
        Item newItem = newItemStack.getItem();
        if (newItem == Items.AIR) {
            throw new ItemInvalidBlunder(this);
        }

        ctx.useMana(this, manaCost * itemStack.getCount());
        if (itemStack.getItem() != newItem) {
            slot.setStack(newItemStack, this, ctx);
        }
        return slot;
    }

    public VectorFragment changeBlockWithVector(SpellContext ctx, VectorFragment pos, VectorFragment colorVector) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        if (colorVector.x() < 0 || colorVector.x() > 255 || colorVector.y() < 0 || colorVector.y() > 255 || colorVector.z() < 0 || colorVector.z() > 255) {
            throw new ColorVectorInvalidRangeBlunder(this, colorVector);
        }
        int[] color = new int[]{new Color((int) colorVector.x(), (int) colorVector.y(), (int) colorVector.z()).getRGB()};

        var entity = world.getBlockEntity(blockPos);
        if (entity instanceof SpellColoredBlockEntity blockEntity) { // allows for any SpellColoredBlockEntity. What seems to be only lights by default
            blockEntity.setColors(color);
            var chachedState = ((BlockEntity) blockEntity).getCachedState();
            world.updateListeners(blockPos, chachedState, chachedState, 0); // update clients
        } else {
            throw new BlockInvalidBlunder(this);
        }

        return pos;
    }

    public EntityFragment changeEntity(SpellContext ctx, EntityFragment entityFragment, ItemTypeFragment dyeItem) throws BlunderException {
        if (!(dyeItem.item() instanceof DyeItem dye)) {
            throw new ItemInvalidBlunder(this);
        }
        DyeColor dyeColor = dye.getColor();

        Entity entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        // wow this code sucks! :thumbsup:
        if (entity instanceof SheepEntity sheep) {
            ctx.useMana(this, manaCost);
            // WOLOLO easter egg
            if (sheep.getColor() == DyeColor.BLUE && dyeColor == DyeColor.RED) {
                sheep.playSound(SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO);
            }
            sheep.setColor(dyeColor);
        } else if ((entity instanceof WolfEntity wolf) && wolf.isTamed()) {
            ctx.useMana(this, manaCost);
            wolf.setCollarColor(dyeColor);
        } else if (entity instanceof CatEntity cat  && cat.isTamed()) {
            ctx.useMana(this, manaCost);
            cat.setCollarColor(dyeColor);
        } else {
            throw new InvalidEntityBlunder(this);
        }

        return entityFragment;
    }
}
