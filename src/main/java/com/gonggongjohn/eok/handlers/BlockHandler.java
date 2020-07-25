package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.blocks.BlockMainReservoir;
import com.gonggongjohn.eok.blocks.BlockOriginalForgeFurnace;
import com.gonggongjohn.eok.blocks.BlockResearchTableAncient;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockHandler {
	public static Block researchTableAncient;
	public static Block mainReservoirBlock;
	public static Block originalForgeFurnace;

	public static void setupBlock() {
		researchTableAncient = new BlockResearchTableAncient().setBlockName("researchTableAncient").setHardness(1.5F)
				.setCreativeTab(EOK.tabEOK);
		mainReservoirBlock = new BlockMainReservoir().setBlockName("mainReservoirBlock").setHardness(1.5F)
				.setCreativeTab(EOK.tabEOK).setBlockTextureName(EOK.MODID + ":" + "mainReservoirBlock");
		originalForgeFurnace = new BlockOriginalForgeFurnace();
	}

	public static void registerBlock() {
		GameRegistry.registerBlock(researchTableAncient, "researchTableAncient");
		GameRegistry.registerBlock(mainReservoirBlock, "mainReservoirBlock");
		GameRegistry.registerBlock(originalForgeFurnace, "originalForgeFurnace");
	}
}
