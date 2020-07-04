package com.gonggongjohn.eok.client.manual;

import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

class ElementLine extends Element {

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int width;
    private final int r;
    private final int g;
    private final int b;

    ElementLine(int x1, int y1, int x2, int y2, int width, int r, int g, int b) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    protected Type getType() {
        return Type.LINE;
    }

    @Override
    protected int getHeight() {
        return 10;
    }

    @Override
    protected void draw(int x, int y, DocumentRenderer renderer) {
        BufferBuilder bb = renderer.bufferBuilder;
        GLUtils.glLineWidth(width);
        GLUtils.enableBlend();
        GLUtils.disableTexture2D();
        bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(x + x1, y + y1, 0).color(r, g, b, 255).endVertex();
        bb.pos(x + x2, y + y2, 0).color(r, g, b, 255).endVertex();
        renderer.tessellator.draw();
        GLUtils.enableTexture2D();
        GLUtils.disableBlend();
    }
}
