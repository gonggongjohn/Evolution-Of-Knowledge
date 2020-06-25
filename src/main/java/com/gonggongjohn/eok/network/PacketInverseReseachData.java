package com.gonggongjohn.eok.network;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.IResearchData;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketInverseReseachData implements IMessage {
	private NBTTagCompound nbt;

	public PacketInverseReseachData() {

	}

	public PacketInverseReseachData(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	public PacketInverseReseachData(ICapabilitySerializable<NBTTagCompound> ser) {
		this.nbt = ser.serializeNBT();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, this.nbt);
	}

	public static class Handler implements IMessageHandler<PacketInverseReseachData, IMessage> {
		@Override
		public IMessage onMessage(PacketInverseReseachData message, MessageContext ctx) {
			if (ctx.side == Side.SERVER) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					EntityPlayer player = EOK.getProxy().getPlayer(ctx);
					if (player.hasCapability(CapabilityHandler.capResearchData, null)) {
						IResearchData cap = player.getCapability(CapabilityHandler.capResearchData, null);
						IStorage storage = CapabilityHandler.capResearchData.getStorage();
						storage.readNBT(CapabilityHandler.capResearchData, cap, null, message.nbt);
					}
				});
			}
			return null;
		}
	}
}
