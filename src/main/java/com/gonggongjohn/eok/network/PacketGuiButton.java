package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.IButtonHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import javax.annotation.Nullable;

public class PacketGuiButton implements IMessage {
    private int buttonID;
    @SuppressWarnings("unused")
	private NBTTagCompound compound;
    @Deprecated
    public PacketGuiButton() {}

    public PacketGuiButton(int buttonID, @Nullable NBTTagCompound compound){
        this.buttonID = buttonID;
        this.compound = compound;
    }

    public PacketGuiButton(int buttonID){
        this(buttonID, null);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buttonID = buf.readInt();
        //compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(buttonID);
        //if(compound != null){
        //    ByteBufUtils.writeTag(buf, compound);
        //}
    }

    public static class Handler implements IMessageHandler<PacketGuiButton, IMessage>{

        @Override
        public IMessage onMessage(PacketGuiButton message, MessageContext ctx) {
            EntityPlayer player = EOK.getProxy().getPlayer(ctx);
            if(player != null){
                EOK.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                    if(player.openContainer instanceof IButtonHandler){
                        ((IButtonHandler) player.openContainer).onButtonPress(message.buttonID);
                    }
                });
            }
            return null;
        }
    }
}
