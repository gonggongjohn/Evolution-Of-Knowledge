package com.gonggongjohn.eok.api.item.meta;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.gonggongjohn.eok.api.item.meta.module.IItemModule;
import com.gonggongjohn.eok.api.item.meta.module.RightClickListenerModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class MetaItem extends Item implements IMetaItem {
	
	//private final List<MetaValueItem> items = new ArrayList<MetaValueItem>(32767);
	/**
	 * 用来存储物品在创造物品栏的显示顺序
	 */
	private final List<Short> itemIds = Lists.newArrayList();
	private final Map<Short, MetaValueItem> items = Maps.newHashMap();
	//private final Map<Short, String> unlocalizedNames = new HashMap<Short, String>();
	
	/**
	 * 用来生成模型路径。<br>
	 * 示例(将<code>"examplemod"</code>换成你的mod id)：<br>
	 * <pre>
	 * <code>
	 *(item) -> {
    return new ModelResourceLocation(new ResourceLocation("examplemod", item.getModelName()), "inventory");
});
	 * </code>
	 * </pre>
	 */
	private final Function<MetaValueItem, ModelResourceLocation> modelLocationFunction;
	private final String modid;
	
	/**
	 * @see MetaItem#modelLocationFunction
	 */
	public MetaItem(ResourceLocation registryName, Function<MetaValueItem, ModelResourceLocation> modelLocationFunction) {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setRegistryName(registryName);
		this.modid = registryName.getResourceDomain();
		this.modelLocationFunction = modelLocationFunction;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(this.isInCreativeTab(tab)) {
			for(short id : this.itemIds) {
				items.add(new ItemStack(this, 1, id));
			}
		}
	}
	
	public MetaValueItem addItem(MetaValueItem item) {
		if(this.items.size() == 32767) {
			throw new IllegalStateException("One MetaItem instance can only hold 32767 MetaValueItems at most");
		}
		item.unlocalizedName = "metaitem." + this.modid + "." + this.getRegistryName().getResourcePath() + "." + item.translationKey;
		this.items.put(item.getId(), item);
		this.itemIds.add(item.getId());
		if(item.model != null) {
			ModelLoader.setCustomModelResourceLocation(this, item.getId(), item.model);
		} else {
			ModelLoader.setCustomModelResourceLocation(this, item.getId(), this.modelLocationFunction.apply(item));
		}
		return item;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).getUnlocalizedName();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		short id = (short)playerIn.getHeldItem(handIn).getMetadata();
		for(IItemModule module : this.items.get(id).modules) {
			if(module instanceof RightClickListenerModule) {
				((RightClickListenerModule) module).onItemRightClock(worldIn, playerIn, handIn);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
}
