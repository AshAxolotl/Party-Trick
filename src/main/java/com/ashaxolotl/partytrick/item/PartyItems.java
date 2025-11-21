package com.ashaxolotl.partytrick.item;

import com.ashaxolotl.partytrick.PartyTrick;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class PartyItems {
    public static final Item PEPERNOOT = register("pepernoot", new Item(new Item.Settings().food(new FoodComponent.Builder()
            .alwaysEdible()
            .snack()
            .nutrition(1)
            .build())));

    public static <T extends Item> T  register(String name, T item) {
        // Return the registered item!
        return Registry.register(Registries.ITEM, PartyTrick.id(name), item);
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup) -> itemGroup.add(PartyItems.PEPERNOOT));
    }

}