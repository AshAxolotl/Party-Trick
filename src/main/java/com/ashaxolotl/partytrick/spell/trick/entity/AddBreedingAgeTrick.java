package com.ashaxolotl.partytrick.spell.trick.entity;

import com.ashaxolotl.partytrick.PartyTrick;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import dev.enjarai.trickster.spell.execution.TickData;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.passive.PassiveEntity;

import java.util.HashMap;

public class AddBreedingAgeTrick extends Trick<AddBreedingAgeTrick> {
    private static final TickData.Key<HashMap<EntityFragment, Integer>> COMPOUND_LEN = new TickData.Key<>(
            PartyTrick.id("age_compound_len"), null
    );

    public AddBreedingAgeTrick() {
        super(Pattern.of(1, 5, 2, 1, 8, 4, 3, 0, 6, 3, 1), Signature.of(FragmentType.ENTITY, FragmentType.NUMBER, AddBreedingAgeTrick::run, FragmentType.ENTITY));
    }

    public EntityFragment run(SpellContext ctx, EntityFragment entityFragment, NumberFragment amount) {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (entity instanceof PassiveEntity passiveEntity) {
            var map = COMPOUND_LEN.set(ctx.data(), COMPOUND_LEN.get(ctx.data()).orElse(new HashMap<>()));
            int stackedAmount = Math.abs(amount.asInt()) + map.getOrDefault(entityFragment, 0);
            ctx.useMana(this, (float) Math.pow(stackedAmount, 2));
            map.put(entityFragment, stackedAmount);

            passiveEntity.setBreedingAge(passiveEntity.getBreedingAge() + amount.asInt()); // Mobs with a bage < 0 will turn into a baby. bage = 0 is breedable. bage > 0 is breeding cooldown
        }

        return entityFragment;
    }
}
