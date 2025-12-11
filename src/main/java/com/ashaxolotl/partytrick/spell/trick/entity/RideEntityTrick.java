package com.ashaxolotl.partytrick.spell.trick.entity;

import com.ashaxolotl.partytrick.misc.PartyTags;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;


public class RideEntityTrick extends Trick<RideEntityTrick> {
    public RideEntityTrick() {
        super(Pattern.of(7, 6, 4, 1, 0, 4, 3, 6, 8, 5, 4), Signature.of(FragmentType.ENTITY, FragmentType.ENTITY, RideEntityTrick::run, FragmentType.VOID));
    }

    public VoidFragment run(SpellContext ctx, EntityFragment riderFragment, EntityFragment mountFragment) {
        var rider = riderFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        var mount = mountFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));

        if (!(rider.canStartRiding(mount) && mount.canAddPassenger(rider)) || mount.getType().isIn(PartyTags.MOUNT_BLACKLIST) || rider.getType().isIn(PartyTags.RIDER_BLACKLIST)) {
            throw new InvalidEntityBlunder(this);
        }

        ctx.useMana(this, 20 + (float) Math.pow(1.35, mount.distanceTo(rider)));
        rider.startRiding(mount);

        return VoidFragment.INSTANCE;
    }
}