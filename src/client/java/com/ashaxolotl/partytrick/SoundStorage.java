package com.ashaxolotl.partytrick;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class SoundStorage {
    public static final SoundStorage INSTANCE = new SoundStorage();

//    private static final int capacity = 6;
//    private ArrayDeque<Identifier> sounds = new ArrayDeque<>(capacity);
    private final List<Identifier> sounds = new ArrayList<>();

    public void addSound(SoundInstance sound) {
        sounds.add(sound.getId());
    }

    public void reset(List<Identifier> remove) {
        sounds.clear();
    }

    public List<Identifier> getSounds() {
        return sounds;
    }
}
