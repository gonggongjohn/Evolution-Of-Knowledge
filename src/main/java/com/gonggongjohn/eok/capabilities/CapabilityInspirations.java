package com.gonggongjohn.eok.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CapabilityInspirations {
    public static class Storage implements Capability.IStorage<IInspirations>{
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IInspirations> capability, IInspirations instance, EnumFacing side) {
            //TODO
            return null;
        }

        @Override
        public void readNBT(Capability<IInspirations> capability, IInspirations instance, EnumFacing side, NBTBase nbt) {
            //TODO
        }
    }
}
