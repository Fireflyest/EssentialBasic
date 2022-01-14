package com.fireflyest.basic.command;

import com.fireflyest.basic.bean.Tpa;
import com.fireflyest.basic.util.TpaUtils;
import com.fireflyest.essential.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TprefuseCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tprefuse")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		if(args.length == 0) {
			Player tper;
			UUID uuid = player.getUniqueId();
			if(TpaUtils.containsReceiver(player.getUniqueId())) {
				Tpa tpa = TpaUtils.getTpa(uuid);
				tper = Bukkit.getPlayer(tpa.getTpaer());
				if (tper != null) tper.sendMessage(Language.TP_APPLY_REFUSE);
			}
			TpaUtils.removeTpa(uuid);
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
