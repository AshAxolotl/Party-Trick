package com.ashaxolotl.partytrick.spell.trick;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.trick.block.ConjureColoredLightTrick;
import com.ashaxolotl.partytrick.spell.trick.color.ChangeColorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.DyeToVectorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.GetColorTrick;
import com.ashaxolotl.partytrick.spell.trick.misc.PepernotenCreationTrick;
import com.ashaxolotl.partytrick.spell.trick.misc.SetCustomModelDataTrick;
import com.ashaxolotl.partytrick.spell.trick.particle.LightParticleTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonSnowballTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonSpitTrick;
import com.ashaxolotl.partytrick.spell.trick.projectile.SummonWindChargeTrick;
import com.ashaxolotl.partytrick.spell.trick.sound.GetSoundTrick;
import com.ashaxolotl.partytrick.spell.trick.sound.MuffleSoundTrick;
import com.ashaxolotl.partytrick.spell.trick.sound.PlaySoundTrick;
import dev.enjarai.trickster.spell.trick.Trick;
import net.minecraft.registry.Registry;

import static dev.enjarai.trickster.spell.trick.Tricks.REGISTRY;


public class Tricks {
    // MISC
    public static final PepernotenCreationTrick PEPERNOTEN_CREATION = register("pepernoten_creation", new PepernotenCreationTrick());
    public static final SetCustomModelDataTrick SET_CUSTOM_MODEL_DATA = register("set_custom_model_data", new SetCustomModelDataTrick());

    // SUMMONS
    public static final SummonSnowballTrick SUMMON_SNOWBALL = register("summon_snowball", new SummonSnowballTrick());
    public static final SummonWindChargeTrick SUMMON_WIND_CHARGE = register("summon_wind_charge", new SummonWindChargeTrick());
    public static final SummonSpitTrick SUMMON_SPIT = register("summon_spit", new SummonSpitTrick());

    // COLOR
    public static final LightParticleTrick LIGHT_PARTICLE = register("light_particle", new LightParticleTrick());
    public static final ConjureColoredLightTrick CONJURE_COLORED_LIGHT = register("conjure_colored_light", new ConjureColoredLightTrick());
    public static final ChangeColorTrick CHANGE_COLOR = register("change_color", new ChangeColorTrick());
    public static final GetColorTrick GET_COLOR = register("get_color", new GetColorTrick());
    public static final DyeToVectorTrick DYE_TO_VECTOR = register("dye_to_vector", new DyeToVectorTrick());

    // SOUNDS
    public static final GetSoundTrick GET_SOUND = register("get_sound", new GetSoundTrick());
    public static final PlaySoundTrick PLAY_SOUND = register("play_sound", new PlaySoundTrick());
    public static final MuffleSoundTrick MUFFLE_SOUND = register("muffle_sound", new MuffleSoundTrick());

    public static <T extends Trick<T>> T register(String path, T trick) {
        return Registry.register(REGISTRY, PartyTrick.id(path), trick);
    }

    public static void register() {
        // init statics
    }
}
