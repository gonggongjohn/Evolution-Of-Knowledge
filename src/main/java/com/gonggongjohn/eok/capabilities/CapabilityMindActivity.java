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

public class CapabilityMindActivity {
    public static class Storage implements Capability.IStorage<IMindActivity>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IMindActivity> capability, IMindActivity instance, EnumFacing side) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setDouble("mindActivity", instance.getMindActivityValue());
            return compound;
        }

        @Override
        public void readNBT(Capability<IMindActivity> capability, IMindActivity instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound compound =(NBTTagCompound) nbt;
            double maV = 100.0D;
            if(compound.hasKey("mindActivity")) {
                maV = compound.getDouble("mindActivity");
            }
            instance.setMindActivityValue(maV);
        }
    }

    public static class Implementation implements IMindActivity{
        private double maV = 100.0D;


        @Override
        public double getMindActivityValue() {
            return this.maV;
        }

        @Override
        public void setMindActivityValue(double maV) {
            this.maV = maV;
        }
    }

    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider {
        private IMindActivity mindActivity = new Implementation();
        private Capability.IStorage<IMindActivity> storage = CapabilityHandler.capMindActivity.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return CapabilityHandler.capMindActivity.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if(CapabilityHandler.capMindActivity.equals(capability)){
                @SuppressWarnings("unchecked")
                T result = (T) mindActivity;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("mindActivity", storage.writeNBT(CapabilityHandler.capMindActivity, mindActivity, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            NBTTagCompound compound = nbt.getCompoundTag("mindActivity");
            storage.readNBT(CapabilityHandler.capMindActivity, mindActivity, null, compound);
        }
    }
}
