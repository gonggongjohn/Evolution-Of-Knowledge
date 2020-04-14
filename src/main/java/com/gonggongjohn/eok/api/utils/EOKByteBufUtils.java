package com.gonggongjohn.eok.api.utils;

import java.io.IOException;
import java.util.ArrayList;

import com.gonggongjohn.eok.utils.MerchantTradeData;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class EOKByteBufUtils {
	public static void writeTradeData(ByteBuf buf, MerchantTradeData toSend) {
		PacketBuffer pb = new PacketBuffer(buf);
		pb.writeItemStack(toSend.getCost1());
		pb.writeItemStack(toSend.getCost2());
		pb.writeItemStack(toSend.getResult());
		buf.writeInt(toSend.getLevel());
	}

	public static MerchantTradeData readTradeData(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		ItemStack cost1 = ItemStack.EMPTY;
		ItemStack cost2 = ItemStack.EMPTY;
		ItemStack result = ItemStack.EMPTY;
		int level = 3;
		try {
			cost1 = pb.readItemStack();
			cost2 = pb.readItemStack();
			result = pb.readItemStack();
			level = buf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new MerchantTradeData(cost1, cost2, result, level);
	}

	public static void writeTradeDataArrayList(ByteBuf buf, ArrayList<MerchantTradeData> toSend) {
		int size = toSend.size();
		buf.writeInt(size);
		for (int i = 0; i < size; i++) {
			writeTradeData(buf, toSend.get(i));
		}
	}

	public static ArrayList<MerchantTradeData> readTradeDataArrayList(ByteBuf buf) {
		ArrayList<MerchantTradeData> result = new ArrayList<MerchantTradeData>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			MerchantTradeData data = readTradeData(buf);
			result.add(data);
		}
		return result;
	}
}
