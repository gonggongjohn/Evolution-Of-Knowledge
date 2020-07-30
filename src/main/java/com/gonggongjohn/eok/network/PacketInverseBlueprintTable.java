package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.IClientCBTHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketInverseBlueprintTable implements IMessage {
    public NBTTagCompound componentPool;

    public PacketInverseBlueprintTable(){

    }

    public PacketInverseBlueprintTable(NBTTagCompound componentPool){
        this.componentPool = componentPool;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        componentPool = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, componentPool);
    }

    public static class Handler implements IMessageHandler<PacketInverseBlueprintTable, IMessage>{

        @Override
        public IMessage onMessage(PacketInverseBlueprintTable message, MessageContext ctx) {
            EntityPlayer player = EOK.getProxy().getPlayer(ctx);
            if (player != null) {
                EOK.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                    if(player.openContainer instanceof IClientCBTHandler){
                        ((IClientCBTHandler)player.openContainer).onComponentPoolUpdate(message.componentPool);
                    }
                });
            }
            return null;
        }
    }
}
