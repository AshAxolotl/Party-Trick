package com.ashaxolotl.partytrick.spell.trick.particle;

import com.ashaxolotl.partytrick.PartyTrick;
import dev.enjarai.trickster.particle.SpellParticleOptions;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;

import org.joml.Vector3d;

import java.awt.*;
import java.util.Optional;

public class LightParticleTrick extends Trick<LightParticleTrick> {
    public LightParticleTrick() {
        super(Pattern.of(1,2,3,4,5), Signature.of(FragmentType.VECTOR, FragmentType.VECTOR.optionalOfArg(), FragmentType.VECTOR.optionalOfArg(), LightParticleTrick::run, FragmentType.VECTOR));
    }

    public VectorFragment run(SpellContext ctx, VectorFragment position, Optional<VectorFragment> optionalDelta, Optional<VectorFragment> optionalColor) throws BlunderException {
        var delta = optionalDelta.orElse(new VectorFragment(new Vector3d (0, 0,0)));
        int color = 0xffffff; // Color of SPELL_WHITE particle
        if (optionalColor.isPresent()) {
            var colorVector = optionalColor.get();
            color = new Color(Math.min((int) colorVector.x(), 225),Math.min((int) colorVector.y(), 225),Math.min((int) colorVector.z(), 225)).getRGB();
        }

        ctx.source().getWorld().spawnParticles(
                new SpellParticleOptions(color), position.x(), position.y(), position.z(),
                    0, delta.x(), delta.y(), delta.z(), 1
            );

        return position;
    }
}

