package com.gonggongjohn.eok.client.manualold;

import com.github.zi_jing.cuckoolib.gui.Colors;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;

class ElementTextLine extends Element {

    private final String str;

    ElementTextLine(String str) {
        this.str = str;
    }

    @Override
    protected Type getType() {
        return Type.TEXTLINE;
    }

    @Override
    protected int getHeight() {
        return 10;
    }

    @Override
    protected void draw(int x, int y, DocumentRendererOld renderer) {
        GLUtils.drawString(str, x, y, Colors.DEFAULT_BLACK);
    }
}
