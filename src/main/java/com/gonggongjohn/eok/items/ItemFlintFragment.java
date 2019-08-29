package com.gonggongjohn.eok.items;

import java.util.Random;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;

import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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

public class ItemFlintFragment extends Item implements IHasModel {

	private final String name = "flint_fragment";

	public ItemFlintFragment() {
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
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		// 先判断是否是对石头或圆石右键
		if (worldIn.getBlockState(pos).getBlock() == Blocks.STONE
				|| worldIn.getBlockState(pos).getBlock() == Blocks.COBBLESTONE) {
			player.swingArm(EnumHand.MAIN_HAND);
			worldIn.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_STONE_PLACE,
					SoundCategory.BLOCKS, 0.8F, 1.0F, true);

			// 生成随机数，使打制有15%概率成功
			Random rand = new Random();
			int i = rand.nextInt(100);
			if (i < 15) {
				if (!worldIn.isRemote && player.getHeldItem(EnumHand.MAIN_HAND).getCount() != 1) {
					// 物品数量-1
					ItemStack itemstack = player.getHeldItem(EnumHand.MAIN_HAND); // 获取手持物品
					int count = itemstack.getCount();
					player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemHandler.flintFragment, count - 1)); // 数量-1

					// 生成掉落物
					ItemStack drops = new ItemStack(ItemHandler.chippedFlintFragment, 1);
					EntityItem entityitem = new EntityItem(worldIn, player.posX, player.posY, player.posZ, drops);
					worldIn.spawnEntity(entityitem);
				} else if (!worldIn.isRemote && player.getHeldItem(EnumHand.MAIN_HAND).getCount() == 1) {
					// 物品数量-1
					player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY); // 因为物品数量=1，物品数量不能为0，所以直接清除它

					// 生成掉落物
					ItemStack drops = new ItemStack(ItemHandler.chippedFlintFragment, 1);
					EntityItem entityitem = new EntityItem(worldIn, player.posX, player.posY, player.posZ, drops);
					worldIn.spawnEntity(entityitem);
				}
			}
		}
		return EnumActionResult.PASS;
	}

}
