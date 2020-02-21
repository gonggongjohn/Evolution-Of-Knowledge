package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.MetaItemsHandler;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ModMetaItem extends MaterialMetaItem {
	public ModMetaItem() {
		super(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}

	@Override
	public void registerSubItems() {
		MetaItemsHandler.CONVEX_LENS = this.addItem(0, "eok_symbol");
		MetaItemsHandler.CONVEX_LENS = this.addItem(1, "convex_lens");
		MetaItemsHandler.CONCAVE_LENS = this.addItem(2, "concave_lens");
		MetaItemsHandler.CHIPPED_FLINT = this.addItem(3, "chipped_flint");
		MetaItemsHandler.GRINDED_FLINT = this.addItem(4, "grinded_flint");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (tab != EOK.tabEOK && tab != CreativeTabs.SEARCH) {
			return;
		}
		for (MetaItem<?>.MetaValueItem metaItem : this.metaItems.valueCollection()) {
			if (!metaItem.isVisible()) {
				continue;
			}
			metaItem.getSubItemHandler().getSubItems(metaItem.getStackForm(), tab, items);
		}
	}
}