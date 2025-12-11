package com.ashaxolotl.partytrick.cca;

import com.ashaxolotl.partytrick.PartyTrick;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class PartyEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<MufflesComponent> MUFFLES = ComponentRegistry.getOrCreate(PartyTrick.id("muffles"), MufflesComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MUFFLES, MufflesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
