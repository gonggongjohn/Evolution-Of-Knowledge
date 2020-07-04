package com.gonggongjohn.eok.client.manual;

import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import java.util.List;

@SuppressWarnings("UnnecessaryLocalVariable")
final class Page {

    protected final List<Element> elements;

    public Page(List<Element> elements) {
        this.elements = elements;
    }

    public void draw(int orgX, int orgY, DocumentRenderer renderer) {
        int currentX = orgX;
        int currentY = orgY;
        for (Element element : elements) {
            GLUtils.resetState();
            GLUtils.pushMatrix();
            try {
                element.draw(currentX, currentY, renderer);
            } catch (Exception e) {
                renderer.error(I18n.format("eok.manual.error.render_error"));
                renderer.error(e.toString());
                e.printStackTrace();
                Minecraft.getMinecraft().player.closeScreen();
            }
            GLUtils.popMatrix();
            currentY += element.getHeight();
        }
    }

    public void remove() {
        for (Element e : elements) {
            e.remove();
        }
    }
}
