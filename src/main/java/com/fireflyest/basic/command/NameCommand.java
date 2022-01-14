package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NameCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("name")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		if(args.length == 1) {
			if(player.getInventory().getItemInMainHand().getType().name().equals("AIR"))return true;
			ItemUtils.setDisplayName(player.getInventory().getItemInMainHand(), args[0].replace("&", "§"));
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
