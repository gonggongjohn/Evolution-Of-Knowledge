package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.MetaItem;
import com.gonggongjohn.eok.api.item.meta.MetaValueItem;
import com.gonggongjohn.eok.api.item.meta.module.RightClickListenerModule;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EOK.MODID)
public class MetaItemHandler {
	
	private static final MetaItem METAITEM = new MetaItem(new ResourceLocation(EOK.MODID, "meta_item0"), (item) ->  {
		return new ModelResourceLocation(new ResourceLocation(EOK.MODID, item.getModelName()), "inventory");
	});
	
	public static final MetaValueItem EOK_SYMBOL = new MetaValueItem(0, "eok_symbol").addModule(new RightClickListenerModule() {

		@Override
		public void onItemRightClock(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
			if(worldIn.isRemote) {
				playerIn.sendStatusMessage(new TextComponentTranslation("eok.messages.welcome_to_eok"), false);
			}
		}
		
	});
	
	public static void setup() {
		METAITEM.setCreativeTab(EOK.tabEOK);
		METAITEM.addItem(EOK_SYMBOL);
	}
	
	@SubscribeEvent
	public static void onItemRegister(Register<Item> event) {
		event.getRegistry().register(METAITEM);
	}
}
