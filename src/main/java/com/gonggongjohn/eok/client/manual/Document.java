package com.gonggongjohn.eok.client.manual;

import java.util.List;

final class Document {

    protected final List<Page> pages;

    public Document(List<Page> pages) {
        this.pages = pages;
    }

    public void remove() {
        for (Page p : pages) {
            p.remove();
        }
    }
}
