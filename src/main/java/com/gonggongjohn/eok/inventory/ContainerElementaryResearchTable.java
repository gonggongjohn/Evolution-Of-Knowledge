package com.gonggongjohn.eok.inventory;

import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.tile.TEElementaryResearchTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerElementaryResearchTable extends Container implements IButtonHandler {
    protected Slot paperInputSlot;
    protected Slot penSlot;
    protected Slot inkSlot;
    protected Slot paperOutputSlot;

    protected TEElementaryResearchTable teERT;

    public ContainerElementaryResearchTable(EntityPlayer player, TileEntity tileEntity) {
        super();

        this.addSlotToContainer(this.paperInputSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN), 0, 229, 12){
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() == ItemHandler.papyrus && super.isItemValid(stack);
            }
        });
        this.addSlotToContainer(this.penSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST), 0, 229, 36) {
            @Override
            public int getItemStackLimit(ItemStack stack) {
                return 1;
            }
        });
        this.addSlotToContainer(this.inkSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.WEST), 0, 229, 60) {
            @Override
            public int getItemStackLimit(ItemStack stack) {
                return 1;
            }
        });
        this.addSlotToContainer(this.paperOutputSlot = new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), 0, 229, 87) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return false;
            }
        });

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 9 + i * 16, 141));
        }
    }


    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        if (slotID == this.paperInputSlot.slotNumber) {
            ItemStack stackOutput = this.paperOutputSlot.getStack();
            //Proceed inputSlot
            if (stack.getItemDamage() == 0) super.putStackInSlot(slotID, stack);
                //Dump procedure when used paper is in inputSlot
            else if (stack.getItemDamage() == 1) {
                NBTTagCompound tempCompound = new NBTTagCompound();
                if (stackOutput.isEmpty()) {
                    ItemStack tempStack = new ItemStack(ItemHandler.papyrus, 1, 2);
                    tempCompound.setInteger("pageNum", 1);
                    if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("data.research")) {
                        int[] tempResearches = stack.getTagCompound().getIntArray("data.research");
                        tempCompound.setIntArray("data.research", tempResearches);
                    }
                    tempStack.setTagCompound(tempCompound);
                    stackOutput = tempStack;
                } else {
                    if (stackOutput.getTagCompound() != null) {
                        int tempPageNum = stackOutput.getTagCompound().getInteger("pageNum");
                        int[] tempResearchesOutput = stackOutput.getTagCompound().getIntArray("data.research");
                        tempCompound.setInteger("pageNum", tempPageNum + 1);
                        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("data.research")) {
                            int[] tempResearchesInput = stack.getTagCompound().getIntArray("data.research");
                            int[] tempResult = new int[tempResearchesInput.length + tempResearchesOutput.length];
                            System.arraycopy(tempResearchesOutput, 0, tempResult, 0, tempResearchesInput.length);
                            System.arraycopy(tempResearchesInput, 0, tempResult, tempResearchesOutput.length, tempResearchesInput.length);
                            tempCompound.setIntArray("data.research", tempResult);
                        }
                        stackOutput.setTagCompound(tempCompound);
                    }
                }
            }
            //Dump procedure when used paper pile is in inputSlot
            else if (stack.getItemDamage() == 2) {
                NBTTagCompound tempCompound = new NBTTagCompound();
                if (stackOutput.isEmpty()) {
                    ItemStack tempStack = new ItemStack(ItemHandler.papyrus, 1, 2);
                    if (stack.getTagCompound() != null) {
                        tempCompound.setInteger("pageNum", stack.getTagCompound().getInteger("pageNum"));
                        if (stack.getTagCompound().hasKey("data.research")) {
                            int[] tempResearches = stack.getTagCompound().getIntArray("data.research");
                            tempCompound.setIntArray("data.research", tempResearches);
                        }
                        tempStack.setTagCompound(tempCompound);
                        stackOutput = tempStack;
                    }
                } else {
                    if (stackOutput.getTagCompound() != null && stack.getTagCompound() != null) {
                        int tempPageNumInput = stack.getTagCompound().getInteger("pageNum");
                        int tempPageNumOutput = stackOutput.getTagCompound().getInteger("pageNum");
                        int[] tempResearchesOutput = stackOutput.getTagCompound().getIntArray("data.research");
                        tempCompound.setInteger("pageNum", tempPageNumInput + tempPageNumOutput);
                        if (stack.getTagCompound().hasKey("data.research")) {
                            int[] tempResearchesInput = stack.getTagCompound().getIntArray("data.research");
                            int[] tempResult = new int[tempResearchesInput.length + tempResearchesOutput.length];
                            System.arraycopy(tempResearchesOutput, 0, tempResult, 0, tempResearchesInput.length);
                            System.arraycopy(tempResearchesInput, 0, tempResult, tempResearchesOutput.length, tempResearchesInput.length);
                            tempCompound.setIntArray("data.research", tempResult);
                        }
                        stackOutput.setTagCompound(tempCompound);
                    }
                }
            }
        } else super.putStackInSlot(slotID, stack);
    }

    @Override
    public void onButtonPress(int activeResearchID) {
        ItemStack stackInput = this.paperInputSlot.getStack();
        ItemStack stackOutput = this.paperOutputSlot.getStack();
        NBTTagCompound compoundOutput;
        if (stackInput.isEmpty() && stackOutput.isEmpty()) return;
        if (!stackOutput.isEmpty()) {
            compoundOutput = stackOutput.getTagCompound();
            if(compoundOutput != null) {
                int pageNum = compoundOutput.getInteger("pageNum");
                int[] researchesOrigin = compoundOutput.getIntArray("data.research");
                if (researchesOrigin.length < pageNum * 10) {
                    int[] researchesExtension = new int[researchesOrigin.length + 1];
                    System.arraycopy(researchesOrigin, 0, researchesExtension, 0, researchesOrigin.length);
                    researchesExtension[researchesOrigin.length] = activeResearchID;
                    compoundOutput.setIntArray("data.research", researchesExtension);
                    stackOutput.setTagCompound(compoundOutput);
                    this.paperOutputSlot.putStack(stackOutput);
                } else {
                    if(questInputSlot(stackInput)){
                        compoundOutput.setInteger("pageNum", pageNum + 1);
                        int[] researchesExtension = new int[researchesOrigin.length + 1];
                        System.arraycopy(researchesOrigin, 0, researchesExtension, 0, researchesOrigin.length);
                        researchesExtension[researchesOrigin.length] = activeResearchID;
                        compoundOutput.setIntArray("data.research", researchesExtension);
                        stackOutput.setTagCompound(compoundOutput);
                        this.paperOutputSlot.putStack(stackOutput);
                        stackInput.splitStack(1);
                        this.paperInputSlot.putStack(stackInput);
                    }
                }
            }
        }
        else {
            if(questInputSlot(stackInput)){
                stackOutput = new ItemStack(ItemHandler.papyrus, 1, 2);
                compoundOutput = new NBTTagCompound();
                compoundOutput.setInteger("pageNum", 1);
                compoundOutput.setIntArray("data.research", new int[]{activeResearchID});
                stackOutput.setTagCompound(compoundOutput);
                this.paperOutputSlot.putStack(stackOutput);
                stackInput.splitStack(1);
                this.paperInputSlot.putStack(stackInput);
            }
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

    public Slot getPaperInputSlot() {
        return this.paperInputSlot;
    }

    public Slot getPaperOutputSlot() {
        return this.paperOutputSlot;
    }

    private boolean questInputSlot(ItemStack inputStack) {
        return !inputStack.isEmpty() && inputStack.getItemDamage() == 0;
    }
}
