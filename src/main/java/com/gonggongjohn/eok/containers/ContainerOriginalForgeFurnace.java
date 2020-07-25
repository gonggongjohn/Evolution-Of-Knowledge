package com.gonggongjohn.eok.containers;

import com.gonggongjohn.eok.api.HeatRegistry;
import com.gonggongjohn.eok.tileEntities.TEOriginalForgeFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOriginalForgeFurnace extends Container {
	TEOriginalForgeFurnace te;
	private float burnTime, fire;

	public float getBurnTime() {
		return burnTime;
	}

	public float getFire() {
		return fire;
	}

	public ContainerOriginalForgeFurnace(TEOriginalForgeFurnace te, EntityPlayer player) {
		this.te = te;
		int i, j;
		for (i = 0; i < 3; i++) {
			this.addSlotToContainer(new Slot(te, i, 8, 21 + i * 21) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem() == Items.coal;
				}

				@Override
				public int getSlotStackLimit() {
					return 1;
				}
			});
		}
		for (i = 0; i < 3; i++) {
			this.addSlotToContainer(new Slot(te, i + 3, 50 + i * 34, 21) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					HeatRegistry registry = HeatRegistry.getInstance();
					return stack != null && registry.findIndex(stack) != null && super.isItemValid(stack);
				}

				@Override
				public int getSlotStackLimit() {
					return 1;
				}
			});
		}
		for (i = 0; i < 3; i++) {
			this.addSlotToContainer(new Slot(te, i + 6, 152, 21 + i * 21));
		}
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		burnTime = te.getBurnTime();
		fire = te.getTemp();
		int i;
		ICrafting crafter;
		for (i = 0; i < crafters.size(); i++) {
			crafter = (ICrafting) crafters.get(i);
			crafter.sendProgressBarUpdate(this, 0, (int) burnTime);
			crafter.sendProgressBarUpdate(this, 1, (int) fire);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
		case 0:
			burnTime = data;
			break;
		case 1:
			fire = data;
			break;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIdx) {
		System.out.println(slotIdx);
		Slot slot = (Slot) this.inventorySlots.get(slotIdx);
		if (slot == null || !slot.getHasStack()) {
			return null;
		}
		ItemStack stack = slot.getStack();
		ItemStack previous = stack.copy();
		int i;
		if (slotIdx < 9) {
			if (!this.mergeItemStack(stack, 9, this.inventorySlots.size(), true)) {
				return null;
			}
		} else {
			if (stack.getItem() == Items.coal) {
				Slot[] slotFuel = { (Slot) inventorySlots.get(0), (Slot) inventorySlots.get(1),
						(Slot) inventorySlots.get(2) };
				for (i = 0; i < 3; i++) {
					if (slotFuel[i].getHasStack()) {
						continue;
					} else {
						ItemStack fuel = stack.copy();
						fuel.stackSize = 1;
						slotFuel[i].putStack(fuel);
						stack.stackSize--;
					}
				}
			} else if (stack.stackSize != 0) {
				if (!this.mergeItemStack(stack, 6, 9, true)) {
					return null;
				}
			}
		}
		if (stack.stackSize == 0) {
			slot.putStack(null);
		} else {
			slot.onSlotChanged();
		}
		if (stack.stackSize == previous.stackSize) {
			return null;
		}
		slot.onPickupFromSlot(player, stack);
		return previous;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}