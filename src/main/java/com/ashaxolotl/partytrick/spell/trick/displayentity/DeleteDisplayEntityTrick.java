package com.ashaxolotl.partytrick.spell.trick.displayentity;

import com.ashaxolotl.partytrick.entity.PartyDisplayEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlockOccupiedBlunder;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;

public class DeleteDisplayEntityTrick  extends Trick<DeleteDisplayEntityTrick> {
    public DeleteDisplayEntityTrick() {
        super(Pattern.of(0, 1, 2), Signature.of(FragmentType.ENTITY, DeleteDisplayEntityTrick::delete, FragmentType.VOID));
    }

    public VoidFragment delete(SpellContext ctx, EntityFragment target) {
        var entity = target.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        var world = ctx.source().getWorld();

        // Block
        if (entity instanceof PartyDisplayEntity.BlockDisplayEntity blockDisplay) {
            var blockPos = blockDisplay.getBlockPos();
            var state = blockDisplay.getBlockState();

            expectCanBuild(ctx, blockPos);
            if (!world.getBlockState(blockPos).isReplaceable()) {
                throw new BlockOccupiedBlunder(this, new VectorFragment(blockPos.toCenterPos().toVector3d()));
            }

            world.setBlockState(blockPos, state);
            blockDisplay.remove(Entity.RemovalReason.DISCARDED);
        }

        // Item
        else if (entity instanceof PartyDisplayEntity.ItemDisplayEntity itemDisplay) {
            var stack = itemDisplay.getItemStack();
            var itemEntity = new ItemEntity(world, itemDisplay.getX(), itemDisplay.getY(), itemDisplay.getZ(), stack);
            itemEntity.setPickupDelay(10);
            world.spawnEntity(itemEntity);
            itemDisplay.remove(Entity.RemovalReason.DISCARDED);
        } else {
            throw new InvalidEntityBlunder(this);
        }
        return VoidFragment.INSTANCE;
    }
}