package com.ashaxolotl.partytrick.mixin.client;

import com.ashaxolotl.partytrick.SoundStorage;
import com.ashaxolotl.partytrick.net.ModNetworking;
import com.ashaxolotl.partytrick.net.SendSoundsPacket;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity{
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    // Sending a list of sounds the player has heard everything surely is a good idea right
    @Inject(method = "tick", at = @At("HEAD"))
    public void sendSoundsPacket(CallbackInfo ci) {
        var sounds = SoundStorage.INSTANCE.getSounds();
//        if (!(sounds.isEmpty())) {
            ModNetworking.CHANNEL.clientHandle().send(new SendSoundsPacket(this.getUuid(), sounds));
            SoundStorage.INSTANCE.reset(sounds.size());
//        }
    }
}
