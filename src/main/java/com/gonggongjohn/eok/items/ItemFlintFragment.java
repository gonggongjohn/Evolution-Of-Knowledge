package com.gonggongjohn.eok.items;

import java.util.ArrayList;
import java.util.Random;

import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.settings.ListHardBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

//作者：zi_jing

public class ItemFlintFragment extends Item{
	
	
	//这是燧石碎片的打制事件
	@SubscribeEvent
	public void Chip(PlayerInteractEvent event) {
		
		//石质方块列表(位于com.gonggongjohn.eok.settings中的ListBlocksMadeByStone类)
		ArrayList ListHardBlocks = new ListHardBlocks();
		
		//ArrayList StoneBlocks = new ArrayList();
		//StoneBlocks.add(Blocks.stone);
		
		if(event.action == Action.LEFT_CLICK_BLOCK) {
			if(!event.entityPlayer.getEntityWorld().isRemote) {
				//判断玩家是否持有物品
				if(event.entityPlayer.inventory.getCurrentItem() != null) {
					
					//判断玩家持有的物品是否为燧石碎片
					if(event.entityPlayer.inventory.getCurrentItem().getItem() == ItemHandler.itemFlintFragment) {
						
						//判断玩家击打的方块是否为石质方块
						if(ListHardBlocks.contains(event.world.getBlock(event.x, event.y, event.z))) {
							//System.out.println("你正在使用燧石碎片挖石头！");
							
							ItemStack nItemStack = event.entityPlayer.getHeldItem();
							//System.out.println("stacksize="+nItemStack.stackSize);
							
							//成功概率30%
							Random rand = new Random();
							int i = rand.nextInt(100);
							if(i<=30) {
								//如果手中燧石碎片数量为1，则直接删除该物品
								//如果不添加该判断，燧石碎片将变为无限（111）
								if(nItemStack.stackSize == 1) {
									//System.out.println("233333");
									//System.out.println(event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem]);
									event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem] = null;
									
								}
								//将燧石碎片数量减1
								else {
									nItemStack.stackSize = nItemStack.stackSize - 1;
									event.entityPlayer.setItemInUse(nItemStack, 0);
								}
								
								//生成打制燧石碎片的掉落物
								EntityItem entityItem = new EntityItem(event.world);
								entityItem.setPosition(event.entityPlayer.posX, event.entityPlayer.posY, event.entityPlayer.posZ);
								entityItem.setEntityItemStack(new ItemStack(ItemHandler.itemChippedFlintFragment,1));
								event.world.spawnEntityInWorld(entityItem);
							}
						}
					}
				}
			}
		}
		
	}
		
	//注册事件
	public ItemFlintFragment() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	
	
	
	//弃置代码
	/*
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        player.swingItem();
		System.out.println(11111);
		return itemstack;
    }
	*/
	/*
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
    {
        if(!player.isClientWorld()) {
        	//player.addChatMessage(new ChatComponentText(itemstack+" "+X+" "+Y+" "+Z+" "+player));
        	System.out.println(itemstack+" "+X+" "+Y+" "+Z+" "+player);
        	
        }
        return false;
    }
	*/
	/*
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(!player.isClientWorld()) {
			System.out.println("\n"+stack+" "+player+" "+world+"\n"+x+" "+y+" "+z+"\n"+p_77648_7_+" "+p_77648_8_+" "+p_77648_9_+" "+p_77648_10_);
		}
		return false;
    }
	
	
	/*
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        System.out.println("11111");
		return false;
    }
	*/
	
	
	
	
	
	
}
