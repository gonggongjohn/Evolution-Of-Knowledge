package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.MetaItemsHandler;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;


public class BluePrintMetaItem extends MaterialMetaItem {
    public static ArrayList<MetaItem<?>.MetaValueItem> BLUE_PRINTS=new ArrayList<MetaItem<?>.MetaValueItem>();

    public BluePrintMetaItem() {
        super();
        this.setCreativeTab(EOK.tabEOK);
    }

    @Override
    public void registerSubItems() {
        MetaItemsHandler.WATER_WHEEL=addItem(300,"water_wheel");
        MetaItemsHandler.WIND_MILL=addItem(301,"wind_mill");

        BLUE_PRINTS.add(MetaItemsHandler.WATER_WHEEL);
        BLUE_PRINTS.add(MetaItemsHandler.WIND_MILL);

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
                    //todo
                }
                break;
            }
        }
        return EnumActionResult.PASS;
    }
}
