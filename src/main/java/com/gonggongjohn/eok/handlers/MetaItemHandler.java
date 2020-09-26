package com.gonggongjohn.eok.handlers;

import com.github.zi_jing.cuckoolib.material.SolidShapes;
import com.github.zi_jing.cuckoolib.metaitem.MaterialMetaItem;
import com.github.zi_jing.cuckoolib.metaitem.MetaItem;
import com.github.zi_jing.cuckoolib.metaitem.MetaValueItem;
import com.github.zi_jing.cuckoolib.metaitem.NormalMetaItem;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemInteraction;
import com.gonggongjohn.eok.EOK;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = EOK.MODID)
public class MetaItemHandler {
    public static final MetaItem META_ITEM = new NormalMetaItem(EOK.MODID, "meta_item0");

    public static final MaterialMetaItem MATERIAL_ITEM = new MaterialMetaItem(EOK.MODID, "material_item0", SolidShapes.DUST, SolidShapes.PLATE);

    public static final MetaItem REDSTONE_META_ITEM = new NormalMetaItem(EOK.MODID, "redstone_meta_item");

    public static final MetaValueItem EOK_SYMBOL = META_ITEM.addItem(0, "eok_symbol").addModule(new IItemInteraction() {
        @Override
        public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
            if (world.isRemote) {
                player.sendStatusMessage(new TextComponentTranslation("eok.messages.welcome_to_eok"), false);
                player.swingArm(hand);
            }
            return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
    });
    public static final MetaValueItem CONVEX_LENS = META_ITEM.addItem(1, "convex_lens");
    public static final MetaValueItem CONCAVE_LENS = META_ITEM.addItem(2, "concave_lens");
    public static final MetaValueItem CHIPPED_FLINT = META_ITEM.addItem(3, "chipped_flint");
    public static final MetaValueItem GROUND_FLINT = META_ITEM.addItem(4, "ground_flint");
    public static final MetaValueItem PLANT_FIBER = META_ITEM.addItem(5, "plant_fiber");
    public static final MetaValueItem RUBBER_MAT = META_ITEM.addItem(6, "rubber_mat");
    public static final MetaValueItem SIMPLE_HEMP_ROPE = META_ITEM.addItem(7, "simple_hemp_rope");
    public static final MetaValueItem STRONG_HEMP_ROPE = META_ITEM.addItem(8, "strong_hemp_rope");
    public static final MetaValueItem SHORT_STICK = META_ITEM.addItem(9, "short_stick");
    public static final MetaValueItem TUBE = META_ITEM.addItem(10, "tube");
    public static final MetaValueItem STONE_ROCK = META_ITEM.addItem(11, "stone_rock");

    public static final MetaValueItem REDSTONE_SWORD = REDSTONE_META_ITEM.addItem(0, "redstone_sword");
    public static final MetaValueItem REDSTONE_PICKAXE = REDSTONE_META_ITEM.addItem(1, "redstone_pickaxe");
    public static final MetaValueItem REDSTONE_AXE = REDSTONE_META_ITEM.addItem(2, "redstone_axe");
    public static final MetaValueItem REDSTONE_HOE = REDSTONE_META_ITEM.addItem(3, "redstone_hoe");
    public static final MetaValueItem REDSTONE_SPADE = REDSTONE_META_ITEM.addItem(4, "redstone_shovel");
    public static final MetaValueItem REDSTONE_HELMET = REDSTONE_META_ITEM.addItem(5, "redstone_helmet");
    public static final MetaValueItem REDSTONE_CHESTPLATE = REDSTONE_META_ITEM.addItem(6, "redstone_chestplate");
    public static final MetaValueItem REDSTONE_LEGGINGS = REDSTONE_META_ITEM.addItem(7, "redstone_leggings");
    public static final MetaValueItem REDSTONE_BOOTS = REDSTONE_META_ITEM.addItem(8, "redstone_boots");

    static {
        META_ITEM.setCreativeTab(EOK.tabEOK);
        MATERIAL_ITEM.setCreativeTab(EOK.tabEOK);
        REDSTONE_META_ITEM.setCreativeTab(EOK.tabEOK);

        REDSTONE_SWORD.setItemStackLimit(1);
        REDSTONE_PICKAXE.setItemStackLimit(1);
        REDSTONE_AXE.setItemStackLimit(1);
        REDSTONE_HOE.setItemStackLimit(1);
        REDSTONE_SPADE.setItemStackLimit(1);
        REDSTONE_HELMET.setItemStackLimit(1);
        REDSTONE_CHESTPLATE.setItemStackLimit(1);
        REDSTONE_LEGGINGS.setItemStackLimit(1);
        REDSTONE_BOOTS.setItemStackLimit(1);
    }

    /**
     * 没有任何用途，它仅被用来加载{@link MetaItemHandler}这个类
     */
    public static void setup() {
    }
}