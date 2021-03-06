package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ConfigHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public class Torcherino extends Item implements IHasModel {

    public Torcherino() {
        String name = "torcherino";
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (!worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString(I18n.format("eok.messages.torcherino_crafted.msg")));
            super.onCreated(stack, worldIn, playerIn);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.swingArm(EnumHand.MAIN_HAND);


        if (worldIn.isRemote)
            return super.onItemRightClick(worldIn, playerIn, handIn);

        int count = playerIn.getHeldItemMainhand().getCount();
        playerIn.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        playerIn.sendMessage(new TextComponentString(I18n.format("eok.messages.torcherino_use.msg")));
        playerIn.sendMessage(new TextComponentString(I18n.format("eok.messages.torcherino_use.msg2")));

        if (ConfigHandler.torcherinoExploding == false) {
            playerIn.attackEntityFrom(DamageSource.GENERIC, 1000);
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        //手上的火把越多，爆炸威力越强
        playerIn.attackEntityFrom(DamageSource.causeExplosionDamage(
                worldIn.createExplosion(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, 6.0F * count, true)),
                1000);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("eok.tooltip.torcherino"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }


}
