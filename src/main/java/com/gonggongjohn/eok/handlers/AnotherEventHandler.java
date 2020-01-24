package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.*;
import com.gonggongjohn.eok.network.*;
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
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class AnotherEventHandler {
    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof EntityPlayer){
            ICapabilitySerializable<NBTTagCompound> providerConsciousness = new CapabilityConsciousness.ProvidePlayer();
            ICapabilitySerializable<NBTTagCompound> providerMindActivity = new CapabilityMindActivity.ProvidePlayer();
            ICapabilitySerializable<NBTTagCompound> providerResearchData = new CapabilityResearchData.ProvidePlayer();
            ICapabilitySerializable<NBTTagCompound> providerSeconds = new CapabilitySeconds.ProvidePlayer();
            ICapabilitySerializable<NBTTagCompound> providerHayTorchState = new CapabilityHayTorchBase.ProvidePlayer();  
            ICapabilitySerializable<NBTTagCompound> providerAnotherSeconds = new CapabilityAnotherSeconds.ProvidePlayer();
            ICapabilitySerializable<NBTTagCompound> providerInspirations = new CapabilityInspirations.ProvidePlayer();

            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "consciousness"), providerConsciousness);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "mindActivity"), providerMindActivity);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "researchData"), providerResearchData);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "seconds"),providerSeconds);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "hayTorchState"), providerHayTorchState);     
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "damage"), providerAnotherSeconds);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "inspirations"), providerInspirations);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event){
        Capability<IConsciousness> capabilityConsciousness = CapabilityHandler.capConsciousness;
        Capability.IStorage<IConsciousness> storageConsciousness = capabilityConsciousness.getStorage();
        Capability<IMindActivity> capabilityMindActivity = CapabilityHandler.capMindActivity;
        Capability.IStorage<IMindActivity> storageMindActivity = capabilityMindActivity.getStorage();
        Capability<IResearchData> capabilityResearchData = CapabilityHandler.capResearchData;
        Capability.IStorage<IResearchData> storageResearchData = capabilityResearchData.getStorage();
        Capability<ISeconds> capabilitySeconds=CapabilityHandler.capSeconds;
        Capability.IStorage<ISeconds> storageSeconds=capabilitySeconds.getStorage();
        Capability<IHayTorch> capabilityHayTorchState = CapabilityHandler.capHayTorchBase;
        Capability.IStorage<IHayTorch> storageHayTorchState = capabilityHayTorchState.getStorage();
        Capability<IAnotherSeconds> capabilityAnotherSeconds = CapabilityHandler.capAnotherSeconds;
        Capability.IStorage<IAnotherSeconds> storageAnotherSeconds = capabilityAnotherSeconds.getStorage();
        Capability<IInspirations> capabilityInspirations = CapabilityHandler.capInspirations;
        Capability.IStorage<IInspirations> storageInspirations = capabilityInspirations.getStorage();
        if(event.getOriginal().hasCapability(capabilityConsciousness, null) && event.getEntityPlayer().hasCapability(capabilityConsciousness, null)){
            NBTBase nbt = storageConsciousness.writeNBT(capabilityConsciousness, event.getOriginal().getCapability(capabilityConsciousness, null), null);
            storageConsciousness.readNBT(capabilityConsciousness, event.getEntityPlayer().getCapability(capabilityConsciousness, null), null, nbt);
        }
        if(event.getOriginal().hasCapability(capabilityMindActivity, null) && event.getEntityPlayer().hasCapability(capabilityMindActivity, null)){
            NBTBase nbt = storageMindActivity.writeNBT(capabilityMindActivity, event.getOriginal().getCapability(capabilityMindActivity, null), null);
            storageMindActivity.readNBT(capabilityMindActivity, event.getEntityPlayer().getCapability(capabilityMindActivity, null), null, nbt);
        }
        if(event.getOriginal().hasCapability(capabilityResearchData, null) && event.getEntityPlayer().hasCapability(capabilityResearchData, null)){
            NBTBase nbt = storageResearchData.writeNBT(capabilityResearchData, event.getOriginal().getCapability(capabilityResearchData, null), null);
            storageResearchData.readNBT(capabilityResearchData, event.getEntityPlayer().getCapability(capabilityResearchData, null), null, nbt);
        }
        if(event.getOriginal().hasCapability(capabilitySeconds, null) && event.getEntityPlayer().hasCapability(capabilitySeconds, null)){
            NBTBase nbt = storageSeconds.writeNBT(capabilitySeconds, event.getOriginal().getCapability(capabilitySeconds, null), null);
            storageSeconds.readNBT(capabilitySeconds, event.getEntityPlayer().getCapability(capabilitySeconds, null), null, nbt);
        }
        if(event.getOriginal().hasCapability(capabilityHayTorchState, null) && event.getEntityPlayer().hasCapability(capabilityHayTorchState, null)){
            NBTBase nbt = storageHayTorchState.writeNBT(capabilityHayTorchState, event.getOriginal().getCapability(capabilityHayTorchState, null), null);
            storageHayTorchState.readNBT(capabilityHayTorchState, event.getEntityPlayer().getCapability(capabilityHayTorchState, null), null, nbt);
        }
        if(event.getOriginal().hasCapability(capabilityAnotherSeconds, null) && event.getEntityPlayer().hasCapability(capabilityAnotherSeconds, null)){
            NBTBase nbt = storageAnotherSeconds.writeNBT(capabilityAnotherSeconds, event.getOriginal().getCapability(capabilityAnotherSeconds, null), null);
            storageAnotherSeconds.readNBT(capabilityAnotherSeconds, event.getEntityPlayer().getCapability(capabilityAnotherSeconds, null), null, nbt);
        }
        if(event.getOriginal().hasCapability(capabilityInspirations, null) && event.getEntityPlayer().hasCapability(capabilityInspirations, null)){
            NBTBase nbt = storageInspirations.writeNBT(capabilityInspirations, event.getOriginal().getCapability(capabilityInspirations, null), null);
            storageInspirations.readNBT(capabilityInspirations, event.getEntityPlayer().getCapability(capabilityInspirations, null), null, nbt);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event){
        if (!event.player.world.isRemote)
        {
            EntityPlayer player = event.player;
            if(player.hasCapability(CapabilityHandler.capConsciousness, null)){
                PacketConsciousness message = new PacketConsciousness();
                IConsciousness consciousness = player.getCapability(CapabilityHandler.capConsciousness, null);
                Capability.IStorage<IConsciousness> storage = CapabilityHandler.capConsciousness.getStorage();

                message.compound = new NBTTagCompound();
                message.compound.setTag("consciousness", storage.writeNBT(CapabilityHandler.capConsciousness, consciousness, null));
                EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
            if(player.hasCapability(CapabilityHandler.capMindActivity, null)){
                PacketMindActivity message = new PacketMindActivity();
                IMindActivity mindActivity = player.getCapability(CapabilityHandler.capMindActivity, null);
                Capability.IStorage<IMindActivity> storage = CapabilityHandler.capMindActivity.getStorage();

                message.compound = new NBTTagCompound();
                message.compound.setTag("mindActivity", storage.writeNBT(CapabilityHandler.capMindActivity, mindActivity, null));
                EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
            if(player.hasCapability(CapabilityHandler.capResearchData, null)){
                PacketResearchData mesage = new PacketResearchData();
                IResearchData researchData = player.getCapability(CapabilityHandler.capResearchData, null);
                Capability.IStorage<IResearchData> storage = CapabilityHandler.capResearchData.getStorage();
                ArrayList<Integer> finList = researchData.getFinishedResearch();
                if(finList.size() == 0){
                    finList.add(1);
                    researchData.setFinishedResearch(finList);
                    NBTBase nbt = storage.writeNBT(CapabilityHandler.capResearchData, researchData, null);
                    storage.readNBT(CapabilityHandler.capResearchData, player.getCapability(CapabilityHandler.capResearchData, null), null, nbt);
                }
                mesage.compound = new NBTTagCompound();
                mesage.compound.setTag("finishedResearch", storage.writeNBT(CapabilityHandler.capResearchData, researchData, null));
                EOK.getNetwork().sendTo(mesage, (EntityPlayerMP) player);
            }
            if(player.hasCapability(CapabilityHandler.capSeconds,null))
            {
                PacketSeconds message=new PacketSeconds();
                ISeconds seconds=player.getCapability(CapabilityHandler.capSeconds,null);
                Capability.IStorage<ISeconds> storage =CapabilityHandler.capSeconds.getStorage();

                message.compound=new NBTTagCompound();
                message.compound.setTag("seconds",storage.writeNBT(CapabilityHandler.capSeconds,seconds,null));

                EOK.getNetwork().sendTo(message, (EntityPlayerMP)player);
            }
            if(player.hasCapability(CapabilityHandler.capHayTorchBase,null))
            {
                PacketHayTorchBase message = new PacketHayTorchBase();
                IHayTorch hayTorchState = player.getCapability(CapabilityHandler.capHayTorchBase, null);
                Capability.IStorage<IHayTorch> storage =CapabilityHandler.capHayTorchBase.getStorage();

                message.compound = new NBTTagCompound();
                message.compound.setTag("hayTorchState", storage.writeNBT(CapabilityHandler.capHayTorchBase, hayTorchState, null));

                EOK.getNetwork().sendTo(message, (EntityPlayerMP)player);
            }
            if(player.hasCapability(CapabilityHandler.capAnotherSeconds, null))
            {
                PacketAnotherSeconds message = new PacketAnotherSeconds();
                IAnotherSeconds anotherSeconds = player.getCapability(CapabilityHandler.capAnotherSeconds, null);
                Capability.IStorage<IAnotherSeconds> storage = CapabilityHandler.capAnotherSeconds.getStorage();

                message.compound = new NBTTagCompound();
                message.compound.setTag("damage", storage.writeNBT(CapabilityHandler.capAnotherSeconds, anotherSeconds, null));

                EOK.getNetwork().sendTo(message, (EntityPlayerMP)player);
            }
            if(player.hasCapability(CapabilityHandler.capInspirations, null))
            {
                PacketInspirations message = new PacketInspirations();
                IInspirations inspirations = player.getCapability(CapabilityHandler.capInspirations, null);
                Capability.IStorage<IInspirations> storage = CapabilityHandler.capInspirations.getStorage();

                message.compound = new NBTTagCompound();
                message.compound.setTag("inspirations", storage.writeNBT(CapabilityHandler.capInspirations, inspirations, null));

                EOK.getNetwork().sendTo(message, (EntityPlayerMP)player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(!event.player.world.isRemote) {
            EntityPlayer player = event.player;
            IConsciousness consciousness = player.getCapability(CapabilityHandler.capConsciousness, null);
            Capability.IStorage<IConsciousness> storage = CapabilityHandler.capConsciousness.getStorage();
            if(player.isPlayerFullyAsleep()){
                consciousness.setConsciousnessValue(100.0D);
            }
            else {
                double conV = consciousness.getConsciousnessValue();
                if(conV >= 0.0D) {
                    consciousness.setConsciousnessValue(conV - 1.0/24000.0);
                }
            }
            player.getEntityData().setTag(CapabilityHandler.capConsciousness.getName(), storage.writeNBT(CapabilityHandler.capConsciousness, consciousness, null));
            PacketConsciousness message = new PacketConsciousness();
            message.compound = new NBTTagCompound();
            message.compound.setTag("consciousness", storage.writeNBT(CapabilityHandler.capConsciousness, consciousness, null));
            EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
        }
    }
    /**玩家右键拾取材料的事件*/
    @SubscribeEvent
    public void onPlayerRightClicked(PlayerInteractEvent.RightClickBlock event)
    {
        if(!event.getWorld().isRemote)
        {
            BlockPos pos=event.getPos();
            IBlockState state=event.getWorld().getBlockState(pos);
            if(state.equals(BlockHandler.blockStoneRock.getDefaultState()))
            {
                event.getWorld().setBlockToAir(pos);
                event.getWorld().spawnEntity(new EntityItem(event.getWorld(),pos.getX(),pos.getY(),pos.getZ(),new ItemStack(BlockHandler.blockStoneRock)));
            }
            else if(state.equals(BlockHandler.blockStick.getDefaultState()))
            {
                event.getWorld().setBlockToAir(pos);
                event.getWorld().spawnEntity(new EntityItem(event.getWorld(),pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.STICK)));
            }
        }
    }

    /**描述方块下方方块被破坏时上方方块自然掉落的事件*/
    @SubscribeEvent
    public void onDownBlockDestroyed(BlockEvent.BreakEvent event)
    {
        if(!event.getWorld().isRemote)
        {
            BlockPos pos=event.getPos();
            IBlockState state=event.getWorld().getBlockState(pos.up());
            if(state.equals(BlockHandler.blockStoneRock.getDefaultState()))
            {
                event.getWorld().setBlockToAir(pos.up());
                event.getWorld().spawnEntity(new EntityItem(event.getWorld(),pos.getX(),pos.getY(),pos.getZ(),new ItemStack(BlockHandler.blockStoneRock)));
            }
            else if(state.equals(BlockHandler.blockStick.getDefaultState()))
            {
                event.getWorld().setBlockToAir(pos.up());
                event.getWorld().spawnEntity(new EntityItem(event.getWorld(),pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.STICK)));
            }
        }
    }
    /** 描述玩家破坏方块时方块不掉落本身的事件*/
    @SubscribeEvent
    public void onDestroyedButNotDropItself(BlockEvent.HarvestDropsEvent event)
    {
        World world=event.getWorld();
        BlockPos pos=event.getPos();

        /**该段代码会导致游戏崩溃
        if(event.getDrops().get(0).isItemEqual(new ItemStack(BlockHandler.blockStick)));
        {
            event.setDropChance(0.0F);
            world.spawnEntity(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.STICK)));
        }
        **/
    }
}
