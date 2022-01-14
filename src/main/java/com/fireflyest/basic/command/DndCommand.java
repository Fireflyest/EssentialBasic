package com.fireflyest.basic.command;

import com.fireflyest.basic.data.Temporary;
import com.fireflyest.essential.data.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class DndCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, @NotNull String[] args) {
		if(!cmd.getName().equalsIgnoreCase("dnd")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		if(args.length == 0) {
			if(Temporary.isDnd(sender.getName())) {
				Temporary.removeDnd(sender.getName());
			}else {
				Temporary.addDnd(sender.getName());
			}
			player.sendMessage(Language.SUCCEED_SWITCH);
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
