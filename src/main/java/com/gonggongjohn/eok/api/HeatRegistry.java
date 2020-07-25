package com.gonggongjohn.eok.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class HeatRegistry {
	private static final HeatRegistry INSTANCE = new HeatRegistry();
	private List<HeatIndex> heatList;

	private HeatRegistry() {
		heatList = new ArrayList<HeatIndex>();
	}

	public static HeatRegistry getInstance() {
		return INSTANCE;
	}

	public void addIndex(HeatIndex index) {
		heatList.add(index);
	}

	public HeatIndex findIndex(ItemStack stack) {
		int i;
		HeatIndex index;
		for (i = 0; i < heatList.size(); i++) {
			index = heatList.get(i);
			if (index.match(stack)) {
				return index;
			}
		}
		return null;
	}
}