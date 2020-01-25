package com.gonggongjohn.eok.items;

import java.util.ArrayList;
import javax.annotation.Nullable;
import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * 左手干草，右手起火器，点火
 */
//当前，左右手持起火器（fire stick），右键点火，点燃前方方块

public class ItemFireStick extends Item implements IHasModel {
	
	private final String name = "fire_stick";
	private int cpt = 0;
	private boolean fire = false;
	
	ArrayList<Long> timeList = new ArrayList<>();

	public ItemFireStick() {
		
		this.maxStackSize = 1;
		
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(EOK.tabEOK);
		
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return !(entityIn.getActiveItemStack().getItem() instanceof ItemBow) ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
		
		ItemHandler.items.add(this);
	}
	
	@Override
	public void registerModel() {
		
		EOK.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
    public BlockPos getFirePos(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {
        
    	pos = pos.offset(facing);

        return pos;
    }

	@Override									 
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		//Random rand = new Random(100);
		
		if(cpt < 20) {
			
			timeList.add(worldIn.getWorldTime());
		}
		else {
			
			if(timeList.size() != 0) {
				
				System.out.println("Total time: " + (timeList.get(timeList.size() - 1) - timeList.get(0)));
				
				//cpt = 20时，连击最短用时37 
				if((timeList.get(timeList.size() - 1) - timeList.get(0)) <= 36) {
					
					fire = true;
				}
			}
			
			timeList.clear();
			cpt = 0;
		}
		
		playerIn.swingArm(EnumHand.MAIN_HAND);
		playerIn.swingArm(EnumHand.OFF_HAND);
	
		if (worldIn.isRemote) {
			
			return super.onItemRightClick(worldIn, playerIn, handIn);
		}
		
		if(playerIn.getHeldItemOffhand().getItem() == this && playerIn.getHeldItemMainhand().getItem() == this) {
			
			cpt++;

			if(fire) {
				
				//playerIn.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
				//playerIn.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
				 
				//点燃自己前方的方块
				worldIn.setBlockState(this.getFirePos(playerIn, worldIn, playerIn.getPosition(), handIn, playerIn.getHorizontalFacing()), Blocks.FIRE.getDefaultState(), 11);
			
				fire = false;
			}
		}
		else {
			
			cpt = 0;
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}