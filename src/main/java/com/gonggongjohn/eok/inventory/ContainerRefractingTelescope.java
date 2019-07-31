package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRefractingTelescope extends Container {
    protected Slot paperSlot;
    private ItemStackHandler items = new ItemStackHandler(1);

    public ContainerRefractingTelescope(EntityPlayer player) {
        super();

        this.addSlotToContainer(this.paperSlot = new SlotItemHandler(items, 0, 18, 19){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });

        for(int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(player.inventory, i, 46 + i * 18, 214));
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

    public Slot getPaperSlot(){
        return this.paperSlot;
    }
}
