package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;

import net.minecraft.item.Item;

public class ItemFlintFragment extends Item implements IHasModel{

	private final String name="flint_fragment";
	public ItemFlintFragment(){
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(EOK.tabEOK);
		ItemHandler.items.add(this);
	}
	
	
	@Override
	public void registerModel() {
		EOK.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	
	//未完成...
	//打制功能
	
}
