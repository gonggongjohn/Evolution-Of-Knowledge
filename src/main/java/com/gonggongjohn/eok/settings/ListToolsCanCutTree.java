package com.gonggongjohn.eok.settings;

import java.util.ArrayList;

import net.minecraft.init.Items;

public class ListToolsCanCutTree extends ArrayList{
	
	
	//
	//作者：zi_jing
	//这个ArrayList用于定义所有可以用来砍树的工具/物品
	//用于com.gonggongjohn.eok.data.TreeTweaker中
	//
	
	public ListToolsCanCutTree() {
		//以下为mod物品
		
		
		
		
		
		//----------------------------------------------------------------------------
		
		//以下为原版物品
		
		this.add(Items.wooden_axe);
		this.add(Items.stone_axe);
		this.add(Items.iron_axe);
		this.add(Items.golden_axe);
		this.add(Items.diamond_axe);
		
	}
	
}
