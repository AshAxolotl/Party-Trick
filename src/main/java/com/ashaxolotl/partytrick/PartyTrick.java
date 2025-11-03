package com.ashaxolotl.partytrick;

import com.ashaxolotl.partytrick.item.ModItems;
import com.ashaxolotl.partytrick.misc.ColorHelper;
import com.ashaxolotl.partytrick.net.ModNetworking;
import com.ashaxolotl.partytrick.spell.fragment.Fragments;
import com.ashaxolotl.partytrick.spell.trick.Tricks;
import dev.enjarai.trickster.Trickster;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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

		ModItems.register();
		ModNetworking.register();
	}


	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}