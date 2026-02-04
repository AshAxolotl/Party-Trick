package com.ashaxolotl.partytrick.cca;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ArmorStandScaleComponent implements CommonTickingComponent, AutoSyncedComponent {
    private final ArmorStandEntity entity;
    private double scale = 1.0;

    public ArmorStandScaleComponent(ArmorStandEntity entity) {
        this.entity = entity;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        scale = tag.getDouble("armor_stand_scale");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putDouble("armor_stand_scale", scale);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        PartyEntityComponents.ARMOR_STAND_SCALE.sync(entity);
    }

    @Override
    public void tick() {

    }
}
