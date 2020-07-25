package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tileEntities.TEResearchTableAncient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockResearchTableAncient extends BlockContainer
{
    private static final String name = "researchTableAncient";
    private IIcon[] icons = new IIcon[6];
    private final Random rand = new Random();

    public BlockResearchTableAncient() {
        super(Material.rock);

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly, float lz){
        if(world.isRemote) return true;
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TEResearchTableAncient){
            player.openGui(EOK.instance, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(world.isRemote) return;
        ArrayList drops = new ArrayList();
        TileEntity teRaw = world.getTileEntity(x, y, z);
        if(teRaw != null && teRaw instanceof TEResearchTableAncient){
            TEResearchTableAncient te = (TEResearchTableAncient) teRaw;
            for(int i = 0; i < te.getSizeInventory(); i++){
                ItemStack stack = te.getStackInSlot(i);
                if(stack != null) drops.add(stack.copy());
            }
        }
        for (int i = 0;i < drops.size();i++)
        {
            EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, (ItemStack) drops.get(i));
            item.setVelocity((rand.nextDouble() - 0.5) * 0.25, rand.nextDouble() * 0.5 * 0.25, (rand.nextDouble() - 0.5) * 0.25);
            world.spawnEntityInWorld(item);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg){
        icons[0] = reg.registerIcon(EOK.MODID + ":" + name + "_bottom");
        icons[1] = reg.registerIcon(EOK.MODID + ":" + name + "_top");
        for(int i = 2; i < 6 ; i++){
            icons[i] = reg.registerIcon(EOK.MODID + ":" + name + "_side");
        }
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return icons[side];
    }
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TEResearchTableAncient();
    }
}
