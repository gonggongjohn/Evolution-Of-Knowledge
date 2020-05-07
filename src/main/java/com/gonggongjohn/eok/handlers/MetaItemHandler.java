package com.gonggongjohn.eok.handlers;

import java.util.List;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.MetaItem;
import com.gonggongjohn.eok.api.item.meta.MetaValueItem;
import com.gonggongjohn.eok.api.item.meta.module.ItemInteractionModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemNameAndTooltipModule;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
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
		int index = item.getModelName().indexOf('.');
		String subdir;
		String modelName;
		if(index == -1 || index == 0) {	// modelName没有命名空间或格式错误，例如itemname和.itemname，则直接把模型放items里
			subdir = "";
			modelName = item.getModelName();
		} else {
			subdir = item.getModelName().substring(0, index);
			modelName = item.getModelName().substring(index + 1);
		}
		return new ModelResourceLocation(new ResourceLocation(EOK.MODID, subdir + "/" + modelName), "inventory");
	});
	
	public static final MetaValueItem EOK_SYMBOL = new MetaValueItem(0, "others.eok_symbol").addModule(new ItemInteractionModule() {

		@Override
		public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
			if(worldIn.isRemote) {
				playerIn.sendStatusMessage(new TextComponentTranslation("eok.messages.welcome_to_eok"), false);
				playerIn.swingArm(handIn);
			}
			return super.onItemRightClick(worldIn, playerIn, handIn);
		}
		
	}).addModule(new ItemNameAndTooltipModule() {

		@Override
		public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
			tooltip.add(I18n.format("metaitem.eok.meta_item0.others.eok_symbol.name"));
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
