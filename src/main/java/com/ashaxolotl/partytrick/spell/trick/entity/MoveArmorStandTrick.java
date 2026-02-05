package com.ashaxolotl.partytrick.spell.trick.entity;

import com.ashaxolotl.partytrick.misc.PartyTags;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.EntityInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
public class MoveArmorStandTrick extends Trick<MoveArmorStandTrick> {
    public MoveArmorStandTrick() {
        super(Pattern.of(1, 0, 2, 1, 6, 3, 1, 8, 5, 1, 4, 7), Signature.of(FragmentType.ENTITY, FragmentType.VECTOR, MoveArmorStandTrick::move, FragmentType.ENTITY));
    }

    public EntityFragment move(SpellContext ctx, EntityFragment entityFragment, VectorFragment vector) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        var realEntity = (entity.getVehicle() != null) ? entity.getVehicle() : entity;
        if (!(realEntity.getType().isIn(PartyTags.ARMOR_STAND_MOVE_WHITELIST))
                || realEntity.getPassengerList().stream().anyMatch(passenger -> !(passenger.getType().isIn(PartyTags.ARMOR_STAND_MOVE_WHITELIST)))
            ) {
            throw new EntityInvalidBlunder(this);
        }

        ctx.useMana(this, (float) vector.vector().length());
        realEntity.setPosition(realEntity.getPos().add(vector.x(), vector.y(), vector.z()));

        return entityFragment;
    }
}
