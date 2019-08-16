package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.utils.ResearchNode;

import java.util.HashMap;
import java.util.List;

public class ResearchHandler {
    public HashMap<String, String> researchRelations = new HashMap<String, String>();

    public ResearchNode test1 = new ResearchNode(0, "test1");
    public ResearchNode test2 = new ResearchNode(0, "test2", test1);

    public ResearchHandler() {
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
