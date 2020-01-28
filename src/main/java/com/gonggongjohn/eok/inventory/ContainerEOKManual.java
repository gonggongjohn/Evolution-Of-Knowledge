package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerEOKManual extends Container implements IButtonHandler {
    
	public int totalPages;
	public int currentPage = 1;
	
	public String photoPath;

    public ContainerEOKManual(EntityPlayer player) {
    	
        super();
    }
    
	public String getPath(int page) {

		String result = "";

		return result;
	}

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
    	
        return new ItemStack(ItemHandler.EOKManual).isItemEqual(playerIn.getHeldItemMainhand());
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
    	
        super.onContainerClosed(playerIn);
    }

    @Override
    public void onButtonPress(int buttonID) {
    	
        if(buttonID == 0) {
        	

        }
    }
    
	public void previousPage() {
		if (totalPages == 0)
			return;

		if (currentPage <= 1) {
			currentPage = 1;
			return;
		}

		if (currentPage > totalPages) {
			currentPage = totalPages;
			return;
		}

		currentPage--;
	}

	public void nextPage() {
		if (totalPages == 0)
			return;

		if (currentPage >= totalPages) {
			currentPage = totalPages;
			return;
		}

		if (currentPage < 1) {
			currentPage = 1;
			return;
		}

		currentPage++;
	}
}
