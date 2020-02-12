package com.gonggongjohn.eok.client.gui;

import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.Colors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiControl {

	private EnumControlType type;
	private MetaGuiScreen gui;
	private int id;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public GuiControl(MetaGuiScreen gui) {
		this.gui = gui;
	}

	protected void setType(EnumControlType type) {
		this.type = type;
	}

	public EnumControlType getType() {
		return this.type;
	}
	
	public void draw(int mouseX, int mouseY, float partialTicks, MetaGuiScreen gui) {
		
	}
	
	public void init(int id) {
		this.id = id;
	}
	
	public void remove() {
		
	}

	public class GuiButton extends GuiControl {
		
		protected int u;
		protected int v;
		protected int u2;
		protected int v2;
		protected Consumer<MetaGuiScreen> func;
		protected String text = "";
		protected int color = 0;
		
		public GuiButton(int x, int y, int width, int height, int u, int v, int u2, int v2, Consumer<MetaGuiScreen> onClick) {
			super(gui);
			this.setType(EnumControlType.BUTTON);
			this.x = x + gui.getOffsetX();
			this.y = y + gui.getOffsetY();
			this.width = width;
			this.height = height;
			this.u = u;
			this.v = v;
			this.u2 = u2;
			this.v2 = v2;
			this.func = onClick;
		}
		
		//public static GuiButton create(int x, int y, int width, int height, int u, int v, int u2, int v2, MetaGuiScreen gui, Consumer<MetaGuiScreen> onClick) {
		//	return new GuiButton(x, y, width, height, u, v, u2, v2, gui, onClick);
		//}

		@Override
		public void init(int id) {
			super.init(id);/*
			ButtonData bd = new ButtonData();
			bd.setPosX(this.x);
			bd.setPosY(this.y);
			bd.setTexture(gui.getTexture());
			bd.setWidth(width);
			bd.setHeight(height);
			bd.setTextureX(u);
			bd.setTextureY(v);
			bd.setTextureX2(u2);
			bd.setTextureY2(v2);
			bd.setCustomTxtColor(true);
			bd.setTxtcolor(color);
			bd.setText(text);*/
			//gui.getButtonList().add(ButtonBuilder.CommonButton(id, bd));
			net.minecraft.client.gui.GuiButton button = new net.minecraft.client.gui.GuiButton(id, x, y, width, height, text) {

				@Override
				public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
					if (this.visible) {

						GL11.glPushMatrix();
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.getTextureManager().bindTexture(gui.getTexture());

						if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height) {
							Gui.drawModalRectWithCustomSizedTexture(x, y, u2, v2, width, height, gui.getTextureWidth(), gui.getTextureHeight());
						} else {
							Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, gui.getTextureWidth(), gui.getTextureHeight());
						}

						if (!text.equals("")) {

								if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
										&& mouseY < this.y + this.height) {
									this.drawCenteredString(mc.fontRenderer, text, this.x + this.width / 2,
											this.y + (this.height - 8) / 2, Colors.MOUSEON_TEXT);
								} else {
									this.drawCenteredString(mc.fontRenderer, text, this.x + this.width / 2,
											this.y + (this.height - 8) / 2, color);
								}
						}

						GL11.glDisable(GL11.GL_BLEND);
						GL11.glPopMatrix();

					}
				}

			};
			
			gui.getButtonList().add(button);
		}
		
		@Override
		public void remove() {
			gui.getButtonList().remove(id);
		}
		
		public void setText(String text, int color) {
			this.text = text;
			this.color = color;
		}
		
	}

	// TODO
	public class GuiProgressBar extends GuiControl {
		public GuiProgressBar(MetaGuiScreen gui) {
			super(gui);
			this.setType(EnumControlType.PROGRESSBAR);
		}
	}

	// TODO
	public class GuiScrollBar extends GuiControl {

		/**
		 * @param type the orientation of the scrollbar, 0 is horizontal, 1 is vertical
		 */
		public GuiScrollBar(int type, MetaGuiScreen gui) {
			super(gui);
			if (type == 0) {
				this.setType(EnumControlType.SCROLLBAR_HORIZONTAL);
			} else if (type == 1) {
				this.setType(EnumControlType.SCROLLBAR_VERTICAL);
			} else {
				throwArgumentException("scroll bar type can only be 0 or 1");
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
	
	protected int getId() {
		return id;
	}
}
