package com.gonggongjohn.eok.data;

import java.util.ArrayList;

import com.gonggongjohn.eok.settings.ListToolsCanCutTree;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockLog;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

//
//作者:zi_jing
//用途:使玩家无法空手挖掘木头
//

public class TreeTweaker {
	@SubscribeEvent
	public void Tweak(PlayerEvent.BreakSpeed event) {
		
		ArrayList ListToolsCanCutTree = new ListToolsCanCutTree();
			//当玩家挖掘木头时执行
			if(event.block instanceof BlockLog) {
				
				
				
				//判断玩家手上是否持有物品
				//不进行此判断可能会使游戏崩溃
				if(event.entityPlayer.inventory.getCurrentItem() != null) {
					//System.out.println(event.entityPlayer.inventory.getCurrentItem().getItem().toString());
					
					//判断玩家是否持有斧头
					if(!ListToolsCanCutTree.contains(event.entityPlayer.inventory.getCurrentItem().getItem())) {
						//将挖掘速度设为0
						event.newSpeed = 0.0F;
					}
					
					
				}
				else {
					//空手，挖掘速度设为0
					event.newSpeed = 0.0F;
				}
			
				
				
				
			}
		}
	
	//注册事件
	public TreeTweaker() {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
