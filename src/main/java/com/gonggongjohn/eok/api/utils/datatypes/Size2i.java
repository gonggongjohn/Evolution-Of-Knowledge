package com.gonggongjohn.eok.api.utils.datatypes;

public class Size2i {
	
	private int width;
	private int height;
	
	public Size2i(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	/**
	 * 利用图形相似原理，在不改变纵横比的前提下把它缩放到给定的最大尺寸，并返回它本身。
	 */
	public Size2i scaleToSize(int width, int height) {
		float a = (float)width / (float)this.width;
		float b = (float)height / (float)this.height;
		float w, h;
		w = this.width * a;
		h = this.height * a;
		if(w > width || h > height) {
			w = this.width * b;
			h = this.height * b;
		}
		this.width = (int) w;
		this.height = (int) h;
		return this;
	}
}
