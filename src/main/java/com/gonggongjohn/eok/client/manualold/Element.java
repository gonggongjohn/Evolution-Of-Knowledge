package com.gonggongjohn.eok.client.manualold;

public abstract class Element {

    protected abstract Type getType();

    protected abstract int getHeight();

    protected abstract void draw(int x, int y, DocumentRendererOld renderer);

    protected void remove() {

    }

    protected void onClicked(DocumentRendererOld renderer) {

    }

    protected enum Type {
        END_OF_PAGE, TEXTLINE, CENTERED_TEXT, IMAGE, LINE, ITEM, CRAFTING, HYPERLINK
    }

}
