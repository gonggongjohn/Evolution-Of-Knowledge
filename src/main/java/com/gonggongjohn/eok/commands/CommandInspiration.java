package com.gonggongjohn.eok.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandInspiration extends CommandBase {
	@Override
	public String getName() {
		return "inspiration";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.inspiration.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 1) {
			throw new WrongUsageException("commands.inspiration.usage");
		} else if (args[0].equals("get")) {
			EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
			sender.sendMessage(new TextComponentTranslation("eok.inspiration.query.pre"));
//            if(entityPlayerMP.hasCapability(CapabilityHandler.capInspirations, null)) {
//                IInspirations inspirations = entityPlayerMP.getCapability(CapabilityHandler.capInspirations, null);
//                int[] insStatus = inspirations.getInspirationsStatus();
//                for(int i = 0; i < insStatus.length; i++){
//                    if(insStatus[i] == 1){
//                        String insName = EOK.inspirationDict.inspirationNameDict.get(i);
//                        sender.sendMessage(new TextComponentTranslation("inspiration." + insName + ".name"));
//                    }
//                }
//            }
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
}
