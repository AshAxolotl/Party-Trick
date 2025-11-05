package com.ashaxolotl.partytrick.spell.trick.sound;

import com.ashaxolotl.partytrick.net.ModNetworking;
import com.ashaxolotl.partytrick.net.RequestSoundsPacket;
import com.ashaxolotl.partytrick.spell.fragment.Fragments;
import dev.enjarai.trickster.cca.MessageHandlerComponent;
import dev.enjarai.trickster.spell.EvaluationResult;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.execution.executor.MessageListenerSpellExecutor;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import io.wispforest.owo.network.OwoNetChannel;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;
import java.util.UUID;

public class GetSoundTrick extends Trick<GetSoundTrick> {
    public GetSoundTrick() {
        super(Pattern.of(3,4,5), Signature.of(FragmentType.ENTITY, GetSoundTrick::get, Fragments.SOUND.listOfRet().thisFunctionExistsSolelyForMessageListeningOnItemsBecauseWeAlreadyHadAnAbstractionForItAndWeReallyDontWantToReworkItSoThisWillHaveToDoHonestly()));
    }

    public EvaluationResult get(SpellContext ctx, EntityFragment entityFragment) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));

        if (entity instanceof ServerPlayerEntity player) {
            var uuid = player.getUuid();
            ModNetworking.CHANNEL.serverHandle(player).send(new RequestSoundsPacket(uuid));
            return new MessageListenerSpellExecutor(ctx.state(), Optional.empty(), Optional.of(new MessageHandlerComponent.Key.Channel(uuid)));
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }

}
