package com.fireflyest.basic.command;

import com.fireflyest.essential.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class HealCommand  implements CommandExecutor{
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("heal")) return true;
		Player player = (sender instanceof Player)? (Player)sender : null;
		if(args.length == 0) {
			if(player == null) {
				sender.sendMessage(Language.ONLY_PLAYER_USE);
				return true;
			}
			healPlayer(player);
		}else if(args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(Language.OFFLINE_PLAYER.replace("%player%", args[0]));
				return true;
			}
			healPlayer(target);
		}else sender.sendMessage(Language.TITLE + "正确用法§3" + cmd.getUsage());
		return true;
	}
	
	private void healPlayer(Player player) {
		double maxHealth = 20;
		AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		if (attribute != null) maxHealth = attribute.getValue();
		player.setHealth(maxHealth);
		player.setFoodLevel(20);
		for(PotionEffect effect: player.getActivePotionEffects()){
			if (effect.getType() == PotionEffectType.FIRE_RESISTANCE )continue;
			if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE )continue;
			if (effect.getType() == PotionEffectType.JUMP )continue;
			if (effect.getType() == PotionEffectType.LUCK )continue;
			if (effect.getType() == PotionEffectType.HERO_OF_THE_VILLAGE )continue;
			if (effect.getType() == PotionEffectType.REGENERATION )continue;
			if (effect.getType() == PotionEffectType.SPEED )continue;
			if (effect.getType() == PotionEffectType.INVISIBILITY )continue;
			player.removePotionEffect(effect.getType());
		}
		player.setFireTicks(0);
		player.sendMessage(Language.TITLE + "已恢复状态");
	}
	
}
