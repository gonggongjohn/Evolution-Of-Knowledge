package com.gonggongjohn.eok.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;



public class ContainerFirstMachine extends Container implements IButtonHandler {
    protected Slot gouSlot;
    private ItemStackHandler items=new ItemStackHandler(1);

    public ContainerFirstMachine(EntityPlayer player)
    {
        super();
        this.addSlotToContainer(this.gouSlot = new SlotItemHandler(items, 0, 224, 15){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });
        for(int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(player.inventory, i, 40 + i * 18, 235));
        }
    }
    @Override
    public void onButtonPress(int buttonID)
    {
        if(buttonID == 0){
            //updateSlot();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return ItemStack.EMPTY;
    }
    @Override
    public void onContainerClosed(EntityPlayer playerIn){
        super.onContainerClosed(playerIn);

        if(playerIn.isServerWorld()){
            ItemStack gouStack = this.gouSlot.getStack();
            if(gouStack != ItemStack.EMPTY){
                playerIn.dropItem(gouStack, false);
            }
        }
    }

    public Slot getGouSlot(){return this.gouSlot;}
}

