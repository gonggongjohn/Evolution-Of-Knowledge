package com.gonggongjohn.eok.utils;

import java.util.HashMap;
import java.util.Map;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.GTMetaItemsHandler;
import com.gonggongjohn.eok.items.BluePrintMetaItem;

import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.util.ResourceLocation;

public class BluePrintDict {
    public Map<MetaItem<?>.MetaValueItem, String[]> textureMap=new HashMap<>();
    public Map<MetaItem<?>.MetaValueItem,Integer> maxPageSize=new HashMap<>();
    public final String[] textureTestCore2D={"gou.png","li.png","guo.png","jia.png","sheng.png","si.png","yi.png"};
    public final String[] textureTestCore3D={"qi.png","yin.png","huo.png","fu.png","bi.png","qu.png","zhi.png"};
    public final String[] textureElementaryResearchTable={"completedstructure.png"};
    public BluePrintDict()
    {
        textureMap.put(GTMetaItemsHandler.BLUE_PRINT_TEST_2D_CORE,textureTestCore2D);
        textureMap.put(GTMetaItemsHandler.BLUE_PRINT_ELEMENTARY_RESEARCH_TABLE,textureElementaryResearchTable);
        textureMap.put(GTMetaItemsHandler.BLUE_PRINT_TEST_3D_CORE,textureTestCore3D);
    }
    public void init()
    {
        for(MetaItem<?>.MetaValueItem metaValueItem : BluePrintMetaItem.BLUE_PRINTS)
        {
            String[] list=EOK.bluePrintDict.textureMap.get(metaValueItem);
            initMap(metaValueItem,list);
        }
    }
    private void initMap(MetaItem<?>.MetaValueItem blueprint, String[] textureLocation)//简化起见，textureLocation只需要写/container/蓝图的unlocalizedname/后的部分即可
    {
        for(int i=0;i<textureLocation.length;i++)
        {
            ResourceLocation location=new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/blueprint/"+blueprint.unlocalizedName+"/"+textureLocation[i]);
            maxPageSize.put(blueprint,textureLocation.length);
        }
    }
}
