package com.ashaxolotl.partytrick.spell.trick.misc;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.datagen.ModModelGenerator;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.ItemInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.MissingItemBlunder;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.fragment.SlotFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;

import java.util.Optional;


public class SetCustomModelDataTrick extends Trick<SetCustomModelDataTrick> {
    public SetCustomModelDataTrick() {
        super(Pattern.of(6, 4, 2, 5, 4, 3, 0, 4, 8), Signature.of(FragmentType.SLOT, FragmentType.NUMBER.optionalOfArg(), SetCustomModelDataTrick::run, FragmentType.SLOT));
    }

    public SlotFragment run(SpellContext ctx, SlotFragment slot, Optional<NumberFragment> number) throws BlunderException {
        var stack = slot.reference(this, ctx);
        if (stack.isEmpty()) {
            throw new MissingItemBlunder(this);
        }

        if (number.isEmpty()) {
            var maxVariants = ModModelGenerator.variants.get(stack.getItem());
            if (maxVariants == null) {
                maxVariants = ModModelGenerator.doNOTdatagenVariantsBecauseILikeMySanity.get(stack.getItem());
            }
            if (maxVariants == null) {
                throw new ItemInvalidBlunder(this);
            }
            ctx.useMana(this, 1);

            int currentVariant = stack.getOrDefault(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(0)).value();
            int newVariant = (currentVariant + 1) % (maxVariants._2+1);
            if (newVariant == 0 || currentVariant < 0 || currentVariant > maxVariants._2) {
                stack.remove(DataComponentTypes.CUSTOM_MODEL_DATA);
            } else {
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(newVariant));
            }

        } else if (number.get().asInt() == 0) {
            ctx.useMana(this, 1);
            stack.remove(DataComponentTypes.CUSTOM_MODEL_DATA);
        } else {
            ctx.useMana(this, 1);
            stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(number.get().asInt()));
        }

        return slot;
    }
}