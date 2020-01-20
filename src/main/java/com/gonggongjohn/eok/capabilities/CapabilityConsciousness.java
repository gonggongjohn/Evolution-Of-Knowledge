package com.gonggongjohn.eok.capabilities;

import com.gonggongjohn.eok.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityConsciousness {
    public static class Storage implements Capability.IStorage<IConsciousness>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IConsciousness> capability, IConsciousness instance, EnumFacing side) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setDouble("consciousness", instance.getConsciousnessValue());
            return compound;
        }

        @Override
        public void readNBT(Capability<IConsciousness> capability, IConsciousness instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound compound =(NBTTagCompound) nbt;
            double conV = 100.0D;
            if(compound.hasKey("consciousness")) {
                conV = compound.getDouble("consciousness");
            }
            instance.setConsciousnessValue(conV);
        }
    }

    public static class Implementation implements IConsciousness{
        private double conV = 100.0D;

        @Override
        public double getConsciousnessValue() {
            return this.conV;
        }

        @Override
        public void setConsciousnessValue(double conV) {
            this.conV = conV;
        }

    }

    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider {
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
            NBTTagCompound compound = nbt.getCompoundTag("consciousness");
            storage.readNBT(CapabilityHandler.capConsciousness, consciousness, null, compound);
        }
    }
}
