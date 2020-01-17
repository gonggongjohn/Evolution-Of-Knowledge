package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
 * 左手干草，右手起火器，点火
 */
//当前，左右手持起火器（fire stick），右键点火，点燃前方方块

public class ItemFireStick extends Item implements IHasModel {
	
	private final String name = "fire_stick";

	public ItemFireStick() {
		
		this.maxStackSize = 1;
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
	
    public BlockPos getFirePos(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {
        
    	pos = pos.offset(facing);

        return pos;
    }

	@Override									 
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		playerIn.swingArm(EnumHand.MAIN_HAND);
		playerIn.swingArm(EnumHand.OFF_HAND);
	
		if (worldIn.isRemote) {
			
			return super.onItemRightClick(worldIn, playerIn, handIn);
		}
		
		if(playerIn.getHeldItemOffhand().getItem() == this && playerIn.getHeldItemMainhand().getItem() == this) {
			
			 playerIn.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			 playerIn.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
			 
			 //点燃自己前方的方块
			 worldIn.setBlockState(this.getFirePos(playerIn, worldIn, playerIn.getPosition(), handIn, playerIn.getHorizontalFacing()), Blocks.FIRE.getDefaultState(), 11);
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}