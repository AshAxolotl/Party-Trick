package com.ashaxolotl.partytrick;

import com.ashaxolotl.partytrick.entity.PartyEntities;
import com.ashaxolotl.partytrick.render.BlockDisplayRenderer;
import com.ashaxolotl.partytrick.render.ItemDisplayRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PartyTrickClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(PartyEntities.BLOCK_DISPLAY, BlockDisplayRenderer::new);
		EntityRendererRegistry.register(PartyEntities.ITEM_DISPLAY, ItemDisplayRenderer::new);
	}
}