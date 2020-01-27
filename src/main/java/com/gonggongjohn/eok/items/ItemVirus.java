package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemVirus extends Item implements IHasModel {
	
	private final String name = "virus";

	public ItemVirus() {
		
		this.maxStackSize = 999999999;
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
		
		System.out.println("EOK留言：新年快乐，EOK祝大家健康，真心希望2020年新型冠状肺炎病毒带来的危机能尽快被控制和解除，2020年1月25日。");
		
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.POISON, 1200, 5));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 1000, 5));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 4));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1000, 5));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1000, 5));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.WITHER, 1000, 5));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 1000, 5));
	    playerIn.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1000, 7));
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}