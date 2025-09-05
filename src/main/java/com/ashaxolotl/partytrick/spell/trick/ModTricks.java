package com.ashaxolotl.partytrick.spell.trick;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.trick.misc.IsEvenTrick;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.trick.Tricks;
import net.minecraft.registry.Registry;

public class ModTricks {
    public static final IsEvenTrick IsEvenTrick = register("is_even", new IsEvenTrick());

    public static <T extends Trick<T>> T register(String path, T trick) {
        return Registry.register(Tricks.REGISTRY, PartyTrick.id(path), trick);
    }

    public static void register() {
        // init statics
    }
}
