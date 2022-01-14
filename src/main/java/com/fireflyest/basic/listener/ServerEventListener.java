package com.fireflyest.basic.listener;

import com.fireflyest.essential.data.Language;
import com.fireflyest.essential.util.TimeUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * @author Fireflyest
 * 2022/1/14 16:30
 */

public class ServerEventListener  implements Listener {

    private static long pingTime = 0;
    
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        long now = TimeUtils.getDate();
        if(now - pingTime < 500){
            event.setMaxPlayers(1);
            event.setMotd("...");
        }else {
            event.setMaxPlayers(100);
            event.setMotd(Language.MOTD);
        }
        pingTime = now;
    }


}
