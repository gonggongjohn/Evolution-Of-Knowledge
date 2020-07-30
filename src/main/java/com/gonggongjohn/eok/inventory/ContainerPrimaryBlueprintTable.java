package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.network.PacketInverseBlueprintTable;
import com.gonggongjohn.eok.tile.TEPrimaryBlueprintTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class ContainerPrimaryBlueprintTable extends Container implements ICBTHandler, IClientCBTHandler{
    protected final Slot penSlot;
    protected final Slot blueprintSlot;
    protected final Slot secondaryBlueprintSlot;
    protected TEPrimaryBlueprintTable tileEntity;
    protected EntityPlayer player;
    protected NBTTagCompound componentPool;
    private int penSlotOffsetX = 228;
    private int penSlotOffsetY = 24;
    private int blueprintSlotOffsetX = 228;
    private int blueprintSlotOffsetY = 60;
    private int secondaryBlueprintSlotOffsetX = 29;
    private int secondaryBlueprintSlotOffsetY = 178;
    private int inventorySlotOffsetX = 58;
    private int inventorySlotOffsetY = 178;

    public ContainerPrimaryBlueprintTable(EntityPlayer player, TileEntity tileEntity){
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
        this.secondaryBlueprintSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST), 0, secondaryBlueprintSlotOffsetX, secondaryBlueprintSlotOffsetY){
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
        this.addSlotToContainer(this.secondaryBlueprintSlot);

        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(player.inventory, i, inventorySlotOffsetX + i * 18, inventorySlotOffsetY));
        }

        this.tileEntity = (TEPrimaryBlueprintTable) tileEntity;
        this.player = player;
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
    public void onWriteActive(NBTTagCompound compound) {
        ItemStack blueprintStack = this.blueprintSlot.getStack();
        if (!blueprintStack.isEmpty()) {
            blueprintStack.setTagCompound(compound);
            this.blueprintSlot.putStack(blueprintStack);
        }
    }

    @Override
    public void onSecondaryInput() {
        if(this.secondaryBlueprintSlot.getHasStack()){
            ItemStack stack = this.secondaryBlueprintSlot.getStack();
            NBTTagCompound compound = stack.getTagCompound();
            if(compound != null && compound.hasKey("blueprint.category") && compound.hasKey("blueprint.structure")){
                if(compound.getString("blueprint.category").equals("secondary")){
                    tileEntity.addComponent(compound);
                }
            }
        }
    }

    @Override
    public void onComponentPoolUpdate(NBTTagCompound componentPool) {
        this.componentPool = componentPool;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        this.componentPool = tileEntity.getComponentPool();
        EOK.getNetwork().sendTo(new PacketInverseBlueprintTable(this.componentPool), (EntityPlayerMP) this.player);
    }

    public Slot getPenSlot() {
        return this.penSlot;
    }

    public Slot getBlueprintSlot() {
        return this.blueprintSlot;
    }

    public Slot getSecondaryBlueprintSlot() {
        return this.secondaryBlueprintSlot;
    }

    public NBTTagCompound getComponentPool(){
        return this.componentPool;
    }

}
