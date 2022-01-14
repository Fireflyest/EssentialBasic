package com.fireflyest.basic.command;

import com.fireflyest.basic.bean.Tpa;
import com.fireflyest.basic.util.TpaUtils;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class TpacceptCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tpaccept")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(args.length == 0) {
			if(player == null) {
				sender.sendMessage(Language.ONLY_PLAYER_USE);
				return true;
			}

			UUID uuid = player.getUniqueId();
			// 是否有请求
			if(TpaUtils.containsReceiver(player.getUniqueId())) {
				Tpa tpa = TpaUtils.getTpa(uuid);
				// 判断是否超时
				if(tpa.getTime() < new Date().getTime()) {
					TpaUtils.removeTpa(uuid);
					player.sendMessage(Language.TITLE + "传送超时！");
					return true;
				}
				// 判断传送方
				Player tper = Bukkit.getPlayer(tpa.getTpaer());
				// 玩家下线
				if (tper == null) return true;

				if(!tpa.isTphere()){ // 请求传送 我->他人
					player.sendMessage(Language.TELEPORT_POINT.replace("%point%", tper.getName()));
					TeleportUtils.teleportTo(player, tper.getLocation(),  player.hasPermission("essential.vip"));
				}else{	// 邀请传送 他人->我
					tper.sendMessage(Language.TELEPORT_POINT.replace("%point%", player.getName()));
					TeleportUtils.teleportTo(tper, player.getLocation(),  tper.hasPermission("essential.vip"));
				}
				TpaUtils.removeTpa(uuid);
				return true;
			}
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
