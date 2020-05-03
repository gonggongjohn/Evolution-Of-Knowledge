package com.gonggongjohn.eok.api.gui.meta;

import java.util.function.Consumer;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.gui.Colors;
import com.gonggongjohn.eok.api.render.GLUtils;
import com.gonggongjohn.eok.api.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiControl {

	protected final EnumControlType type;
	protected final MetaGuiScreen gui;
	public final int id;
	/**
	 * 控件的<strong>相对</strong>横坐标。<br><br>
	 * This control's <strong>relative</strong> X coordinate.
	 * 
	 * @see GuiControl#getX
	 */
	protected int x;
	/**
	 * 控件的<strong>相对</strong>纵坐标。<br><br>
	 * This control's <strong>relative</strong> Y coordinate.
	 * 
	 * @see GuiControl#getY
	 */
	protected int y;
	protected int width = 0;
	protected int height = 0;

	private GuiControl(int id, MetaGuiScreen gui, EnumControlType type) {
		this.id = id;
		this.gui = gui;
		this.type = type;
	}
	
	public final EnumControlType getType() {
		return this.type;
	}
	
	public void draw(int mouseX, int mouseY, float partialTicks, MetaGuiScreen gui) {
		
	}
	
	public void init() {
		
	}
	
	public void remove() {
		
	}
	
	public final boolean isMouseOn() {
		if(this.width == 0 || this.height == 0) return false;
		int mouseX = Utils.getMouseX();
		int mouseY = Utils.getMouseY();
		return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
	}
	
	protected final int getId() {
		return id;
	}
	
	/**
	 * 设置控件的<strong>相对</strong>位置，必须在{@link MetaGuiScreen#initGui}中调用。<br><br>
	 * Sets the control's <strong>relative</strong> position, it must be called in
	 *  {@link MetaGuiScreen#initGui}.
	 */
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 获取控件的<strong>相对</strong>横坐标。<br><br>
	 * Gets this control's <strong>relative</strong> X coordinate.
	 * 
	 * @see GuiControl#x
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * 获取控件的<strong>相对</strong>纵坐标。<br><br>
	 * Gets this control's <strong>relative</strong> Y coordinate.
	 * 
	 * @see GuiControl#y
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * 它用来创建适用于一个Gui的控件。<br><br>
	 * It is used to create controls for a Gui.
	 */
	public static class ControlFactory {
		private final MetaGuiScreen gui;
		private int nextId = 0;
		
		public ControlFactory(MetaGuiScreen gui) {
			this.gui = gui;
		}
		
		public Button createButton(int width, int height, int u, int v, int u2, int v2, Consumer<MetaGuiScreen> onClick) {
			return (Button) gui.addControl(new Button(nextId++, width, height, u, v, u2, v2, onClick, this.gui));
		}
		
		public ProgressBar createProgressBar(int width, int height) {
			return (ProgressBar) gui.addControl(new ProgressBar(nextId++, width, height, this.gui));
		}
		
		// TODO more controls
	}

	public static class Button extends GuiControl {
		
		protected int u;
		protected int v;
		protected int u2;
		protected int v2;
		protected Consumer<MetaGuiScreen> func;
		protected String text = "";
		protected int color = 0;
		protected net.minecraft.client.gui.GuiButton buttonMc;
		
		protected Button(int id, int width, int height, int u, int v, int u2, int v2, Consumer<MetaGuiScreen> onClick, MetaGuiScreen gui) {
			super(id, gui, EnumControlType.BUTTON);
			this.x = 0;
			this.y = 0;
			this.width = width;
			this.height = height;
			this.u = u;
			this.v = v;
			this.u2 = u2;
			this.v2 = v2;
			this.func = onClick;
		}

		@Override
		public void init() {
			
			boolean mouseOn = this.isMouseOn();

			this.buttonMc = new net.minecraft.client.gui.GuiButton(id, x, y, width, height, text) {

				@Override
				public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
					if (this.visible) {

						int x = gui.getOffsetX() + getX();
						int y = gui.getOffsetY() + getY();
						
						GLUtils.pushMatrix();
						GLUtils.enableBlend();
						GLUtils.normalBlend();
						GLUtils.color4f(1f, 1f, 1f, 1f);						
						GLUtils.bindTexture(gui.getTexture());
						if (mouseOn) {
							Gui.drawModalRectWithCustomSizedTexture(x, y, u2, v2, width, height, gui.getTextureWidth(), gui.getTextureHeight());
						} else {
							Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, gui.getTextureWidth(), gui.getTextureHeight());
						}

						if (!text.equals("")) {
								if (mouseOn) {
									GLUtils.drawCenteredString(text, x + width / 2, y + (this.height - 8) / 2, Colors.MOUSEON_TEXT);
								} else {
									GLUtils.drawCenteredString(text, x + width / 2, y + (this.height - 8) / 2, color);
								}
						}
						GLUtils.disableBlend();
						GLUtils.popMatrix();
					}
				}

			};
			
			gui.getButtonList().add(buttonMc);
		}
		
		@Override
		public void remove() {
			gui.getButtonList().remove(this.getId());
		}
		
		public void setText(String text, int color) {
			this.text = text;
			this.color = color;
		}

		@Override
		public void setPos(int x, int y) {
			super.setPos(x, y);
			this.buttonMc.x = x + gui.getOffsetX();
			this.buttonMc.y = y + gui.getOffsetY();
		}
		
	}

	public static class ProgressBar extends GuiControl {
		
		/**
		 * 范围/Range: [0,100]
		 */
		private int progress = 0;
		
		protected ProgressBar(int id, int width, int height, MetaGuiScreen gui) {
			super(id, gui, EnumControlType.PROGRESSBAR);
			this.x = 0;
			this.y = 0;
			this.width = width;
			this.height = height;
		}

		public int getProgress() {
			return progress;
		}

		/**
		 * 设置进度，进度必须为0-100的整数。
		 */
		public void setProgress(int progress) {
			if(progress < 0 || progress > 100)
				throwArgumentException("progress must be in [0,100]");
			this.progress = progress;
		}

		@Override
		public void draw(int mouseX, int mouseY, float partialTicks, MetaGuiScreen gui) {
			super.draw(mouseX, mouseY, partialTicks, gui);
			int x = getX() + gui.getOffsetX();
			int y = getY() + gui.getOffsetY();
			GLUtils.drawRect(x, y, x + width, y + height, 0xFFFFFFFF);
			if(progress > 0) {
				GLUtils.drawRect(x + 1, y + 1, x + (int)((float)(progress) / 100F * (width - 1)), y + height - 1, 0xFF00FF00);
			}
		}
	}

	// TODO  ScrollBar
	public static class ScrollBar extends GuiControl {

		public final int direction;
		
		/**
		 * @param type the orientation of the scrollbar, 0 is horizontal, 1 is vertical
		 */
		protected ScrollBar(int id, int type, MetaGuiScreen gui) {
			super(id, gui, EnumControlType.SCROLLBAR);
			if (type == 0 || type == 1) {
				this.direction = type;
			} else {
				throwArgumentException("scroll bar type can only be 0 or 1");
				direction = 0;	// 永远不会执行
			}
		}
		
	}

	public static enum EnumControlType {
		BUTTON("button", 0),
		PROGRESSBAR("progressbar", 1),
		SCROLLBAR("scrollbar", 2),
		INPUTBOX("inputbox", 3),
		CHECKBOX("checkbox", 4);

		String name;
		int index;

		private EnumControlType(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	private static void throwArgumentException(String s) {
		EOK.getLogger().error("An error occurred when rendering Gui, " + s + ".");
		throw new IllegalArgumentException(s);
	}
}
