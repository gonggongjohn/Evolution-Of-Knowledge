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

public class CapabilityHayTorchBase {
	
    public static class Storage implements Capability.IStorage<IHayTorch> {
    	
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IHayTorch> capability, IHayTorch instance, EnumFacing side) {
        	
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("HayTorchState",instance.getHayTorchState());
            return compound;
        }
        
        @Override
        public void readNBT(Capability<IHayTorch> capability, IHayTorch instance, EnumFacing side, NBTBase nbt) {
        	
            NBTTagCompound compound = new NBTTagCompound();
            int hayTorchState = 0;
            if(compound.hasKey("hayTorchState")) {
            	
                hayTorchState = compound.getInteger("hayTorchState");
            }
            instance.setHayTorchState(hayTorchState);
        }
    }

    public static class Implementation implements IHayTorch {
    	
        private int hayTorchState = 0;
        
        @Override
        public int getHayTorchState() {
        	
            return this.hayTorchState;
        }
        
        @Override
        public void setHayTorchState(int hayTorchState) {
        	
            this.hayTorchState = hayTorchState;
        }
    }
    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider {
    	
        private IHayTorch hayTorchState = new Implementation();
        private Capability.IStorage<IHayTorch> storage = CapabilityHandler.capHayTorchBase.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        	
            return CapabilityHandler.capHayTorchBase.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        	
           if(CapabilityHandler.capHayTorchBase.equals(capability)) {
        	   
               @SuppressWarnings("unchecked")
                       T result = (T)hayTorchState;
               return result;
           }
           return null;
        }
        @Override
        public NBTTagCompound serializeNBT() {
        	
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("hayTorchState",storage.writeNBT(CapabilityHandler.capHayTorchBase, hayTorchState, null));
            return compound;
        }
        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
        	
            NBTTagCompound compound = nbt.getCompoundTag("hayTorchState");
            storage.readNBT(CapabilityHandler.capHayTorchBase, hayTorchState, null, compound);
        }
    }
}
