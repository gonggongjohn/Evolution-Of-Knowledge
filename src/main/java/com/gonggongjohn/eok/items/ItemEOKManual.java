package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.GUIHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemEOKManual extends Item implements IHasModel {
	
    private final String name = "eok_manual";
    
    public ItemEOKManual() {
    	
    	this.setMaxStackSize(1);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.items.add(this);
    }

    @Override
    public void registerModel() {
    	
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    	
        BlockPos pos = playerIn.getPosition();
        int id = GUIHandler.GUIEOKManual;
            
        playerIn.openGui(EOK.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
