package com.ashaxolotl.partytrick.cca;

import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class PartyEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<MufflesComponent> MUFFLES = ComponentRegistry.getOrCreate(PartyTrick.id("muffles"), MufflesComponent.class);
    public static final ComponentKey<ArmorStandScaleComponent> ARMOR_STAND_SCALE = ComponentRegistry.getOrCreate(PartyTrick.id("armor_stand_scale"), ArmorStandScaleComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MUFFLES, MufflesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerFor(ArmorStandEntity.class, ARMOR_STAND_SCALE, ArmorStandScaleComponent::new);
    }
}
