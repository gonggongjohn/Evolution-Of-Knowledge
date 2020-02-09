package com.gonggongjohn.eok.utils;

import java.util.HashMap;

import com.gonggongjohn.eok.client.gui.GuiControl;
import com.gonggongjohn.eok.client.gui.MetaGuiScreen;

public class ControlMap extends HashMap<Integer, GuiControl> {

	private static final long serialVersionUID = 5402402572080883425L;
	private MetaGuiScreen gui;
	
	public ControlMap(MetaGuiScreen gui) {
		super();
		this.gui = gui;
	}
	
	@Override
	public GuiControl put(Integer key, GuiControl value) {
		switch(value.getType()) {
		case BUTTON:
			((GuiControl.GuiButton)value).init(key);
			break;
		default:
			break;
		}
		return super.put(key, value);
	}

	@Override
	public GuiControl remove(Object key) {
		this.get(key).remove();
		return super.remove(key);
	}

	@Override
	public boolean remove(Object key, Object value) {
		((GuiControl)value).remove();
		return super.remove(key, value);
	}
	
	public MetaGuiScreen getGui() {
		return gui;
	}
	
}
