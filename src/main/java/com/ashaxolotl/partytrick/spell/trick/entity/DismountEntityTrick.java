package com.ashaxolotl.partytrick.spell.trick.entity;

import com.ashaxolotl.partytrick.misc.Tags;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;

public class DismountEntityTrick extends Trick<DismountEntityTrick> {
    public DismountEntityTrick() {
        super(Pattern.of(1, 2, 4, 7, 8, 4, 3, 6, 8, 5, 4), Signature.of(FragmentType.ENTITY, DismountEntityTrick::run, FragmentType.ENTITY));
    }

    public EntityFragment run(SpellContext ctx, EntityFragment entityFragment) {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        var vehicle = entity.getVehicle();
        if (vehicle == null || vehicle.getType().isIn(Tags.DISMOUNT_BLACKLIST)) {
            throw new InvalidEntityBlunder(this);
        }

        ctx.useMana(this, (float) Math.pow(entity.distanceTo(vehicle), 2));
        entity.stopRiding();

        return entityFragment;
    }
}