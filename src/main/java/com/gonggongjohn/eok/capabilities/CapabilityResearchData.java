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

public class CapabilityResearchData {
    public static class Storage implements Capability.IStorage<IResearchData>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IResearchData> capability, IResearchData instance, EnumFacing side){
            NBTTagCompound compound = new NBTTagCompound();
            compound.setIntArray("finishedResearch", instance.getFinishedResearch());
            return compound;
        }

        @Override
        public void readNBT(Capability<IResearchData> capability, IResearchData instance, EnumFacing side, NBTBase nbt){
            NBTTagCompound compound = (NBTTagCompound) nbt;
            int[] finArray = new int[100];
            if(compound.hasKey("finishedResearch")){
                finArray = compound.getIntArray("finishedResearch");
            }
            instance.setFinishedResearch(finArray);
        }
    }

    public static class Implementation implements IResearchData{
        private int[] finArray;

        @Override
        public int[] getFinishedResearch(){
            return this.finArray;
        }

        @Override
        public void setFinishedResearch(int[] finArray) {
            this.finArray = finArray;
        }
    }

    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider{
        private IResearchData researchData = new Implementation();
        private Capability.IStorage<IResearchData> storage = CapabilityHandler.capResearchData.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return CapabilityHandler.capResearchData.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if(CapabilityHandler.capResearchData.equals(capability)){
                @SuppressWarnings("unchecked")
                T result = (T) researchData;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("finishedResearch", storage.writeNBT(CapabilityHandler.capResearchData, researchData, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            NBTTagCompound compound = nbt.getCompoundTag("finishedResearch");
            storage.readNBT(CapabilityHandler.capResearchData, researchData, null, compound);
        }
    }
}
