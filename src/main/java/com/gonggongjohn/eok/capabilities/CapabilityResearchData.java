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
import java.util.ArrayList;

public class CapabilityResearchData {
    public static class Storage implements Capability.IStorage<IResearchData>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IResearchData> capability, IResearchData instance, EnumFacing side){
            NBTTagCompound compound = new NBTTagCompound();
            ArrayList<Integer> finList = instance.getFinishedResearch();
            for(int i = 0; i < finList.size(); i++) {
                compound.setInteger("" + i, finList.get(i));
            }
            return compound;
        }

        @Override
        public void readNBT(Capability<IResearchData> capability, IResearchData instance, EnumFacing side, NBTBase nbt){
            NBTTagCompound compound = (NBTTagCompound) nbt;
            ArrayList<Integer> finList = new ArrayList<Integer>();
            if(compound.getSize() != 0){
                for(int i =0; i < compound.getSize(); i++){
                    finList.add(compound.getInteger("" + i));
                }
            }
            instance.setFinishedResearch(finList);
        }
    }

    public static class Implementation implements IResearchData{
        private ArrayList<Integer> finList = new ArrayList<>();

        @Override
        public ArrayList<Integer> getFinishedResearch(){
            return this.finList;
        }

        @Override
        public void setFinishedResearch(ArrayList<Integer> finList) {
            this.finList = finList;
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
