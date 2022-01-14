package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TableCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("table")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(args.length == 0) {
			if(player == null) {
				sender.sendMessage(Language.ONLY_PLAYER_USE);
				return true;
			}
			player.openWorkbench(null, true);
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
