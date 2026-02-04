package com.ashaxolotl.partytrick.spell.trick.entity.query;

import com.ashaxolotl.partytrick.cca.PartyEntityComponents;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.*;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.RetType;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.EulerAngle;
import org.joml.Vector3d;

public class GetArmorStandStateTrick extends Trick<GetArmorStandStateTrick> {
    public GetArmorStandStateTrick() {
        super(Pattern.of(0, 1, 2, 3, 4, 5), Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, GetArmorStandStateTrick::get, RetType.ANY));
    }

    public Fragment get(SpellContext ctx, EntityFragment entityFragment, NumberFragment numberFragment) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof ArmorStandEntity armorStand) {
            return switch (numberFragment.asInt()) {
                case 0 -> eulerToVector(armorStand.getHeadRotation());
                case 1 -> eulerToVector(armorStand.getBodyRotation());
                case 2 -> eulerToVector(armorStand.getLeftArmRotation());
                case 3 -> eulerToVector(armorStand.getRightArmRotation());
                case 4 -> eulerToVector(armorStand.getLeftLegRotation());
                case 5 -> eulerToVector(armorStand.getRightLegRotation());
                case 6 -> new NumberFragment(armorStand.getYaw());
                case 7 -> new NumberFragment(PartyEntityComponents.ARMOR_STAND_SCALE.get(armorStand).getScale());
                case 8 -> BooleanFragment.of(armorStand.hasNoGravity());
                case 9 -> BooleanFragment.of(armorStand.isInvisible());
                case 10 -> BooleanFragment.of(armorStand.shouldHideBasePlate());
                case 11 -> BooleanFragment.of(armorStand.shouldShowArms());
                case 12 -> BooleanFragment.of(armorStand.isSmall());
                case 13 -> BooleanFragment.of(armorStand.isCustomNameVisible());
                case 14 -> BooleanFragment.of(armorStand.isInvulnerable() && armorStand.disabledSlots == 4144959); // "protected" property being both Invulnerable and DisabledSlots = 4144959
                default -> throw (numberFragment.asInt() < 0) ? new NumberTooSmallBlunder(this, 0) : new NumberTooLargeBlunder(this, 14);
            };
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }

    private static VectorFragment eulerToVector(EulerAngle eulerAngle) {
        return new VectorFragment(new Vector3d(eulerAngle.getPitch(), eulerAngle.getYaw(), eulerAngle.getRoll()));
    }
}