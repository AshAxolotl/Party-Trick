package com.ashaxolotl.partytrick.spell.trick.displayentity;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.entity.PartyBlockDisplayEntity;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class MoveDisplayEntityTrick extends Trick<MoveDisplayEntityTrick> {
    public MoveDisplayEntityTrick() {
        super(Pattern.of(6, 7, 8), Signature.of(FragmentType.ENTITY, FragmentType.VECTOR, FragmentType.BOOLEAN, MoveDisplayEntityTrick::move, FragmentType.ENTITY));
    }

    // TODO how to change the potion AFTER it got a transformation?
    public EntityFragment move(SpellContext ctx, EntityFragment target, VectorFragment vector, BooleanFragment booleanFragment) {
        var entity = target.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof PartyBlockDisplayEntity displayEntity) {
            displayEntity.setStartInterpolation(0);
            var test = displayEntity.getTransformation(displayEntity.getDataTracker());
            if (booleanFragment.asBoolean()) {
                displayEntity.setTransformation(new AffineTransformation(test.getTranslation().add(new Vector3f((float) vector.x(), (float) vector.y(), (float) vector.z())), test.getLeftRotation(), test.getScale(), test.getRightRotation()));
            } else {
                displayEntity.setPosition(displayEntity.getPos().add(new Vec3d(vector.x(), vector.y(), vector.z())));
            }

            displayEntity.setInterpolationDuration(10);
//            displayEntity.timer += 10;
        } else {
            throw new InvalidEntityBlunder(this);
        }

        return target;
    }
}
