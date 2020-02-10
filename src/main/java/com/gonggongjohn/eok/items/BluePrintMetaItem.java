package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.blocks.MultiBlockCompBase;
import com.gonggongjohn.eok.client.gui.GUIBluePrint;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.GUIHandler;
import com.gonggongjohn.eok.handlers.MetaItemsHandler;
import com.gonggongjohn.eok.utils.JudgeWithFacing;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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
        MetaItemsHandler.BLUE_PRINT_TEST_2D_CORE =addItem(302,"test_2d_core");
        MetaItemsHandler.BLUE_PRINT_TEST_3D_CORE=addItem(303,"test_3d_core");
        MetaItemsHandler.BLUE_PRINT_ELEMENTARY_RESEARCH_TABLE =addItem(304,"elementary_research_table");
        initBluePrint(MetaItemsHandler.BLUE_PRINT_TEST_2D_CORE, BlockHandler.blockTest2DCore);
        initBluePrint(MetaItemsHandler.BLUE_PRINT_TEST_3D_CORE,BlockHandler.blockTest3DCore);
        initBluePrint(MetaItemsHandler.BLUE_PRINT_ELEMENTARY_RESEARCH_TABLE,BlockHandler.blockStoneTable);

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
            int damage=player.getHeldItem(hand).getItemDamage();
            for (MetaItem<?>.MetaValueItem metaItem : BLUE_PRINTS) {
                if (damage==metaItem.getMetaValue()&&player.getHeldItem(hand).getItem() instanceof MetaItem) {
                    Block clickingBlock = worldIn.getBlockState(pos).getBlock();
                    if (storage.get(metaItem) == clickingBlock && clickingBlock instanceof MultiBlockCompBase) {
                        MultiBlockCompBase multiBlock = (MultiBlockCompBase) clickingBlock;
                        String name = EOK.multiBlockDict.structureNameDict.get(clickingBlock.getUnlocalizedName());
                        int dimension = EOK.multiBlockDict.structureDimensionDict.get(name);
                        JudgeWithFacing result = multiBlock.checkStructure(worldIn, pos, worldIn.getBlockState(pos), dimension, name);
                        if (result.isComplete()) {
                            ((MultiBlockCompBase) clickingBlock).createMultiBlock(worldIn, pos, name, result.getFacing());
                        } else System.out.println("structure is not completed!");
                    }
                    else continue;
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
            BlockPos pos = playerIn.getPosition();
            int id= GUIHandler.GUIBluePrint;
            ItemStack itemstack=playerIn.getHeldItem(handIn);
            for(MetaItem<?>.MetaValueItem metaValueItem:BLUE_PRINTS)
            {
                if(itemstack.getItemDamage()==metaValueItem.getMetaValue()&&itemstack.getItem() instanceof MetaItem<?>)
                {
                    GUIBluePrint.blueprintHolding=metaValueItem;
                    playerIn.openGui(EOK.instance,id,worldIn,pos.getX(),pos.getY(),pos.getZ());
                    break;
                }
            }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
