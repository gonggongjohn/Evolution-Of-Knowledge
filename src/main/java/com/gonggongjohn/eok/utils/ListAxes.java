package com.gonggongjohn.eok.utils;

import java.util.ArrayList;

import net.minecraft.init.Items;

//TODO 重写

@Deprecated
public class ListAxes extends ArrayList{
	
	/*
	 * 等待重写
	 */
	
	public ListAxes() {
		this.add(Items.WOODEN_AXE);
		this.add(Items.IRON_AXE);
		this.add(Items.STONE_AXE);
		this.add(Items.GOLDEN_AXE);
		this.add(Items.DIAMOND_AXE);
	}
}
