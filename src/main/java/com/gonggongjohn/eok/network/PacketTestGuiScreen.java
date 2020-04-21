package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.client.gui.GuiEOKManual;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketTestGuiScreen implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<PacketTestGuiScreen, IMessage> {

		@Override
		public IMessage onMessage(PacketTestGuiScreen message, MessageContext ctx) {
			if(ctx.side == Side.CLIENT) {
				Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new GuiEOKManual()));
			}
			return null;
		}

	}

}
