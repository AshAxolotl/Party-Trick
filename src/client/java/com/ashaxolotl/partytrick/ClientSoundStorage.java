package com.ashaxolotl.partytrick;

import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class ClientSoundStorage {
    public static final ClientSoundStorage INSTANCE = new ClientSoundStorage();
    private final List<Identifier> sounds = new ArrayList<>();

    public void addSound(SoundInstance sound, Vec3d listenerPos) {
        var id = sound.getId();
        if (!sounds.contains(id)) {
            if (sound.isRelative() || sound.getAttenuationType() == SoundInstance.AttenuationType.NONE) {
                sounds.add(id);
            } else if (listenerPos.isInRange(
                    new Vec3d(sound.getX(), sound.getY(), sound.getZ()),
                    Math.max(sound.getVolume(), 1.0F) * sound.getSound().getAttenuation()
            )) {
                sounds.add(id);
            }
        }
    }

    public void reset() {
        sounds.clear();
    }

    public List<Identifier> getSounds() {
        return sounds;
    }
}
