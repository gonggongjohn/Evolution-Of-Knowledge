package com.gonggongjohn.eok.capabilities;

import com.gonggongjohn.eok.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityConsciousness {
    public static class Storage implements Capability.IStorage<IConsciousness>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IConsciousness> capability, IConsciousness instance, EnumFacing side) {
            NBTTagList list = new NBTTagList();
            NBTTagCompound compound = new NBTTagCompound();
            compound.setFloat("consciousness", instance.getConsciousnessValue());
            list.set(0, compound);
            return list;
        }

        @Override
        public void readNBT(Capability<IConsciousness> capability, IConsciousness instance, EnumFacing side, NBTBase nbt) {
            NBTTagList list = (NBTTagList) nbt;
            NBTTagCompound compound = list.getCompoundTagAt(0);
            float conV = compound.getFloat("consciousness");
            instance.setConsciousnessValue(conV);
        }
    }

    public static class Implementation implements IConsciousness{
        private float conV;

        public Implementation() {
            this.conV = 100.0F;
        }

        @Override
        public float getConsciousnessValue() {
            return this.conV;
        }

        @Override
        public void setConsciousnessValue(float conV) {
            this.conV = conV;
        }

    }

    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>{
        private IConsciousness consciousness = new Implementation();
        private Capability.IStorage<IConsciousness> storage = CapabilityHandler.capConsciousness.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return CapabilityHandler.capConsciousness.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if(CapabilityHandler.capConsciousness.equals(capability)){
                @SuppressWarnings("unchecked")
                T result = (T) consciousness;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("consciousness", storage.writeNBT(CapabilityHandler.capConsciousness, consciousness, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            NBTTagList list = (NBTTagList) nbt.getTag("consciousness");
            storage.readNBT(CapabilityHandler.capConsciousness, consciousness, null, list);
        }
    }
}
