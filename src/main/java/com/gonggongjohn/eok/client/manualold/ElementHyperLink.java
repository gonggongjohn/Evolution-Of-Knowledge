package com.gonggongjohn.eok.client.manualold;

import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import com.github.zi_jing.cuckoolib.client.util.ClientUtils;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ElementHyperLink extends Element {

    private final boolean isExternal;
    private final String text;
    private final String link;
    private int x = 0;
    private int y = 0;

    ElementHyperLink(String text, String link, boolean isExternal) {
        this.isExternal = isExternal;
        this.link = link;
        this.text = text;
    }

    @Override
    protected Type getType() {
        return Type.HYPERLINK;
    }

    @Override
    protected int getHeight() {
        return 10;
    }

    @Override
    protected void draw(int x, int y, DocumentRendererOld renderer) {
        this.x = x;
        this.y = y;
        int mouseX = ClientUtils.getMouseX();
        int mouseY = ClientUtils.getMouseY();
        boolean mouseOn = mouseX > x && mouseX < x + renderer.width && mouseY > y && mouseY < y + 10;
        if (!mouseOn) {
            GLUtils.drawCenteredString(this.text, x + renderer.width / 2, y, Colors.DEFAULT_BLACK);
        } else {
            GLUtils.drawCenteredString(this.text, x + renderer.width / 2, y, 0x00FF00);
            GLUtils.drawSimpleToolTip(Lists.newArrayList(text, I18n.format("eok.manual.hyperlink.mouseon")));
        }
    }

    @Override
    protected void onClicked(DocumentRendererOld renderer) {
        int mouseX = ClientUtils.getMouseX();
        int mouseY = ClientUtils.getMouseY();
        if (mouseX > x && mouseX < x + renderer.width && mouseY > y && mouseY < y + 10) {
            if (!renderer.reload(this.isExternal, this.link))
                Minecraft.getMinecraft().player.closeScreen();
        }
    }

}
