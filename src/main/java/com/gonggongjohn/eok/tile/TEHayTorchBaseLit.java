package com.gonggongjohn.eok.tile;

import javax.annotation.Nullable;

import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEHayTorchBaseLit extends TileEntity implements ITickable {
	
    private int sec = 0; 
    private int changeSecond = 0;
    protected ItemStackHandler hayTorch = new ItemStackHandler();
    
    private boolean change(int changeSecond) {
    	
    	if(changeSecond > 10) {
    		
    		return true;
    	}
    	else {
    		
    		return false;
    	}
	
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    	
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
        this.hayTorch.deserializeNBT(compound.getCompoundTag("damage"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	
        super.writeToNBT(compound);
        compound.setTag("damage", this.hayTorch.serializeNBT());
        return super.writeToNBT(compound);
    }
    
    @Override
    public void update() {
    	
        if (!world.isRemote) {
        	
            if (sec < 20) {
            	
            	sec++;
            }
            else {
            	
                sec = 0;
                if(changeSecond < 20) {
                	
    	        	if(hayTorch.getStackInSlot(0) == ItemStack.EMPTY) {
    	        		
    	        		System.out.println("  " + changeSecond);
    	        		changeSecond++;
    	        		
    	            	if(change(changeSecond)) {
    	
    	            		world.setBlockState(getPos(), BlockHandler.blockHayTorchBase.getDefaultState());
    	            	}
    	        	} 
    	        	else if (hayTorch.getStackInSlot(0).getItem() == ItemHandler.driedHay) {
    		    		
    		    		System.out.println("Dired Hay Added");
    		    		
    		    		changeSecond = 0;
    		    		
    		    		hayTorch.setStackInSlot(0, ItemStack.EMPTY);
    		    	}
                }
            }
    	}
    	/*
    	if(hayTorch.getStackInSlot(0) == ItemStack.EMPTY) {
    		
    		System.out.println("Add");

    		//world.setBlockState(getPos(), BlockHandler.blockHayTorchBaseHasHaTorch.getDefaultState());
    		
    		hayTorch.setStackInSlot(0, new ItemStack(ItemHandler.eokSymbol, 1));
    	}
    	*/
    	
    	/*
    	if(hayTorch.getStackInSlot(0) == ItemStack.EMPTY) {
    		
    		System.out.println("Empty");

    		world.setBlockState(getPos(),BlockHandler.blockHayTorchBase.getDefaultState());
    				
    	}
    	*/
    }
}