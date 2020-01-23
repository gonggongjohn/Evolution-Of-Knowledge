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

public class CapabilityAnotherSeconds {
	
    public static class Storage implements Capability.IStorage<IAnotherSeconds>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IAnotherSeconds> capability, IAnotherSeconds instance, EnumFacing side)
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("damage", instance.getAnotherSecondsValue());
            return compound;
        }
        @Override
        public void readNBT(Capability<IAnotherSeconds> capability, IAnotherSeconds instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound = new NBTTagCompound();
            int AnotherSecV = 59;
            if(compound.hasKey("damage"))
            	AnotherSecV = compound.getInteger("damage");
            instance.setAnotherSecondsValue(AnotherSecV);
        }
    }

    public static class Implementation implements IAnotherSeconds
    {
        private int AnotherSecV = 59;
        @Override
        public int getAnotherSecondsValue()
        {
            return this.AnotherSecV;
        }
        @Override
        public void setAnotherSecondsValue(int AnotherSecV)
        {
            this.AnotherSecV = AnotherSecV;
        }
    }
    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private IAnotherSeconds anotherSeconds = new Implementation();
        private Capability.IStorage<IAnotherSeconds> storage = CapabilityHandler.capAnotherSeconds.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            return CapabilityHandler.capAnotherSeconds.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
        {
           if(CapabilityHandler.capAnotherSeconds.equals(capability))
           {
               @SuppressWarnings("unchecked")
                       T result=(T)anotherSeconds;
               return result;
           }
           return null;
        }
        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("damage", storage.writeNBT(CapabilityHandler.capAnotherSeconds, anotherSeconds, null));
            return compound;
        }
        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            NBTTagCompound compound = nbt.getCompoundTag("damage");
            storage.readNBT(CapabilityHandler.capAnotherSeconds, anotherSeconds, null, compound);
        }
    }
}
