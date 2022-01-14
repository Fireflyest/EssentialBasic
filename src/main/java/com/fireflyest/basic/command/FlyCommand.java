package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("fly")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(args.length == 0) {
			if(player == null) {
				sender.sendMessage(Language.ONLY_PLAYER_USE);
				return true;
			}
			player.setAllowFlight(!player.getAllowFlight());
			player.sendMessage(Language.TITLE + "飞行: §3" + player.getAllowFlight());
			return true;
		}else if(args.length == 1) {
			if(!sender.hasPermission("essential.fly.give"))  {
				sender.sendMessage(Language.NOT_PERMISSION.replace("%permission%", "essential.fly.give"));
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(Language.OFFLINE_PLAYER.replace("%player%", args[0]));
				return true;
			}
			target.setAllowFlight(!target.getAllowFlight());
			sender.sendMessage(Language.SUCCEED_SWITCH+"§3"+args[0]);
			target.sendMessage(Language.TITLE + "飞行: §3" + target.getAllowFlight());
			return true;
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
