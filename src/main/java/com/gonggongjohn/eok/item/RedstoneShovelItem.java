package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tier.RedstoneTier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;

public class RedstoneShovelItem extends ShovelItem {
    private static final IItemTier tierRedstone = new RedstoneTier();

    public RedstoneShovelItem() {
        super(tierRedstone, 1.5F, -3.0F, new Properties().group(EOK.EOK_ITEMGROUP));
    }
}
