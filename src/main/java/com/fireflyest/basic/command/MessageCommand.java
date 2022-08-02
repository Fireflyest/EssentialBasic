package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class MessageCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("message")) return true;
		if(args.length >= 2) {
			Player target = Bukkit.getPlayerExact(args[0]);
			if(target == null) {
				sender.sendMessage(Language.OFFLINE_PLAYER.replace("%player%", args[0]));
				return true;
			}
			String msg;
			StringBuilder message = new StringBuilder();
			for(int i = 1 ; i < args.length ; i++) {
				message.append(" ").append(args[i]);
			}
			msg = message.toString();
			sender.sendMessage(Language.TITLE+"悄悄对§3"+args[0] + "§f说 §7▶§f" + msg);
			target.sendMessage(Language.TITLE+"§3"+sender.getName()+"§f悄悄对你说 §7▶§f" + msg);
			return true;
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
