package com.gonggongjohn.eok.client.manual;

import net.minecraft.util.ResourceLocation;

public class DocumentRenderer {
    private int org1X;
    private int org1Y;
    private int org2X;
    private int org2Y;
    private int width;
    private int height;
    private ResourceLocation documentLocation;
    private boolean initialized;

    public DocumentRenderer(int org1X, int org1Y, int org2X, int org2Y, int width, int height, ResourceLocation documentLocation) {
        this.org1X = org1X;
        this.org1Y = org1Y;
        this.org2X = org2X;
        this.org2Y = org2Y;
        this.width = width;
        this.height = height;
        this.documentLocation = documentLocation;
        this.initialized = false;
    }

    public void initialize() {

    }
}
