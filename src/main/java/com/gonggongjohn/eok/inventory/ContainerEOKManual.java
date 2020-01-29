package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerEOKManual extends Container {
	
    public ContainerEOKManual(EntityPlayer player) {
    	
        super();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
    	
        return new ItemStack(ItemHandler.EOKManual).isItemEqual(playerIn.getHeldItemMainhand());
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
    	
        super.onContainerClosed(playerIn);
    }
}