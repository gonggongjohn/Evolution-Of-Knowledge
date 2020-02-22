package com.gonggongjohn.eok.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class SerializationUtils {
	public static ItemStack getItemStackFromNBT(NBTTagCompound compound) {
		if(compound.getInteger("count") == 0) {
			return ItemStack.EMPTY;
		}
		String namespace = compound.getString("namespace");
		String path = compound.getString("path");
		int count = compound.getInteger("count");
		int meta = compound.getInteger("meta");
		NBTTagCompound tag = compound.getCompoundTag("nbt");
		ResourceLocation location = new ResourceLocation(namespace, path);
		Item item = Item.REGISTRY.getObject(location);
		ItemStack stack = new ItemStack(item, count, meta);
		stack.setTagCompound(tag);
		return stack;
	}
	
	public static void setItemStackToNBT(NBTTagCompound compound, ItemStack stack) {
		if(stack == ItemStack.EMPTY) {
			compound.setInteger("count", 0);
			return;
		}
		ResourceLocation location = Item.REGISTRY.getNameForObject(stack.getItem());
		String namespace = location.getResourceDomain();
		String path = location.getResourcePath();
		int count = stack.getCount();
		int meta = stack.getMetadata();
		NBTTagCompound tag = stack.getTagCompound();
		compound.setString("namespace", namespace);
		compound.setString("path", path);
		compound.setInteger("count", count);
		compound.setInteger("meta", meta);
		compound.setTag("tag", tag);
	}
	
	public static InputStream getResource(ResourceLocation location) throws IOException {
		return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
	}
	
	public static NBTTagCompound readTagFromStream(InputStream stream) throws IOException {
		return CompressedStreamTools.readCompressed(stream);
	}
	
	public static void writeTagToStream(OutputStream stream, NBTTagCompound compound) throws IOException {
		CompressedStreamTools.writeCompressed(compound, stream);
	}
}
