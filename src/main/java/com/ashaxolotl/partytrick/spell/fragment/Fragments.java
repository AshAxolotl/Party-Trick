package com.ashaxolotl.partytrick.spell.fragment;

import dev.enjarai.trickster.Trickster;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import io.wispforest.endec.StructEndec;
import net.minecraft.registry.Registry;

import java.util.OptionalInt;

public class Fragments {
    public static final FragmentType<SoundFragment> SOUND = register("sound", SoundFragment.ENDEC, 1952207);

    private static <T extends Fragment> FragmentType<T> register(String name, StructEndec<T> codec, int color) {
        return Registry.register(FragmentType.REGISTRY, Trickster.id(name), new FragmentType<>(codec, OptionalInt.of(color)));
    }

    public static void register() {
        // init statics
    }
}
