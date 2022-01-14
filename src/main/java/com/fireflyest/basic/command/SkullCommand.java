package com.fireflyest.basic.command;

import java.util.UUID;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.essential.bean.PlayerData;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SkullCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("skull")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(player == null) {
			sender.sendMessage(Language.ONLY_PLAYER_USE);
			return true;
		}
		new BukkitRunnable(){
			@Override
			public void run() {

				if(args.length == 1) {
					OfflinePlayer target = Bukkit.getPlayer(args[0]);
					if(target == null){
						PlayerData pd = (PlayerData) EssentialBasic.getData().queryOne(PlayerData.class, "name", args[0]);
						if(pd != null){
							target = Bukkit.getOfflinePlayer(UUID.fromString(pd.getUuid()));
						}
					}
					player.getInventory().addItem(ItemUtils.createSkull(target));
				}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());

			}
		}.runTaskAsynchronously(EssentialBasic.getInstance());
		return true;
	}
	
}
