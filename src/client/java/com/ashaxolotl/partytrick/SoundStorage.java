package com.ashaxolotl.partytrick;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;

import java.util.ArrayDeque;

public class SoundStorage {
    public static final SoundStorage INSTANCE = new SoundStorage();

    private static final int capacity = 6;
    private final ArrayDeque<Identifier> sounds = new ArrayDeque<>(capacity);

    public void addSound(SoundInstance sound) {
        if (sounds.size() == capacity) {
            sounds.removeFirst();
        }
        sounds.add(sound.getId());
    }

    public ArrayDeque<Identifier> getSounds() {
        return sounds;
    }
}
