package com.ashaxolotl.partytrick.entity;

import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PartyEntities {
    public static final EntityType<PartyDisplayEntity.BlockDisplayEntity> BLOCK_DISPLAY = Registry.register(Registries.ENTITY_TYPE, PartyTrick.id("block_display"),
            EntityType.Builder.create(PartyDisplayEntity.BlockDisplayEntity::new, SpawnGroup.MISC).dimensions(0, 0).build());

    public static final EntityType<PartyDisplayEntity.ItemDisplayEntity> ITEM_DISPLAY = Registry.register(Registries.ENTITY_TYPE, PartyTrick.id("item_display"),
            EntityType.Builder.create(PartyDisplayEntity.ItemDisplayEntity::new, SpawnGroup.MISC).dimensions(0, 0).build());

    public static void register() {

    }
}
