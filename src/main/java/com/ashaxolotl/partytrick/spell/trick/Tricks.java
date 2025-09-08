package com.ashaxolotl.partytrick.spell.trick;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.trick.particle.LightParticleTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonSnowballTrick;
import dev.enjarai.trickster.spell.trick.Trick;
import net.minecraft.registry.Registry;

import static dev.enjarai.trickster.spell.trick.Tricks.REGISTRY;


public class Tricks {
    public static final SummonSnowballTrick SUMMON_SNOWBALL = register("summon_snowball", new SummonSnowballTrick());
    public static final LightParticleTrick LIGHT_PARTICLE = register("light_particle", new LightParticleTrick());

    public static <T extends Trick<T>> T register(String path, T trick) {
        return Registry.register(REGISTRY, PartyTrick.id(path), trick);
    }

    public static void register() {
        // init statics
    }
}
