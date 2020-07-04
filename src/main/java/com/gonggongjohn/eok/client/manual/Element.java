package com.gonggongjohn.eok.client.manual;

public abstract class Element {

    protected abstract Type getType();

    protected abstract int getHeight();

    protected abstract void draw(int x, int y, DocumentRenderer renderer);

    protected void remove() {

    }

    protected void onClicked(DocumentRenderer renderer) {

    }

    protected enum Type {
        END_OF_PAGE, TEXTLINE, CENTERED_TEXT, IMAGE, LINE, ITEM, CRAFTING, HYPERLINK
    }

}
