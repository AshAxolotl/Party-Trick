package com.ashaxolotl.partytrick.spell.fragment;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;
import io.wispforest.owo.serialization.endec.MinecraftEndecs;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;

public record SoundFragment(SoundEvent sound) implements Fragment {
    public static final StructEndec<SoundFragment> ENDEC = StructEndecBuilder.of(
            MinecraftEndecs.ofRegistry(Registries.SOUND_EVENT).fieldOf("sound", SoundFragment::sound),
            SoundFragment::new
    );

    @Override
    public FragmentType<?> type() {
        return Fragments.SOUND;
    }

    @Override
    public Text asText() {
        // There is no standard for subtitle translations :(
        return Text.of(sound.getId().toString());
    }

    @Override
    public int getWeight() {
        return 16;
    }
}
