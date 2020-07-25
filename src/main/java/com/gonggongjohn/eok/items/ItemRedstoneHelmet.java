package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import com.gonggongjohn.eok.utils.RedstoneArmorMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRedstoneHelmet extends RedstoneArmorMaterial implements IHasModel {

    public ItemRedstoneHelmet() {
        super(EntityEquipmentSlot.HEAD);
        String name = "redstone_helmet";
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setMaxStackSize(1);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack item) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300, 0));
        }
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
