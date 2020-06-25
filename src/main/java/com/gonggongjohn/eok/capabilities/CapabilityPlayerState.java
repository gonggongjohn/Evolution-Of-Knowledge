package com.gonggongjohn.eok.capabilities;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityPlayerState implements IPlayerState, ICapabilitySerializable<NBTTagCompound> {
	public static final ResourceLocation KEY = new ResourceLocation(EOK.MODID, "player_state");

	private float consciousness, mindActivity;

	@Override
	public float getConsciousness() {
		return this.consciousness;
	}

	@Override
	public void setConsciousness(float value) {
		this.consciousness = Math.min((Math.max(value, 0)), 1);
	}

	@Override
	public float getMindActivity() {
		return this.mindActivity;
	}

	@Override
	public void setMindActivity(float value) {
		this.mindActivity = Math.min((Math.max(value, 0)), 1);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("consciousness", this.consciousness);
		nbt.setFloat("mindActivity", this.mindActivity);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.consciousness = nbt.getFloat("consciousness");
		this.mindActivity = nbt.getFloat("mindActivity");
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityHandler.capPlayerState;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityHandler.capPlayerState ? (T) this : null;
	}
}