package com.fireflyest.basic.listener;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.basic.data.Temporary;
import com.fireflyest.basic.util.DeathMsgUtils;
import com.fireflyest.essential.api.Data;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ChatUtils;
import com.fireflyest.essential.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Fireflyest
 * 2022/1/8 21:43
 */

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setPlayerListHeader(Language.LIST_HEADER.replace(",", "\n"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        // 替换自己的信息
        Player player = event.getPlayer();
        String name = player.getName();
        event.getRecipients().remove(player);
        // 禁言否
        if(Temporary.isMute(name)){
            event.setCancelled(true);
            player.sendMessage(Language.TITLE + "禁言中...");
        }
        // 聊天颜色
        if(event.getPlayer().hasPermission("essential.chat.color")){
            event.setMessage(event.getMessage().replace("&", "§"));
        }
        // @
        if(event.getMessage().contains("@")){
            String[] msg = event.getMessage().split(" ");
            for(String demo : msg){
                if(!demo.contains("@"))continue;
                String targetName = demo.substring(demo.indexOf("@")+1);
                Player target = Bukkit.getPlayer(targetName);
                if(target == null)break;
                event.setMessage(event.getMessage().replace("@" +targetName,  "§6@§b"+target.getName()+"§r"));
                target.playSound(target.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
            }
        }
        // 聊天格式
        event.setFormat("%1$s §7▶§f %2$s");
        // 发送给自己按钮
        String prefix = event.getPlayer().getDisplayName().replace(event.getPlayer().getName(), "");
        ChatUtils.sendMessage(prefix, event.getPlayer().getName(), player, TimeUtils.getTime(), event.getMessage());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Location loc = event.getEntity().getLocation();
        Temporary.putBack(event.getEntity().getName(), loc);
        String name = event.getEntity().getName();
        String msg = event.getDeathMessage()+"";
        msg = DeathMsgUtils.convertDeathMsg(name, msg, event.getEntity().getLastDamageCause().getCause());
        event.setDeathMessage("§6☠ §f"+ msg);
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().spigot().respawn();
                cancel();
            }
        }.runTask(EssentialBasic.getInstance());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location loc = event.getFrom();
        Temporary.putBack(event.getPlayer().getName(), loc);
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event){
        if(event.getItem().getAmount() < 0)event.getItem().setAmount(0);
    }

}
