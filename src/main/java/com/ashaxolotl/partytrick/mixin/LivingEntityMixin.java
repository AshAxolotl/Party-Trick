package com.ashaxolotl.partytrick.mixin;

import com.ashaxolotl.partytrick.cca.PartyEntityComponents;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyExpressionValue(
        method = "getScale", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/AttributeContainer;getValue(Lnet/minecraft/registry/entry/RegistryEntry;)D"))
    private double modifyArmorStandScale(double original) {
        if ((Object) this instanceof ArmorStandEntity) {
            return original * PartyEntityComponents.ARMOR_STAND_SCALE.get(this).getScale();
        }
        return original;
    }
}
