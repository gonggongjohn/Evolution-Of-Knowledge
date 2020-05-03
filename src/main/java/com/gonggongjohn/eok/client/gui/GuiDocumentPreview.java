package com.gonggongjohn.eok.client.gui;

import java.io.File;

import com.gonggongjohn.eok.utils.DocumentRenderer;

import net.minecraft.client.Minecraft;

/**
 * Want to load a document from your file system? Use this.
 * 
 * @see GuiEOKManual
 * @see DocumentRenderer
 */
public class GuiDocumentPreview extends GuiEOKManual {

	private final String path;
	
	public GuiDocumentPreview(String path) {
		super();
		this.path = path;
	}
	
	@Override
	protected void initDocument() {
		renderer = new DocumentRenderer(17, 13, 149, 13, 115, 135, new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + DocumentRenderer.localManualPath + path));
	}
}
