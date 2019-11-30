package com.gonggongjohn.eok.utils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchNode {
    @SuppressWarnings("unused")
	private int index;
    private String unlocalizedName;
    private ResearchNode[] fatherNodes;

    public ResearchNode(int index, String unlocalizedName,@Nullable ResearchNode... fatherNodes) {
        this.index = index;
        this.unlocalizedName = unlocalizedName;
        this.fatherNodes = fatherNodes;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public boolean hasFatherNodes(){
        return fatherNodes != null;
    }

    public List<ResearchNode> getFatherNodes(){
        if(fatherNodes != null)
         return new ArrayList<>(Arrays.asList(fatherNodes));
        else return null;
    }
}
