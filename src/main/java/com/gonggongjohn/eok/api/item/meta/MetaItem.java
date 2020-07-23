package com.gonggongjohn.eok.api.item.meta;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.module.*;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetaItem extends Item implements IMetaItem {
	protected static final ModelResourceLocation EMPTY_MODEL = new ModelResourceLocation("builtin/missing",
			"inventory");

	protected final String modid;

	/**
	 * 用于存储MetaValueItem的注册表
	 */
	protected final TShortObjectMap<MetaValueItem> metaItem;

	/**
	 * 用于存储MetaValueItem模型的注册表
	 */
	protected final TShortObjectMap<List<ModelResourceLocation>> itemModel;

	public MetaItem(ResourceLocation registryName) {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setRegistryName(registryName);
		this.modid = registryName.getResourceDomain();
		this.metaItem = new TShortObjectHashMap<>();
		this.itemModel = new TShortObjectHashMap<>();
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (tab == EOK.tabEOK) {
			this.metaItem.forEachValue((metaValueItem) -> {
				items.add(metaValueItem.getItemStack());
				return true;
			});
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModel() {
		this.metaItem.forEachEntry((id, metaValueItem) -> {
			int modelCount = metaValueItem.modelCount;
			if (modelCount == 1) {
				ResourceLocation location = this.getItemModel(metaValueItem);
				ModelBakery.registerItemVariants(this, location);
				this.itemModel.put(id, Arrays.asList(new ModelResourceLocation(location, "inventory")));
			} else if (modelCount > 1) {
				List<ModelResourceLocation> models = new ArrayList<>();
				for (int i = 0; i < modelCount; i++) {
					ResourceLocation location = this.getItemModel(metaValueItem, i);
					ModelBakery.registerItemVariants(this, location);
					models.add(new ModelResourceLocation(location, "inventory"));
				}
				this.itemModel.put(id, models);
			}
			return true;
		});
		ModelLoader.setCustomMeshDefinition(this, (stack) -> {
			short metadate = (short) stack.getMetadata();
			MetaValueItem metaValueItem = this.getMetaValueItem(stack);
			int modelCount = metaValueItem.modelCount;
			List<ModelResourceLocation> models = this.itemModel.get(metadate);
			if (modelCount == 1 && !models.isEmpty()) {
				return models.get(0);
			} else if (modelCount > 1 && !models.isEmpty()) {
				if (metaValueItem.containsModule("modelProvider")) {
					return models.get(metaValueItem.modelProvider.getModelIndex(stack));
				}
				return models.get(0);
			}
			return EMPTY_MODEL;
		});
	}

	public ResourceLocation getItemModel(MetaValueItem metaValueItem, int id) {
		return new ResourceLocation(this.modid, "metaitem/" + metaValueItem.unlocalizedName);
	}

	public ResourceLocation getItemModel(MetaValueItem metaValueItem) {
		return new ResourceLocation(this.modid, "metaitem/" + metaValueItem.unlocalizedName);
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

	@Nonnull
	@Override
	public String getUnlocalizedName(@Nonnull ItemStack stack) {
		return "metaitem." + this.modid + "." + this.getMetaValueItem(stack).getUnlocalizedName();
	}

	@Override
	public int getItemStackLimit(@Nonnull ItemStack stack) {
		return this.getMetaValueItem(stack).getItemStackLimit();
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
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
	public void addInformation(@Nonnull ItemStack stack, World world, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flag) {
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
	@Nonnull
	@Override
	public ItemStack getContainerItem(@Nonnull ItemStack stack) {
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
	public boolean showDurabilityBar(@Nonnull ItemStack stack) {
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
	public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
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
	public int getRGBDurabilityForDisplay(@Nonnull ItemStack stack) {
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
	public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn, @Nonnull EntityLivingBase target,
											@Nonnull EnumHand hand) {
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
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
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
	public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
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

	@Nonnull
	@Override
	public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
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
	public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
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
	public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
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
	public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase player, int timeLeft) {
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

	@Nonnull
	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase player) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null) {
			IItemUse itemUse = metaValueItem.itemUse;
			if (itemUse != null) {
				return itemUse.onItemUseFinish(stack, player);
			}
		}
		return super.onItemUseFinish(stack, world, player);
	}

	/* ---------- ItemFuelModule ---------- */

	@Override
	public int getItemBurnTime(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule("itemFuel")) {
			return metaValueItem.itemFuel.getItemBurnTime(stack);
		}
		return super.getItemBurnTime(stack);
	}
}