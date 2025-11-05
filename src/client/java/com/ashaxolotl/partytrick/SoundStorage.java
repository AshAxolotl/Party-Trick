package com.ashaxolotl.partytrick;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class SoundStorage {
    public static final SoundStorage INSTANCE = new SoundStorage();
    private List<Identifier> sounds = new ArrayList<>();

    public void addSound(SoundInstance sound) {
        var id = sound.getId();
        if (!sounds.contains(id)) {
            sounds.add(id);
        }
    }

    public void reset(int oldLength) {
        sounds = sounds.subList(oldLength, sounds.size());
    }

    public List<Identifier> getSounds() {
        return sounds;
    }
}
