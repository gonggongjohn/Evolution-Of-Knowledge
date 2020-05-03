package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tile.*;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void register() {
		GameRegistry.registerTileEntity(TEElementaryResearchTable.class,
				new ResourceLocation(EOK.MODID, "te_elementary_research_table"));
		GameRegistry.registerTileEntity(TEHaystack.class, new ResourceLocation(EOK.MODID, "te_haystack"));
	}
}