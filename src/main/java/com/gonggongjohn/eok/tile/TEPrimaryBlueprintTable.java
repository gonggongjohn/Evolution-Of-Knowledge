package com.gonggongjohn.eok.tile;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TEPrimaryBlueprintTable extends TileEntity implements ITickable {
    protected final ItemStackHandler penSlot = new ItemStackHandler();
    protected final ItemStackHandler blueprintSlot = new ItemStackHandler();
    protected final ItemStackHandler secondaryBlueprintSlot = new ItemStackHandler();
    protected NBTTagCompound componentPool;

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)){
            T result = (T) penSlot;
            if(facing != null){
                switch (facing){
                    case UP:
                        result = (T) penSlot;
                        break;
                    case DOWN:
                        result = (T) blueprintSlot;
                        break;
                    case EAST:
                        result = (T) secondaryBlueprintSlot;
                        break;
                    default:
                        result = (T) penSlot;
                }
            }
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.penSlot.deserializeNBT(compound.getCompoundTag("penSlot"));
        this.blueprintSlot.deserializeNBT(compound.getCompoundTag("blueprintSlot"));
        this.secondaryBlueprintSlot.deserializeNBT(compound.getCompoundTag("secondaryBlueprintSlot"));
        this.componentPool = compound.getCompoundTag("componentPool");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        compound.setTag("penSlot", this.penSlot.serializeNBT());
        compound.setTag("blueprintSlot", this.blueprintSlot.serializeNBT());
        compound.setTag("secondaryBlueprintSlot", this.secondaryBlueprintSlot.serializeNBT());
        compound.setTag("componentPool", this.componentPool);
        return super.writeToNBT(compound);
    }

    public void addComponent(NBTTagCompound compound){
        if(this.componentPool != null && this.componentPool.hasKey("componentList")) {
            NBTTagList list = (NBTTagList) this.componentPool.getTag("componentList");
            list.appendTag(compound);
            this.componentPool.setTag("componentList", list);
        }
        else{
            this.componentPool = new NBTTagCompound();
            NBTTagList list = new NBTTagList();
            list.appendTag(compound);
            this.componentPool.setTag("componentList", list);
        }
        markDirty();
    }

    public NBTTagCompound getComponentPool(){
        return this.componentPool;
    }

    @Override
    public void update() {
        //TODO
    }
}
