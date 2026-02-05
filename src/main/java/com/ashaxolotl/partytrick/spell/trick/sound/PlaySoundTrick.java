package com.ashaxolotl.partytrick.spell.trick.sound;

import com.ashaxolotl.partytrick.spell.fragment.FragmentTypes;
import com.ashaxolotl.partytrick.spell.fragment.SoundFragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

import java.util.List;
import java.util.Optional;

public class PlaySoundTrick extends Trick<PlaySoundTrick> {
    public PlaySoundTrick() {
        super(Pattern.of(2, 1, 4, 7, 6, 4), Signature.of(FragmentType.VECTOR, FragmentTypes.SOUND, FragmentType.NUMBER.optionalOfArg(), FragmentType.NUMBER.optionalOfArg(), FragmentType.ENTITY.variadicOfArg().unpack().optionalOfArg(), PlaySoundTrick::play, FragmentType.VECTOR));
    }

    public VectorFragment play(SpellContext ctx, VectorFragment location, SoundFragment soundFragment, Optional<NumberFragment> optionalVolume, Optional<NumberFragment> optionalPitch, Optional<List<EntityFragment>> targets) throws BlunderException {
        ctx.useMana(this, 2);
        float volume = (float) optionalVolume.orElse(new NumberFragment(1)).number();
        float pitch = (float) optionalPitch.orElse(new NumberFragment(1)).number();


        if (targets.isEmpty()) {
            ctx.source().getWorld().playSound(
                    null,
                    location.x(), location.y(), location.z(),
                    soundFragment.sound(), SoundCategory.MASTER,
                    volume, pitch
            );
        } else {
            var sound = Registries.SOUND_EVENT.getEntry(soundFragment.sound());
            targets.get().forEach(t -> {
                if (t.getEntity(ctx).orElse(null) instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(
                            sound, SoundCategory.MASTER,
                            location.x(), location.y(), location.z(),
                            volume, pitch,
                            ctx.source().getWorld().random.nextLong()));
                }
            });
        }
        return location;
    }

}
