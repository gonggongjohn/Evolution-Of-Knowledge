package com.gonggongjohn.eok.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TESecondaryBlueprintTable extends TileEntity implements ITickable {
    protected final ItemStackHandler penSlot = new ItemStackHandler();
    protected final ItemStackHandler blueprintSlot = new ItemStackHandler();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)){
            T result = (T) penSlot;
            if(facing != null){
                switch (facing){
                    case UP:
                        result = (T) penSlot;
                        break;
                    case DOWN:
                        result = (T) blueprintSlot;
                        break;
                    default:
                        result = (T) penSlot;
                }
            }
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.penSlot.deserializeNBT(compound.getCompoundTag("penSlot"));
        this.blueprintSlot.deserializeNBT(compound.getCompoundTag("blueprintSlot"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        compound.setTag("penSlot", this.penSlot.serializeNBT());
        compound.setTag("blueprintSlot", this.blueprintSlot.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {

    }
}
