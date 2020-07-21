package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.client.manualold.DocumentRendererOld;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * Want to load a document from your file system? Use this.
 *
 * @see GuiEOKManual
 * @see DocumentRendererOld
 */
public class GuiDocumentPreview extends GuiEOKManual {

    private final String path;

    public GuiDocumentPreview(String path) {
        super();
        this.path = path;
    }

    @Override
    protected void initDocument() {
        renderer = new DocumentRendererOld(17, 13, 149, 13, 115, 135, new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + DocumentRendererOld.localManualPath + path));
    }
}
