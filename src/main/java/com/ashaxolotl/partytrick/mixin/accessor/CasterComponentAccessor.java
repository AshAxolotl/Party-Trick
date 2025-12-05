package com.ashaxolotl.partytrick.mixin.accessor;

import dev.enjarai.trickster.cca.CasterComponent;
import dev.enjarai.trickster.spell.execution.PlayerSpellExecutionManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CasterComponent.class)
public interface CasterComponentAccessor {
    @Accessor("player")
    PlayerEntity trickster$getPlayer();

    @Accessor("collarExecutionManager")
    PlayerSpellExecutionManager trickster$getCollarExecutionManager();
}
