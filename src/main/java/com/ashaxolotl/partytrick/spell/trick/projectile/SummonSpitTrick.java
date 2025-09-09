package com.ashaxolotl.partytrick.spell.trick.projectile;

import com.ashaxolotl.partytrick.PartyTrick;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.trick.inventory.GetInventorySlotTrick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.entity.damage.DamageTypes.SPIT;

public class SummonSpitTrick extends Trick<SummonSpitTrick> {
    public SummonSpitTrick() {
        super(Pattern.of(0,4,8,2,4,6,0,1,2,5,8,7,6,3,0), Signature.of(FragmentType.VECTOR, SummonSpitTrick::fromCaster, FragmentType.ENTITY));
        overload(Signature.of(FragmentType.VECTOR, FragmentType.ENTITY, SummonSpitTrick::fromLlama, FragmentType.ENTITY));

    }

    public EntityFragment fromCaster(SpellContext ctx, VectorFragment pos)  throws BlunderException {
        var caster = ctx.source().getCaster().orElseThrow(() -> new NoPlayerBlunder(this)); // TODO is this the right blunder?
        ctx.useMana(this, cost(ctx.source().getPos().distance(pos.vector())));

        var world = ctx.source().getWorld();
        var projectile = EntityType.LLAMA_SPIT.create(world);
        projectile.setOwner(caster);
        projectile.setPos(pos.x(), pos.y(), pos.z());
        world.spawnEntity(projectile);
        return EntityFragment.from(projectile);
    }

    public EntityFragment fromLlama(SpellContext ctx, VectorFragment pos, EntityFragment entity) throws BlunderException {
        var llama = entity.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (!(llama instanceof LlamaEntity)) {
            throw new EntityInvalidBlunder(this);
        }
        ctx.useMana(this, cost(llama.getPos().distanceTo((Vec3d) pos.vector())));

        var world = ctx.source().getWorld();
        var projectile = EntityType.LLAMA_SPIT.create(world);
        projectile.setOwner(llama);
        projectile.setPos(pos.x(), pos.y(), pos.z());
        world.spawnEntity(projectile);
        return EntityFragment.from(projectile);



    }

    private float cost(double dist) {
        return (float) (20 + Math.pow(dist, (dist / 3)));
    }
}
