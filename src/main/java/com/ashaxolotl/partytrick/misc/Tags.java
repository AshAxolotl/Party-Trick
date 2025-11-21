package com.ashaxolotl.partytrick.misc;

import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class Tags {
    public static final TagKey<Block> COLOR_BLOCK_BLACKLIST = block("color_block_blacklist"); // blacklisting form block coloring
    public static final TagKey<Item> COLOR_ITEM_BLACKLIST = item("color_item_blacklist"); // blacklisting form item coloring
    public static final TagKey<EntityType<?>> SPIT_BLACKLIST = entity("spit_blacklist"); // Mobs that cannot be used for spit
    public static final TagKey<EntityType<?>> MOUNT_BLACKLIST = entity("mount_blacklist"); // Entities that cannot be used as mount
    public static final TagKey<EntityType<?>> RIDER_BLACKLIST = entity("rider_blacklist"); // Entities that cannot be given as rider for ride entity trick
    public static final TagKey<EntityType<?>> DISMOUNT_BLACKLIST = entity("dismount_blacklist"); // Entities that cannot be dismounted from their vehicle

    private static TagKey<Block> block(String id) {
        return TagKey.of(RegistryKeys.BLOCK, PartyTrick.id(id));
    }

    private static TagKey<EntityType<?>> entity(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, PartyTrick.id(id));
    }

    private static TagKey<Item> item(String id) {
        return TagKey.of(RegistryKeys.ITEM, PartyTrick.id(id));
    }
}
