package com.ashaxolotl.partytrick.spell.trick.sound;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.fragment.Fragments;
import com.ashaxolotl.partytrick.spell.fragment.SoundFragment;
import dev.enjarai.trickster.ModSounds;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.InvalidEventBlunder;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class PlaySoundTrick extends Trick<PlaySoundTrick> {
    public PlaySoundTrick() {
        super(Pattern.of(0,1,2), Signature.of(Fragments.SOUND, FragmentType.NUMBER, FragmentType.NUMBER, PlaySoundTrick::play, Fragments.SOUND));
    }

    public SoundFragment play(SpellContext ctx, SoundFragment soundFragment, NumberFragment volume, NumberFragment pitch) throws BlunderException {
        var pos = ctx.source().getPos();
        ctx.source().getWorld().playSound(
                null,
                pos.x,
                pos.y,
                pos.z,
                soundFragment.sound(),
                SoundCategory.NEUTRAL,
                (float) volume.number(),
                (float) volume.number()
        );
        return soundFragment;
    }
}
