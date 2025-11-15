package com.ashaxolotl.partytrick.spell.trick.entity;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;


// TODO MANA COST AND BLACKLIST FOR BOTH MOUNTS AND RIDERS
public class RideEntityTrick extends Trick<RideEntityTrick> {
    public RideEntityTrick() {
        super(Pattern.of(0, 1, 2), Signature.of(FragmentType.ENTITY, FragmentType.ENTITY, RideEntityTrick::run, FragmentType.VOID));
    }

    public VoidFragment run(SpellContext ctx, EntityFragment mountFragment, EntityFragment riderFragment) {
        var mount = mountFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        var rider = riderFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));

        if (!rider.startRiding(mount)) {
            throw new InvalidEntityBlunder(this);
        }

        return VoidFragment.INSTANCE;
    }
}