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

public class CapabilitySeconds {
    public static class Storage implements Capability.IStorage<ISeconds>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<ISeconds> capability, ISeconds instance, EnumFacing side)
        {
            NBTTagCompound compound= new NBTTagCompound();
            compound.setInteger("seconds",instance.getSecondsValue());
            return compound;
        }
        @Override
        public void readNBT(Capability<ISeconds> capability, ISeconds instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound =new NBTTagCompound();
            int SecV=59;
            if(compound.hasKey("seconds"))
                SecV=compound.getInteger("seconds");
            instance.setSecondsValue(SecV);
        }
    }

    public static class Implementation implements ISeconds
    {
        private int secV=59;
        @Override
        public int getSecondsValue()
        {
            return this.secV;
        }
        @Override
        public void setSecondsValue(int secV)
        {
            this.secV=secV;
        }
    }
    public static class ProvidePlayer implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private ISeconds seconds=new Implementation();
        private Capability.IStorage<ISeconds> storage= CapabilityHandler.capSeconds.getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            return CapabilityHandler.capSeconds.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
        {
           if(CapabilityHandler.capSeconds.equals(capability))
           {
               @SuppressWarnings("unchecked")
                       T result=(T)seconds;
               return result;
           }
           return null;
        }
        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound compound=new NBTTagCompound();
            compound.setTag("seconds",storage.writeNBT(CapabilityHandler.capSeconds,seconds,null));
            return compound;
        }
        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            NBTTagCompound compound=nbt.getCompoundTag("seconds");
            storage.readNBT(CapabilityHandler.capSeconds,seconds,null,compound);
        }
    }
}
