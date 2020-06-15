package com.gonggongjohn.eok.api.item.meta;

import com.gonggongjohn.eok.api.item.meta.module.*;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
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
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.List;

public class MetaItem extends Item implements IMetaItem {
    protected String modid;

    /**
     * 用于存储MetaValueItem的注册表
     */
    protected TShortObjectMap<MetaValueItem> metaItem = new TShortObjectHashMap<>();

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

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IItemNameProvider provider = this.getMetaValueItem(stack).nameProvider;
        if (provider != null) {
            return provider.getItemStackDisplayName(stack);
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        IItemTooltipProvider provider = this.getMetaValueItem(stack).tooltipProvider;
        if (provider != null) {
            provider.addInformation(stack, world, tooltip, flag);
        } else {
            super.getItemStackDisplayName(stack);
        }
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

    /* ---------- ItemContainerModule ---------- */

    /**
     * @see net.minecraft.item.Item#getContainerItem
     */
    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        IContainerItemProvider provider = this.getMetaValueItem(stack).containerModule;
        if (provider != null) {
            return provider.getContainerItem(stack);
        }
        return super.getContainerItem(stack);
    }

    /* ---------- ItemToolDamageModule ---------- */

    public void damageItem(ItemStack stack, int damage) {
        IToolDamage toolDamage = this.getMetaValueItem(stack).toolDamage;
        if (toolDamage != null) {
            toolDamage.damageItem(stack, damage);
        }
    }

    /**
     * @see net.minecraft.item.Item#getDamage
     */
    @Override
    public int getDamage(ItemStack stack) {
        IToolDamage toolDamage = this.getMetaValueItem(stack).toolDamage;
        if (toolDamage != null) {
            return toolDamage.getItemDamage(stack);
        }
        return super.getDamage(stack);
    }

    /**
     * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
     */
    @Override
    public int getMaxDamage(ItemStack stack) {
        IToolDamage toolDamage = this.getMetaValueItem(stack).toolDamage;
        if (toolDamage != null) {
            return toolDamage.getItemMaxDamage(stack);
        }
        return super.getMaxDamage(stack);
    }

    /* ---------- ItemDurabilityBarModule ---------- */

    /**
     * @see net.minecraft.item.Item#showDurabilityBar
     */
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        IDurabilityBarProvider provider = this.getMetaValueItem(stack).durabilityBarProvider;
        if (provider != null) {
            return provider.showDurabilityBar(stack);
        }
        return super.showDurabilityBar(stack);
    }

    /**
     * @see net.minecraft.item.Item#getDurabilityForDisplay
     */
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IDurabilityBarProvider provider = this.getMetaValueItem(stack).durabilityBarProvider;
        if (provider != null) {
            return provider.getDurabilityForDisplay(stack);
        }
        return super.getDurabilityForDisplay(stack);
    }

    /**
     * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
     */
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        IDurabilityBarProvider provider = this.getMetaValueItem(stack).durabilityBarProvider;
        if (provider != null) {
            return provider.getRGBDurabilityForDisplay(stack);
        }
        return super.getRGBDurabilityForDisplay(stack);
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
        IItemInteraction itemInteraction = this.getMetaValueItem(stack).itemInteraction;
        if (itemInteraction != null) {
            return itemInteraction.itemInteractionForEntity(stack, playerIn, target, hand);
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    /**
     * @see net.minecraft.item.Item#onItemRightClick
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        IItemInteraction itemInteraction = this.getMetaValueItem(player.getHeldItem(hand)).itemInteraction;
        if (itemInteraction != null) {
            return itemInteraction.onItemRightClick(world, player, hand);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        IItemInteraction itemInteraction = this.getMetaValueItem(stack).itemInteraction;
        if (itemInteraction != null) {
            return itemInteraction.onLeftClickEntity(stack, player, entity);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    /* ---------- ItemUseModule ---------- */

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        IItemUse itemUse = this.getMetaValueItem(stack).itemUse;
        if (itemUse != null) {
            return itemUse.getItemUseAction(stack);
        }
        return super.getItemUseAction(stack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        IItemUse itemUse = this.getMetaValueItem(stack).itemUse;
        if (itemUse != null) {
            return itemUse.getMaxItemUseDuration(stack);
        }
        return super.getMaxItemUseDuration(stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        IItemUse itemUse = this.getMetaValueItem(stack).itemUse;
        if (itemUse != null) {
            itemUse.onUsingTick(stack, player, count);
        } else {
            super.onUsingTick(stack, player, count);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
        IItemUse itemUse = this.getMetaValueItem(stack).itemUse;
        if (itemUse != null) {
            itemUse.onPlayerStoppedUsing(stack, world, player, timeLeft);
        } else {
            super.onPlayerStoppedUsing(stack, world, player, timeLeft);
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player) {
        IItemUse itemUse = this.getMetaValueItem(stack).itemUse;
        if (itemUse != null) {
            return itemUse.onItemUseFinish(stack, player);
        }
        return super.onItemUseFinish(stack, world, player);
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