package com.ashaxolotl.partytrick.spell.trick.entity.query;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.cca.PartyEntityComponents;
import com.ashaxolotl.partytrick.spell.fragment.FragmentTypes;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.*;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.RetType;
import dev.enjarai.trickster.spell.type.Signature;
import io.vavr.control.Either;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.EulerAngle;
import org.joml.Vector3d;

import java.util.Optional;

public class GetArmorStandStateTrick extends Trick<GetArmorStandStateTrick> {
    public GetArmorStandStateTrick() {
        super(Pattern.of(1, 0, 2, 1, 6, 7, 8, 1, 4, 7), Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, GetArmorStandStateTrick::get, FragmentType.BOOLEAN.or(FragmentType.NUMBER.or(FragmentType.VECTOR))));
    }

    public Either<BooleanFragment, Either<NumberFragment, VectorFragment>> get(SpellContext ctx, EntityFragment entityFragment, NumberFragment numberFragment) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof ArmorStandEntity armorStand) {
            return switch (numberFragment.asInt()) {
                case 0 -> eulerToVector(armorStand.getHeadRotation());
                case 1 -> eulerToVector(armorStand.getBodyRotation());
                case 2 -> eulerToVector(armorStand.getLeftArmRotation());
                case 3 -> eulerToVector(armorStand.getRightArmRotation());
                case 4 -> eulerToVector(armorStand.getLeftLegRotation());
                case 5 -> eulerToVector(armorStand.getRightLegRotation());
                case 6 -> Either.right(Either.left(new NumberFragment(armorStand.getYaw())));
                case 7 -> Either.right(Either.left(new NumberFragment(getScale(armorStand))));
                case 8 -> Either.left(BooleanFragment.of(armorStand.hasNoGravity()));
                case 9 -> Either.left(BooleanFragment.of(armorStand.shouldHideBasePlate()));
                case 10 -> Either.left(BooleanFragment.of(armorStand.isInvisible()));
                case 11 -> Either.left(BooleanFragment.of(armorStand.shouldShowArms()));
                case 12 -> Either.left(BooleanFragment.of(armorStand.isSmall()));
                case 13 -> Either.left(BooleanFragment.of(armorStand.isCustomNameVisible()));
                case 14 -> Either.left(BooleanFragment.of(armorStand.isInvulnerable() && armorStand.disabledSlots == 4144959)); // "protected" property being both Invulnerable and DisabledSlots = 4144959
                default -> throw (numberFragment.asInt() < 0) ? new NumberTooSmallBlunder(this, 0) : new NumberTooLargeBlunder(this, 14);
            };
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }

    private static Either<BooleanFragment, Either<NumberFragment, VectorFragment>> eulerToVector(EulerAngle eulerAngle) {
        return Either.right(Either.right(new VectorFragment(new Vector3d(eulerAngle.getPitch(), eulerAngle.getYaw(), eulerAngle.getRoll()))));
    }

    private static double getScale(ArmorStandEntity armorStand) {
        var modifier = armorStand.getAttributeInstance(EntityAttributes.GENERIC_SCALE).getModifier(PartyTrick.id("armor_stand_scale"));
        return (modifier == null) ? 1.0 : modifier.value() + 1.0;
    }
}