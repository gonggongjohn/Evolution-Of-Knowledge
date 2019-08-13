package com.gonggongjohn.eok.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TEElementaryResearchTable extends TileEntity implements ITickable {
    protected ItemStackHandler paperSlot = new ItemStackHandler();
    protected ItemStackHandler toolSlot = new ItemStackHandler();

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
            T result = (T) (facing == EnumFacing.DOWN ? paperSlot : toolSlot);
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.paperSlot.deserializeNBT(compound.getCompoundTag("paperSlot"));
        this.toolSlot.deserializeNBT(compound.getCompoundTag("toolSlot"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("paperSlot", this.paperSlot.serializeNBT());
        compound.setTag("toolSlot", this.toolSlot.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if(!this.world.isRemote){
            //TODO
        }
    }
}
