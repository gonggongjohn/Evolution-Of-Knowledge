package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;

public class GUIControl {

	private EnumControlType type;
	private int x;
	private int y;

	private GUIControl() {
	}

	protected void setType(EnumControlType type) {
		this.type = type;
	}

	public EnumControlType getType() {
		return this.type;
	}

	public void setX(int x) {
		if (x < 0) {
			EOK.getLogger().error("An error occurred when initializating GUI, abscissa can't be negative.");
			throw new IllegalArgumentException("Abscissa can't be negative");
		}
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		if (y < 0) {
			EOK.getLogger().error("An error occurred when initializating GUI, ordinate can't be negative.");
			throw new IllegalArgumentException("Ordinate can't be negative");
		}
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public static class GUIButton extends GUIControl {
		public GUIButton() {
			this.setType(EnumControlType.BUTTON);
		}
	}

	public static class GUIProgressBar extends GUIControl {
		public GUIProgressBar() {
			this.setType(EnumControlType.PROGRESSBAR);
		}
	}

	public static class GUIScrollBar extends GUIControl {

		/**
		 * @param type 滚动条方向，0为横向，1为纵向。
		 */
		public GUIScrollBar(int type) {
			if (type == 0) {
				this.setType(EnumControlType.SCROLLBAR_HORIZONTAL);
			} else if (type == 1) {
				this.setType(EnumControlType.SCROLLBAR_VERTICAL);
			} else {
				EOK.getLogger().error("An error occurred when initializating GUI, scroll bar type can only be 0 or 1.");
				throw new IllegalArgumentException("Scroll bar type can only be 0 or 1");
			}
		}
	}

	public enum EnumControlType {
		BUTTON("button", 0),
		PROGRESSBAR("progressbar", 1),
		SCROLLBAR_HORIZONTAL("scrollbar_horizontal", 2),
		SCROLLBAR_VERTICAL("scrollbar_vertical", 3),
		INPUTBOX("inputbox", 4),
		CHECKBOX("checkbox", 5);

		String name;
		int id;

		private EnumControlType(String name, int id) {
			this.name = name;
			this.id = id;
		}
	}
}
