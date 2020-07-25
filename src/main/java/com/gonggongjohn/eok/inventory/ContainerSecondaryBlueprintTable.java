package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerSecondaryBlueprintTable extends Container implements ICBTHandler {
    protected final Slot penSlot;
    protected final Slot blueprintSlot;
    private int penSlotOffsetX = 224;
    private int penSlotOffsetY = 29;
    private int blueprintSlotOffsetX = 224;
    private int blueprintSlotOffsetY = 68;
    private int inventorySlotOffsetX = 47;
    private int inventorySlotOffsetY = 177;

    public ContainerSecondaryBlueprintTable(EntityPlayer player, TileEntity tileEntity){
        super();
        this.penSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), 0, penSlotOffsetX, penSlotOffsetY);
        this.blueprintSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN), 0, blueprintSlotOffsetX, blueprintSlotOffsetY){
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() == ItemHandler.bluePrint && super.isItemValid(stack);
            }

            @Override
            public int getItemStackLimit(@Nonnull ItemStack stack) {
                return 1;
            }
        };
        this.addSlotToContainer(this.penSlot);
        this.addSlotToContainer(this.blueprintSlot);

        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(player.inventory, i, inventorySlotOffsetX + i * 18, inventorySlotOffsetY));
        }
    }

    @Override
    public void onWriteActive(NBTTagCompound compound) {
        ItemStack blueprintStack = this.blueprintSlot.getStack();
        if (!blueprintStack.isEmpty()) {
            blueprintStack.setTagCompound(compound);
            this.blueprintSlot.putStack(blueprintStack);
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

    public Slot getPenSlot() {
        return this.penSlot;
    }

    public Slot getBlueprintSlot() {
        return this.blueprintSlot;
    }
}
