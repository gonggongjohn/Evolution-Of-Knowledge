package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.client.gui.GUIScreenTest;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketTestGUIScreen implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<PacketTestGUIScreen, IMessage> {

		@Override
		public IMessage onMessage(PacketTestGUIScreen message, MessageContext ctx) {
			if(ctx.side == Side.CLIENT) {
				Minecraft.getMinecraft().displayGuiScreen(new GUIScreenTest());
			}
			return null;
		}

	}

}
