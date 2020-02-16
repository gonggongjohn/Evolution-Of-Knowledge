package com.gonggongjohn.eok.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class StorageResearchData implements Capability.IStorage<IResearchData> {
	@Override
	public NBTBase writeNBT(Capability<IResearchData> capability, IResearchData instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		List<Integer> data = instance.getFinishedResearch();
		int[] array = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			array[i] = data.get(i);
		}
		nbt.setIntArray("data", array);
		return nbt;
	}

	@Override
	public void readNBT(Capability<IResearchData> capability, IResearchData instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		int[] array = tag.getIntArray("data");
		List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			data.add(array[i]);
		}
		instance.setFinishedResearch(data);
	}
}