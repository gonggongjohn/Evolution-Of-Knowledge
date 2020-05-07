package com.gonggongjohn.eok.api.item.meta;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaItem extends Item implements IMetaItem {
	
	/**
	 * 因为Map是无序的，所以还要一个有序列表来存储物品在创造物品栏的显示顺序
	 */
	private final List<Short> itemIds = Lists.newArrayList();
	private final Map<Short, MetaValueItem> items = Maps.newHashMap();
	
	/**
	 * 用来生成模型路径。<strong>注意：如果 {@code MetaValueItem} 的构造函数中添加了
	 *  {@code ModelResourceLocation} 参数，会优先使用构造函数中指定的模型路径。</strong>
	 * <br>
	 * 示例(将 {@code "examplemod"} 换成你的mod id)：<br>
	 * <pre>
	 * <code>
	 *(item) -> {
	 *    return new ModelResourceLocation(new ResourceLocation("examplemod", item.getModelName()), "inventory");
	 *});
	 * </code>
	 * </pre>
	 */
	private final Function<MetaValueItem, ModelResourceLocation> modelLocationFunction;
	private final String modid;
	
	/**
	 * @param modelLocationFunction 用来生成模型路径，见字段 {@link #modelLocationFunction}
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
	
	/**
	 * 给这个MetaItem添加子物品
	 */
	public MetaValueItem addItem(MetaValueItem item) {
		if(this.items.size() == 32767) {
			throw new IllegalStateException("One MetaItem instance can only hold 32767 MetaValueItems at most");
		}
		item.metaItem = this;
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

	/* ---------- ItemArmorModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#onArmorTick
	 */
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		this.items.get((short)itemStack.getMetadata()).armorModule.onArmorTick(world, player, itemStack);
	}

	/**
	 * @see net.minecraft.item.Item#isValidArmor
	 */
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		return this.items.get((short)stack.getMetadata()).armorModule.isValidArmor(stack, armorType, entity);
	}

	/**
	 * @see net.minecraft.item.Item#getEquipmentSlot
	 */
	@Override
	@Nullable
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).armorModule.getEquipmentSlot(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getArmorTexture
	 */
	@Override
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return this.items.get((short)stack.getMetadata()).armorModule.getArmorTexture(stack, entity, slot, type);
	}

	/**
	 * @see net.minecraft.item.Item#getArmorModel
	 */
	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		return this.items.get((short)itemStack.getMetadata()).armorModule.getArmorModel(entityLiving, itemStack, armorSlot, _default);
	}

	/**
	 * @see net.minecraft.item.Item#renderHelmetOverlay
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		this.items.get((short)stack.getMetadata()).armorModule.renderHelmetOverlay(stack, player, resolution, partialTicks);
	}

	/* ---------- ItemAttributesModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#hasEffect
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).attributesModule.hasEffect(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getSmeltingExperience
	 */
	@Override
	public float getSmeltingExperience(ItemStack item) {
		return this.items.get((short)item.getMetadata()).attributesModule.getSmeltingExperience(item);
	}

	/**
	 * @see net.minecraft.item.Item#isBeaconPayment
	 */
	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).attributesModule.isBeaconPayment(stack);
	}
	
	/* ---------- ItemBehaviorModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#onUpdate
	 */
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		this.items.get((short)stack.getMetadata()).behaviorModule.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	/**
	 * @see net.minecraft.item.Item#onCreated
	 */
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		this.items.get((short)stack.getMetadata()).behaviorModule.onCreated(stack, worldIn, playerIn);
	}

	/**
	 * @see net.minecraft.item.Item#onDroppedByPlayer
	 */
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return this.items.get((short)item.getMetadata()).behaviorModule.onDroppedByPlayer(item, player);
	}
	
	/* ---------- ItemContainerModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getContainerItem
	 */
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return this.items.get((short)itemStack.getMetadata()).containerModule.getContainerItem(itemStack);
	}
	
	/* ---------- ItemDurabilityModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getDamage
	 */
	@Override
	public int getDamage(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).durabilityModule.getDamage(stack);
	}

	/**
	 * @see net.minecraft.item.Item#showDurabilityBar
	 */
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).durabilityModule.showDurabilityBar(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getDurabilityForDisplay
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).durabilityModule.getDurabilityForDisplay(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).durabilityModule.getRGBDurabilityForDisplay(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	@Override
	public int getMaxDamage(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).durabilityModule.getMaxDamage(stack);
	}
	
	/* ---------- ItemEnchantmentModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#isEnchantable
	 */
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).enchantmentModule.isEnchantable(stack);
	}

	/**
	 * @see net.minecraft.item.Item#canApplyAtEnchantingTable
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return this.items.get((short)stack.getMetadata()).enchantmentModule.canApplyAtEnchantingTable(stack, enchantment);
	}
	
	/* ---------- ItemEntityModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getEntityLifespan
	 */
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return this.items.get((short)itemStack.getMetadata()).entityModule.getEntityLifespan(itemStack, world);
	}

	/**
	 * @see net.minecraft.item.Item#hasCustomEntity
	 */
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).entityModule.hasCustomEntity(stack);
	}

	/**
	 * @see net.minecraft.item.Item#createEntity
	 */
	@Override
	@Nullable
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return this.items.get((short)itemstack.getMetadata()).entityModule.createEntity(world, location, itemstack);
	}

	/**
	 * @see net.minecraft.item.Item#onEntityItemUpdate
	 */
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		return this.items.get((short)entityItem.getItem().getMetadata()).entityModule.onEntityItemUpdate(entityItem);
	}
	
	/* ---------- ItemFuelModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getItemBurnTime
	 */
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return this.items.get((short)itemStack.getMetadata()).fuelModule.getItemBurnTime(itemStack);
	}
	
	/* ---------- ItemHorseArmorModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getHorseArmorType
	 */
	@Override
	public HorseArmorType getHorseArmorType(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).horseArmorModule.getHorseArmorType(stack);
	}

	/**
	 * @see net.minecraft.item.Item#getHorseArmorTexture
	 */
	@Override
	public String getHorseArmorTexture(EntityLiving wearer, ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).horseArmorModule.getHorseArmorTexture(wearer, stack);
	}

	/**
	 * @see net.minecraft.item.Item#onHorseArmorTick
	 */
	@Override
	public void onHorseArmorTick(World world, EntityLiving horse, ItemStack armor) {
		this.items.get((short)armor.getMetadata()).horseArmorModule.onHorseArmorTick(world, horse, armor);
	}
	
	/* ---------- ItemInteractionModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#itemInteractionForEntity
	 */
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		return this.items.get((short)stack.getMetadata()).interactionModule.itemInteractionForEntity(stack, playerIn, target, hand);
	}

	/**
	 * @see net.minecraft.item.Item#getMaxItemUseDuration
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).interactionModule.getMaxItemUseDuration(stack);
	}

	/**
	 * @see net.minecraft.item.Item#doesSneakBypassUse
	 */
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return this.items.get((short)stack.getMetadata()).interactionModule.doesSneakBypassUse(stack, world, pos, player);
	}

	/**
	 * @see net.minecraft.item.Item#canHarvestBlock
	 */
	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).interactionModule.canHarvestBlock(state, stack);
	}

	/**
	 * @see net.minecraft.item.Item#onItemRightClick
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return this.items.get((short)playerIn.getHeldItem(handIn).getMetadata()).interactionModule.onItemRightClick(worldIn, playerIn, handIn);
	}

	/**
	 * @see net.minecraft.item.Item#onItemUse
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return this.items.get((short)player.getHeldItem(hand).getMetadata()).interactionModule.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	/**
	 * @see net.minecraft.item.Item#onItemUseFinish
	 */
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return this.items.get((short)stack.getMetadata()).interactionModule.onItemUseFinish(stack, worldIn, entityLiving);
	}

	/**
	 * @see net.minecraft.item.Item#hitEntity
	 */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return this.items.get((short)stack.getMetadata()).interactionModule.hitEntity(stack, target, attacker);
	}

	/**
	 * @see net.minecraft.item.Item#onBlockDestroyed
	 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		return this.items.get((short)stack.getMetadata()).interactionModule.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	/**
	 * @see net.minecraft.item.Item#onPlayerStoppedUsing
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		this.items.get((short)stack.getMetadata()).interactionModule.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
	}

	/**
	 * @see net.minecraft.item.Item#onItemUseFirst
	 */
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		return this.items.get((short)player.getHeldItem(hand).getMetadata()).interactionModule.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	/**
	 * @see net.minecraft.item.Item#onBlockStartBreak
	 */
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		return this.items.get((short)itemstack.getMetadata()).interactionModule.onBlockStartBreak(itemstack, pos, player);
	}

	/**
	 * @see net.minecraft.item.Item#onUsingTick
	 */
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		this.items.get((short)stack.getMetadata()).interactionModule.onUsingTick(stack, player, count);
	}

	/**
	 * @see net.minecraft.item.Item#onLeftClickEntity
	 */
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return this.items.get((short)stack.getMetadata()).interactionModule.onLeftClickEntity(stack, player, entity);
	}

	/**
	 * @see net.minecraft.item.Item#onEntitySwing
	 */
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).interactionModule.onEntitySwing(entityLiving, stack);
	}
	
	/* ---------- ItemNameAndTooltipModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getHighlightTip
	 */
	@Override
	public String getHighlightTip(ItemStack item, String displayName) {
		return this.items.get((short)item.getMetadata()).nameAndTooltipModule.getHighlightTip(item, displayName);
	}

	/**
	 * @see net.minecraft.item.Item#getItemStackDisplayName
	 */
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return this.items.get((short)stack.getMetadata()).nameAndTooltipModule.getItemStackDisplayName(stack);
	}

	/**
	 * @see net.minecraft.item.Item#addInformation
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		this.items.get((short)stack.getMetadata()).nameAndTooltipModule.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	/* ---------- ItemToolModule ---------- */
	
	/**
	 * @see net.minecraft.item.Item#getDestroySpeed
	 */
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return this.items.get((short)stack.getMetadata()).toolModule.getDestroySpeed(stack, state);
	}

	/**
	 * @see net.minecraft.item.Item#canDestroyBlockInCreative
	 */
	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
		return this.items.get((short)stack.getMetadata()).toolModule.canDestroyBlockInCreative(world, pos, stack, player);
	}

	/**
	 * @see net.minecraft.item.Item#shouldCauseBlockBreakReset
	 */
	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
		return this.items.get((short)newStack.getMetadata()).toolModule.shouldCauseBlockBreakReset(oldStack, newStack);
	}

	/**
	 * @see net.minecraft.item.Item#canContinueUsing
	 */
	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
		return this.items.get((short)newStack.getMetadata()).toolModule.canContinueUsing(oldStack, newStack);
	}

	/**
	 * @see net.minecraft.item.Item#canDisableShield
	 */
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
		return this.items.get((short)stack.getMetadata()).toolModule.canDisableShield(stack, shield, entity, attacker);
	}

	/**
	 * @see net.minecraft.item.Item#isShield
	 */
	@Override
	public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
		return this.items.get((short)stack.getMetadata()).toolModule.isShield(stack, entity);
	}
}
