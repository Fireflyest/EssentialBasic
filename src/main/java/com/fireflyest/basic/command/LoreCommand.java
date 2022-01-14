package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoreCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("lore")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		if(args.length == 2) {
			if(player.getInventory().getItemInMainHand().getType().name().equals("AIR")) return true;
			int line = Integer.parseInt(args[0]);
			ItemUtils.setLore(player.getInventory().getItemInMainHand(), line, args[1].replace("&", "§"));
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
