package com.ashaxolotl.partytrick.spell.trick.misc;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.type.Signature;

public class IsEvenTrick extends Trick<IsEvenTrick> {
    public IsEvenTrick() {
        super(Pattern.of(3, 4, 5), Signature.of(FragmentType.NUMBER, IsEvenTrick::activate, FragmentType.BOOLEAN));
    }

    public BooleanFragment activate(SpellContext ctx, NumberFragment number) throws BlunderException {
        return BooleanFragment.of(number.number() % 2 == 0);
    }
}