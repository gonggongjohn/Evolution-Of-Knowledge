package com.gonggongjohn.eok.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TEFirstMachine extends TileEntity implements ITickable {
    protected ItemStackHandler gou =new ItemStackHandler();
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            @SuppressWarnings("unchecked")
            T result = (T) gou;
            return result;
        }
        return super.getCapability(capability,facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.gou.deserializeNBT(compound.getCompoundTag("gou"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("gou",this.gou.serializeNBT());
        return super.writeToNBT(compound);
    }
    @Override
    public void update()
    {
        if(!this.world.isRemote)
        {

        }
    }
}
