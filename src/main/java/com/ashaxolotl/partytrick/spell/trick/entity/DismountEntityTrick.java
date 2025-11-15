package com.ashaxolotl.partytrick.spell.trick.entity;

import com.ashaxolotl.partytrick.PartyTrick;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public class DismountEntityTrick extends Trick<DismountEntityTrick> {
    public DismountEntityTrick() {
        super(Pattern.of(6, 7, 8), Signature.of(FragmentType.ENTITY, DismountEntityTrick::run, FragmentType.ENTITY));
    }

    public EntityFragment run(SpellContext ctx, EntityFragment entityFragment) {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        var vehicle = entity.getVehicle();
        if (vehicle == null) {
            throw new InvalidEntityBlunder(this);
        }

        entity.stopRiding();

        return entityFragment;
    }
}