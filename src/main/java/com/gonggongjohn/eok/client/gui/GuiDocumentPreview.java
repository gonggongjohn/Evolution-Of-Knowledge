package com.gonggongjohn.eok.client.gui;

import java.io.File;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.DocumentRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Want to load a document from your file system? Use this.
 * 
 * @see GuiScreenTest
 * @see DocumentRenderer
 */
public class GuiDocumentPreview extends GuiScreenTest {

	public GuiDocumentPreview(String path) {
		this.setTexture(new ResourceLocation(EOK.MODID + ":textures/gui/screen/test_screen.png"));
		this.setTextureSize(422, 800);
		this.setWindowSize(401, 281);
		this.setTitle("");
		renderer = new DocumentRenderer(18, 18, 210, 18, 170, 230, new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + path));
		pages = renderer.getPages();
	}
}
