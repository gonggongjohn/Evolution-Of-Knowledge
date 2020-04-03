package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.client.gui.GuiDocumentPreview;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiScreen implements IMessage {

	/**
	 * GuiDocumentPreview	0	path
	 */
	private int id;
	private String addition;

	public PacketGuiScreen() {
		
	}
	
	public PacketGuiScreen(int id, String addition) {
		this.id = id;
		this.addition = addition;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		this.addition = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		ByteBufUtils.writeUTF8String(buf, addition);
	}

	public static class Handler implements IMessageHandler<PacketGuiScreen, IMessage> {

		@Override
		public IMessage onMessage(PacketGuiScreen message, MessageContext ctx) {
			if(ctx.side == Side.CLIENT) {
				switch(message.id) {
				case 0:
					Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new GuiDocumentPreview(message.addition)));
					break;
				default:
					break;
				}
			}
			return null;
		}

	}

	public int getId() {
		return id;
	}

	public String getAddition() {
		return addition;
	}
}
