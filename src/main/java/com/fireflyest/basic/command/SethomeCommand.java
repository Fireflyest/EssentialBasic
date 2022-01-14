package com.fireflyest.basic.command;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.basic.bean.Home;
import com.fireflyest.essential.data.Language;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SethomeCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("sethome")) return true;
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
				if(args.length == 0) {
					player.sendMessage(Language.TITLE + "指令需要加上家的名字，例如/sethome a");
					cancel();
					return;
				}else if(args.length == 1) {
					// 非vip大于三
					if((homes.size() >3 && !player.hasPermission("essential.vip"))){
						player.sendMessage(Language.MAXIMUM_AMOUNT);
						cancel();
						return;
					}
					// 大于五
					if(homes.size() > 5) {
						player.sendMessage(Language.MAXIMUM_AMOUNT);
						cancel();
						return;
					}
					boolean has = false;
					Location loc = player.getLocation();
					// 存在就更新
					for(Home h : homes){
						if(!h.getName().equals(args[0])) continue;
						has = true;
						locationToHome(loc, h);
						EssentialBasic.getData().update(h);
						break;
					}
					// 不存在就添加
					if(!has){
						Home h = new Home();
						h.setName(args[0]);
						h.setOwner(player.getName());
						locationToHome(loc, h);
						EssentialBasic.getData().insert(h);
					}
					player.sendMessage(Language.SUCCEED_SETTLE.replace("%home%", args[0]));
				}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());

				cancel();
			}
		}.runTaskAsynchronously(EssentialBasic.getInstance());
		return true;
	}

	private void locationToHome(Location loc, Home home) {
		home.setX(loc.getX());
		home.setY(loc.getY());
		home.setZ(loc.getZ());
		home.setYaw(loc.getYaw());
		home.setPitch(loc.getPitch());
		if (loc.getWorld() != null) {
			home.setWorld(loc.getWorld().getName());
		}

	}
	
}
