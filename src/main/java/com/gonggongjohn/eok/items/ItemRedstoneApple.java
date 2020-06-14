package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRedstoneApple extends ItemFood implements IHasModel {

    private final String name = "redstone_apple";

    public ItemRedstoneApple()
    {
        super(4, 0.6F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setMaxStackSize(64);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 250, 2));
            player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 100, 1));
            player.addExperience(10);
        }
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
