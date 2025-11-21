package com.ashaxolotl.partytrick.spell.trick.entity.query;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.trick.entity.query.AbstractEntityQueryTrick;
import net.minecraft.entity.Entity;

import java.util.Optional;

public class GetVehicleTrick extends AbstractEntityQueryTrick<Entity, Optional<EntityFragment>> {
    public GetVehicleTrick() {
        super(Pattern.of(7, 8, 4, 3, 6, 8, 5, 4), Entity.class, FragmentType.ENTITY.optionalOfRet());
    }

    @Override
    protected Optional<EntityFragment> run(SpellContext ctx, Entity entity) throws BlunderException {
        var vehicle = entity.getVehicle();
        if (vehicle == null) {
            return Optional.empty();
        }

        return Optional.of(EntityFragment.from(vehicle));
    }
}
