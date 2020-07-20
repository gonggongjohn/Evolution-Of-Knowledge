package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.ICBTHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBlueprintTable implements IMessage {
    public NBTTagCompound nbt;

    public PacketBlueprintTable() {
    }

    public PacketBlueprintTable(NBTTagCompound compound){
        this.nbt = compound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<PacketBlueprintTable, IMessage> {

        @Override
        public IMessage onMessage(PacketBlueprintTable message, MessageContext ctx) {
            EntityPlayer player = EOK.getProxy().getPlayer(ctx);
            if (player != null) {
                EOK.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                    if (player.openContainer instanceof ICBTHandler){
                        ((ICBTHandler) player.openContainer).onWriteActive(message.nbt);
                    }
                });
            }
            return null;
        }
    }
}
