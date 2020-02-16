package com.gonggongjohn.eok.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class StoragePlayerState implements Capability.IStorage<IPlayerState> {
	@Override
	public NBTBase writeNBT(Capability<IPlayerState> capability, IPlayerState instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("consciousness", instance.getConsciousness());
		nbt.setFloat("mindActivity", instance.getMindActivity());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPlayerState> capability, IPlayerState instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setConsciousness(tag.getFloat("consciousness"));
		instance.setMindActivity(tag.getFloat("mindActivity"));
	}
}