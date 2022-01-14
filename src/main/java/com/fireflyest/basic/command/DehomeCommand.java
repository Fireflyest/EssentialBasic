package com.fireflyest.basic.command;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.basic.bean.Home;
import com.fireflyest.essential.data.Language;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DehomeCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("dehome")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		// 获取所有家
		List<Home> homes = EssentialBasic.getData().query(Home.class, "owner", player.getName());
		if(args.length == 0) {
			player.sendMessage(Language.TITLE + "指令需要加上家的名字，例如/sethome a");
			return true;
		}else if(args.length == 1) {
			// 存在就更新
			for(Home h : homes){
				if(!h.getName().equals(args[0])) continue;
				EssentialBasic.getData().delete(h);
				break;
			}
			player.sendMessage(Language.SUCCEED_DELETE.replace("%home%", args[0]));
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
}
