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
		MetaItemsHandler.CONVEX_LENS = this.addItem(0, "convex.lens");
		MetaItemsHandler.CONCAVE_LENS = this.addItem(1, "concave.lens");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (tab != EOK.tabEOK || tab != CreativeTabs.SEARCH) {
			return;
		}
		for (MetaItem<?>.MetaValueItem metaItem : metaItems.valueCollection()) {
			if (!metaItem.isVisible()) {
				continue;
			}
			metaItem.getSubItemHandler().getSubItems(metaItem.getStackForm(), tab, items);
		}
	}

	@Override
	public ResourceLocation createItemModelPath(MetaItem<?>.MetaValueItem metaValueItem, String postfix) {
		return new ResourceLocation(EOK.MODID, this.formatModelPath(metaValueItem) + postfix);
	}
}