package com.gonggongjohn.eok.utils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class ResearchUtils {
    public static ArrayList<Integer> finishedResearch = new ArrayList<Integer>();

    public static ArrayList<Integer> sortRelations(ArrayList<Integer> source){
        if(source.size() != 2) {
            for (int i = 0; i < (source.size() / 2); i++) {
                for (int j = 0; j < (source.size() / 2); j++) {
                    if (source.get(j * 2) > source.get(j * 2 + 2)) {
                        int temp = source.get(j * 2);
                        source.add(j * 2, source.get(j * 2 + 2));
                        source.add(j * 2 + 2, temp);
                        temp = source.get(j * 2 + 1);
                        source.add(j * 2 + 1, source.get(j * 2 + 3));
                        source.add(j * 2 + 3, temp);
                    }
                }
            }
        }
        return source;
    }
}
