package com.gonggongjohn.eok.utils;

import net.minecraft.util.ResourceLocation;

public class ButtonData {

	private int posX;
	private int posY;
	private int width;
	private int height;
	private int textureX;
	private int textureY;
	private int textureX2;
	private int textureY2;
	private ResourceLocation texture;
	private String text = "";
	private int txtcolor;
	private boolean customTxtColor = false;

	public boolean isCustomTxtColor() {
		return customTxtColor;
	}

	public void setCustomTxtColor(boolean customTxtColor) {
		this.customTxtColor = customTxtColor;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextureX() {
		return textureX;
	}

	public void setTextureX(int textureX) {
		this.textureX = textureX;
	}

	public int getTextureY() {
		return textureY;
	}

	public void setTextureY(int textureY) {
		this.textureY = textureY;
	}

	public int getTextureX2() {
		return textureX2;
	}

	public void setTextureX2(int textureX2) {
		this.textureX2 = textureX2;
	}

	public int getTextureY2() {
		return textureY2;
	}

	public void setTextureY2(int textureY2) {
		this.textureY2 = textureY2;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public void setTexture(ResourceLocation texture) {
		this.texture = texture;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTxtcolor() {
		return txtcolor;
	}

	public void setTxtcolor(int txtcolor) {
		this.txtcolor = txtcolor;
	}

	/*
	 * public ButtonData(int posX, int posY, int width, int height, int textureX,
	 * int textureY, int textureX2, int textureY2, ResourceLocation texture, String
	 * text, int txtcolor) { this.posX = posX; this.posY = posY; this.width = width;
	 * this.height = height; this.textureX = textureX; this.textureY = textureY;
	 * this.textureX2 = textureX2; this.textureY2 = textureY2; this.texture =
	 * texture; this.text = text; this.txtcolor = txtcolor; }
	 */
	public ButtonData() {

	}
}
