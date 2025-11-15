package com.ashaxolotl.partytrick.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Entity.class)
public abstract class EntityMixin {

    @Unique
    private final Entity self = (Entity) (Object) this;

    @Inject(method = "addPassenger", at = @At(value = "TAIL"))
    private void fixUnableToRidePlayers(Entity passenger, CallbackInfo ci) {
        if (!self.getWorld().isClient && self.isPlayer()) {
            ((ServerPlayerEntity) self).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(self));
        }
    }

    @Inject(method = "removePassenger", at = @At(value = "TAIL"))
    private void fixUnableToStopRidingPlayers(Entity passenger, CallbackInfo ci) {
        if (!self.getWorld().isClient && self.isPlayer()) {
            ((ServerPlayerEntity) self).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(self));
        }
    }
}
