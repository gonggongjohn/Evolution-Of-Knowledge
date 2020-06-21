package com.gonggongjohn.eok.api.item.meta;

import java.util.Arrays;

import org.apache.commons.lang3.Validate;

import com.gonggongjohn.eok.api.item.meta.module.IContainerItemProvider;
import com.gonggongjohn.eok.api.item.meta.module.IDurabilityBarProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemInteraction;
import com.gonggongjohn.eok.api.item.meta.module.IItemModelProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemModule;
import com.gonggongjohn.eok.api.item.meta.module.IItemNameProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemTooltipProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemUse;
import com.gonggongjohn.eok.api.item.meta.module.IToolDamage;
import com.gonggongjohn.eok.api.item.meta.module.ItemArmorModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemAttributesModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemEnchantmentModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemEntityModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemFuelModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemHorseArmorModule;
import com.gonggongjohn.eok.api.item.meta.module.ItemToolModule;

import net.minecraft.item.ItemStack;

public class MetaValueItem {
	protected short id;
	protected int modelCount, stackLimit;
	protected MetaItem metaItem;
	protected String unlocalizedName;

	protected IItemModelProvider modelProvider;
	protected IItemNameProvider nameProvider;
	protected IItemTooltipProvider tooltipProvider;
	protected IContainerItemProvider containerModule;
	protected IDurabilityBarProvider durabilityBarProvider;
	protected IToolDamage toolDamage;
	protected IItemUse itemUse;
	protected IItemInteraction itemInteraction;

	protected ItemArmorModule armorModule = new ItemArmorModule();
	protected ItemAttributesModule attributesModule = new ItemAttributesModule();
	protected ItemEnchantmentModule enchantmentModule = new ItemEnchantmentModule();
	protected ItemEntityModule entityModule = new ItemEntityModule();
	protected ItemFuelModule fuelModule = new ItemFuelModule();
	protected ItemHorseArmorModule horseArmorModule = new ItemHorseArmorModule();
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
		} else if (module instanceof IItemNameProvider) {
			this.nameProvider = (IItemNameProvider) module;
		} else if (module instanceof IItemTooltipProvider) {
			this.tooltipProvider = (IItemTooltipProvider) module;
		} else if (module instanceof IContainerItemProvider) {
			this.containerModule = (IContainerItemProvider) module;
		} else if (module instanceof IDurabilityBarProvider) {
			this.durabilityBarProvider = (IDurabilityBarProvider) module;
		} else if (module instanceof IToolDamage) {
			this.toolDamage = (IToolDamage) module;
		} else if (module instanceof IItemUse) {
			this.itemUse = (IItemUse) module;
		} else if (module instanceof IItemInteraction) {
			this.itemInteraction = (IItemInteraction) module;
		} else {
			throw new IllegalArgumentException("Invalid module type, add it to other type MetaValueItem");
		}
		return this;
	}

	public boolean containsModule(String name) {
		return Arrays.stream(this.getClass().getDeclaredFields()).filter((field) -> field.getName() == name)
				.filter((field) -> {
					try {
						field.setAccessible(true);
						return IItemModule.class.isInstance(field.get(this));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					return false;
				}).findFirst().isPresent();
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
		return new ItemStack(this.metaItem, count, this.id);
	}

	public short getId() {
		return this.id;
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
}