package com.gonggongjohn.eok.blocks;

import java.util.Random;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.GUIHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.tile.TEHayTorchBaseLit;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHayTorchBaseLit extends BlockContainer implements IHasModel {
	
    public final String name = "hay_torch_base_lit";
    public static final AxisAlignedBB HAY_TORCH_BASE_LIT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockHayTorchBaseLit() {
    	
        super(Material.WOOD);
        
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setHardness(7.0F);
        this.setLightLevel(1);
        
        BlockHandler.BLOCK_REGISTRY.add(this);
        ItemHandler.ITEM_REGISTRY.add(new ItemBlock(this).setRegistryName(name));
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	
        return HAY_TORCH_BASE_LIT_AABB;
    }
    
    @Override
    public void registerModel() {
    	
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
        if(!worldIn.isRemote) {
        	
            int id = GUIHandler.GUIHayTorchBaseLit;
            playerIn.openGui(EOK.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    	
    	double dx = (double)pos.getX() + 0.5D;
        double dy = (double)pos.getY() + 1.1D;
        //double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double dz = (double)pos.getZ() + 0.5D;
        double dRand = rand.nextDouble() * 0.6D - 0.3D;

        if (rand.nextDouble() < 0.2D) {
        	
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        //flaming out on four sides, the position is at up middle. (Can be specify with torch later)
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, dx - 0.02D, dy, dz + dRand, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, dx - 0.02D, dy, dz + dRand, 0.0D, 0.0D, 0.0D);

        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, dx + 0.02D, dy, dz + dRand, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, dx + 0.02D, dy, dz + dRand, 0.0D, 0.0D, 0.0D);

        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, dx + dRand, dy, dz - 0.52D, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, dx + dRand, dy, dz - 0.02D, 0.0D, 0.0D, 0.0D);

        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, dx + dRand, dy, dz + 0.52D, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, dx + dRand, dy, dz + 0.02D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
    	
    	return new TEHayTorchBaseLit();
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	
        return EnumBlockRenderType.MODEL;
    }
}
