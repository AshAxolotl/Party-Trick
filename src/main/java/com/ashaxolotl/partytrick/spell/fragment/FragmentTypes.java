package com.ashaxolotl.partytrick.spell.fragment;

import com.ashaxolotl.partytrick.PartyTrick;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import io.wispforest.endec.StructEndec;
import net.minecraft.registry.Registry;

import java.util.OptionalInt;

public class FragmentTypes {
    public static final FragmentType<SoundFragment> SOUND = register("sound", SoundFragment.ENDEC, 1269642);

    private static <T extends Fragment> FragmentType<T> register(String name, StructEndec<T> codec, int color) {
        return Registry.register(FragmentType.REGISTRY, PartyTrick.id(name), new FragmentType<>(codec, OptionalInt.of(color)));
    }

    public static void register() {
        // init statics
    }
}
