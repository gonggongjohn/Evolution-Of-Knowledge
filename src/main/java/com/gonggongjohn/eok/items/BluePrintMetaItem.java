package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.MetaItemsHandler;
import com.gonggongjohn.eok.utils.IMultiBlock;
import com.gonggongjohn.eok.utils.JudgeWithFacing;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BluePrintMetaItem extends MaterialMetaItem {
    public static ArrayList<MetaItem<?>.MetaValueItem> BLUE_PRINTS=new ArrayList<MetaItem<?>.MetaValueItem>();

    public BluePrintMetaItem() {
        super();
        this.setCreativeTab(EOK.tabEOK);
    }
    public static Map<MetaItem<?>.MetaValueItem,Block> storage=new HashMap<MetaItem<?>.MetaValueItem,Block>();
    private void initBluePrint(MetaItem<?>.MetaValueItem metaValueItem,Block relatedBlock)
    {
        BLUE_PRINTS.add(metaValueItem);
        storage.put(metaValueItem,relatedBlock);
    }

    @Override
    public void registerSubItems() {
        MetaItemsHandler.WATER_WHEEL=addItem(300,"water_wheel");
        MetaItemsHandler.WIND_MILL=addItem(301,"wind_mill");
        MetaItemsHandler.TEST_2D_CORE=addItem(302,"test_2d_core");
        MetaItemsHandler.ELEMENTARY_RESEARCH_TABLE=addItem(303,"elementary_research_table");
        //记得这两个要改！（暂无对应方块）
        initBluePrint(MetaItemsHandler.WATER_WHEEL, Blocks.DIRT);
        initBluePrint(MetaItemsHandler.WIND_MILL,Blocks.STONE);

        initBluePrint(MetaItemsHandler.TEST_2D_CORE, BlockHandler.blockTest2DCore);
        initBluePrint(MetaItemsHandler.ELEMENTARY_RESEARCH_TABLE,BlockHandler.blockStoneTable);

        for(MetaItem<?>.MetaValueItem metaValueItem:BLUE_PRINTS)
        {
            metaValueItem.setMaxStackSize(1);
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab != EOK.tabEOK){
            return;
        }
        for(MetaItem<?>.MetaValueItem metaItem : metaItems.valueCollection()){
            if(!metaItem.isVisible()){
                continue;
            }
            metaItem.getSubItemHandler().getSubItems(metaItem.getStackForm(), tab, items);
        }
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            Item item = player.getHeldItem(hand).getItem();
            for (MetaItem<?>.MetaValueItem metaItem : BLUE_PRINTS) {
                if (item.equals(metaItem.getMetaItem())){
                    boolean openGuiFlag=true;
                    Block clickingBlock=worldIn.getBlockState(pos).getBlock();
                    if(storage.get(metaItem).equals(clickingBlock)&&clickingBlock instanceof IMultiBlock)
                    {
                        //JudgeWithFacing result=((IMultiBlock)clickingBlock).checkStructure(worldIn,pos,worldIn.getBlockState(pos),);
                    }
                    if(openGuiFlag)
                    {
                        System.out.println("open Gui of the blueprint");
                    }
                }
                break;
            }
        }
        return EnumActionResult.PASS;
    }
}
