package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.MetaItem;
import com.gonggongjohn.eok.api.item.meta.MetaValueItem;
import com.gonggongjohn.eok.api.item.meta.module.IItemInteraction;
import com.gonggongjohn.eok.items.*;
import com.gonggongjohn.eok.utils.ToolMaterials;
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

	public static final MetaValueItem CONVEX_LENS = META_ITEM.addItem(1, "convex_lens");
	public static final MetaValueItem CONCAVE_LENS = META_ITEM.addItem(2, "concave_lens");
	public static final MetaValueItem CHIPPED_FLINT = META_ITEM.addItem(3, "chipped_flint");
	public static final MetaValueItem GROUND_FLINT = META_ITEM.addItem(4, "ground_flint");
	public static final MetaValueItem PLANT_FIBER = META_ITEM.addItem(5, "plant_fiber");
	public static final MetaValueItem RUBBER_MAT = META_ITEM.addItem(6, "rubber_mat");
	public static final MetaValueItem SIMPLE_HEMP_ROPE = META_ITEM.addItem(7, "simple_hemp_rope");
	public static final MetaValueItem STRONG_HEMP_ROPE = META_ITEM.addItem(8, "strong_hemp_rope");
	public static final MetaValueItem SHORT_STICK = META_ITEM.addItem(9, "short_stick");
	public static final MetaValueItem TUBE = META_ITEM.addItem(10, "tube");
	public static final MetaValueItem REDSTONE_SWORD = META_ITEM.addItem(11,"redstone_sword");
	public static final MetaValueItem REDSTONE_PICKAXE = META_ITEM.addItem(12,"redstone_pickaxe");
	public static final MetaValueItem REDSTONE_AXE = META_ITEM.addItem(13,"redstone_axe");
	public static final MetaValueItem REDSTONE_HOE = META_ITEM.addItem(14,"redstone_hoe");
	public static final MetaValueItem REDSTONE_SPADE = META_ITEM.addItem(15,"redstone_shovel");
	public static final MetaValueItem REDSTONE_HELMET = META_ITEM.addItem(16,"redstone_helmet");
	public static final MetaValueItem REDSTONE_CHESTPLATE = META_ITEM.addItem(17,"redstone_chestplate");
	public static final MetaValueItem REDSTONE_LEGGINGS = META_ITEM.addItem(18,"redstone_leggings");
	public static final MetaValueItem REDSTONE_BOOTS = META_ITEM.addItem(19,"redstone_boots");



	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		META_ITEM.registerItemModel();
	}

	@SubscribeEvent
	public static void onItemRegister(Register<Item> event) {
		event.getRegistry().register(META_ITEM);
	}
}