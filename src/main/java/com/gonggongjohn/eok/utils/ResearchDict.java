package com.gonggongjohn.eok.utils;

import java.util.HashMap;
import java.util.HashSet;

public class ResearchDict {
    public HashMap<Integer, String> researchNameDict = new HashMap<Integer, String>();
    public HashMap<HashSet<Integer>, Integer> researchRelationDict = new HashMap<HashSet<Integer>, Integer>();

    public ResearchDict(){

    }

    public void initName(){
        researchNameDict.put(0, "root");
        researchNameDict.put(1, "elementary_arithmetic");
        researchNameDict.put(2, "power");
        researchNameDict.put(3, "logarithm");
    }

    public void initRelation(){
        researchRelationDict.put(createSet(1), 2);
        researchRelationDict.put(createSet(1,2), 3);
    }

    private HashSet<Integer> createSet(int... element){
        HashSet<Integer> relationSet = new HashSet<Integer>();
        for(int i = 0; i < element.length; i++)
        relationSet.add(element[i]);
        return relationSet;
    }
}
