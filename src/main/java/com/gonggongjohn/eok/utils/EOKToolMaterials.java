package com.gonggongjohn.eok.utils;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EOKToolMaterials {
	public static final EOKToolMaterial CHIPPED_FLINT = new EOKToolMaterial(2, 2000, 0, 1.0F, 5);

	@SideOnly(Side.CLIENT)
	public static void setupClient() {
		CHIPPED_FLINT.name = I18n.format("materials.CHIPPED_FLINT.name");
	}
}
