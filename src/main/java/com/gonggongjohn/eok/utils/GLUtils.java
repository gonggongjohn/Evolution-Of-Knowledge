package com.gonggongjohn.eok.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class GLUtils {
	
	public static class ColorRGB {
		private int r;
		private int g;
		private int b;
		
		public ColorRGB(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		public int getR() {
			return r;
		}

		public int getG() {
			return g;
		}

		public int getB() {
			return b;
		}
		
	}
	
	private static final class ExternalImageTexture extends AbstractTexture {

		private BufferedImage image;
		
		public ExternalImageTexture(File file) throws FileNotFoundException, IOException {
			this.image = TextureUtil.readBufferedImage(new FileInputStream(file));
		}
		
		public ExternalImageTexture(BufferedImage image) {
			this.image = image;
		}
		
		@Override
		public void loadTexture(IResourceManager resourceManager) throws IOException {
			this.deleteGlTexture();
			TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), image, false, false);
		}
		
	}
	
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
	 * @param hex strings like "3FFF3F", "3fff3f" or "3FfF3f"<br>
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
	
	public static void drawString(String str, int x, int y, int color) {
		ColorRGB rgb = hexToRGB(color);
		color3i(rgb.r, rgb.g, rgb.b);
		Minecraft.getMinecraft().fontRenderer.drawString(str, x, y, 0, false);
	}
	
	public static void drawStringWithShadow(String str, int x, int y, int color) {
		ColorRGB rgb = hexToRGB(color);
		color3i(rgb.r, rgb.g, rgb.b);
		Minecraft.getMinecraft().fontRenderer.drawString(str, x, y, 0, true);
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
	
	/**
	 * Similar to {@link TextureManager#bindTexture}<br>
	 * But this method can bind an existing PNG file to the TextureManager.
	 * 
	 * @param file
	 * @return GLTextureID
	 * @throws IOException
	 */
	public static int bindTexture(File file) throws IOException {
		ITextureObject texture = new ExternalImageTexture(file);
		texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
		bindTexture(texture.getGlTextureId());
		return texture.getGlTextureId();
	}
	
	public static int bindTexture(BufferedImage image) throws IOException {
		ITextureObject texture = new ExternalImageTexture(image);
		texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
		bindTexture(texture.getGlTextureId());
		return texture.getGlTextureId();
	}
	
	public static void deleteTexture(int textureId) {
		TextureUtil.deleteTexture(textureId);
	}
}
