package com.fireflyest.basic.listener;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.basic.data.Temporary;
import com.fireflyest.basic.util.DeathMsgUtils;
import com.fireflyest.essential.data.Config;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.ChatUtils;
import com.fireflyest.essential.util.TimeUtils;
import com.fireflyest.essential.world.WorldManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Objects;

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
                Player target = Bukkit.getPlayerExact(targetName);
                if(target == null)break;
                event.setMessage(event.getMessage().replace("@" +targetName,  "§6@§b"+target.getName()+"§r"));
                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(name + "在聊天框呼叫你").create());
                target.playSound(target.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
            }
        }
        String worldName = player.getWorld().getName();
        String worldTitle = WorldManager.getTitle(worldName);
        // 聊天格式
        event.setFormat(worldTitle + "%1$s §7▶§f %2$s");
        // 发送按钮
        String prefix = event.getPlayer().getDisplayName().replace(event.getPlayer().getName(), "");
        for (Player recipient : event.getRecipients()) {
            ChatUtils.sendMessage(worldTitle, prefix, event.getPlayer().getName(), recipient, TimeUtils.getTime(), event.getMessage());
        }
        event.getRecipients().clear();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Location loc = event.getEntity().getLocation();
        Temporary.putBack(event.getEntity().getName(), loc);

        String name = event.getEntity().getName();
        String msg = event.getDeathMessage();
        EntityDamageEvent entityDamageEvent = event.getEntity().getLastDamageCause();
        if (entityDamageEvent != null) {
            msg = DeathMsgUtils.convertDeathMsg(name, msg, entityDamageEvent.getCause());
        }
        event.setDeathMessage("§6☠ §f"+ msg);
        event.getEntity().setBedSpawnLocation(Objects.requireNonNull(Bukkit.getWorld(Config.MAIN_WORLD)).getSpawnLocation(), true);

        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().spigot().respawn();
            }
        }.runTask(EssentialBasic.getInstance());
    }



    @EventHandler
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(Objects.requireNonNull(Bukkit.getWorld(Config.MAIN_WORLD)).getSpawnLocation());
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
