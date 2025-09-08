package com.ashaxolotl.partytrick.spell.blunder;

import dev.enjarai.trickster.spell.blunder.TrickBlunderException;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import net.minecraft.text.MutableText;

public class ColorVectorInvalidRangeBlunder extends TrickBlunderException {
    public final VectorFragment pos;

    public ColorVectorInvalidRangeBlunder(Trick<?> source, VectorFragment pos) {
        super(source);
        this.pos = pos;
    }

    @Override
    public MutableText createMessage() {
        return super.createMessage().append("Vector ").append(pos.asFormattedText()).append(" is not a valid RGB color vector");
    }
}
