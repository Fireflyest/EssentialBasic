package com.fireflyest.basic.command;

import com.fireflyest.basic.data.Temporary;
import com.fireflyest.essential.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MuteCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("mute")) return true;
		if(args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(Language.OFFLINE_PLAYER.replace("%player%", args[0]));
				return true;
			}
			switchMute(target);
			sender.sendMessage(Language.TITLE + "操作成功");
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}

	private void switchMute(Player player){
		if(Temporary.isMute(player.getName())){
			Temporary.removeMute(player.getName());
			player.sendMessage(Language.TITLE  + "你被解除禁言");
		}else{
			Temporary.addMute(player.getName(), 1000 * 60 * 5);
			player.sendMessage(Language.TITLE  + "你已被禁言");
		}
	}
	
}
