package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerHayTorchBaseHasHayTorch extends Container {
	
    protected Slot torchSlot;
    
    //private ItemStackHandler items = new ItemStackHandler(1);

    public ContainerHayTorchBaseHasHayTorch(EntityPlayer player, TileEntity tileEntity) {
    	
        super();
        
        torchSlot.putStack(new ItemStack(ItemHandler.hayTorch, 1));
        
        this.addSlotToContainer(this.torchSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), 0, 80, 36) {
            
        	@Override
            public int getItemStackLimit(ItemStack stack) {
        		
                return 1;
            }
        });
        
        for(int i = 0; i < 9; ++i) {
        	
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
		for (int i = 0; i < 9; i++) {
			
			this.addSlotToContainer(new Slot(player.inventory, 9 + i, 8 + 18 * i, 84));
		}
		for (int i = 0; i < 9; i++) {
			
			this.addSlotToContainer(new Slot(player.inventory, 18 + i, 8 + 18 * i, 102));
		}
		for (int i = 0; i < 9; i++) {
			
			this.addSlotToContainer(new Slot(player.inventory, 27 + i, 8 + 18 * i, 120));
		}
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return null;
    }
    
    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
    	
        super.onContainerClosed(playerIn);
            
        /*
        if(itemStack.getItem() == ItemHandler.hayTorch) {
            	
        	
        }
        else {
        	
        }
        */
    }

    public Slot getTorchSlot() {
        return this.torchSlot;
    }
}
