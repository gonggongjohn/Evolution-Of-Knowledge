package com.gonggongjohn.eok.utils;

import java.util.HashMap;
import java.util.HashSet;

public class ResearchDict {
    public final HashMap<Integer, String> researchNameDict = new HashMap<>();
    public final HashMap<HashSet<Integer>, Integer> researchRelationDict = new HashMap<>();

    public ResearchDict() {

    }

    public void initName() {
        researchNameDict.put(0, "root");
        researchNameDict.put(1, "elementary_arithmetic");
        researchNameDict.put(2, "power");
        researchNameDict.put(3, "logarithm");
    }

    public void initRelation() {
        researchRelationDict.put(createSet(1), 2);
        researchRelationDict.put(createSet(1, 2), 3);
    }

    private HashSet<Integer> createSet(int... element) {
        HashSet<Integer> relationSet = new HashSet<>();
        for (int value : element) relationSet.add(value);
        return relationSet;
    }
}
