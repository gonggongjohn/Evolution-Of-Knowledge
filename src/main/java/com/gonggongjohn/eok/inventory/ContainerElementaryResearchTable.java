package com.gonggongjohn.eok.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerElementaryResearchTable extends Container implements IButtonHandler{
    protected Slot paperSlot;
    protected Slot toolSlot;
    private ItemStackHandler items = new ItemStackHandler(2);

    public ContainerElementaryResearchTable(EntityPlayer player) {
        super();

        this.addSlotToContainer(this.paperSlot = new SlotItemHandler(items, 0, 224, 15){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });
        this.addSlotToContainer(this.toolSlot = new SlotItemHandler(items, 1, 224, 46){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });

        for(int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(player.inventory, i, 47 + i * 18, 174));
        }
    }


    @Override
    public void onButtonPress(int buttonID) {

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }
}
