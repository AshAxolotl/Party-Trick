package com.ashaxolotl.partytrick.datagen;

import com.ashaxolotl.partytrick.PartyTrick;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PartyTrickDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        // Needs to be a resourcepack to be able to override trickster
        fabricDataGenerator.createBuiltinResourcePack(PartyTrick.id("variant_models")).addProvider(ModModelGenerator::new);
    }
}
