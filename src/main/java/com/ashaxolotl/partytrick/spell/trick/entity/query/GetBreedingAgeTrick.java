package com.ashaxolotl.partytrick.spell.trick.entity.query;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.trick.entity.query.AbstractEntityQueryTrick;
import net.minecraft.entity.passive.PassiveEntity;

public class GetBreedingAgeTrick extends AbstractEntityQueryTrick<PassiveEntity, NumberFragment> {
    public GetBreedingAgeTrick() {
        super(Pattern.of(3, 4, 5), PassiveEntity.class, FragmentType.NUMBER);
    }

    @Override
    protected NumberFragment run(SpellContext ctx, PassiveEntity entity) throws BlunderException {
        return new NumberFragment(entity.getBreedingAge());
    }
}
