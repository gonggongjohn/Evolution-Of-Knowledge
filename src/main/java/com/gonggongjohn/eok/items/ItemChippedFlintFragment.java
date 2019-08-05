package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;

import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.item.Item;

public class ItemChippedFlintFragment extends Item implements IHasModel {
	private final String name="chipped_flint_fragment";
	
	public ItemChippedFlintFragment() {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(EOK.tabEOK);
		ItemHandler.items.add(this);
	}
	@Override
	public void registerModel() {
		EOK.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	
	//未完成
	//点火工具/磨制
}
