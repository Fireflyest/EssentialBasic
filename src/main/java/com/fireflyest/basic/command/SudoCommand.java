package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SudoCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("sudo")) return true;
		if(args.length == 2) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(Language.OFFLINE_PLAYER.replace("%player%", args[0]));
				return true;
			}
			Bukkit.dispatchCommand(target, args[1].replace("_", " "));
			return true;
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
