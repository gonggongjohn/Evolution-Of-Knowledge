package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.MetaItem;
import com.gonggongjohn.eok.api.item.meta.MetaValueItem;
import com.gonggongjohn.eok.api.item.meta.module.IItemInteraction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EOK.MODID)
public class MetaItemHandler {
	public static final MetaItem META_ITEM = new MetaItem(new ResourceLocation(EOK.MODID, "meta_item0"));

	public static final MetaValueItem EOK_SYMBOL = META_ITEM.addItem(0, "eok_symbol").addModule(new IItemInteraction() {
		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
			if (world.isRemote) {
				player.sendStatusMessage(new TextComponentTranslation("eok.messages.welcome_to_eok"), false);
				player.swingArm(hand);
			}
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		}
	});

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		META_ITEM.registerItemModel();
	}

	@SubscribeEvent
	public static void onItemRegister(Register<Item> event) {
		event.getRegistry().register(META_ITEM);
	}
}