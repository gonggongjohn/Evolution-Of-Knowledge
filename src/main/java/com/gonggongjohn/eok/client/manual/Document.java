package com.gonggongjohn.eok.client.manual;

import java.util.ArrayList;
import java.util.List;

final class Document {
    private List<Element> elementList = new ArrayList<>();

    void addElement(Element element) {
        this.elementList.add(element);
    }
}
