package com.ashaxolotl.partytrick.spell.trick.projectile;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.EntityType;

public class SummonSnowballTrick extends Trick<SummonSnowballTrick> {
    public SummonSnowballTrick() {
        super(Pattern.of(5, 2, 1, 0, 3, 4, 5, 8, 3, 6, 5, 7, 3), Signature.of(FragmentType.VECTOR, SummonSnowballTrick::run, FragmentType.ENTITY));
    }

    public EntityFragment run(SpellContext ctx, VectorFragment pos) throws BlunderException {
        var world = ctx.source().getWorld();

        ctx.useMana(this, cost(ctx.source().getPos().distance(pos.vector())));

        var projectile = EntityType.SNOWBALL.create(world);
        projectile.setPos(pos.x(), pos.y(), pos.z());

        ctx.source().getPlayer().ifPresent(projectile::setOwner);

        world.spawnEntity(projectile);
        return EntityFragment.from(projectile);
    }

    private float cost(double dist) {
        return (float) (10 + 4 * dist);
    }
}
