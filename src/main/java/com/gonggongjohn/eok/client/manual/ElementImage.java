package com.gonggongjohn.eok.client.manual;

import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import com.github.zi_jing.cuckoolib.util.Size2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ElementImage extends Element {

    private final int width;
    private final int height;
    private final ResourceLocation location;
    private final int realWidth;
    private final int realHeight;

    ElementImage(ResourceLocation location, int windowWidth, int windowHeight) throws IOException {
        BufferedImage orgImage = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream());
        this.width = orgImage.getWidth();
        this.height = orgImage.getHeight();
        Size2i size = new Size2i(width, height);
        size.scaleToSize(windowWidth, windowHeight);
        this.realWidth = size.getWidth();
        this.realHeight = size.getHeight();
        this.location = location;
    }

    /**
     * 未实现，请勿调用。
     *
     * @param image        image
     * @param windowWidth  windowWidth
     * @param windowHeight windowHeight
     */
    @Deprecated
    private ElementImage(File image, int windowWidth, int windowHeight) {
        /*BufferedImage orgImage = TextureUtil.readBufferedImage(new FileInputStream(image));
        int orgImageWidth = orgImage.getWidth();
        int orgImageHeight = orgImage.getHeight();
        Size2i size = new Size2i(orgImageWidth, orgImageHeight);
        size.scaleToSize(windowWidth, windowHeight);
        this.realWidth = size.getWidth();
        this.realHeight = size.getHeight();
        BufferedImage scaledImage = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_INT_ARGB);
        scaledImage.getGraphics().drawImage(orgImage, 0, 0, realWidth, realHeight, null);
        int[] pixels = scaledImage.getRGB(0, 0, realWidth, realHeight, new int[realWidth * realHeight], 0, realWidth);
        this.pixelsBuffer = ByteBuffer.allocateDirect(realWidth * realHeight * 4);
        for (int pixel : pixels) {
            pixelsBuffer.put((byte) ((pixel >> 16) & 0xFF));
            pixelsBuffer.put((byte) ((pixel >> 8) & 0xFF));
            pixelsBuffer.put((byte) (pixel & 0xFF));
            pixelsBuffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        pixelsBuffer.flip();*/
        // 我 觉 得 这 个 功 能 实 现 不 了
        throw new UnsupportedOperationException("Loading a image from file is not currently supported yet!");
    }

    @Override
    protected Type getType() {
        return Type.IMAGE;
    }

    @Override
    protected int getHeight() {
        return realHeight;
    }

    @Override
    protected void draw(int x, int y, DocumentRenderer renderer) {
        GLUtils.bindTexture(this.location);
        GLUtils.drawScaledCustomSizeModalRect(x + renderer.width / 2 - realWidth / 2, y, 0, 0, width, height, realWidth, realHeight, width, height);
    }

    @Override
    protected void remove() {

    }
}
