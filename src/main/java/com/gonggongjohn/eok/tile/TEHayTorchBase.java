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

public class TEHayTorchBase extends TileEntity implements ITickable {
    
    private boolean hasHayTorch = false;
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
    	
    	if(hayTorch.getStackInSlot(0).getItem() == ItemHandler.hayTorch && hasHayTorch == false) {
    		
    		System.out.println("Hay Torch Added");
    		hasHayTorch = true;

    		world.setBlockState(getPos(), BlockHandler.blockHayTorchBaseLit.getDefaultState());
    		
    		hayTorch.setStackInSlot(0, ItemStack.EMPTY);
    	}
    	
        if(!this.world.isRemote) {
        	
        }
    }
}
