package com.ashaxolotl.partytrick.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.world.World;

public class PartyDisplayEntity extends DisplayEntity {
//    public final int Temporary;

    public PartyDisplayEntity(EntityType<?> entityType, World world, boolean temporary) {
        super(entityType, world);
//        Temporary = temporary;
    }

    @Override
    protected void refreshData(boolean shouldLerp, float lerpProgress) {

    }


    // Kinda cursed ig
    public static class BlockDisplayEntity extends DisplayEntity.BlockDisplayEntity {
        public BlockDisplayEntity(EntityType<?> entityType, World world) {
            super(entityType, world);
        }
    }

    public static class ItemDisplayEntity extends DisplayEntity.ItemDisplayEntity {
        public ItemDisplayEntity(EntityType<?> entityType, World world) {
            super(entityType, world);
        }
    }

    public static class TextDisplayEntity extends DisplayEntity.TextDisplayEntity {
        public TextDisplayEntity(EntityType<?> entityType, World world) {
            super(entityType, world);
        }
    }
}
