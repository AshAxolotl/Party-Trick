package com.ashaxolotl.partytrick.spell.trick.sound;

import com.ashaxolotl.partytrick.misc.ServerSoundStorage;
import com.ashaxolotl.partytrick.spell.fragment.Fragments;
import com.ashaxolotl.partytrick.spell.fragment.SoundFragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class GetSoundTrick extends Trick<GetSoundTrick> {
    public GetSoundTrick() {
        super(Pattern.of(1, 2, 5, 8, 7, 6, 4, 1), Signature.of(FragmentType.ENTITY, GetSoundTrick::get, Fragments.SOUND.listOfRet()));
    }

    public List<SoundFragment> get(SpellContext ctx, EntityFragment entityFragment) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof ServerPlayerEntity player) {
            var sounds = ServerSoundStorage.INSTANCE.getPlayerSounds(player.getUuid());
            if (sounds == null) {
                return List.of();
            }
            List<SoundFragment> list = new ArrayList<>();
            sounds.forEach(s -> {
                var sound = Registries.SOUND_EVENT.get(s);
                if (sound != null) {
                    list.add(new SoundFragment(sound));
                }
            });
            return list;
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }
}
