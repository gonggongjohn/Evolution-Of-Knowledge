package com.gonggongjohn.eok.tile;

import javax.annotation.Nullable;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEHayTorchBase extends TileEntity implements ITickable {
    
    protected ItemStackHandler hayTorch = new ItemStackHandler();
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
        	
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    	
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
        	
            @SuppressWarnings("unchecked")
            T result = (T) hayTorch;
            return result;
        }
        return super.getCapability(capability,facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	
        super.readFromNBT(compound);
        this.hayTorch.deserializeNBT(compound.getCompoundTag("hayTorch"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	
        super.writeToNBT(compound);
        compound.setTag("hayTorch", this.hayTorch.serializeNBT());
        return super.writeToNBT(compound);
    }
    
    @Override
    public void update() {
    	
    	if(hayTorch.getStackInSlot(0).getItem() == ItemHandler.hayTorch) {
    		
    		System.out.println("hayTorch");
    		//变成桌子哈哈哈
    		world.setBlockState(getPos(),BlockHandler.blockStoneTable.getDefaultState());
    	}
    	
        if(!this.world.isRemote) {
        	
        }
    }
}
	
	
	
	/*
	
    private int sec=0,drystate=0,decomposedstate=0;
    private boolean canDry()
    {
        return ((world.canSeeSky(getPos().up()))&&(!world.isRainingAt(getPos().up())));
    }
    @Override
    public void update() {
    	
    	int hayTorchState = 0;
    	
    	
    	
    	/*
    	
        final int dryseconds=5;//晾干时间（单位：秒）
        final int decomposedseconds=5;//腐烂时间（单位：秒）
        
        if (!world.isRemote) {
            if (sec < 20) sec++;
            else {
                sec = 0;
                if (this.canDry()) {
                    if (drystate < dryseconds) {
                        ++drystate;
                    } else {
                        drystate = 0;
                        world.setBlockState(getPos(), BlockHandler.blockStoneTable.getDefaultState());
                    }
                }
                else
                    if(decomposedstate<decomposedseconds){++decomposedstate;}
                    else
                    {
                        decomposedstate=0;
                        world.setBlockState(getPos(),BlockHandler.blockStoneTable.getDefaultState());
                    }
            }
        }
        */