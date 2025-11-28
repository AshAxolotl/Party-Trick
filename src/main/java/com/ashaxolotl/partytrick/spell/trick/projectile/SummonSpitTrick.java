package com.ashaxolotl.partytrick.spell.trick.projectile;

import com.ashaxolotl.partytrick.PartyTrick;
import com.ashaxolotl.partytrick.misc.Tags;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.*;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec3d;


public class SummonSpitTrick extends Trick<SummonSpitTrick> {
    public SummonSpitTrick() {
        super(Pattern.of(0,4,8,2,4,6,0,1,2,5,8,7,6,3,0), Signature.of(FragmentType.VECTOR, SummonSpitTrick::fromCaster, FragmentType.ENTITY));
        overload(Signature.of(FragmentType.VECTOR, FragmentType.ENTITY, SummonSpitTrick::fromEntity, FragmentType.ENTITY));

    }

    public EntityFragment fromCaster(SpellContext ctx, VectorFragment pos)  throws BlunderException {
        var caster = ctx.source().getCaster().orElseThrow(() -> new NoPlayerBlunder(this));
        return spell(ctx, pos, caster);
    }

    public EntityFragment fromEntity(SpellContext ctx, VectorFragment pos, EntityFragment entityFragment) throws BlunderException {
        var entity = entityFragment.getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));
        if (!(entity instanceof LivingEntity) || entity instanceof PlayerEntity || entity.getType().isIn(Tags.SPIT_BLACKLIST)) {
            throw new EntityInvalidBlunder(this);
        }
        return spell(ctx, pos, entity);
    }

    private EntityFragment spell(SpellContext ctx, VectorFragment pos, Entity entity) throws BlunderException {
        ctx.useMana(this, cost(entity.getPos().distanceTo(new Vec3d(pos.x(), pos.y(), pos.z()))));

        var world = ctx.source().getWorld();
        ProjectileEntity projectile;
        if (entity instanceof ShulkerEntity) {
            projectile = EntityType.SHULKER_BULLET.create(world);
        } else {
            projectile = EntityType.LLAMA_SPIT.create(world);
        }
        projectile.setOwner(entity);
        projectile.setPos(pos.x(), pos.y(), pos.z());
        world.spawnEntity(projectile);
        projectile.setPos(pos.x(), pos.y(), pos.z()); // yes you need to do this again else spit will instantly disappear when 0, 0, 0 has a block on a server. Minecraft WTF
        return EntityFragment.from(projectile);
    }

    private float cost(double dist) {
        return (float) (20 + Math.pow(dist, (dist / 3)));
    }
}