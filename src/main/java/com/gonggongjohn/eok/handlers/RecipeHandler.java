package com.gonggongjohn.eok.handlers;

import com.github.zi_jing.cuckoolib.recipe.RecipeHelper;
import com.gonggongjohn.eok.EOK;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeHandler {
    public RecipeHandler() {
        registerRecipe();
    }

    private static void registerRecipe() {
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_sword", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 0), " # ", " # ", " X ", '#', Blocks.REDSTONE_BLOCK, 'X', Items.STICK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_pickaxe", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 1), "###", " X ", " X ", '#', Blocks.REDSTONE_BLOCK, 'X', Items.STICK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_axe", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 2), "## ", "#X ", " X ", '#', Blocks.REDSTONE_BLOCK, 'X', Items.STICK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_hoe", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 3), "## ", " X ", " X ", '#', Blocks.REDSTONE_BLOCK, 'X', Items.STICK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_shovel", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 4), " # ", " X ", " X ", '#', Blocks.REDSTONE_BLOCK, 'X', Items.STICK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_helmet", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 5), "###", "# #", '#', Blocks.REDSTONE_BLOCK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_chestplate", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 6), "# #", "###", "###", '#', Blocks.REDSTONE_BLOCK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_leggings", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 7), "###", "# #", "# #", '#', Blocks.REDSTONE_BLOCK);
        RecipeHelper.addShapedCraftingRecipe(EOK.MODID, "redstone_boots", new ItemStack(MetaItemHandler.REDSTONE_META_ITEM, 1, 8), "# #", "# #", '#', Blocks.REDSTONE_BLOCK);
    }
}
