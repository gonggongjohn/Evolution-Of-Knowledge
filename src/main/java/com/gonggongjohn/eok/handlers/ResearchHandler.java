package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.utils.ResearchNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResearchHandler {
    public HashMap<String, String> researchRelations = new HashMap<String, String>();
    public static List<ResearchNode> researchDict = new ArrayList<ResearchNode>();

    public ResearchNode gravity = new ResearchNode(1, "gravity");
    public ResearchNode temperature = new ResearchNode(2, "temperature");

    public ResearchNode test1 = new ResearchNode(3, "test1");
    public ResearchNode test2 = new ResearchNode(4, "test2", test1);

    public ResearchHandler() {
        researchDict.add(0, new ResearchNode(0, "researchRoot"));
        researchDict.add(1, gravity);
        researchDict.add(2, temperature);
        researchDict.add(3, test1);
        researchDict.add(4, test2);
        initChildren(test1);
        initChildren(test2);
    }

    private void initChildren(ResearchNode childNode){
        String childName = childNode.getUnlocalizedName();
        List<ResearchNode> nodeList = childNode.getFatherNodes();
        for(int i = 0; i < nodeList.size(); i ++){
            String fatherName = nodeList.get(i).getUnlocalizedName();
            researchRelations.put(fatherName, childName);
        }
    }

}
