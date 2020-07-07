package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.render.ICustomModel;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;

public class BlockRedstoneLog extends Block implements IHasModel {

    public final String name = "redstone_log";

    public BlockRedstoneLog() {
        super(Material.WOOD);
        this.setRegistryName(name);
        this.setUnlocalizedName("eok." + name);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(EOK.tabEOK);
        BlockHandler.BLOCK_REGISTRY.add(this);
        ItemHandler.ITEM_REGISTRY.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}