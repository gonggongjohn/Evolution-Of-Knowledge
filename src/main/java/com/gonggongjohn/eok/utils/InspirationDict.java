package com.gonggongjohn.eok.utils;

import java.util.HashMap;

public class InspirationDict {
    public HashMap<Integer, String> inspirationNameDict = new HashMap<Integer, String>();

    public InspirationDict(){

    }

    public void initName(){
        inspirationNameDict.put(0, "root");
        inspirationNameDict.put(1, "fire_preservation");
    }
}
