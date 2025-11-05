package com.ashaxolotl.partytrick.misc;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServerSoundStorage {
    public static final ServerSoundStorage INSTANCE = new ServerSoundStorage();

    private final Map<UUID, List<Identifier>> playerSounds = new HashMap<>();


    public List<Identifier> getPlayerSounds(UUID uuid) {
        return playerSounds.get(uuid);
    }

    public void setPlayerSounds(UUID uuid, List<Identifier> sounds) {
        playerSounds.put(uuid, sounds);
    }
}
