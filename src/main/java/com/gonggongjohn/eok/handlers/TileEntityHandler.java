package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.tileEntities.TEMainReservoir;
import com.gonggongjohn.eok.tileEntities.TEOriginalForgeFurnace;
import com.gonggongjohn.eok.tileEntities.TEResearchTableAncient;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TEResearchTableAncient.class, "tileResearchTableAncient");
		GameRegistry.registerTileEntity(TEMainReservoir.class, "tileMainReservoir");
		GameRegistry.registerTileEntity(TEOriginalForgeFurnace.class, "tileOriginalForgeFurnace");
	}
}