package com.ashaxolotl.partytrick.spell.trick.sound;

import com.ashaxolotl.partytrick.spell.fragment.Fragments;
import com.ashaxolotl.partytrick.spell.fragment.SoundFragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Optional;

public class MuffleSoundTrick extends Trick<MuffleSoundTrick> {
    public MuffleSoundTrick() {
        super(Pattern.of(6, 7, 8), Signature.of(FragmentType.ENTITY, FragmentType.NUMBER.optionalOfArg(), Fragments.SOUND.variadicOfArg().unpack().optionalOfArg(), MuffleSoundTrick::run, FragmentType.ENTITY));
    }

    // TODO how does the amount work?
    public EntityFragment run(SpellContext ctx, EntityFragment entityFragment, Optional<NumberFragment> amount, Optional<List<SoundFragment>> targets) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof ServerPlayerEntity serverPlayer) {
            // TODO
            return entityFragment;
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }
}

