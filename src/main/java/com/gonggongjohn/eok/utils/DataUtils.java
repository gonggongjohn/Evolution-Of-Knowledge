package com.gonggongjohn.eok.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class DataUtils {
	public static ItemStack NBTToItemStack(NBTTagCompound compound) {
		ItemStack stack = ItemStack.EMPTY;
		stack.deserializeNBT(compound);
		return stack;
	}
	
	public static NBTTagCompound ItemStackToNBT(ItemStack stack) {
		return stack.serializeNBT();
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
	
	public static NBTTagCompound readTabFromFile(File file) throws FileNotFoundException, IOException {
		return readTagFromStream(new FileInputStream(file));
	}
	
	public static void writeTagToFile(File file, NBTTagCompound compound) throws FileNotFoundException, IOException {
		writeTagToStream(new FileOutputStream(file), compound);
	}
}
