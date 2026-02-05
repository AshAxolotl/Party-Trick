package com.ashaxolotl.partytrick.spell.trick.sound;

import com.ashaxolotl.partytrick.cca.PartyEntityComponents;
import com.ashaxolotl.partytrick.spell.fragment.FragmentTypes;
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
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuffleSoundTrick extends Trick<MuffleSoundTrick> {
    public MuffleSoundTrick() {
        super(Pattern.of(0, 3, 6, 0, 1, 2, 5, 8, 2), Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, FragmentTypes.SOUND.variadicOfArg().unpack().optionalOfArg(), MuffleSoundTrick::run, FragmentType.ENTITY));
    }

    public EntityFragment run(SpellContext ctx, EntityFragment entityFragment, NumberFragment amount, Optional<List<SoundFragment>> sounds) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof ServerPlayerEntity serverPlayer) {
            ctx.useMana(this, 10);

            if (sounds.isEmpty()) {
                serverPlayer.getComponent(PartyEntityComponents.MUFFLES).addMuffle(Optional.empty(), amount.number());
            } else {
                List<Identifier> list = new ArrayList<>();
                sounds.get().forEach(s -> list.add(s.sound().getId()));
                serverPlayer.getComponent(PartyEntityComponents.MUFFLES).addMuffle(Optional.of(list), amount.number());
            }
            return entityFragment;
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }
}

