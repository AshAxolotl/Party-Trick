package com.ashaxolotl.partytrick.misc;

import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class PartySounds {
    public static final SoundEvent CLICK = register("click");
    public static final SoundEvent WHIP = register("whip");

    private static SoundEvent register(String path) {
        var id = PartyTrick.id(path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {

    }
}
