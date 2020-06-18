package com.gonggongjohn.eok.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class TEStoneMill extends TileEntity implements ITickable, ILockableContainer {
	
    public float prevRotationAngle = 0F;
    public float rotationAngle = 0F;
    public int numPlayersUsing;
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	
        return super.writeToNBT(compound);
    }

    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1) {
        	
            this.numPlayersUsing = type;
            this.numPlayersUsing++;
            return true;
        }
        else {
        	
            return super.receiveClientEvent(id, type);
        }
    }

    public void openInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            if (this.numPlayersUsing < 0) {
            	
                this.numPlayersUsing = 0;
            }

            //this.numPlayersUsing++;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
        }
    }

    public void closeInventory(EntityPlayer player) {

    }
    
    @Override
    public void update() {

        if(this.numPlayersUsing > 0) {
        	
			this.prevRotationAngle = this.rotationAngle;
	        if(this.rotationAngle >= 1.1F) {
	
	        	this.prevRotationAngle = 0.0F;
	            this.rotationAngle = 0.0F; 
	            this.numPlayersUsing = 0;
	        }
	        else {
	        	
	        	this.rotationAngle += 0.03F;
	        }
        }
    }

	@Override
	public int getSizeInventory() {

		return 0;
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {

		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return false;
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		
		return 0;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public String getName() {

		return null;
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {

		return null;
	}

	@Override
	public String getGuiID() {

		return null;
	}

	@Override
	public boolean isLocked() {

		return false;
	}

	@Override
	public void setLockCode(LockCode code) {
		
	}

	@Override
	public LockCode getLockCode() {
		
		return null;
	}

	public void setCustomInventoryName(String displayName) {
		
	}
}