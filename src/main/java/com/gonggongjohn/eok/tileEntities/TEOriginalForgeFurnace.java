package com.gonggongjohn.eok.tileEntities;

import com.gonggongjohn.eok.api.HeatIndex;
import com.gonggongjohn.eok.api.HeatRegistry;
import com.gonggongjohn.eok.api.HeatableTool;
import com.gonggongjohn.eok.handlers.HeatableItemHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TEOriginalForgeFurnace extends TileEntity implements IInventory {
	private ItemStack[] items = new ItemStack[9];
	private float fire = 20;
	private int burnTime;

	public float getTemp() {
		return fire;
	}

	public float getBurnTime() {
		return burnTime;
	}

	private void heatItem(int idx) {
		if (items[idx] == null) {
			return;
		}
		HeatRegistry registry = HeatRegistry.getInstance();
		HeatIndex index = registry.findIndex(items[idx]);
		if (index == null) {
			return;
		}
		if (!index.hasOutput()) {
			return;
		}
		float temp = HeatableTool.getTemperature(items[idx]);
		float delta = HeatableTool.getIncreaseTemperature(items[idx], 5, fire);
		if (fire > temp) {
			temp += delta;
			if (temp > fire) {
				temp = fire;
			}
		} else if (fire < temp) {
			temp -= delta / 5;
			if (temp < fire) {
				temp = fire;
			}
		}
		HeatableTool.setTemperature(items[idx], temp);
	}

	private void recipeUpdate(int idx) {
		if (!HeatableTool.isMelt(items[idx])) {
			return;
		}
		HeatRegistry registry = HeatRegistry.getInstance();
		float temp = HeatableTool.getTemperature(items[idx]);
		ItemStack stack = registry.findIndex(items[idx]).output;
		Item item = stack.getItem();
		if (item == HeatableItemHandler.melt) {
			items[idx] = null;
		} else {
			items[idx] = new ItemStack(item, stack.stackSize);
			if (registry.findIndex(items[idx]) != null) {
				items[idx] = HeatableTool.setTemperature(items[idx], temp);
			}
		}
	}

	@Override
	public void updateEntity() {
		int i;
		if (items[0] != null && items[1] == null) {
			items[1] = items[0];
			items[0] = null;
		}
		if (items[1] != null && items[2] == null) {
			items[2] = items[1];
			items[1] = null;
		}
		if (burnTime <= 0 && items[2] != null) {
			items[2] = null;
			burnTime = 1600;
		}
		for (i = 3; i < 6; i++) {
			heatItem(i);
			recipeUpdate(i);
		}
		for (i = 6; i < 9; i++) {
			items[i] = HeatableTool.updateTemperature(items[i]);
		}
		if (burnTime > 0) {
			fire += 0.5;
			burnTime--;
		} else {
			fire -= 0.1;
		}
		if (fire < 20) {
			fire = 20;
		}
		if (fire > 900) {
			fire = 900;
		}
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (items[slot] != null) {
			ItemStack stack;
			if (items[slot].stackSize <= amount) {
				stack = items[slot];
				items[slot] = null;
				return stack;
			}
			stack = items[slot].splitStack(amount);
			if (items[slot].stackSize == 0) {
				items[slot] = null;
			}
			return stack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		items[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return "container.originalForgeFurnace";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		items = new ItemStack[getSizeInventory()];
		int i;
		byte b;
		for (i = 0; i < list.tagCount(); i++) {
			NBTTagCompound comp = list.getCompoundTagAt(i);
			b = comp.getByte("Slot");
			if (b >= 0 && b < items.length) {
				items[b] = ItemStack.loadItemStackFromNBT(comp);
			}
		}
		fire = nbt.getFloat("fire");
		burnTime = nbt.getInteger("burnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		int i;
		for (i = 0; i < items.length; ++i) {
			if (items[i] != null) {
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Slot", (byte) i);
				items[i].writeToNBT(comp);
				list.appendTag(comp);
			}
		}
		nbt.setTag("Items", list);
		nbt.setFloat("fire", fire);
		nbt.setInteger("burnTime", burnTime);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
}