package com.ashaxolotl.partytrick.spell.trick;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.trick.block.ConjureColoredLightTrick;
import com.ashaxolotl.partytrick.spell.trick.color.ChangeColorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.DyeToVectorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.GetColorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.RemoveColorTrick;
import com.ashaxolotl.partytrick.spell.trick.misc.PepernotenCreationTrick;
import com.ashaxolotl.partytrick.spell.trick.particle.LightParticleTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonSnowballTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonSpitTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonWindChargeTrick;
import dev.enjarai.trickster.spell.trick.Trick;
import net.minecraft.registry.Registry;

import static dev.enjarai.trickster.spell.trick.Tricks.REGISTRY;


public class Tricks {
    public static final SummonSnowballTrick SUMMON_SNOWBALL = register("summon_snowball", new SummonSnowballTrick());
    public static final SummonWindChargeTrick SUMMON_WIND_CHARGE = register("summon_wind_charge", new SummonWindChargeTrick());
    public static final SummonSpitTrick SUMMON_SPIT = register("summon_spit", new SummonSpitTrick());
    public static final LightParticleTrick LIGHT_PARTICLE = register("light_particle", new LightParticleTrick());
    public static final ConjureColoredLightTrick CONJURE_COLORED_LIGHT = register("conjure_colored_light", new ConjureColoredLightTrick());
    public static final PepernotenCreationTrick PEPERNOTEN_CREATION = register("pepernoten_creation", new PepernotenCreationTrick());
    public static final ChangeColorTrick CHANGE_COLOR = register("change_color", new ChangeColorTrick());
    public static final RemoveColorTrick REMOVE_COLOR = register("remove_color", new RemoveColorTrick());
    public static final GetColorTrick GET_COLOR = register("get_color", new GetColorTrick());
    public static final DyeToVectorTrick DYE_TO_VECTOR = register("dye_to_vector", new DyeToVectorTrick());


    public static <T extends Trick<T>> T register(String path, T trick) {
        return Registry.register(REGISTRY, PartyTrick.id(path), trick);
    }

    public static void register() {
        // init statics
    }
}
