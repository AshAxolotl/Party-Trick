package com.ashaxolotl.partytrick;

import com.ashaxolotl.partytrick.entity.PartyEntities;
import com.ashaxolotl.partytrick.render.PartyBlockDisplayRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PartyTrickClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(PartyEntities.PARTY_BLOCK_DISPLAY, PartyBlockDisplayRenderer::new);
	}
}