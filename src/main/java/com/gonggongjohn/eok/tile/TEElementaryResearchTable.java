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
    protected ItemStackHandler penSlot = new ItemStackHandler();
    protected ItemStackHandler inkSlot = new ItemStackHandler();

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
            T result = (T) ((facing == EnumFacing.EAST) ? inkSlot : (facing == EnumFacing.DOWN ? paperSlot : penSlot));
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.paperSlot.deserializeNBT(compound.getCompoundTag("paperSlot"));
        this.penSlot.deserializeNBT(compound.getCompoundTag("penSlot"));
        this.inkSlot.deserializeNBT(compound.getCompoundTag("inkSlot"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("paperSlot", this.paperSlot.serializeNBT());
        compound.setTag("penSlot", this.penSlot.serializeNBT());
        compound.setTag("inkSlot", this.inkSlot.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if(!this.world.isRemote){
            //TODO
        }
    }
}
