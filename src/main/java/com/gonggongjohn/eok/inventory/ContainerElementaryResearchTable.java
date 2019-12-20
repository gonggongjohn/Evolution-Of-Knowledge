package com.gonggongjohn.eok.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
            this.addSlotToContainer(new Slot(player.inventory, i, 6 + i * 18, 147));
        }
    }


    @Override
    public void onButtonPress(int activeResearchID) {
        ItemStack stack = this.paperSlot.getStack();
        if(stack == null| stack.isEmpty()) return;
        NBTTagCompound compound = stack.getTagCompound();
        int[] temp,extensionTemp;
        if(compound == null) {
            extensionTemp = new int[]{activeResearchID};
            compound = new NBTTagCompound();
        }
        else{
            temp = compound.getIntArray("data.research");
            extensionTemp = new int[temp.length + 1];
            for(int i = 0; i < temp.length; i++) extensionTemp[i]=temp[i];
            extensionTemp[temp.length] = activeResearchID;
        }
        compound.setIntArray("data.research", extensionTemp);
        stack.setTagCompound(compound);
        this.paperSlot.putStack(stack);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }

    public Slot getPaperSlot(){
        return this.paperSlot;
    }
}
