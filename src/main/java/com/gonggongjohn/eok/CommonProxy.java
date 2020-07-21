package com.gonggongjohn.eok;

import com.gonggongjohn.eok.client.gui.overlay.PlayerVitalSigns;
import com.gonggongjohn.eok.handlers.*;
import com.gonggongjohn.eok.render.StructureHighlightRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		TileEntityHandler.register();
		new ConfigHandler(event);
		EntityHandler.register();
	}

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new PlayerVitalSigns());
		MinecraftForge.EVENT_BUS.register(new StructureHighlightRenderer());
		NetworkRegistry.INSTANCE.registerGuiHandler(EOK.instance, new GUIHandler());
		TradeHandler.setup();
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void registerItemRenderer(Item item, int meta, String id) {

	}

	public void registerItemRenderer(Item item, int meta, String pathName, String id) {

	}

	public IThreadListener getThreadListener(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player.mcServer;
		} else
			return null;
	}

	public EntityPlayer getPlayer(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player;
		} else
			return null;
	}
}
