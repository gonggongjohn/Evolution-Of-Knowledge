package com.gonggongjohn.eok.data;

import com.gonggongjohn.eok.handlers.ItemHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;


//
//作者:zi_jing
//用途:破坏树叶有60%几率掉落木棍
//

public class LeavesTweaker {
	

		@SubscribeEvent
		public void Tweak(BlockEvent.HarvestDropsEvent event) {
			if(!event.world.isRemote) {
				//判断破坏的方块是否为树叶
				if(event.block instanceof BlockLeaves) {
					//添加掉落物
					event.drops.add(new ItemStack(Items.stick));
					event.dropChance = 0.6F;
					
				}
			}
		}
		//注册事件
		public LeavesTweaker() {
			MinecraftForge.EVENT_BUS.register(this);
		}
}
