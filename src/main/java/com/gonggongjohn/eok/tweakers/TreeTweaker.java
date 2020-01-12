package com.gonggongjohn.eok.tweakers;

import com.gonggongjohn.eok.handlers.ConfigHandler;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TreeTweaker {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void BreakSpeed(PlayerEvent.BreakSpeed event) {
		if (ConfigHandler.canPlayerChopTreesWithBareHands == false)
			return;

		if (event.getState().getBlock() != Blocks.LOG && event.getState().getBlock() != Blocks.LOG)
			return;

		if (event.getEntityPlayer() == null)
			return;

		if (event.getEntityPlayer().inventory.getCurrentItem() == null)
			return;

		// 如果玩家没有拿斧头，则将挖掘速度设为0
		if (!(event.getEntityPlayer().inventory.getCurrentItem().getItem() instanceof ItemAxe)) {
			event.setNewSpeed(0.0F);
			return;
		}
	}
	
	/*
	 * 烂尾代码，等以后再写（逃
	 * 科学砍树
	 */
//	
//	@SubscribeEvent(priority = EventPriority.HIGHEST)
//	public void chop(HarvestDropsEvent event) {
//		if(event.getHarvester() == null)
//			return;
//		
//		if(!event.getHarvester().isServerWorld())
//			return;
//		
//		IBlockState stateWood = event.getState();
//		IBlockState stateLeaves = null;
//		World world = event.getWorld();
//		ArrayList<BlockPos> posWoods = new ArrayList<BlockPos>();
//		ArrayList<BlockPos> posLeaves = new ArrayList<BlockPos>();
//		ArrayList<BlockPos> posFallingWoods = new ArrayList<BlockPos>();
//		ArrayList<BlockPos> posFallingLeaves = new ArrayList<BlockPos>();
//		ArrayList<BlockPos> posFallingSticks = new ArrayList<BlockPos>();
//		ArrayList<BlockPos> posFallingSaplings = new ArrayList<BlockPos>();
//		
//		if(stateWood.getBlock() != Blocks.LOG)
//			return;
//		
//		// 只有竖向的木头才是树干
//		if(stateWood.getValue(BlockLog.LOG_AXIS) == EnumAxis.X || 
//				stateWood.getValue(BlockLog.LOG_AXIS) == EnumAxis.Z)
//			return;
//		
//		event.getDrops().clear();
//		
//		{
//			int x = event.getPos().getX();
//			int y = event.getPos().getY() - 3;
//			int z = event.getPos().getZ();
//			int _toY = event.getPos().getY() + 100;
//			// 树的高度（仅仅是木头的高度）
//			int height = 0;
//			
//			if(_toY > 255)
//				_toY = 255;
//			
//			for(BlockPos pos : BlockPos.getAllInBox(x - 1, y, z - 1, x + 1, 255, z + 1)) {
//				if(pos.getY() > 255)
//					continue;
//				if(world.getBlockState(pos) == stateWood)
//					posWoods.add(pos);
//			}
//			
//			// 获取树的高度
//			for(int i = 0; i < _toY; i++) {
//				if(y == 255)
//					break;
//				
//				if(world.getBlockState(new BlockPos(x, y, z)) == stateWood) {
//					height++;
//				}else {
//					// 顺便获取树叶的state
//					if(i > 4) {
//						// 如果木头正上方一格就有树叶，则选择该树叶
//						if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.LEAVES || 
//								world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.LEAVES2) {
//							stateLeaves = world.getBlockState(new BlockPos(x, y, z));
//							break;
//						}else {
//							// 若木头正上方没树叶，就搜索周围4*4空间的树叶
//							for(BlockPos pos : BlockPos.getAllInBox(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2)) {
//								if(world.getBlockState(pos).getBlock() == Blocks.LEAVES ||
//										world.getBlockState(pos).getBlock() == Blocks.LEAVES2) {
//								stateLeaves = world.getBlockState(pos);
//								break;
//								}
//							}
//						}
//					}
//					break;
//				}
//				x++;
//				y++;
//				z++;
//			}
//			
//			// 此处开始搜索树叶
//			
//			x = event.getPos().getX();
//			y = event.getPos().getY() + height;
//			z = event.getPos().getZ();
//			int fromX = x - 6;
//			int fromY = y - 4;
//			int fromZ = z - 6;
//			int toX = x + 6;
//			int toY = y + 4;
//			int toZ = z + 6;
//			
//			for(BlockPos pos : BlockPos.getAllInBox(fromX, fromY, fromZ, toX, toY, toZ)) {
//				if(pos.getY() > 255)
//					continue;
//				if(stateLeaves == null) {	// 如果前面没能获取到树叶的state，就直接判断方块种类
//					if(world.getBlockState(pos).getBlock() == Blocks.LEAVES ||
//							world.getBlockState(pos).getBlock() == Blocks.LEAVES2) {
//						posLeaves.add(pos);
//					}
//					continue;// 不执行后面用到stateLeaves的判断代码，以免发生错误
//				}
//				// 已获取到树叶的state，直接判断
//				if(world.getBlockState(pos) == stateLeaves) {
//					posLeaves.add(pos);
//				}
//			}
//		}
//		
//		{
//			int x = event.getPos().getX();
//			int y = event.getPos().getY();
//			int z = event.getPos().getZ();
//			BlockPos pos = event.getPos();
//			IBlockState woodRotated = stateWood.withProperty(BlockLog.AXIS, Axis.X);
//			EnumFacing facing = event.getHarvester().getHorizontalFacing();
//			if(facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
//				facing = EnumFacing.VALUES[(int)Math.random() * 4 + 2];
//			}
//			Vec3i offset = facing.getDirectionVec();
//			if(isAir(world, move(pos, offset))) {
//				posFallingWoods.add(move(pos, offset));
//			}
//			pos = move(pos, offset);
//			
//			for(int i = 0; i < posWoods.size(); i++) {
//				
//			}
//		}
//		
//	}
//	
//	private void createFallingBlock(World world, double x, double y, double z, IBlockState state) {
//		EntityFallingBlock entity = new EntityFallingBlock(world, x+0.5, y, z+0.5, state);
//		entity.fallTime = -3000000;
//		world.spawnEntity(entity);
//	}
//	
//	private BlockPos move(int x, int y, int z, Vec3i offset) {
//		return new BlockPos(x + offset.getX(), y + offset.getY(), z + offset.getZ());
//	}
//	
//	private BlockPos move(BlockPos p, Vec3i o) {
//		return new BlockPos(p.getX() + o.getX(), p.getY() + o.getY(), p.getZ() + o.getZ());
//	}
//	
//	private BlockPos invertedMove(int x, int y, int z, Vec3i offset) {
//		return new BlockPos(x - offset.getX(), y - offset.getY(), z - offset.getZ());
//	}
//	
//	private BlockPos invertedMove(BlockPos p, Vec3i o) {
//		return new BlockPos(p.getX() - o.getX(), p.getY() - o.getY(), p.getZ() - o.getZ());
//	}
//	
//	private boolean isAir(World world, int x, int y, int z) {
//		return world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.AIR ? true : false;
//	}
//	
//	private boolean isAir(World w, BlockPos p) {
//		return w.getBlockState(new BlockPos(p.getX(), p.getY(), p.getZ())).getBlock() == Blocks.AIR ? true : false;
//	}
//	
//	private void processWoods(World world, int x, int y, int z, int height, EnumFacing direction) {
//		int logX = x;
//		int logY = y - 1;	// 后面要加1
//		int logZ = z;
//		int fallX = x;
//		int fallY = y;
//		int fallZ = z;
//		int offsetX = 0;
//		int offsetY = 1;
//		int offsetZ = 0;
//		BlockPos logPos;
//		BlockPos fallPos;
//		//IBlockState state = Blocks.LOG.getDefaultState();
//		
//		switch (direction) {
//		case NORTH:
//			offsetZ = -1;
//			break;
//		case SOUTH:
//			offsetZ = 1;
//			break;
//		case WEST:
//			offsetX = -1;
//			break;
//		case EAST:
//			offsetX = 1;
//			break;
//		default:
//			break;
//		}
//		
//		for(int i = 0; i < height; i++) {
//			fallX += offsetX;
//			fallY += offsetY;
//			fallZ += offsetZ;
//			logY += 1;
//			logPos = new BlockPos(logX, logY, logZ);
//			// 目标位置
//			fallPos = new BlockPos(fallX, fallY, fallZ);
//			
//			/*
//			if(world.getBlockState(fallPos).getBlock() != Blocks.AIR) {
//				if(world.getBlockState(new BlockPos(fallX, fallY - 1, fallZ)) == Blocks.AIR) {
//					fallPos = new BlockPos(fallX, --fallY, fallZ);
//				}else if(world.getBlockState(new BlockPos(fallX, fallY + 1, fallZ)) == Blocks.AIR) {
//					fallPos = new BlockPos(fallX, ++fallY, fallZ);
//				}else {
//					return;
//				}
//				
//			}
//			*/
//			BlockPos startPos = new BlockPos(logX - 1, logY - 5, logZ - 1);
//			BlockPos endPos = new BlockPos(logX + 1, logY + 30, logZ + 1);
//			int tmpX;
//			int tmpY;
//			int tmpZ;
//			Block tmpBlock;
//			
//			if(world.isAreaLoaded(startPos, endPos)) {
//				for(BlockPos pos : BlockPos.getAllInBox(startPos, endPos)) {
//					tmpBlock = world.getBlockState(pos).getBlock();
//					if(tmpBlock == Blocks.LOG || tmpBlock == Blocks.LOG2) {
//						
//					}
//				}
//				world.setBlockToAir(logPos);
//				createFallingBlock(world, fallX, fallY, fallZ, Blocks.LOG.getDefaultState());
//			}
//		}
//	}
	
	public TreeTweaker() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
}
