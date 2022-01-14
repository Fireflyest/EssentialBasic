package com.fireflyest.basic.command;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.essential.bean.Point;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ConvertUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetwarpCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("setwarp")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		new BukkitRunnable(){
			@Override
			public void run() {
				List<Point> points = EssentialBasic.getData().query(Point.class);
				if(args.length == 0) {
					StringBuilder builder = new StringBuilder(Language.TITLE).append("[");
					int i = 0;
					for (Point p: points) {
						if(i > 0) builder.append(", ");
						builder.append(p.getName());
						i++;
					}
					builder.append("]");
					sender.sendMessage(Language.TITLE + builder.toString());
				}else if(args.length == 1) {
					if(player == null) {
						sender.sendMessage(Language.ONLY_PLAYER_USE);
						cancel();
						return;
					}
					boolean has = false;
					Location loc = player.getLocation();
					// 存在就更新
					for(Point p : points){
						if(!p.getName().equals(args[0])) continue;
						has = true;
						ConvertUtils.locationToPoint(loc, p);
						EssentialBasic.getData().update(p);
						break;
					}
					// 不存在就添加
					if(!has){
						Point p = new Point();
						p.setName(args[0]);
						ConvertUtils.locationToPoint(loc, p);
						EssentialBasic.getData().insert(p);
					}
					player.sendMessage(Language.TITLE + "成功设置点§3" + args[0]);
					cancel();
					return;
				}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());

				cancel();
			}
		}.runTaskAsynchronously(EssentialBasic.getInstance());

		return true;
	}
	
}
