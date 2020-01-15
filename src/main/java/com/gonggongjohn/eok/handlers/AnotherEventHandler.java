package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.*;
import com.gonggongjohn.eok.network.PacketConsciousness;
import com.gonggongjohn.eok.network.PacketMindActivity;
import com.gonggongjohn.eok.network.PacketResearchData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "consciousness"), providerConsciousness);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "mindActivity"), providerMindActivity);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "researchData"), providerResearchData);
            event.addCapability(new ResourceLocation(EOK.MODID + ":" + "seconds"),providerSeconds);
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
                    consciousness.setConsciousnessValue(conV - 0.005D);
                }
            }
            player.getEntityData().setTag(CapabilityHandler.capConsciousness.getName(), storage.writeNBT(CapabilityHandler.capConsciousness, consciousness, null));
            PacketConsciousness message = new PacketConsciousness();
            message.compound = new NBTTagCompound();
            message.compound.setTag("consciousness", storage.writeNBT(CapabilityHandler.capConsciousness, consciousness, null));
            EOK.getNetwork().sendTo(message, (EntityPlayerMP) player);
        }
    }
}
