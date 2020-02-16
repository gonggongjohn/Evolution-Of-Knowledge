package com.gonggongjohn.eok.handlers;

import java.util.ArrayList;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.CapabilityAnotherSeconds;
import com.gonggongjohn.eok.capabilities.CapabilityInspirations;
import com.gonggongjohn.eok.capabilities.CapabilityPlayerState;
import com.gonggongjohn.eok.capabilities.CapabilityResearchData;
import com.gonggongjohn.eok.capabilities.CapabilitySeconds;
import com.gonggongjohn.eok.capabilities.IAnotherSeconds;
import com.gonggongjohn.eok.capabilities.IInspirations;
import com.gonggongjohn.eok.capabilities.IPlayerState;
import com.gonggongjohn.eok.capabilities.IResearchData;
import com.gonggongjohn.eok.capabilities.ISeconds;
import com.gonggongjohn.eok.network.PacketAnotherSeconds;
import com.gonggongjohn.eok.network.PacketInspirations;
import com.gonggongjohn.eok.network.PacketResearchData;
import com.gonggongjohn.eok.network.PacketSeconds;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AnotherEventHandler {
	@SubscribeEvent
	public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			ICapabilitySerializable<NBTTagCompound> providerResearchData = new CapabilityResearchData.ProvidePlayer();
			ICapabilitySerializable<NBTTagCompound> providerSeconds = new CapabilitySeconds.ProvidePlayer();
			ICapabilitySerializable<NBTTagCompound> providerAnotherSeconds = new CapabilityAnotherSeconds.ProvidePlayer();
			ICapabilitySerializable<NBTTagCompound> providerInspirations = new CapabilityInspirations.ProvidePlayer();
			e.addCapability(new ResourceLocation(EOK.MODID + ":" + "researchData"), providerResearchData);
			e.addCapability(new ResourceLocation(EOK.MODID + ":" + "seconds"), providerSeconds);
			e.addCapability(new ResourceLocation(EOK.MODID + ":" + "damage"), providerAnotherSeconds);
			e.addCapability(new ResourceLocation(EOK.MODID + ":" + "inspirations"), providerInspirations);

			e.addCapability(CapabilityPlayerState.KEY, new CapabilityPlayerState());
		}
	}

	@SubscribeEvent
	public void onPlayerClone(Clone e) {
		syncPlayerCapability(e, CapabilityHandler.capPlayerState);

		Capability<IResearchData> capabilityResearchData = CapabilityHandler.capResearchData;
		Capability.IStorage<IResearchData> storageResearchData = capabilityResearchData.getStorage();
		Capability<ISeconds> capabilitySeconds = CapabilityHandler.capSeconds;
		Capability.IStorage<ISeconds> storageSeconds = capabilitySeconds.getStorage();
		Capability<IAnotherSeconds> capabilityAnotherSeconds = CapabilityHandler.capAnotherSeconds;
		Capability.IStorage<IAnotherSeconds> storageAnotherSeconds = capabilityAnotherSeconds.getStorage();
		Capability<IInspirations> capabilityInspirations = CapabilityHandler.capInspirations;
		Capability.IStorage<IInspirations> storageInspiration = capabilityInspirations.getStorage();

		if (e.getOriginal().hasCapability(capabilityResearchData, null)
				&& e.getEntityPlayer().hasCapability(capabilityResearchData, null)) {
			NBTBase nbt = storageResearchData.writeNBT(capabilityResearchData,
					e.getOriginal().getCapability(capabilityResearchData, null), null);
			storageResearchData.readNBT(capabilityResearchData,
					e.getEntityPlayer().getCapability(capabilityResearchData, null), null, nbt);
		}
		if (e.getOriginal().hasCapability(capabilitySeconds, null)
				&& e.getEntityPlayer().hasCapability(capabilitySeconds, null)) {
			NBTBase nbt = storageSeconds.writeNBT(capabilitySeconds,
					e.getOriginal().getCapability(capabilitySeconds, null), null);
			storageSeconds.readNBT(capabilitySeconds, e.getEntityPlayer().getCapability(capabilitySeconds, null), null,
					nbt);
		}
		if (e.getOriginal().hasCapability(capabilityAnotherSeconds, null)
				&& e.getEntityPlayer().hasCapability(capabilityAnotherSeconds, null)) {
			NBTBase nbt = storageAnotherSeconds.writeNBT(capabilityAnotherSeconds,
					e.getOriginal().getCapability(capabilityAnotherSeconds, null), null);
			storageAnotherSeconds.readNBT(capabilityAnotherSeconds,
					e.getEntityPlayer().getCapability(capabilityAnotherSeconds, null), null, nbt);
		}
		if (e.getOriginal().hasCapability(capabilityInspirations, null)
				&& e.getEntityPlayer().hasCapability(capabilityInspirations, null)) {
			NBTBase nbt = storageInspiration.writeNBT(capabilityInspirations,
					e.getOriginal().getCapability(capabilityInspirations, null), null);
			storageInspiration.readNBT(capabilityInspirations,
					e.getEntityPlayer().getCapability(capabilityInspirations, null), null, nbt);
		}
	}

	private <T> void syncPlayerCapability(Clone e, Capability<T> capability) {
		IStorage storage = capability.getStorage();
		T original = e.getOriginal().getCapability(capability, null);
		T cap = e.getEntityPlayer().getCapability(capability, null);
		NBTBase nbt = storage.writeNBT(capability, original, null);
		storage.readNBT(capability, cap, null, nbt);
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent e) {
		if (!e.player.world.isRemote) {
			EntityPlayer player = e.player;
			if (player.hasCapability(CapabilityHandler.capPlayerState, null)) {

			}
			if (player.hasCapability(CapabilityHandler.capResearchData, null)) {
				PacketResearchData mesage = new PacketResearchData();
				IResearchData researchData = player.getCapability(CapabilityHandler.capResearchData, null);
				Capability.IStorage<IResearchData> storage = CapabilityHandler.capResearchData.getStorage();
				ArrayList<Integer> finList = researchData.getFinishedResearch();
				if (finList.size() == 0) {
					finList.add(1);
					researchData.setFinishedResearch(finList);
					NBTBase nbt = storage.writeNBT(CapabilityHandler.capResearchData, researchData, null);
					storage.readNBT(CapabilityHandler.capResearchData,
							player.getCapability(CapabilityHandler.capResearchData, null), null, nbt);
				}
				mesage.compound = new NBTTagCompound();
				mesage.compound.setTag("finishedResearch",
						storage.writeNBT(CapabilityHandler.capResearchData, researchData, null));
				EOK.getNetwork().sendTo(mesage, (EntityPlayerMP) player);
			}
			if (player.hasCapability(CapabilityHandler.capSeconds, null)) {
				PacketSeconds message = new PacketSeconds();
				ISeconds seconds = player.getCapability(CapabilityHandler.capSeconds, null);
				Capability.IStorage<ISeconds> storage = CapabilityHandler.capSeconds.getStorage();

				message.compound = new NBTTagCompound();
				message.compound.setTag("seconds", storage.writeNBT(CapabilityHandler.capSeconds, seconds, null));

				EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
			}
			if (player.hasCapability(CapabilityHandler.capAnotherSeconds, null)) {
				PacketAnotherSeconds message = new PacketAnotherSeconds();
				IAnotherSeconds anotherSeconds = player.getCapability(CapabilityHandler.capAnotherSeconds, null);
				Capability.IStorage<IAnotherSeconds> storage = CapabilityHandler.capAnotherSeconds.getStorage();

				message.compound = new NBTTagCompound();
				message.compound.setTag("damage",
						storage.writeNBT(CapabilityHandler.capAnotherSeconds, anotherSeconds, null));

				EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
			}
			if (player.hasCapability(CapabilityHandler.capInspirations, null)) {
				PacketInspirations message = new PacketInspirations();
				IInspirations inspirations = player.getCapability(CapabilityHandler.capInspirations, null);
				Capability.IStorage<IInspirations> storage = CapabilityHandler.capInspirations.getStorage();

				message.compound = new NBTTagCompound();
				message.compound.setTag("inspirations",
						storage.writeNBT(CapabilityHandler.capInspirations, inspirations, null));
				EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
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
	public void onPlayerRightClicked(PlayerInteractEvent.RightClickBlock event) {
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
	public void onDownBlockDestroyed(BlockEvent.BreakEvent event) {
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
	public void onDestroyedButNotDropItself(BlockEvent.HarvestDropsEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		if (!event.getDrops().isEmpty()
				&& event.getDrops().get(0).isItemEqual(new ItemStack(BlockHandler.blockStick))) {
			event.setDropChance(0.0F);
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STICK)));
		}
	}
}