package com.gonggongjohn.eok.api.item.meta;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import gnu.trove.map.TShortObjectMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			this.metaItem.forEachValue((metaValueItem) -> items.add(metaValueItem.getItemStack()));
		}
	}

	protected MetaValueItem createMetaValueItem(short id, String unlocalizedName) {
		return new MetaValueItem(this, id, unlocalizedName);
	}

	/**
	 * 给这个MetaItem添加子物品
	 */
	public MetaValueItem addItem(short id, String unlocalizedName) {
		Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, id,
				"MetaValueItem ID [ " + id + " ] of item [ " + unlocalizedName + " ] is invalid");
		if (this.metaItem.containsKey(id)) {
			throw new IllegalArgumentException(
					"MetaValueItem ID [ " + id + " ] of item [ " + unlocalizedName + " ] is registered");
		}
		MetaValueItem metaValueItem = this.createMetaValueItem(id, unlocalizedName);
		this.metaItem.put(id, metaValueItem);
		return metaValueItem;
	}

	public MetaValueItem getMetaValueItem(short id) {
		return this.metaItem.get(id);
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

	/* ---------- ItemArmorModule ---------- */

	/**
	 * @see net.minecraft.item.Item#onArmorTick
	 */
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		this.getMetaValueItem(stack).armorModule.onArmorTick(world, player, stack);
	}

	/**
	 * @see net.minecraft.item.Item#isValidArmor
	 */
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		return this.getMetaValueItem(stack).armorModule.isValidArmor(stack, armorType, entity);
	}

	/**
	 * @see net.minecraft.item.Item#getEquipmentSlot
	 */
	@Override
	@Nullable
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
		return this.getMetaValueItem(stack).armorModule.getEquipmentSlot(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getArmorTexture
	 */
	@Override
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return this.getMetaValueItem(stack).armorModule.getArmorTexture(stack, entity, slot, type);
	}

	/**
	 * @see net.minecraft.item.Item#getArmorModel
	 */
	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		return this.getMetaValueItem(stack).armorModule.getArmorModel(entityLiving, stack, armorSlot, _default);
	}

	/**
	 * @see net.minecraft.item.Item#renderHelmetOverlay
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution,
			float partialTicks) {
		this.getMetaValueItem(stack).armorModule.renderHelmetOverlay(stack, player, resolution, partialTicks);
	}

	/* ---------- ItemAttributesModule ---------- */

	/**
	 * @see net.minecraft.item.Item#hasEffect
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return this.getMetaValueItem(stack).attributesModule.hasEffect(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getSmeltingExperience
	 */
	@Override
	public float getSmeltingExperience(ItemStack stack) {
		return this.getMetaValueItem(stack).attributesModule.getSmeltingExperience(stack);
	}

	/**
	 * @see net.minecraft.item.Item#isBeaconPayment
	 */
	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return this.getMetaValueItem(stack).attributesModule.isBeaconPayment(stack);
	}

	/* ---------- ItemBehaviorModule ---------- */

	/**
	 * @see net.minecraft.item.Item#onUpdate
	 */
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		this.getMetaValueItem(stack).behaviorModule.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	/**
	 * @see net.minecraft.item.Item#onCreated
	 */
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		this.getMetaValueItem(stack).behaviorModule.onCreated(stack, worldIn, playerIn);
	}

	/**
	 * @see net.minecraft.item.Item#onDroppedByPlayer
	 */
	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
		return this.getMetaValueItem(stack).behaviorModule.onDroppedByPlayer(stack, player);
	}

	/* ---------- ItemContainerModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getContainerItem
	 */
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return this.getMetaValueItem(stack).containerModule.getContainerItem(stack);
	}

	/* ---------- ItemDurabilityModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getDamage
	 */
	@Override
	public int getDamage(ItemStack stack) {
		return this.getMetaValueItem(stack).durabilityModule.getDamage(stack);
	}

	/**
	 * @see net.minecraft.item.Item#showDurabilityBar
	 */
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this.getMetaValueItem(stack).durabilityModule.showDurabilityBar(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getDurabilityForDisplay
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return this.getMetaValueItem(stack).durabilityModule.getDurabilityForDisplay(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return this.getMetaValueItem(stack).durabilityModule.getRGBDurabilityForDisplay(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	@Override
	public int getMaxDamage(ItemStack stack) {
		return this.getMetaValueItem(stack).durabilityModule.getMaxDamage(stack);
	}

	/* ---------- ItemEnchantmentModule ---------- */

	/**
	 * @see net.minecraft.item.Item#isEnchantable
	 */
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return this.getMetaValueItem(stack).enchantmentModule.isEnchantable(stack);
	}

	/**
	 * @see net.minecraft.item.Item#canApplyAtEnchantingTable
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return this.getMetaValueItem(stack).enchantmentModule.canApplyAtEnchantingTable(stack, enchantment);
	}

	/* ---------- ItemEntityModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getEntityLifespan
	 */
	@Override
	public int getEntityLifespan(ItemStack stack, World world) {
		return this.getMetaValueItem(stack).entityModule.getEntityLifespan(stack, world);
	}

	/**
	 * @see net.minecraft.item.Item#hasCustomEntity
	 */
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return this.getMetaValueItem(stack).entityModule.hasCustomEntity(stack);
	}

	/**
	 * @see net.minecraft.item.Item#createEntity
	 */
	@Override
	@Nullable
	public Entity createEntity(World world, Entity location, ItemStack stack) {
		return this.getMetaValueItem(stack).entityModule.createEntity(world, location, stack);
	}

	/**
	 * @see net.minecraft.item.Item#onEntityItemUpdate
	 */
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		return this.getMetaValueItem((short) entityItem.getItem().getMetadata()).entityModule
				.onEntityItemUpdate(entityItem);
	}

	/* ---------- ItemFuelModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getItemBurnTime
	 */
	@Override
	public int getItemBurnTime(ItemStack stack) {
		return this.getMetaValueItem(stack).fuelModule.getItemBurnTime(stack);
	}

	/* ---------- ItemHorseArmorModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getHorseArmorType
	 */
	@Override
	public HorseArmorType getHorseArmorType(ItemStack stack) {
		return this.getMetaValueItem(stack).horseArmorModule.getHorseArmorType(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getHorseArmorTexture
	 */
	@Override
	public String getHorseArmorTexture(EntityLiving wearer, ItemStack stack) {
		return this.getMetaValueItem(stack).horseArmorModule.getHorseArmorTexture(wearer, stack);
	}

	/**
	 * @see net.minecraft.item.Item#onHorseArmorTick
	 */
	@Override
	public void onHorseArmorTick(World world, EntityLiving horse, ItemStack stack) {
		this.getMetaValueItem(stack).horseArmorModule.onHorseArmorTick(world, horse, stack);
	}

	/* ---------- ItemInteractionModule ---------- */

	/**
	 * @see net.minecraft.item.Item#itemInteractionForEntity
	 */
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		return this.getMetaValueItem(stack).interactionModule.itemInteractionForEntity(stack, playerIn, target, hand);
	}

	/**
	 * @see net.minecraft.item.Item#getMaxItemUseDuration
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return this.getMetaValueItem(stack).interactionModule.getMaxItemUseDuration(stack);
	}

	/**
	 * @see net.minecraft.item.Item#doesSneakBypassUse
	 */
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return this.getMetaValueItem(stack).interactionModule.doesSneakBypassUse(stack, world, pos, player);
	}

	/**
	 * @see net.minecraft.item.Item#canHarvestBlock
	 */
	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return this.getMetaValueItem(stack).interactionModule.canHarvestBlock(state, stack);
	}

	/**
	 * @see net.minecraft.item.Item#onItemRightClick
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		return this.getMetaValueItem(player.getHeldItem(hand)).interactionModule.onItemRightClick(world, player, hand);
	}

	/**
	 * @see net.minecraft.item.Item#onItemUse
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return this.getMetaValueItem(player.getHeldItem(hand)).interactionModule.onItemUse(player, worldIn, pos, hand,
				facing, hitX, hitY, hitZ);
	}

	/**
	 * @see net.minecraft.item.Item#onItemUseFinish
	 */
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return this.getMetaValueItem(stack).interactionModule.onItemUseFinish(stack, worldIn, entityLiving);
	}

	/**
	 * @see net.minecraft.item.Item#hitEntity
	 */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return this.getMetaValueItem(stack).interactionModule.hitEntity(stack, target, attacker);
	}

	/**
	 * @see net.minecraft.item.Item#onBlockDestroyed
	 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		return this.getMetaValueItem(stack).interactionModule.onBlockDestroyed(stack, worldIn, state, pos,
				entityLiving);
	}

	/**
	 * @see net.minecraft.item.Item#onPlayerStoppedUsing
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		this.getMetaValueItem(stack).interactionModule.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
	}

	/**
	 * @see net.minecraft.item.Item#onItemUseFirst
	 */
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		return this.getMetaValueItem(player.getHeldItem(hand)).interactionModule.onItemUseFirst(player, world, pos,
				side, hitX, hitY, hitZ, hand);
	}

	/**
	 * @see net.minecraft.item.Item#onBlockStartBreak
	 */
	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
		return this.getMetaValueItem(stack).interactionModule.onBlockStartBreak(stack, pos, player);
	}

	/**
	 * @see net.minecraft.item.Item#onUsingTick
	 */
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		this.getMetaValueItem(stack).interactionModule.onUsingTick(stack, player, count);
	}

	/**
	 * @see net.minecraft.item.Item#onLeftClickEntity
	 */
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return this.getMetaValueItem(stack).interactionModule.onLeftClickEntity(stack, player, entity);
	}

	/**
	 * @see net.minecraft.item.Item#onEntitySwing
	 */
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		return this.getMetaValueItem(stack).interactionModule.onEntitySwing(entityLiving, stack);
	}

	/* ---------- ItemNameAndTooltipModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getHighlightTip
	 */
	@Override
	public String getHighlightTip(ItemStack stack, String displayName) {
		return this.getMetaValueItem(stack).nameAndTooltipModule.getHighlightTip(stack, displayName);
	}

	/**
	 * @see net.minecraft.item.Item#getItemStackDisplayName
	 */
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return this.getMetaValueItem(stack).nameAndTooltipModule.getItemStackDisplayName(stack);
	}

	/**
	 * @see net.minecraft.item.Item#addInformation
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		this.getMetaValueItem(stack).nameAndTooltipModule.addInformation(stack, world, tooltip, flag);
	}

	/* ---------- ItemToolModule ---------- */

	/**
	 * @see net.minecraft.item.Item#getDestroySpeed
	 */
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return this.getMetaValueItem(stack).toolModule.getDestroySpeed(stack, state);
	}

	/**
	 * @see net.minecraft.item.Item#canDestroyBlockInCreative
	 */
	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
		return this.getMetaValueItem(stack).toolModule.canDestroyBlockInCreative(world, pos, stack, player);
	}

	/**
	 * @see net.minecraft.item.Item#shouldCauseBlockBreakReset
	 */
	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
		return this.getMetaValueItem(newStack).toolModule.shouldCauseBlockBreakReset(oldStack, newStack);
	}

	/**
	 * @see net.minecraft.item.Item#canContinueUsing
	 */
	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
		return this.getMetaValueItem(newStack).toolModule.canContinueUsing(oldStack, newStack);
	}

	/**
	 * @see net.minecraft.item.Item#canDisableShield
	 */
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity,
			EntityLivingBase attacker) {
		return this.getMetaValueItem(stack).toolModule.canDisableShield(stack, shield, entity, attacker);
	}

	/**
	 * @see net.minecraft.item.Item#isShield
	 */
	@Override
	public boolean isShield(ItemStack stack, EntityLivingBase entity) {
		return this.getMetaValueItem(stack).toolModule.isShield(stack, entity);
	}
}