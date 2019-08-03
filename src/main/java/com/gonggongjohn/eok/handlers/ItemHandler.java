package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.items.ItemChippedFlintFragment;
import com.gonggongjohn.eok.items.ItemFlintFragment;
import com.gonggongjohn.eok.items.ItemMaterialBase;
import com.gonggongjohn.eok.items.ItemPapyrus;
import com.gonggongjohn.eok.items.ItemPolishedFlintFragment;
import com.gonggongjohn.eok.items.ItemRefractingTelescope;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    public static final List<Item> items = new ArrayList<Item>();

    public static final Item papyrus = new ItemPapyrus();
    public static final Item refractingTelescope = new ItemRefractingTelescope();
    public static final Item flintFragment = new ItemFlintFragment();
    public static final Item chippedFlintFragment = new ItemChippedFlintFragment();
    public static final Item polishedFlintFragment = new ItemPolishedFlintFragment();

    public static final Item convexLens = new ItemMaterialBase("convexLens");
    public static final Item concaveLens = new ItemMaterialBase("concaveLens");
    public static final Item eokSymbol = new ItemMaterialBase("eok_symbol");
    public static final Item plantFiber = new ItemMaterialBase("plant_fiber");

}
