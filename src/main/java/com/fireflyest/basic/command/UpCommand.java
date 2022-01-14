package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class UpCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("up")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}

		if(args.length == 1) {
			Location loc = player.getLocation().add(0, Double.parseDouble(args[0]), 0);
			Location f = loc.clone().add(0, -1, 0);
			if(f.getBlock().getType().name().equals("AIR"))f.getBlock().setType(Material.GLASS);
			TeleportUtils.teleportTo(player, loc, player.hasPermission("essential.vip"));
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
