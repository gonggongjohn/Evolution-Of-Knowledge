package com.gonggongjohn.eok.api.item.meta;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.module.IContainerItemProvider;
import com.gonggongjohn.eok.api.item.meta.module.IDurabilityBarProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemInteraction;
import com.gonggongjohn.eok.api.item.meta.module.IItemNameProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemTooltipProvider;
import com.gonggongjohn.eok.api.item.meta.module.IItemUse;
import com.gonggongjohn.eok.api.item.meta.module.IToolDamage;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaItem extends Item implements IMetaItem {
	protected String modid;

	/**
	 * 用于存储MetaValueItem的注册表
	 */
	protected TShortObjectMap<MetaValueItem> metaItem;

	public MetaItem(ResourceLocation registryName) {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setRegistryName(registryName);
		this.modid = registryName.getResourceDomain();
		this.metaItem = new TShortObjectHashMap();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == EOK.tabEOK) {
			this.metaItem.forEachValue((metaValueItem) -> items.add(metaValueItem.getItemStack()));
		}
	}

	protected MetaValueItem createMetaValueItem(short id, String unlocalizedName) {
		return new MetaValueItem(this, id, unlocalizedName);
	}

	/**
	 * 给这个MetaItem添加子物品
	 */
	public MetaValueItem addItem(int id, String unlocalizedName) {
		Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, id,
				"MetaValueItem ID [ " + id + " ] of item [ " + unlocalizedName + " ] is invalid");
		if (this.metaItem.containsKey((short) id)) {
			throw new IllegalArgumentException(
					"MetaValueItem ID [ " + id + " ] of item [ " + unlocalizedName + " ] is registered");
		}
		MetaValueItem metaValueItem = this.createMetaValueItem((short) id, unlocalizedName);
		this.metaItem.put((short) id, metaValueItem);
		return metaValueItem;
	}

	public MetaValueItem getMetaValueItem(short id) {
		if (this.metaItem.containsKey(id)) {
			return this.metaItem.get(id);
		}
		return null;
	}

	public MetaValueItem getMetaValueItem(ItemStack stack) {
		return this.getMetaValueItem((short) stack.getMetadata());
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getMetaValueItem(stack).getUnlocalizedName();
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return this.getMetaValueItem(stack).getItemStackLimit();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemNameProvider provider = metaValueItem.nameProvider;
			if (provider != null) {
				return provider.getItemStackDisplayName(stack);
			}
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemTooltipProvider provider = metaValueItem.tooltipProvider;
			if (provider != null) {
				provider.addInformation(stack, world, tooltip, flag);
				return;
			}
		}
		super.getItemStackDisplayName(stack);
	}

	/* ---------- ItemContainerModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getContainerItem
	 */
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IContainerItemProvider provider = metaValueItem.containerModule;
			if (provider != null) {
				return provider.getContainerItem(stack);
			}
		}
		return super.getContainerItem(stack);
	}

	/* ---------- ItemToolDamageModule ---------- */

	public void damageItem(ItemStack stack, int damage) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IToolDamage toolDamage = metaValueItem.toolDamage;
			if (toolDamage != null) {
				toolDamage.damageItem(stack, damage);
			}
		}
	}

	public int getItemDamage(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IToolDamage toolDamage = metaValueItem.toolDamage;
			if (toolDamage != null) {
				return toolDamage.getItemDamage(stack);
			}
		}
		return 0;
	}

	public int getItemMaxDamage(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IToolDamage toolDamage = metaValueItem.toolDamage;
			if (toolDamage != null) {
				return toolDamage.getItemMaxDamage(stack);
			}
		}
		return 0;
	}

	/* ---------- ItemDurabilityBarModule ---------- */

	/**
	 * @see net.minecraft.item.Item#showDurabilityBar
	 */
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IDurabilityBarProvider provider = metaValueItem.durabilityBarProvider;
			if (provider != null) {
				return provider.showDurabilityBar(stack);
			}
		}
		return super.showDurabilityBar(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getDurabilityForDisplay
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IDurabilityBarProvider provider = metaValueItem.durabilityBarProvider;
			if (provider != null) {
				return provider.getDurabilityForDisplay(stack);
			}
		}
		return super.getDurabilityForDisplay(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IDurabilityBarProvider provider = metaValueItem.durabilityBarProvider;
			if (provider != null) {
				return provider.getRGBDurabilityForDisplay(stack);
			}
		}
		return super.getRGBDurabilityForDisplay(stack);
	}

	/* ---------- ItemInteractionModule ---------- */

	/**
	 * @see net.minecraft.item.Item#itemInteractionForEntity
	 */
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemInteraction itemInteraction = metaValueItem.itemInteraction;
			if (itemInteraction != null) {
				return itemInteraction.itemInteractionForEntity(stack, playerIn, target, hand);
			}
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}

	/**
	 * @see net.minecraft.item.Item#onItemRightClick
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		MetaValueItem metaValueItem = this.getMetaValueItem(player.getHeldItem(hand));
		if (metaValueItem != null) {
			IItemInteraction itemInteraction = metaValueItem.itemInteraction;
			if (itemInteraction != null) {
				return itemInteraction.onItemRightClick(world, player, hand);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemInteraction itemInteraction = metaValueItem.itemInteraction;
			if (itemInteraction != null) {
				return itemInteraction.onLeftClickEntity(stack, player, entity);
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	/* ---------- ItemUseModule ---------- */

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemUse itemUse = metaValueItem.itemUse;
			if (itemUse != null) {
				return itemUse.getItemUseAction(stack);
			}
		}
		return super.getItemUseAction(stack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemUse itemUse = metaValueItem.itemUse;
			if (itemUse != null) {
				return itemUse.getMaxItemUseDuration(stack);
			}
		}
		return super.getMaxItemUseDuration(stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemUse itemUse = metaValueItem.itemUse;
			if (itemUse != null) {
				itemUse.onUsingTick(stack, player, count);
				return;
			}
		}
		super.onUsingTick(stack, player, count);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemUse itemUse = metaValueItem.itemUse;
			if (itemUse != null) {
				itemUse.onPlayerStoppedUsing(stack, world, player, timeLeft);
				return;
			}
		}
		super.onPlayerStoppedUsing(stack, world, player, timeLeft);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemUse itemUse = metaValueItem.itemUse;
			if (itemUse != null) {
				return itemUse.onItemUseFinish(stack, player);
			}
		}
		return super.onItemUseFinish(stack, world, player);
	}
}