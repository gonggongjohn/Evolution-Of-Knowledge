package com.gonggongjohn.eok.capabilities;

import java.util.ArrayList;
import java.util.List;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.CapabilityHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityResearchData implements IResearchData, ICapabilitySerializable<NBTTagCompound> {
	public static final ResourceLocation KEY = new ResourceLocation(EOK.MODID, "research_data");

	private List<Integer> data = new ArrayList<>();

	@Override
	public List<Integer> getFinishedResearch() {
		return this.data;
	}

	@Override
	public void setFinishedResearch(List<Integer> list) {
		this.data = list;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityHandler.capResearchData;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityHandler.capResearchData ? (T) this : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		List<Integer> data = this.getFinishedResearch();
		int[] array = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			array[i] = data.get(i);
		}
		nbt.setIntArray("data", array);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		int[] array = nbt.getIntArray("data");
		List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			data.add(array[i]);
		}
		this.setFinishedResearch(data);
	}
}