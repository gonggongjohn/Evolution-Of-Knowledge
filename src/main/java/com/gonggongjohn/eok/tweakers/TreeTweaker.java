package com.gonggongjohn.eok.tweakers;

import java.util.ArrayList;

import com.gonggongjohn.eok.handlers.ConfigHandler;
import com.gonggongjohn.eok.utils.ListAxes;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TreeTweaker {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void BreakSpeed(PlayerEvent.BreakSpeed event) {
		if (ConfigHandler.disableBareHandedCuttingOfTrees == false)
			return;
		
		if (!(event.getState().getBlock() == Blocks.LOG || event.getState().getBlock() == Blocks.LOG2))
			return;
		
		if (event.getEntityPlayer() == null)
			return;
		
		if(event.getEntityPlayer().inventory.getCurrentItem() == null)
			return;
		
		ArrayList ListAxes = new ListAxes();
		//如果玩家没有拿斧头，则将挖掘速度设为0
		if (!ListAxes.contains(event.getEntityPlayer().inventory.getCurrentItem().getItem())) {
			event.setNewSpeed(0.0F);
		}
	}
	
	public TreeTweaker(){
		MinecraftForge.EVENT_BUS.register(this);
	}
}
