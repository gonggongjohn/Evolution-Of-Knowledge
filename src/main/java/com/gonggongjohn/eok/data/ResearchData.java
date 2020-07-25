package com.gonggongjohn.eok.data;

import com.gonggongjohn.eok.utils.ResearchUtils;

import java.util.ArrayList;

public class ResearchData {
    public static ArrayList<ArrayList<Integer>> researchRelations = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<Integer> utilResearches = new ArrayList<>();

    public static void setRelation(int researchID, int from, int to){
        if(researchRelations.size() -1 <= researchID){
            ArrayList<Integer> relation = new ArrayList<Integer>();
            relation.add(from);
            relation.add(to);
            researchRelations.add(relation);
        }
        else{
            ArrayList<Integer> relation = researchRelations.get(researchID);
            relation.add(from);
            relation.add(to);
            researchRelations.add(researchID, relation);
        }
    }

    public static void setUtil(int id){
        utilResearches.add(id);
    }

    public static void initRsearch(){
        setRelation(0, 0, 0);
        setRelation(1, 0, 0);
        setRelation(2, 0, 1);
        setRelation(3, 1, 2);
        setRelation(4, 1, 2);
        setRelation(4, 1, 3);
        setUtil(1);
        setUtil(2);
        setUtil(3);
        setUtil(5);
        setUtil(8);
        ResearchUtils.finishedResearch.add(0);
        ResearchUtils.finishedResearch.add(1);
    }

}
