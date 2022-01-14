package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("top")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(args.length == 0) {
			if(player == null) {
				sender.sendMessage(Language.ONLY_PLAYER_USE);
				return true;
			}
			Location loc = player.getLocation();
			loc.setY(loc.getChunk().getChunkSnapshot().getHighestBlockYAt(Math.abs((int)loc.getX()%16), Math.abs((int)loc.getZ()%16))+2.5);
			TeleportUtils.teleportTo(player, loc, player.hasPermission("essential.vip"));
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
