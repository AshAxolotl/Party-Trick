package com.ashaxolotl.partytrick.spell.trick.entity;

import com.ashaxolotl.partytrick.cca.PartyEntityComponents;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.*;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.MathHelper;

public class SetArmorStandStateTrick extends Trick<SetArmorStandStateTrick> {

    final static int MANA_COST = 1; // TODO think of the magic number

    public SetArmorStandStateTrick() {
        super(Pattern.of(3, 4, 5), Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, FragmentType.BOOLEAN, SetArmorStandStateTrick::setBoolean, FragmentType.ENTITY));
        overload(Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, FragmentType.NUMBER, SetArmorStandStateTrick::setNumber, FragmentType.ENTITY));
        overload(Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, FragmentType.VECTOR, SetArmorStandStateTrick::setVector, FragmentType.ENTITY));
    }

    public ArmorStandEntity getAmorStand(SpellContext ctx, EntityFragment entityFragment) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof ArmorStandEntity armorStand) {
            ctx.checkMana(this, MANA_COST);
            return armorStand;
        } else {
            throw new InvalidEntityBlunder(this);
        }
    }

    public EntityFragment setVector(SpellContext ctx, EntityFragment entityFragment, NumberFragment index, VectorFragment vectorFragment) throws BlunderException {
        ArmorStandEntity armorStand = getAmorStand(ctx, entityFragment);
        EulerAngle eulerAngle = new EulerAngle((float) vectorFragment.x(), (float) vectorFragment.y(), (float) vectorFragment.z());
        switch (index.asInt()) {
            case 0 -> armorStand.setHeadRotation(eulerAngle);
            case 1 -> armorStand.setBodyRotation(eulerAngle);
            case 2 -> armorStand.setLeftArmRotation(eulerAngle);
            case 3 -> armorStand.setRightArmRotation(eulerAngle);
            case 4 -> armorStand.setLeftLegRotation(eulerAngle);
            case 5 -> armorStand.setRightLegRotation(eulerAngle);
            default -> throw (index.asInt() < 0) ? new NumberTooSmallBlunder(this, 0) : new NumberTooLargeBlunder(this, 5);
        }
        ctx.useMana(this, MANA_COST);
        return entityFragment;
    }

    public EntityFragment setNumber(SpellContext ctx, EntityFragment entityFragment, NumberFragment index, NumberFragment numberFragment) throws BlunderException {
        ArmorStandEntity armorStand = getAmorStand(ctx, entityFragment);
        switch (index.asInt()) {
            case 6 -> armorStand.setYaw(MathHelper.wrapDegrees((float) numberFragment.number()));
            case 7 -> PartyEntityComponents.ARMOR_STAND_SCALE.get(armorStand).setScale(MathHelper.clamp(numberFragment.number(), 0.0625, 1.5)); // TODO make this configurable?
            default -> throw (index.asInt() < 6) ? new NumberTooSmallBlunder(this, 6) : new NumberTooLargeBlunder(this, 7);
        }
        ctx.useMana(this, MANA_COST);
        return entityFragment;
    }

    public EntityFragment setBoolean(SpellContext ctx, EntityFragment entityFragment, NumberFragment index, BooleanFragment booleanFragment) throws BlunderException {
        ArmorStandEntity armorStand = getAmorStand(ctx, entityFragment);
        boolean bool = booleanFragment.bool;
        switch (index.asInt()) {
            case 8 -> armorStand.setNoGravity(bool);
            case 9 -> armorStand.setInvisible(bool);
            case 10 -> armorStand.setHideBasePlate(bool);
            case 11 -> armorStand.setShowArms(bool);
            case 12 -> armorStand.setSmall(bool);
            case 13 -> armorStand.setCustomNameVisible(bool);
            case 14 -> {
                armorStand.setInvulnerable(bool);
                armorStand.disabledSlots = bool ? 4144959 : 0;
            }
            default -> throw (index.asInt() < 8) ? new NumberTooSmallBlunder(this, 8) : new NumberTooLargeBlunder(this, 14);
        }
        ctx.useMana(this, MANA_COST);
        return entityFragment;
    }
}