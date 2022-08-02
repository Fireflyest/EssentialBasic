package com.fireflyest.basic.command;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.basic.bean.Home;
import com.fireflyest.basic.data.Temporary;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("home")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		new BukkitRunnable(){
			@Override
			public void run() {
				// 获取所有家
				List<Home> homes = EssentialBasic.getData().query(Home.class, "owner", player.getName());
				if(args.length == 0) {		// home
					if(homes.size() == 0){
						player.sendMessage(Language.HAVE_NOT_SETTLE.replace("%home%", ""));
					}else {
						StringBuilder builder = new StringBuilder(Language.TITLE).append("[");
						int i = 0;
						for (Home h: homes) {
							if(i > 0) builder.append(", ");
							builder.append(h.getName());
							i++;
						}
						builder.append("]");
						player.sendMessage(Language.TITLE + builder);
					}
				}else if(args.length == 1) {		// home <name>
					boolean has = false;
					for(Home h: homes){
						if(!h.getName().equals(args[0])) continue;
						has = true;
						Temporary.putBack(player.getName(), player.getLocation());
						Location loc = new Location(Bukkit.getWorld(h.getWorld()), h.getX(), h.getY(), h.getZ(), h.getYaw(), h.getPitch());
						TeleportUtils.teleportTo(player, loc, player.hasPermission("essential.vip"));
						player.sendMessage(Language.TELEPORT_POINT.replace("%point%", "home("+args[0]+")"));
					}
					if(!has){	// 如果不存在
						player.sendMessage(Language.HAVE_NOT_SETTLE.replace("%home%", args[0]));
					}
				}else {				// error
					sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
				}
			}
		}.runTask(EssentialBasic.getInstance());

		return true;
	}
	
}
