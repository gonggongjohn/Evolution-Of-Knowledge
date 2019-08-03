package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;

import net.minecraft.item.Item;

public class ItemPolishedFlintFragment extends Item implements IHasModel{
	
	private final String name="polished_flint_fragment";
	public ItemPolishedFlintFragment() {
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
	//打火工具

}
