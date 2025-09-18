package com.ashaxolotl.partytrick.misc;

// CODE BASED OF SPECTRUM: https://github.com/DaFuqs/Spectrum/blob/1.21.1-aria-for-painters-mojmap/src/main/java/de/dafuqs/spectrum/helpers/BlockVariantHelper.java

import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ColorHelper {

    // cache for getBlockColorVariant()
    private static final Map<Block, Map<DyeColor, Block>> coloredBlockCache = new HashMap<>();
    // cache for getItemColorVariant()
    private static final Map<Item, Map<DyeColor, Item>> coloredItemsCache = new HashMap<>();
    // ordered color strings so "light_" and "dark" variants match before non-light. I hope doing this on load makes it supports mod that add new dye
    private static final List<String> COLOR_STRINGS;
    static {
        List<String> list = new java.util.ArrayList<>(List.of());
        for (DyeColor dyeColor : DyeColor.values()) {
            String name = dyeColor.asString();
            if (name.contains("light") || name.contains("dark")) {
                list.addFirst(name);
            } else {
                list.add(name);
            }
        }
        COLOR_STRINGS = list;
    }

    public static String getDyeColorString(Identifier identifier) {
        for (String colorString : COLOR_STRINGS) {
            if (identifier.getPath().contains(colorString)) {
                return colorString;
            }
        }

        return "";
    }

    public static BlockState getBlockColorVariant(World world, BlockPos blockPos, DyeColor newColor) {
        // TODO could block entities be implemented?
        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity != null) {
            return Blocks.AIR.getDefaultState();
        }

        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        // CHECK CACHE
        if (coloredBlockCache.containsKey(block)) {
            Map<DyeColor, Block> colorMap = coloredBlockCache.get(block);
            if (colorMap.containsKey(newColor)) {
                Block newBlock = colorMap.get(newColor);
                return newBlock.getStateWithProperties(blockState);
            }
        }

        Identifier identifier = Registries.BLOCK.getId(block);
        String colorString = getDyeColorString(identifier);

        // If the block is a "color block" else give air
        Block returnBlock = Blocks.AIR;
        if (!colorString.isEmpty()) {
            Identifier newIdentifier = Identifier.of(identifier.getNamespace(), identifier.getPath().replace(colorString, newColor.toString()));
            returnBlock = Registries.BLOCK.get(newIdentifier); // Will give air if the new color variant of the block doesn't exist
        }

        // add to cache
        if (coloredBlockCache.containsKey(block)) {
            Map<DyeColor, Block> colorMap = coloredBlockCache.get(block);
            colorMap.put(newColor, returnBlock);
        } else {
            Map<DyeColor, Block> colorMap = new HashMap<>();
            colorMap.put(newColor, returnBlock);
            coloredBlockCache.put(block, colorMap);
        }

        return returnBlock.getStateWithProperties(blockState);
    }

    public static ItemStack getItemColorVariant(ItemStack stack, DyeColor newColor) {
        Item item = stack.getItem();

        // CHECK CACHE
        if (coloredItemsCache.containsKey(item)) {
            Map<DyeColor, Item> colorMap = coloredItemsCache.get(item);
            if (colorMap.containsKey(newColor)) {
                Item newItem = colorMap.get(newColor);
                return stack.withItem(newItem);
            }
        }

        Identifier identifier = Registries.ITEM.getId(item);
        String colorString = getDyeColorString(identifier);

        // If the item is a "color item" else give air
        Item returnItem = Items.AIR;
        if (!colorString.isEmpty()) {
            Identifier newIdentifier = Identifier.of(identifier.getNamespace(), identifier.getPath().replace(colorString, newColor.toString()));
            returnItem = Registries.ITEM.get(newIdentifier); // Will give air if the new color variant of the item doesn't exist
        }

        // add to cache
        if (coloredItemsCache.containsKey(item)) {
            Map<DyeColor, Item> colorMap = coloredItemsCache.get(item);
            colorMap.put(newColor, returnItem);
        } else {
            Map<DyeColor, Item> colorMap = new HashMap<>();
            colorMap.put(newColor, returnItem);
            coloredItemsCache.put(item, colorMap);
        }

        return stack.withItem(returnItem);
    }
}
