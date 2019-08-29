package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.EOKToolMaterial;
import com.gonggongjohn.eok.utils.ToolBase;

public class EOKItemAxe extends ToolBase{

	public EOKItemAxe(EOKToolMaterial material,String name) {
		super();
		//this.name = name;
		ItemHandler.items.add(this);
	}

	
	
}
