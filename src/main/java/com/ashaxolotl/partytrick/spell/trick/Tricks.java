package com.ashaxolotl.partytrick.spell.trick;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.spell.trick.block.ConjureColoredLightTrick;
import com.ashaxolotl.partytrick.spell.trick.color.ChangeColorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.DyeToVectorTrick;
import com.ashaxolotl.partytrick.spell.trick.color.GetColorTrick;
import com.ashaxolotl.partytrick.spell.trick.displayentity.DeleteDisplayEntityTrick;
import com.ashaxolotl.partytrick.spell.trick.displayentity.MoveDisplayEntityTrick;
import com.ashaxolotl.partytrick.spell.trick.displayentity.SummonDisplayEntityTrick;
import com.ashaxolotl.partytrick.spell.trick.entity.AddBreedingAgeTrick;
import com.ashaxolotl.partytrick.spell.trick.entity.DismountEntityTrick;
import com.ashaxolotl.partytrick.spell.trick.entity.query.GetBreedingAgeTrick;
import com.ashaxolotl.partytrick.spell.trick.entity.RideEntityTrick;
import com.ashaxolotl.partytrick.spell.trick.entity.query.GetVehicleTrick;
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

@SuppressWarnings("unused")
public class Tricks {
    // MISC
    public static final PepernotenCreationTrick PEPERNOTEN_CREATION = register("pepernoten_creation", new PepernotenCreationTrick());
    public static final SetCustomModelDataTrick SET_CUSTOM_MODEL_DATA = register("set_custom_model_data", new SetCustomModelDataTrick());

    // ENTITY
    public static final AddBreedingAgeTrick ADD_BREEDING_AGE = register("add_breeding_age", new AddBreedingAgeTrick());
    public static final GetBreedingAgeTrick GET_BREEDING_AGE = register("get_breeding_age", new GetBreedingAgeTrick());
    public static final RideEntityTrick RIDE_ENTITY = register("ride_entity", new RideEntityTrick());
    public static final DismountEntityTrick DISMOUNT_ENTITY = register("dismount_entity", new DismountEntityTrick());
    public static final GetVehicleTrick GET_VEHICLE = register("get_vehicle", new GetVehicleTrick());

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

    // DISPLAY
    public static final SummonDisplayEntityTrick SUMMON_DISPLAY_ENTITY = register("summon_display_entity", new SummonDisplayEntityTrick());
    public static final DeleteDisplayEntityTrick DELETE_DISPLAY_ENTITY = register("delete_display_entity", new DeleteDisplayEntityTrick());
    public static final MoveDisplayEntityTrick MOVE_DISPLAY_ENTITY = register("move_display_entity", new MoveDisplayEntityTrick());

    public static <T extends Trick<?>> T register(String path, T trick) {
        return Registry.register(REGISTRY, PartyTrick.id(path), trick);
    }

    public static void register() {
        // init statics
    }
}
