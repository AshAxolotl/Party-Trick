package com.ashaxolotl.partytrick.spell.execution.executor;

import dev.enjarai.trickster.Trickster;
import dev.enjarai.trickster.spell.SpellExecutor;
import dev.enjarai.trickster.spell.execution.executor.SpellExecutorType;
import io.wispforest.endec.StructEndec;
import net.minecraft.registry.Registry;

public class SpellExecutorTypeMod {

    public static final SpellExecutorType<SoundListenerSpellExecutor> SOUND_LISTENER = register("sound_listener", SoundListenerSpellExecutor.ENDEC);

    private static <T extends SpellExecutor> dev.enjarai.trickster.spell.execution.executor.SpellExecutorType<T> register(String name, StructEndec<T> codec, StructEndec<T> netEndec) {
        return Registry.register(SpellExecutorType.REGISTRY, Trickster.id(name), new dev.enjarai.trickster.spell.execution.executor.SpellExecutorType<>(codec, netEndec));
    }

    private static <T extends SpellExecutor> dev.enjarai.trickster.spell.execution.executor.SpellExecutorType<T> register(String name, StructEndec<T> codec) {
        return register(name, codec, codec);
    }

    public static void register() {
        // init
    }
}
