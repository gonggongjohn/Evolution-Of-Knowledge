package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.utils.physics.ILawBase;
import com.gonggongjohn.eok.api.utils.physics.ParameterList;
import com.gonggongjohn.eok.api.utils.physics.TherimalDynamics;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemTestMath extends Item {
    public final String name = "test math item";

    public ItemTestMath()
    {
        super();
        this.setCreativeTab(EOK.tabEOK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ItemHandler.ITEM_REGISTRY.add(this);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        double p=1,V=2,n=3;
        ILawBase counter = new TherimalDynamics.IdealGasLaw(p,V,n,0, new ParameterList[]{ParameterList.Temperature});
        System.out.println(counter.solve(ParameterList.Temperature));
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
