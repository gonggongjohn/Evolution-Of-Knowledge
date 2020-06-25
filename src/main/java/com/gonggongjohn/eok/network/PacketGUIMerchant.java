package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.api.utils.EOKByteBufUtils;
import com.gonggongjohn.eok.inventory.ContainerMerchant;
import com.gonggongjohn.eok.utils.MerchantTradeData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

public class PacketGUIMerchant implements IMessage {
    public NBTTagCompound compound;
    public ArrayList<MerchantTradeData> tradeList;

    public PacketGUIMerchant() {
    }

    public PacketGUIMerchant(NBTTagCompound compound, ArrayList<MerchantTradeData> tradeList) {
        this.compound = compound;
        this.tradeList = tradeList;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
        tradeList = EOKByteBufUtils.readTradeDataArrayList(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
        EOKByteBufUtils.writeTradeDataArrayList(buf, tradeList);
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public void setCompound(NBTTagCompound compound) {
        this.compound = compound;
    }

    public ArrayList<MerchantTradeData> getTradeList() {
        return tradeList;
    }

    public void setTradeList(ArrayList<MerchantTradeData> tradeList) {
        this.tradeList = tradeList;
    }

    public static class Handler implements IMessageHandler<PacketGUIMerchant, IMessage> {

        @Override
        public IMessage onMessage(PacketGUIMerchant message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                ContainerMerchant container = (ContainerMerchant) ctx.getServerHandler().player.openContainer;
                NBTTagCompound nbt = message.compound;
                container.tradeList = message.tradeList;
                if (nbt.hasKey("currentPage", 3)) {
                    container.currentPage = nbt.getInteger("currentPage");
                }
                if (nbt.hasKey("currentDeal", 3)) {
                    container.currentDeal = nbt.getInteger("currentDeal");
                }
                if (nbt.hasKey("operation", 3)) {
                    switch (nbt.getInteger("operation")) {
                        case 0:
                            container.previousPage();
                            break;
                        case 1:
                            container.nextPage();
                            break;
                        case 2:
                            container.buy();
                            break;
                    }
                }

            }

            if (ctx.side == Side.CLIENT) {
                // ContainerMerchant container = (ContainerMerchant)
                // ctx.getServerHandler().player.openContainer;
                EntityPlayer player = Minecraft.getMinecraft().player;
                ContainerMerchant container = (ContainerMerchant) player.openContainer;
                NBTTagCompound nbt = message.compound;
                if (nbt.hasKey("currentPage", 3)) {
                    container.currentPage = nbt.getInteger("currentPage");
                }
                if (nbt.hasKey("currentDeal", 3)) {
                    container.currentDeal = nbt.getInteger("currentDeal");
                }
                if (nbt.hasKey("totalPages", 3)) {
                    container.totalPages = nbt.getInteger("totalPages");
                }
                container.tradeList = message.tradeList;

            }
            return null;
        }

    }

}
