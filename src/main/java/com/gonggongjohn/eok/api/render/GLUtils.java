package com.gonggongjohn.eok.api.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.api.gui.Colors;
import com.gonggongjohn.eok.api.utils.Utils;
import com.gonggongjohn.eok.api.utils.datatypes.ColorRGB;
import com.gonggongjohn.eok.api.utils.datatypes.ExternalImageTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

public class GLUtils {
	
	private static final Gui GUI = new Gui();
	
	public static int rgbToHex(int r, int g, int b) {
		r <<= 16;
		g <<= 8;
		return r | g | b;
	}
	
	public static ColorRGB hexToRGB(int hex) {
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0x00FF00) >> 8;
		int b = (hex & 0x0000FF);
		return new ColorRGB(r, g, b);
	}
	
	/**
	 * @param hex 像"3FFF3F"、"3fff3f"、"3FfF3f"这样的字符串。<br>
	 * 它也支持以'#'开头的字符串<br> 
	 * strings like "3FFF3F", "3fff3f" or "3FfF3f"<br>
	 * 	It also supports strings what starts with '#'
	 */
	public static ColorRGB hexStringToRGB(String hex) {
		hex = hex.toUpperCase();
		int color = 0;
		for(char c : hex.toCharArray()) {
			switch(c) {
			case '#': continue;
			case '0': color = color << 4 | 0x00000000; break;
			case '1': color = color << 4 | 0x00000001; break;
			case '2': color = color << 4 | 0x00000002; break;
			case '3': color = color << 4 | 0x00000003; break;
			case '4': color = color << 4 | 0x00000004; break;
			case '5': color = color << 4 | 0x00000005; break;
			case '6': color = color << 4 | 0x00000006; break;
			case '7': color = color << 4 | 0x00000007; break;
			case '8': color = color << 4 | 0x00000008; break;
			case '9': color = color << 4 | 0x00000009; break;
			case 'A': color = color << 4 | 0x0000000A; break;
			case 'B': color = color << 4 | 0x0000000B; break;
			case 'C': color = color << 4 | 0x0000000C; break;
			case 'D': color = color << 4 | 0x0000000D; break;
			case 'E': color = color << 4 | 0x0000000E; break;
			case 'F': color = color << 4 | 0x0000000F; break;
			default: break;
			}
		}
		return hexToRGB(color);
	}
	
	public static int getStringWidth(String str) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(str);
	}
	
	public static int getCharWidth(char c) {
		return Minecraft.getMinecraft().fontRenderer.getCharWidth(c);
	}
	
	public static String trimStringToWidth(String str, int width) {
		return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(str, width);
	}
	
	public static void drawString(String str, int x, int y, int color) {
		ColorRGB rgb = hexToRGB(color);
		color3i(rgb.getR(), rgb.getG(), rgb.getB());
		Minecraft.getMinecraft().fontRenderer.drawString(str, x, y, color, false);
	}
	
	public static void drawStringWithShadow(String str, int x, int y, int color) {
		ColorRGB rgb = hexToRGB(color);
		color3i(rgb.getR(), rgb.getG(), rgb.getB());
		Minecraft.getMinecraft().fontRenderer.drawString(str, x, y, color, true);
	}
	
	public static void drawCenteredString(String str, int x, int y, int color) {
		int width = getStringWidth(str);
		drawString(str, x - width / 2, y, color);
	}
	
	public static void drawCenteredStringWithShadow(String str, int x, int y, int color) {
		int width = getStringWidth(str);
		drawStringWithShadow(str, x - width / 2, y, color);
	}
	
	public static void resetState() {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.glLineWidth(1.0F);
	}
	
	public static void glLineWidth(float width) {
		GlStateManager.glLineWidth(width);
	}
	
	public static void color3i(int r, int g, int b) {
		GlStateManager.color(r / 255.0F, g / 255.0F, b / 255.0F);
	}
	
	public static void color4i(int r, int g, int b, int a) {
		GlStateManager.color(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
	}
	
	public static void color3f(float r, float g, float b) {
		GlStateManager.color(r, g, b);
	}
	
	public static void color4f(float r, float g, float b, float a) {
		GlStateManager.color(r, g, b, a);
	}
	
	public static void bindTexture(ResourceLocation location) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(location);
	}
	
	/**
	 * @param textureId GLTextureId
	 */
	public static void bindTexture(int textureId) {
		GlStateManager.bindTexture(textureId);
	}
	
	public static int loadTexture(File file) throws IOException {
		ITextureObject texture = new ExternalImageTexture(file);
		texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
		return texture.getGlTextureId();
	}
	
	public static int loadTexture(BufferedImage image) throws IOException {
		ITextureObject texture = new ExternalImageTexture(image);
		texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
		return texture.getGlTextureId();
	}
	
	public static void deleteTexture(int textureId) {
		TextureUtil.deleteTexture(textureId);
	}
	
	public static void enableBlend() {
		GlStateManager.enableBlend();
	}
	
	public static void disableBlend() {
		GlStateManager.disableBlend();
	}
	
	public static void normalBlend() {
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void enableTexture2D() {
		GlStateManager.enableTexture2D();
	}
	
	public static void disableTexture2D() {
		GlStateManager.disableTexture2D();
	}
	
	public static void pushMatrix() {
		GlStateManager.pushMatrix();
	}
	
	public static void popMatrix() {
		GlStateManager.popMatrix();
	}
	
	/**
	 * 绘制一个矩形，<strong>颜色必须使用ARGB格式(例如<code>0x60FFFFFF</code>)</strong>。
	 */
	public static void drawRect(int x1, int y1, int x2, int y2, int color) {
		Gui.drawRect(x1, y1, x2, y2, color);
	}
	
	/**
	 * 绘制一个贴图，整个贴图的大小必须是256*256.
	 */
	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		GUI.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}
	
	/**
	 * 绘制一个自定义贴图大小的贴图，<code>width</code>和<code>height</code>是要绘制贴图区域的大小，
	 * <code>textureWidth</code>和<code>textureHeight</code>是整个贴图的大小。
	 */
	public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
	}
	
	/**
	 * 绘制一个自定义贴图大小的贴图，<strong>可以对贴图进行缩放</strong>，
	 * <code>uWidth</code>和<code>vHeight</code>是要绘制的贴图区域的大小，
	 * <code>width</code>和<code>height</code>是要绘制到屏幕上的实际大小(缩放后的)
	 * ，<code>tileWidth</code>和<code>tileHeight</code>是整个贴图的大小。
	 */
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
		Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
	}
	
	public static void drawSimpleToolTip(List<String> lines) {
		ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
		int screenWidth = r.getScaledWidth();
		int screenHeight = r.getScaledHeight();
		int mouseX = Utils.getMouseX();
		int mouseY = Utils.getMouseY();
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		int longest = 0;
		int current;
		for(String s : lines) {
			if((current = GLUtils.getStringWidth(s)) > longest)
				longest = current;
		}
		width = longest + 6;
		height = lines.size() * 10 + 6;
		if(mouseX + width <= screenWidth) {	// 右边
			if(mouseY + height <= screenHeight) {	// 右下
				x = mouseX;
				y = mouseY;
			} else if(mouseY - height >= 0){	// 右上
				x = mouseX;
				y = mouseY - height;
			} else {	// 右边其他位置
				if(mouseY + height / 2 <= screenHeight && mouseY - height / 2 >= 0) {	// 右边以鼠标为y中心
					x = mouseX;
					y = mouseY - height / 2;
				} else {	// 右边屏幕中间
					x = mouseX;
					y = screenHeight / 2 - height / 2;
				}
			}
		} else if(mouseX - width >= 0) {	// 左边
			if(mouseY + height <= screenHeight) {	// 下边
				x = mouseX - width;
				y = mouseY;
			} else if(mouseY - height >= 0){	// 上边
				x = mouseX - width;
				y = mouseY - height;
			} else {	// 左边其他位置
				if(mouseY + height / 2 <= screenHeight && mouseY - height / 2 >= 0) {	// 右边以鼠标为y中心
					x = mouseX - width;
					y = screenHeight / 2 - height / 2;
				} else {	// 右边屏幕中间
					x = mouseX - width;
					y = screenHeight / 2 - height / 2;
				}
			}
		} else if(mouseY - height >= 0) {	// 上边
			if(mouseX + width / 2 <= screenWidth && mouseX - width / 2 >= 0) {	// 上边以鼠标为x中心
				x = mouseX - width / 2;
				y = mouseY - height;
			} else {	// 上边屏幕中间
				x = screenWidth / 2 - width / 2;
				y = mouseY - height;
			}
		} else if(mouseY + height <= screenWidth) {	// 下边
			if(mouseX + width / 2 <= screenWidth && mouseX - width / 2 >= 0) {	// 下边以鼠标为x中心
				x = mouseX - width / 2;
				y = mouseY + height;
			} else {	// 下边屏幕中间
				x = screenWidth / 2 - width / 2;
				y = mouseY;
			}
		} else {	// 要是执行到这怕不是你屏幕分辨率太低了...
			if(mouseY - height / 2 >= 0) {	// 以鼠标为中心
				x = mouseX - width / 2;
				y = mouseY - height / 2;
			} else {	// 最顶端在屏幕顶端
				x = mouseX - width / 2;
				y = 0;
			}
		}
		GLUtils.drawRect(x, y, x + width, y + height, 0x80FFFFFF);
		int currentY = y + 3;
		for(String s : lines) {
			GLUtils.drawString(s, x + 3, currentY, Colors.DEFAULT_BLACK);
			currentY += 10;
		}
	}
	
}
