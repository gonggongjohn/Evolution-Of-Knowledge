package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.util.RedstoneArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class RedstoneHelmetItem extends ArmorItem {
    private static final IArmorMaterial redstoneArmorMaterial = new RedstoneArmorMaterial();

    public RedstoneHelmetItem() {
        super(redstoneArmorMaterial, EquipmentSlotType.HEAD, new Properties().group(EOK.EOK_ITEMGROUP));
    }

//    @Override
//    public void onArmorTick(World world, PlayerEntity player, ItemStack item) {
//        if (!world.isRemote) {
//            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 300, 0));
//            player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 300, 0));
//        }
//    }
}
