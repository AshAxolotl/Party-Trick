package com.ashaxolotl.partytrick.net;

import com.ashaxolotl.partytrick.PartyTrick;
import io.wispforest.owo.network.OwoNetChannel;

public class ModNetworking {
    public static OwoNetChannel CHANNEL = OwoNetChannel.create(PartyTrick.id("main"));

    public static void register() {
        CHANNEL.registerServerbound(SendSoundsPacket.class, SendSoundsPacket::handleServer);
    }
}
