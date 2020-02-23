package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.ISlotHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

public class PacketSlotChange implements IMessage {
    private int slotID;
    @SuppressWarnings("unused")
    private NBTTagCompound compound;

    @Deprecated
    public PacketSlotChange() {
    }

    public PacketSlotChange(int slotID, @Nullable NBTTagCompound compound) {
        this.slotID = slotID;
        this.compound = compound;
    }

    public PacketSlotChange(int slotID) {
        this(slotID, null);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slotID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slotID);
    }

    public static class Handler implements IMessageHandler<PacketSlotChange, IMessage>{

        @Override
        public IMessage onMessage(PacketSlotChange message, MessageContext ctx) {
            EntityPlayer player = EOK.getProxy().getPlayer(ctx);
            if(player != null) {
                EOK.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                    if (player.openContainer instanceof ISlotHandler) {
                        ((ISlotHandler) player.openContainer).onSlotChange(message.slotID);
                    }
                });
            }
            return null;
        }
    }
}
