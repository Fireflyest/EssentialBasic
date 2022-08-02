package com.fireflyest.basic.command;

import com.fireflyest.basic.data.Temporary;
import com.fireflyest.basic.util.TpaUtils;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TphereCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tphere")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}

		if(args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) {sender.sendMessage(Language.OFFLINE_PLAYER.replace("%player%", args[0])); return true; }
			if(Temporary.isDnd(target.getName())){
				player.sendMessage(Language.TP_APPLY_REFUSE);
				return true;
			}
			if(TpaUtils.containsReceiver(target.getUniqueId())) {
				sender.sendMessage(Language.ALREADY_HAS_TPER.replace("%player%", args[0]));
				return true;
			}
			TpaUtils.addTpa(player.getUniqueId(), target.getUniqueId(), true);
			target.sendMessage(Language.TELEPORT_IVTTP.replace("%player%", player.getName()));
			ChatUtils.sendApplyButton(target, "/tp");
			player.sendMessage(Language.SUCCEED_SEND_TP);
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
