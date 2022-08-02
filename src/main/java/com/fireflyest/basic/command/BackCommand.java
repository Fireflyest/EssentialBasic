package com.fireflyest.basic.command;

import com.fireflyest.basic.data.Temporary;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BackCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, @NotNull String[] args) {
		if(!cmd.getName().equalsIgnoreCase("back")) return true;

		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}

		if(args.length == 0) {
			Location backLoc = Temporary.getBack(player.getName());
			Temporary.putBack(player.getName(), player.getLocation());
			TeleportUtils.teleportTo(player, backLoc, player.hasPermission("essential.vip"));
			player.sendMessage(Language.TELEPORT_POINT.replace("%point%", "back"));
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
