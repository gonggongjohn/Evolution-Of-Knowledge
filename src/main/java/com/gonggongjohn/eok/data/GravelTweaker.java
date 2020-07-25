package com.gonggongjohn.eok.data;

import java.util.Random;

import com.gonggongjohn.eok.handlers.ItemHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockGravel;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

//
//作者：zi_jing
//用途：挖掘沙砾有一定概率掉落燧石碎片
//

public class GravelTweaker {
	
	@SubscribeEvent
	public void Tweak(BlockEvent.HarvestDropsEvent event) {
		if(!event.world.isRemote) {
			if(event.block instanceof BlockGravel) {
				event.drops.clear();
				//生成一个范围为0-100的随机整数
				Random rand = new Random();
				int i = rand.nextInt(100);
				//判断随机数大小
				if(i<=30) {
					event.drops.add(new ItemStack(ItemHandler.itemFlintFragment));
					event.dropChance = 1.0F;
					//System.out.println(i+"，燧石碎片！");
				}
				else {
					event.drops.add(new ItemStack(Item.getItemFromBlock(Blocks.gravel)));
					event.dropChance = 1.0F;
					//System.out.println(i+"，沙砾！");
				}
			
				//event.drops.add(new ItemStack(ItemHandler.itemFlintFragment));
			}
		}
	}
	
	//注册事件
	public GravelTweaker() {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
