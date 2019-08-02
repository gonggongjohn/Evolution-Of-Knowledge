package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRefractingTelescope extends Container implements IButtonHandler{
    protected Slot paperSlot;
    private ItemStackHandler items = new ItemStackHandler(1);

    public ContainerRefractingTelescope(EntityPlayer player) {
        super();

        this.addSlotToContainer(this.paperSlot = new SlotItemHandler(items, 0, 205, 235){
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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return new ItemStack(ItemHandler.refractingTelescope).isItemEqual(playerIn.getHeldItemMainhand());
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn){
        super.onContainerClosed(playerIn);

        if(playerIn.isServerWorld()){
            ItemStack paperStack = this.paperSlot.getStack();
            if(paperStack != null){
                playerIn.dropItem(paperStack, false);
            }
        }
    }

    public void updateSlot(){
        ItemStack stack = this.paperSlot.getStack();
        if(stack == null| stack.isEmpty()) return;
        NBTTagCompound compound = stack.getTagCompound();
        int temp;
        if(compound == null) {
            temp = 0;
            compound = new NBTTagCompound();
        }
        else temp = compound.getInteger("data.universe");
        compound.setInteger("data.universe", ++temp);
        stack.setTagCompound(compound);
        this.paperSlot.putStack(stack);
    }

    @Override
    public void onButtonPress(int buttonID) {
        System.out.println("1243124213421354235213532151253151");
        if(buttonID == 0){
            updateSlot();
        }
    }
}
