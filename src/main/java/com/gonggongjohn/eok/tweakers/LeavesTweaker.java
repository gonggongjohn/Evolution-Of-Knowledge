package com.gonggongjohn.eok.tweakers;

import java.util.Random;

import com.gonggongjohn.eok.EOK;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EOK.MODID)
public class LeavesTweaker {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void drop(HarvestDropsEvent event) {
		if (event.getWorld().isRemote)
			return;

		if (!(event.getState().getBlock() == Blocks.LEAVES || event.getState().getBlock() == Blocks.LEAVES2))
			return;

		if (event.getHarvester() == null)
			return;

		if (event.getHarvester().inventory.getCurrentItem() == null)
			return;

		if (event.getHarvester().inventory.getCurrentItem().getItem() == Items.SHEARS)
			return;

		if (event.isSilkTouching())
			return;

		Random rand = new Random();
		// 50%几率掉落
		if (rand.nextInt(100) < 50) {
			BlockPos pos = event.getPos();
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			event.getWorld().spawnEntity(new EntityItem(event.getWorld(), x, y, z, new ItemStack(Items.STICK, 1)));
		}
	}
}
