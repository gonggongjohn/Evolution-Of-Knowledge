package com.gonggongjohn.eok.tile;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEElementaryResearchTable extends TileEntity implements ITickable {
    protected ItemStackHandler paperInputSlot = new ItemStackHandler();
    protected ItemStackHandler penSlot = new ItemStackHandler();
    protected ItemStackHandler inkSlot = new ItemStackHandler();
    protected ItemStackHandler paperOutputSlot = new ItemStackHandler();

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
            @SuppressWarnings("unchecked")
            T result = (T) inkSlot;
            if(facing != null) {
                switch (facing) {
                    case DOWN:
                        result = (T) paperInputSlot;
                        break;
                    case UP:
                        result = (T) paperOutputSlot;
                        break;
                    case EAST:
                        result = (T) penSlot;
                        break;
                    default:
                        result = (T) inkSlot;
                        break;
                }
            }
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.paperInputSlot.deserializeNBT(compound.getCompoundTag("paperInputSlot"));
        this.penSlot.deserializeNBT(compound.getCompoundTag("penSlot"));
        this.inkSlot.deserializeNBT(compound.getCompoundTag("inkSlot"));
        this.paperOutputSlot.deserializeNBT(compound.getCompoundTag("paperOutputSlot"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("paperInputSlot", this.paperInputSlot.serializeNBT());
        compound.setTag("penSlot", this.penSlot.serializeNBT());
        compound.setTag("inkSlot", this.inkSlot.serializeNBT());
        compound.setTag("paperOutputSlot", this.paperOutputSlot.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if(!this.world.isRemote){
            //TODO
        }
    }
}
