package com.gonggongjohn.eok.utils;

import java.util.HashMap;

public class InspirationDict {
    public final HashMap<Integer, String> inspirationNameDict = new HashMap<>();

    public InspirationDict() {

    }

    public void initName() {
        inspirationNameDict.put(0, "root");
        inspirationNameDict.put(1, "fire_preservation");
    }
}
