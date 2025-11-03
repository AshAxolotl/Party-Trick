package com.ashaxolotl.partytrick;

import com.ashaxolotl.partytrick.net.ModClientNetworking;
import net.fabricmc.api.ClientModInitializer;

public class PartyTrickClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModClientNetworking.register();
	}
}