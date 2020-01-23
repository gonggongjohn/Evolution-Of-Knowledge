package com.gonggongjohn.eok.capabilities;

import com.gonggongjohn.eok.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class CapabilityInspirations {
    public static class Storage implements Capability.IStorage<IInspirations>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IInspirations> capability, IInspirations instance, EnumFacing side) {
            NBTTagCompound compound = new NBTTagCompound();
            int[] insStatus = instance.getInspirationsStatus();
            for(int i = 0; i < insStatus.length; i++){
                compound.setInteger("" + i, insStatus[i]);
            }
            return compound;
        }

        @Override
        public void readNBT(Capability<IInspirations> capability, IInspirations instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            int[] insStatus = new int[100];
            Arrays.fill(insStatus, 0);
            if(compound != null && compound.getSize() != 0){
                for(int i = 0; i < compound.getSize(); i++){
                    insStatus[i] = compound.getInteger("" + i);
                }
            }
            instance.setInspirationsStatus(insStatus);
        }
    }

    public static class Implementation implements IInspirations{
        private int[] insStatus = new int[100];

        @Override
        public int[] getInspirationsStatus() {
            return this.insStatus;
        }

        @Override
        public void setInspirationsStatus(int[] insStatus) {
            this.insStatus = insStatus;
        }
    }

    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider{
        private IInspirations inspirations = new Implementation();
        private Capability.IStorage<IInspirations> storage = CapabilityHandler.capInspirations.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return CapabilityHandler.capInspirations.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if(CapabilityHandler.capInspirations.equals(capability)){
                @SuppressWarnings("unchecked")
                T result = (T) inspirations;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("inspirations", storage.writeNBT(CapabilityHandler.capInspirations, inspirations, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            NBTTagCompound compound = nbt.getCompoundTag("inspirations");
            storage.readNBT(CapabilityHandler.capInspirations, inspirations, null, compound);
        }
    }
}
