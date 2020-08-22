package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tier.RedstoneTier;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;

public class RedstoneAxeItem extends AxeItem {
    private static final IItemTier tierRedstone = new RedstoneTier();

    public RedstoneAxeItem() {
        super(tierRedstone, 5.0F, -3.0F, new Properties().group(EOK.EOK_ITEMGROUP));
    }
}
