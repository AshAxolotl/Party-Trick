package com.ashaxolotl.partytrick.entity;

import com.ashaxolotl.partytrick.PartyTrick;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class PartyBlockDisplayEntity extends DisplayEntity.BlockDisplayEntity {
//    public final boolean Temporary; TODO
    public int timer = 0;

    public PartyBlockDisplayEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();


        if (this.getWorld().isClient ) {
            if (this.interpolationTarget == null) {
                var transform = getTransformation(this.getDataTracker());
                this.setPosition(getPos().add(new Vec3d(transform.getTranslation().x, transform.getTranslation().y, transform.getTranslation().z)));
                this.setTransformation(new AffineTransformation(new Vector3f(), transform.getLeftRotation(), transform.getScale(), transform.getRightRotation()));
            }
        }
    }
}
