package com.ashaxolotl.partytrick.net;

import com.ashaxolotl.partytrick.misc.ServerSoundStorage;
import io.wispforest.owo.network.ServerAccess;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public record SendSoundsPacket(UUID uuid, List<Identifier> sounds) {
    public void handleServer(ServerAccess access) {
        ServerSoundStorage.INSTANCE.setPlayerSounds(uuid, sounds);
    }
}
