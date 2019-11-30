package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.capabilities.IMindActivity;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketMindActivity implements IMessage {
    public NBTTagCompound compound;

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<PacketMindActivity, IMessage> {
        @Override
            public IMessage onMessage(PacketMindActivity message, MessageContext ctx) {
            if(ctx.side == Side.CLIENT) {
                final NBTBase nbt = message.compound.getTag("mindActivity");
                Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        EntityPlayer player = Minecraft.getMinecraft().player;
                        if (player.hasCapability(CapabilityHandler.capMindActivity, null)) {
                            IMindActivity mindActivity = player.getCapability(CapabilityHandler.capMindActivity, null);
                            Capability.IStorage<IMindActivity> storage = CapabilityHandler.capMindActivity.getStorage();
                            storage.readNBT(CapabilityHandler.capMindActivity, mindActivity, null, nbt);
                        }
                    }
                });
            }
            return null;
        }
    }
}
