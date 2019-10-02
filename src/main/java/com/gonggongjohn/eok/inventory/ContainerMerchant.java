package com.gonggongjohn.eok.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMerchant extends Container {
	
	private ItemStackHandler items = new ItemStackHandler(3);
	
	protected Slot slot1 = new SlotItemHandler(items, 0, 129, 27) {

		@Override
		public boolean isItemValid(ItemStack stack) {
			return true;
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 64;
		}
		
	};
	
	protected Slot slot2;
	protected Slot slot3;
	

	public ContainerMerchant(EntityPlayer player) {
		super();

		this.addSlotToContainer(slot1);
		

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO 自动生成的方法存根
		return true;
	}

}
