package com.fireflyest.basic.command;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.essential.bean.Point;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ConvertUtils;
import com.fireflyest.essential.util.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WarpCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("warp")) return true;
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
					if(!player.hasPermission("essential.warp."+args[0]))  {
						sender.sendMessage(Language.NOT_PERMISSION.replace("%permission%", "essential.warp."+args[0]));
						cancel();
						return;
					}
					boolean has = false;
					// 存在就更新
					for(Point p : points){
						if(! p.getName().equals(args[0])) continue;
						has = true;
						Location loc = ConvertUtils.pointToLocation(p);
						TeleportUtils.teleportTo(player, loc, player.hasPermission("essential.vip"));
						player.sendMessage(Language.TELEPORT_POINT.replace("%point%", args[0]));
						break;
					}

					if(!has){
						sender.sendMessage(Language.HAVE_NOT_SET_POI.replace("%point%", args[0]));
					}
					cancel();
				}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());

			}
		}.runTaskAsynchronously(EssentialBasic.getInstance());
		return true;
	}
	
}
