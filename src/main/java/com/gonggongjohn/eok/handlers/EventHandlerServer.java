package com.gonggongjohn.eok.handlers;

import java.util.List;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.CapabilityPlayerState;
import com.gonggongjohn.eok.capabilities.CapabilityResearchData;
import com.gonggongjohn.eok.capabilities.IPlayerState;
import com.gonggongjohn.eok.capabilities.IResearchData;
import com.gonggongjohn.eok.network.PacketPlayerState;
import com.gonggongjohn.eok.network.PacketResearchData;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = EOK.MODID)
public class EventHandlerServer {
	@SubscribeEvent
	public static void onStoneWork(RightClickBlock e) {
		if (e.getWorld().isRemote) {
			return;
		}
		if (e.getFace() != EnumFacing.UP) {
			return;
		}
		BlockPos pos = e.getPos();
		World world = e.getWorld();
		IBlockState state = world.getBlockState(pos);
		if (!(state.isFullBlock() && state.getMaterial() == Material.ROCK)) {
			return;
		}
		ItemStack stack = e.getItemStack();
		if (stack.getItem() != Items.FLINT) {
			return;
		}
		EntityPlayer player = e.getEntityPlayer();
		Vec3d vec = e.getHitVec();
		vec = vec.subtract(pos.getX(), pos.getY(), pos.getZ());
		stack.shrink(1);
		ItemStack result;
		if (vec.x >= 0.25 && vec.x <= 0.75 && vec.z >= 0.25 && vec.z <= 0.75) {
			result = MetaItemsHandler.GRINDED_FLINT.getStackForm();
		} else {
			result = MetaItemsHandler.CHIPPED_FLINT.getStackForm();
		}
		player.inventory.addItemStackToInventory(result);
		if (!result.isEmpty()) {
			InventoryHelper.spawnItemStack(e.getWorld(), pos.getX(), pos.getY(), pos.getZ(), result);
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			e.addCapability(CapabilityResearchData.KEY, new CapabilityResearchData());
			e.addCapability(CapabilityPlayerState.KEY, new CapabilityPlayerState());
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(Clone e) {
		syncPlayerCapability(e, CapabilityHandler.capPlayerState);
		syncPlayerCapability(e, CapabilityHandler.capResearchData);
	}

	private static <T> void syncPlayerCapability(Clone e, Capability<T> capability) {
		IStorage storage = capability.getStorage();
		T original = e.getOriginal().getCapability(capability, null);
		T cap = e.getEntityPlayer().getCapability(capability, null);
		NBTBase nbt = storage.writeNBT(capability, original, null);
		storage.readNBT(capability, cap, null, nbt);
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent e) {
		if (!e.player.world.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) e.player;
			if (player.hasCapability(CapabilityHandler.capPlayerState, null)) {
				IPlayerState state = player.getCapability(CapabilityHandler.capPlayerState, null);
				IStorage storage = CapabilityHandler.capPlayerState.getStorage();
				NBTTagCompound nbt = (NBTTagCompound) storage.writeNBT(CapabilityHandler.capPlayerState, state, null);
				PacketPlayerState mesage = new PacketPlayerState(nbt);
				EOK.getNetwork().sendTo(mesage, player);
			}
			if (player.hasCapability(CapabilityHandler.capResearchData, null)) {
				IResearchData data = player.getCapability(CapabilityHandler.capResearchData, null);
				IStorage storage = CapabilityHandler.capResearchData.getStorage();
				List<Integer> finList = data.getFinishedResearch();
				if (finList.isEmpty()) {
					finList.add(1);
					data.setFinishedResearch(finList);
				}
				NBTTagCompound nbt = (NBTTagCompound) storage.writeNBT(CapabilityHandler.capResearchData, data, null);
				storage.readNBT(CapabilityHandler.capResearchData,
						player.getCapability(CapabilityHandler.capResearchData, null), null, nbt);
				PacketResearchData mesage = new PacketResearchData(nbt);
				EOK.getNetwork().sendTo(mesage, player);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if (!e.player.world.isRemote) {
			EntityPlayer player = e.player;
			if (player.hasCapability(CapabilityHandler.capPlayerState, null)) {
				IPlayerState state = player.getCapability(CapabilityHandler.capPlayerState, null);
				if (player.isPlayerFullyAsleep()) {
					state.setConsciousness(1);
				} else {
					float con = state.getConsciousness();
					state.setConsciousness(con - 1 / 24000);
				}
			}
		}
	}

	/** 玩家右键拾取材料的事件 */
	@SubscribeEvent
	public static void onPlayerRightClicked(PlayerInteractEvent.RightClickBlock event) {
		if (!event.getWorld().isRemote) {
			BlockPos pos = event.getPos();
			IBlockState state = event.getWorld().getBlockState(pos);
			if (state.equals(BlockHandler.blockStoneRock.getDefaultState())) {
				event.getWorld().setBlockToAir(pos);
				event.getWorld().spawnEntity(new EntityItem(event.getWorld(), pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(BlockHandler.blockStoneRock)));
			} else if (state.equals(BlockHandler.blockStick.getDefaultState())) {
				event.getWorld().setBlockToAir(pos);
				event.getWorld().spawnEntity(new EntityItem(event.getWorld(), pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(Items.STICK)));
			}
		}
	}

	/** 描述方块下方方块被破坏时上方方块自然掉落的事件 */
	@SubscribeEvent
	public static void onDownBlockDestroyed(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote) {
			BlockPos pos = event.getPos();
			IBlockState state = event.getWorld().getBlockState(pos.up());
			if (state.equals(BlockHandler.blockStoneRock.getDefaultState())) {
				event.getWorld().setBlockToAir(pos.up());
				event.getWorld().spawnEntity(new EntityItem(event.getWorld(), pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(BlockHandler.blockStoneRock)));
			} else if (state.equals(BlockHandler.blockStick.getDefaultState())) {
				event.getWorld().setBlockToAir(pos.up());
				event.getWorld().spawnEntity(new EntityItem(event.getWorld(), pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(Items.STICK)));
			}
		}
	}

	/** 描述玩家破坏方块时方块不掉落本身的事件 */
	@SubscribeEvent
	public static void onDestroyedButNotDropItself(BlockEvent.HarvestDropsEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		if (!event.getDrops().isEmpty()
				&& event.getDrops().get(0).isItemEqual(new ItemStack(BlockHandler.blockStick))) {
			event.setDropChance(0.0F);
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STICK)));
		}
	}
}