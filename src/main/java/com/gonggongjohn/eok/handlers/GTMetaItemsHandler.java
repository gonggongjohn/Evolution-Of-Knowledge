package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.items.BluePrintMetaItem;
import com.gonggongjohn.eok.items.GTModMetaItem;

import gregtech.api.items.metaitem.MetaItem;

public class GTMetaItemsHandler {
	public static MetaItem<?>.MetaValueItem CONVEX_LENS;
	public static MetaItem<?>.MetaValueItem CONCAVE_LENS;
	public static MetaItem<?>.MetaValueItem CHIPPED_FLINT;
	public static MetaItem<?>.MetaValueItem GRINDED_FLINT;

	public static void init() {
		GTModMetaItem metaItem = new GTModMetaItem();
		metaItem.setRegistryName(EOK.MODID, "meta_item");

		BluePrintMetaItem bpMetaItem = new BluePrintMetaItem();
		bpMetaItem.setRegistryName(EOK.MODID, "blue_print_meta_item");
	}

	// edited by molybdenum
	public static MetaItem<?>.MetaValueItem BLUE_PRINT_TEST_2D_CORE;
	public static MetaItem<?>.MetaValueItem BLUE_PRINT_TEST_3D_CORE;
	public static MetaItem<?>.MetaValueItem BLUE_PRINT_ELEMENTARY_RESEARCH_TABLE;
}