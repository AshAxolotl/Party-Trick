package com.ashaxolotl.partytrick.mixin;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.misc.ModSounds;
import com.ashaxolotl.partytrick.mixin.accessor.CasterComponentAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.trickster.cca.CasterComponent;
import dev.enjarai.trickster.item.LeashItem;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.SpellPart;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;

import static dev.enjarai.trickster.ModSounds.randomPitch;

// The most normal mixin in the mod!
@Mixin(LeashItem.class)
public class LeashSoundMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Ldev/enjarai/trickster/cca/CasterComponent;playCastSound(FF)V"))
    private void sound(CasterComponent instance, float pitchRange, float startPitch, Operation<Void> original, @Local ItemStack stack) {
        int variant = stack.getOrDefault(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(0)).value();
        if (variant == 1) {
            playSound(((CasterComponentAccessor) instance).trickster$getPlayer(), ModSounds.CLICK, 1.5f, 1, 0);
        } else {
            original.call(instance, pitchRange, startPitch);
        }
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Ldev/enjarai/trickster/cca/CasterComponent;queueCollarSpell(Ldev/enjarai/trickster/spell/SpellPart;Ljava/util/List;)Ljava/util/Optional;"))
    private Optional<Integer> cast(CasterComponent instance, SpellPart spell, List<Fragment> arguments, Operation<Optional<Integer>> original, @Local ItemStack stack) {
        int variant = stack.getOrDefault(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(0)).value();
        if (variant == 1) {
            playSound(((CasterComponentAccessor) instance).trickster$getPlayer(), ModSounds.CLICK, 1.5f, 1, 0);
            return ((CasterComponentAccessor) instance).trickster$getCollarExecutionManager().queue(spell, arguments);
        } else {
            return original.call(instance, spell, arguments);
        }
    }

    @Unique
    private void playSound(PlayerEntity target, SoundEvent sound, float volume, float startPitch, float pitchRange) {
        PartyTrick.LOGGER.info(target.toString());
        if (target instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.getServerWorld().playSoundFromEntity(
                    null, serverPlayer, sound, SoundCategory.PLAYERS, volume , randomPitch(startPitch, pitchRange)
            );
        }
    }
}

