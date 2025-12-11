package com.ashaxolotl.partytrick;

import com.ashaxolotl.partytrick.entity.PartyEntities;
import com.ashaxolotl.partytrick.item.PartyItems;
import com.ashaxolotl.partytrick.misc.PartySounds;
import com.ashaxolotl.partytrick.net.PartyNetworking;
import com.ashaxolotl.partytrick.spell.fragment.Fragments;
import com.ashaxolotl.partytrick.spell.trick.Tricks;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartyTrick implements ModInitializer {
	public static final String MOD_ID = "partytrick";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Fragments.register();
		Tricks.register();

		PartyItems.register();
		PartyEntities.register();
		PartyNetworking.register();
		PartySounds.register();

		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(id("variant_models"), container, ResourcePackActivationType.ALWAYS_ENABLED));
	}


	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}