package com.gonggongjohn.eok.blocks;


import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.tile.TEHaystack;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;


public class BlockHaystack extends BlockContainer implements IHasModel {

    public final String name = "haystack";

    public BlockHaystack() {

        super(Material.GRASS);
        this.setUnlocalizedName("eok." + name);
        this.setCreativeTab(EOK.tabEOK);
        this.setRegistryName(name);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.PLANT);

        BlockHandler.BLOCK_REGISTRY.add(this);
        ItemHandler.ITEM_REGISTRY.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TEHaystack();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void registerModel() {

        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}