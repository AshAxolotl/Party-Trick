package com.ashaxolotl.partytrick.spell.trick.particle;

import com.ashaxolotl.partytrick.PartyTrick;
import dev.enjarai.trickster.particle.ModParticles;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import org.joml.Vector3d;

import java.util.Optional;

public class LightParticleTrick extends Trick<LightParticleTrick> {
    public LightParticleTrick() {
        super(Pattern.of(1,2,3,4,5), Signature.of(FragmentType.VECTOR, FragmentType.VECTOR.optionalOfArg(), LightParticleTrick::run, FragmentType.VECTOR));
    }

    public VectorFragment run(SpellContext ctx, VectorFragment position, Optional<VectorFragment> optionalDelta) throws BlunderException {
        var delta = optionalDelta.orElse(new VectorFragment(new Vector3d (0, 0,0)));
        PartyTrick.LOGGER.info(delta.toString());
        ctx.source().getWorld().spawnParticles(
                    ModParticles.SPELL_WHITE, position.x(), position.y(), position.z(),
                    1, delta.x(), delta.y(), delta.z(), 0 // TODO: when the speed is 1 it flies in random directions WHAT?!
            );

        return position;
    }
}

