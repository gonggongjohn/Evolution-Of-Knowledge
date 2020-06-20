package com.gonggongjohn.eok.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.tile.TEStoneMill;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStoneMill extends BlockContainer implements IHasModel {
	
    public final String name = "stone_mill";
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    //private boolean isActing;

    public BlockStoneMill() {
    	
        super(Material.ROCK);
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setHardness(5.0F);
        this.setCreativeTab(EOK.tabEOK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
    	
        return new TEStoneMill();
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    	ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);
    	
        //这个GUIChest没懂原理所以不会改，运行到这一步会打开身上的背包界面（0.1s）
        playerIn.displayGUIChest(ilockablecontainer);
        //我加了这个用来关闭GUI，的确不会出现闪烁的背包GUI界面了，但是十字准星会有0.1s变成鼠标，吐血了
        playerIn.closeScreen();

        return true;
    }
    

    @Nullable
    public ILockableContainer getLockableContainer(World worldIn, BlockPos pos) {
    	
        return this.getContainer(worldIn, pos, false);
    }
    
    @Nullable
    private ILockableContainer getContainer(World worldIn, BlockPos pos, boolean b) {

        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if (!(tileentity instanceof TEStoneMill)) {
        	
            return null;
        }
        else {

            ILockableContainer ilockablecontainer = (TEStoneMill)tileentity;

            TileEntity anothorTileEntity = worldIn.getTileEntity(pos);
            //神奇语句！！！
            ilockablecontainer = new InventoryLargeChest("", null, (TEStoneMill)anothorTileEntity);
            return ilockablecontainer;
        }
	}

	public void registerModel() {
    	
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "");
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	
        this.setDefaultFacing(worldIn, pos, state);
    }
	
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
    	
        if (!worldIn.isRemote) {
        	
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing));
        }
    }
	
    @SideOnly(Side.CLIENT)
    //@SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    	/*
        if (this.isActing)
        {
            EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            //double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            switch (enumfacing)
            {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            }
        }
        */
    }
	
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));

        this.setDefaultFacing(worldIn, pos, state);
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TEStoneMill)
            {
                ((TEStoneMill)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }
    
    protected BlockStateContainer createBlockState() {
    	
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {

        super.breakBlock(worldIn, pos, state);
    }
    
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
    
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
    
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockHandler.blockStoneMill);
    }
	
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    public void setState(boolean isActing, World worldIn, BlockPos pos)
    {

    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    /*
	public boolean isActing() {
		return isActing;
	}
	*/
}
