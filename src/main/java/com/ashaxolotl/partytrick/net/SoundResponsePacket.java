package com.ashaxolotl.partytrick.net;

import com.ashaxolotl.partytrick.spell.fragment.SoundFragment;
import dev.enjarai.trickster.cca.MessageHandlerComponent;
import dev.enjarai.trickster.cca.ModGlobalComponents;
import io.wispforest.owo.network.ServerAccess;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public record SoundResponsePacket(UUID uuid, List<Identifier> sounds) {
    public void handleServer(ServerAccess access) {
        var handler = ModGlobalComponents.MESSAGE_HANDLER.get(access.player().getWorld().getScoreboard());
        var channel = new MessageHandlerComponent.Key.Channel(uuid);

        sounds.forEach(s -> handler.send(channel, new SoundFragment(Registries.SOUND_EVENT.get(s))));
    }
}
