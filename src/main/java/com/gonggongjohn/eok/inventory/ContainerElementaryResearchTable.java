package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.tile.TEElementaryResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerElementaryResearchTable extends Container implements IButtonHandler{
    protected Slot paperSlot;
    protected Slot penSlot;
    protected Slot inkSlot;

    protected TEElementaryResearchTable teERT;

    public ContainerElementaryResearchTable(EntityPlayer player, TileEntity tileEntity) {
        super();

        this.addSlotToContainer(this.paperSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN), 0, 229, 12){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });
        this.addSlotToContainer(this.penSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), 0, 229, 36){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });
        this.addSlotToContainer(this.inkSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST), 0, 229, 60){
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });

        for(int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(player.inventory, i, 9 + i * 16, 141));
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
