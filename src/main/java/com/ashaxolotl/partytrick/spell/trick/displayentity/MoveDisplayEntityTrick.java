package com.ashaxolotl.partytrick.spell.trick.displayentity;

import com.ashaxolotl.partytrick.entity.PartyDisplayEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.util.math.Vec3d;

public class MoveDisplayEntityTrick extends Trick<MoveDisplayEntityTrick> {
    public MoveDisplayEntityTrick() {
        super(Pattern.of(6, 7, 8), Signature.of(FragmentType.ENTITY, FragmentType.VECTOR, MoveDisplayEntityTrick::move, FragmentType.ENTITY));
    }

    // TODO make the movement smooth
    public EntityFragment move(SpellContext ctx, EntityFragment target, VectorFragment vector) {
        var entity = target.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof PartyDisplayEntity displayEntity) { // TODO THIS DOESNT WORK BLEGH
            displayEntity.setPosition(displayEntity.getPos().add(new Vec3d(vector.x(), vector.y(), vector.z())));
        } else {
            throw new InvalidEntityBlunder(this);
        }

        return target;
    }
}
