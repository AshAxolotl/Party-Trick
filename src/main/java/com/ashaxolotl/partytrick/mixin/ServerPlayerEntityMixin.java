package com.ashaxolotl.partytrick.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Unique
    private final Entity self = (Entity) (Object) this;

    // TODO TEST THIS
    @Inject(method = "onDisconnect", at = @At(value = "HEAD"))
    private void fixDisconnectingWhileRidingPlayersCausingIssuesTM(CallbackInfo ci) {
        var vehicle = self.getRootVehicle();
        if (vehicle != null) {
            if (vehicle.isPlayer() || vehicle.getPlayerPassengers() > 1) {
                self.stopRiding();
            }
        }
    }
}
