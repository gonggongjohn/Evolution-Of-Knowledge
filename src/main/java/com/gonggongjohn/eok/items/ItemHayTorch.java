package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHayTorch extends Item implements IHasModel {
	
	private final String name = "hay_torch";

	public ItemHayTorch() {
		
		this.maxStackSize = 64;
		this.setMaxDamage(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(EOK.tabEOK);
		
		ItemHandler.items.add(this);
	}

	@Override
	public void registerModel() {
		
		EOK.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float fitZ) {
		
		ItemStack itemStack = playerIn.getHeldItem(hand);
		
		if(!playerIn.canPlayerEdit(pos, facing, itemStack)) {
			
			return EnumActionResult.FAIL;
		}
		else {
			
			if(!tryOnFire(worldIn, pos.offset(facing), playerIn)) {
				
				return EnumActionResult.PASS;
			}

	        if (playerIn instanceof EntityPlayerMP) {
	        	
	            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)playerIn, pos, itemStack);
	        }
	        
			return EnumActionResult.SUCCESS;
		}
	}
	
	
	public boolean tryOnFire(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		
		if(worldIn.isAirBlock(pos)) {
			
            worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
			
			return true;
		}
		
		return false;
	}
}