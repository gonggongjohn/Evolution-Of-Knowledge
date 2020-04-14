package com.gonggongjohn.eok.api.gui.meta;

import java.util.HashMap;

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
			((GuiControl.GuiButton)value).init();
			break;
		default:
			break;
		}
		return super.put(key, value);
	}
	
	public GuiControl put(GuiControl control) {
		return super.put(control.id, control);
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
