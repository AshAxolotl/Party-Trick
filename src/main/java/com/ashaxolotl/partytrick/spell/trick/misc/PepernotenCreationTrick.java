package com.ashaxolotl.partytrick.spell.trick.misc;

import com.ashaxolotl.partytrick.item.PartyItems;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.ItemInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.MissingItemBlunder;
import dev.enjarai.trickster.spell.blunder.NoPlayerBlunder;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.SlotFragment;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.item.Items;

import java.util.Optional;

public class PepernotenCreationTrick extends Trick<PepernotenCreationTrick> {
    public PepernotenCreationTrick() {
        super(Pattern.of(3,4,5,1,3,6,8,5,7,4,1), Signature.of(FragmentType.SLOT.optionalOfArg(), PepernotenCreationTrick::run, FragmentType.VOID));
    }

    public VoidFragment run(SpellContext ctx, Optional<SlotFragment> slot) throws BlunderException {
        var sourceSlot = slot.orElseGet(() -> {
            var player = ctx.source().getPlayer().orElseThrow(() -> new NoPlayerBlunder(this));
            var inventory = player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                var stack = inventory.getStack(i);

                if (stack.isOf(Items.SUGAR)) {
                    return new SlotFragment(i, Optional.empty());
                }
            }
            throw new MissingItemBlunder(this);
        });

        var sourceItem = sourceSlot.getItem(this, ctx);
        if (sourceItem != Items.SUGAR) {
            throw new ItemInvalidBlunder(this);
        }

        var input = sourceSlot.move(this, ctx, 1);

        try {
            ctx.useMana(this, 10);
            ctx.source().offerOrDropItem(PartyItems.PEPERNOOT.getDefaultStack());

            return VoidFragment.INSTANCE;
        } catch (Exception e) {
            ctx.source().offerOrDropItem(input);
            throw e;
        }

    }
}
