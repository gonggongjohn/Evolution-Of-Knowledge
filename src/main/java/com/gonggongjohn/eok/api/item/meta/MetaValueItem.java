package com.gonggongjohn.eok.api.item.meta;

import org.apache.commons.lang3.Validate;

import com.gonggongjohn.eok.api.item.meta.module.IItemModelProvider;
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

import net.minecraft.item.ItemStack;

public class MetaValueItem {
	protected short id;
	protected int modelCount, stackLimit;
	protected MetaItem metaItem;
	protected String unlocalizedName;

	protected IItemModelProvider modelProvider;

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

	public MetaValueItem(MetaItem metaItem, short id, String unlocalizedName) {
		Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, id, "MetaValueItem ID [ " + id + " ] is invalid");
		this.metaItem = metaItem;
		this.id = id;
		this.unlocalizedName = unlocalizedName;
		this.modelCount = 1;
		this.stackLimit = 64;
	}

	public MetaValueItem addModule(IItemModule module) {
		if (module instanceof IItemModelProvider) {
			this.modelProvider = (IItemModelProvider) module;
		} else if (module instanceof ItemArmorModule) {
			this.armorModule = (ItemArmorModule) module;
		} else if (module instanceof ItemAttributesModule) {
			this.attributesModule = (ItemAttributesModule) module;
		} else if (module instanceof ItemBehaviorModule) {
			this.behaviorModule = (ItemBehaviorModule) module;
		} else if (module instanceof ItemContainerModule) {
			this.containerModule = (ItemContainerModule) module;
		} else if (module instanceof ItemDurabilityModule) {
			this.durabilityModule = (ItemDurabilityModule) module;
		} else if (module instanceof ItemEnchantmentModule) {
			this.enchantmentModule = (ItemEnchantmentModule) module;
		} else if (module instanceof ItemEntityModule) {
			this.entityModule = (ItemEntityModule) module;
		} else if (module instanceof ItemFuelModule) {
			this.fuelModule = (ItemFuelModule) module;
		} else if (module instanceof ItemHorseArmorModule) {
			this.horseArmorModule = (ItemHorseArmorModule) module;
		} else if (module instanceof ItemInteractionModule) {
			this.interactionModule = (ItemInteractionModule) module;
		} else if (module instanceof ItemNameAndTooltipModule) {
			this.nameAndTooltipModule = (ItemNameAndTooltipModule) module;
		} else if (module instanceof ItemToolModule) {
			this.toolModule = (ItemToolModule) module;
		} else {
			throw new IllegalArgumentException("Invalid module type, add it to other type MetaValueItem");
		}
		return this;
	}

	public MetaValueItem setModelCount(int count) {
		if (count < 1) {
			throw new IllegalArgumentException("Model count must be a positive number");
		}
		this.modelCount = count;
		return this;
	}

	public int getModelCount() {
		return this.modelCount;
	}

	public MetaValueItem setItemStackLimit(int limit) {
		this.stackLimit = limit;
		return this;
	}

	public int getItemStackLimit() {
		return this.stackLimit;
	}

	public ItemStack getItemStack() {
		return this.getItemStack(1);
	}

	public ItemStack getItemStack(int count) {
		return new ItemStack(this.metaItem, this.id, count);
	}

	public short getId() {
		return this.id;
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
}