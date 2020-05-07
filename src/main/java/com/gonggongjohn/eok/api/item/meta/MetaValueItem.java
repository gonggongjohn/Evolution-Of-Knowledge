package com.gonggongjohn.eok.api.item.meta;

import com.gonggongjohn.eok.api.item.meta.module.IItemModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemArmorModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemAttributesModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemBehaviorModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemContainerModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemDurabilityModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemEnchantmentModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemEntityModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemFuelModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemHorseArmorModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemInteractionModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemNameAndTooltipModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemToolModule;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class MetaValueItem {
	
	private final short id;
	
	protected MetaItem metaItem;
	protected String translationKey;
	
	/**
	 * @see MetaItem#addItem
	 */
	protected String unlocalizedName = "";
	
	/**
	 * @see MetaItem#addItem
	 */
	protected ModelResourceLocation model = null;
	
	protected ItemArmorModule armorModule = new ItemArmorModule();
	protected ItemAttributesModule attributesModule = new ItemAttributesModule();
	protected ItemBehaviorModule behaviorModule = new ItemBehaviorModule();
	protected ItemContainerModule containerModule = new ItemContainerModule();
	protected ItemDurabilityModule durabilityModule = new ItemDurabilityModule();
	protected ItemEnchantmentModule enchantmentModule = new ItemEnchantmentModule();
	protected ItemEntityModule entityModule = new ItemEntityModule();
	protected ItemFuelModule fuelModule = new ItemFuelModule();
	protected ItemHorseArmorModule horseArmorModule = new ItemHorseArmorModule();
	protected ItemInteractionModule interactionModule = new ItemInteractionModule();
	protected ItemNameAndTooltipModule nameAndTooltipModule = new ItemNameAndTooltipModule();
	protected ItemToolModule toolModule = new ItemToolModule();
	
	public MetaValueItem(int id, String translationKey) {
		if(id < 0 || id > 32766) {
			throw new IllegalArgumentException("id must be in [0, 32766]");
		}
		this.id = (short) id;
		this.translationKey = translationKey;
	}
	
	public MetaValueItem(int id, String translationKey, ModelResourceLocation customModel) {
		this(id, translationKey);
		this.model = customModel;
	}
	
	public MetaValueItem addModule(IItemModule module) {
		if(module instanceof ItemArmorModule) {
			this.armorModule = (ItemArmorModule)module;
		} else if(module instanceof ItemAttributesModule) {
			this.attributesModule = (ItemAttributesModule)module;
		} else if(module instanceof ItemBehaviorModule) {
			this.behaviorModule = (ItemBehaviorModule)module;
		} else if(module instanceof ItemContainerModule) {
			this.containerModule = (ItemContainerModule)module;
		} else if(module instanceof ItemDurabilityModule) {
			this.durabilityModule = (ItemDurabilityModule)module;
		} else if(module instanceof ItemEnchantmentModule) {
			this.enchantmentModule = (ItemEnchantmentModule)module;
		} else if(module instanceof ItemEntityModule) {
			this.entityModule = (ItemEntityModule)module;
		} else if(module instanceof ItemFuelModule) {
			this.fuelModule = (ItemFuelModule)module;
		} else if(module instanceof ItemHorseArmorModule) {
			this.horseArmorModule = (ItemHorseArmorModule)module;
		} else if(module instanceof ItemInteractionModule) {
			this.interactionModule = (ItemInteractionModule)module;
		} else if(module instanceof ItemNameAndTooltipModule) {
			this.nameAndTooltipModule = (ItemNameAndTooltipModule)module;
		} else if(module instanceof ItemToolModule) {
			this.toolModule = (ItemToolModule)module;
		} else {
			throw new IllegalArgumentException("Invalid module type. It shouldn't happen.");
		}
		return this;
	}
	
	public ItemStack getItemStack(int count) {
		return new ItemStack(this.metaItem, this.getId(), count);
	}
	
	public short getId() {
		return this.id;
	}
	
	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
	
	public String getModelName() {
		return this.translationKey;
	}
	
}
