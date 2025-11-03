package com.ashaxolotl.partytrick.net;

import com.ashaxolotl.partytrick.SoundStorage;
import io.vavr.collection.Array;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModClientNetworking {
    public static void register() {
        ModNetworking.CHANNEL.registerClientbound(RequestSoundsPacket.class, (message, clientAccess) -> {
            var sounds = SoundStorage.INSTANCE.getSounds();
            ModNetworking.CHANNEL.clientHandle().send(new SoundResponsePacket(message.uuid(), (sounds.stream().toList())));
        });
    }
}
