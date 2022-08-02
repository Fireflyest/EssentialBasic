package com.fireflyest.basic.listener;

import com.fireflyest.basic.EssentialBasic;
import com.fireflyest.basic.bean.Sign;
import com.fireflyest.essential.api.Storage;
import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TimeUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Fireflyest
 * 2022/2/6 12:56
 */

public class BlockEventListener implements Listener {

    private static long interactTime = 0;

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        Player player = event.getPlayer();

        // 牌子的彩色颜色
        if (player.hasPermission("essential.chat.color")){
            // 颜色
            int i = 0;
            for (String line : event.getLines()) {
                event.setLine(i, line.replace("&", "§"));
                i++;
            }
        }

        String type = event.getLine(0);

        if(type == null || !type.startsWith("#") || !player.isOp()) return;
        Location loc = event.getBlock().getLocation();
        if (loc.getWorld() == null) return;

        Sign sign = null;
        if("#command".equals(type)){
            event.setLine(0, "§f§l[§9§l指令§l§f]");
            sign = new Sign(event.getLine(1), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
        }else if("#warp".equals(type)){
            event.setLine(0, "§f§l[§9§l传送§f§l]");
            sign = new Sign( "warp " + event.getLine(1), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
        }

        if (sign != null) {
            final Sign finalSign = sign;
            new BukkitRunnable(){
                @Override
                public void run() {
                    EssentialBasic.getData().insert(finalSign);
                }
            }.runTaskAsynchronously(EssentialBasic.getInstance());
        }
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event){
        // 判断是否方块交互
        if(! event.hasBlock() || event.getClickedBlock() == null) return;
        // 交互间隔
        long now = TimeUtils.getDate();
        if((now - interactTime < 100)){
            return;
        }
        interactTime = now;
        // 判断是否牌子
        Block block = event.getClickedBlock();
        if( !block.getType().name().contains("SIGN")) return;
        // 创造模式破坏
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && event.getAction() == Action.LEFT_CLICK_BLOCK){
            return;
        }

        // 交互位置
        Location loc =block.getLocation();
        if (loc.getWorld() == null) return;

        Storage storage = EssentialBasic.getStorage();
        String sql = String.format("select * from sign where world='%s' and x='%s' and y='%s' and z='%s'", loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
        Sign sign = storage.inquiry(sql, Sign.class);
        if (sign != null) {
            event.getPlayer().performCommand(sign.getCommand());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        // 交互位置
        Location loc = event.getBlock().getLocation();
        if (loc.getWorld() == null) return;

        // 判断是否牌子
        Block block = event.getBlock();
        if( !block.getType().name().contains("SIGN")) return;

        // 看是否指令牌子
        Storage storage = EssentialBasic.getStorage();
        Sign sign = storage.inquiry(
                String.format("select * from sign where world='%s' and x='%s' and y='%s' and z='%s'", loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ()),
                Sign.class);
        // 不是空则是
        if (sign != null) {
            if(event.getPlayer().getGameMode() == GameMode.CREATIVE){
                EssentialBasic.getData().delete(sign);
            }else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Language.TITLE + "创造模式下才能破坏");
            }
        }

    }

}
