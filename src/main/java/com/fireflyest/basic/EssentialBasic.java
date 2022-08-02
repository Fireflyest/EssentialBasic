package com.fireflyest.basic;

import com.fireflyest.basic.bean.Home;
import com.fireflyest.basic.bean.Sign;
import com.fireflyest.basic.command.*;
import com.fireflyest.basic.listener.BlockEventListener;
import com.fireflyest.basic.listener.PlayerEventListener;
import com.fireflyest.basic.listener.ServerEventListener;
import com.fireflyest.essential.api.Data;
import com.fireflyest.essential.api.Storage;
import com.fireflyest.essential.bean.Point;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * @author Fireflyest
 * 2022/1/6 10:19
 */
public class EssentialBasic extends JavaPlugin {

    public static JavaPlugin getInstance() { return plugin; }

    private static JavaPlugin plugin;

    public static Data getData() {
        return data;
    }

    private static Data data;

    public static Storage getStorage() {
        return storage;
    }

    private static Storage storage;

    @Override
    public void onEnable() {
        plugin = this;

        // 存储
        this.setupData();
        // 指令
        this.setExecutor();

        this.getServer().getPluginManager().registerEvents( new PlayerEventListener(), this );
        this.getServer().getPluginManager().registerEvents( new ServerEventListener(), this );
        this.getServer().getPluginManager().registerEvents( new BlockEventListener(), this );

        for (Player onlinePlayer : getServer().getOnlinePlayers()) {
            this.getServer().getPluginManager().callEvent(new PlayerJoinEvent(onlinePlayer, null));
        }
    }

    @Override
    public void onDisable() {
    }

    private void setupData() {
        RegisteredServiceProvider<Data> rspd = Bukkit.getServer().getServicesManager().getRegistration(Data.class);
        if (rspd == null) {
            Bukkit.getLogger().warning("Data not found!");
            return;
        }
        RegisteredServiceProvider<Storage> rsps = Bukkit.getServer().getServicesManager().getRegistration(Storage.class);
        if (rsps == null) {
            Bukkit.getLogger().warning("Storage not found!");
            return;
        }
        data = rspd.getProvider();
        storage = rsps.getProvider();

        data.createTable(Home.class);
        data.createTable(Point.class);
        data.createTable(Sign.class);
    }

    private void setExecutor() {
//            this.getCommand("rule").setExecutor( new RuleCommands());
            Objects.requireNonNull(this.getCommand("fly")).setExecutor( new FlyCommand());
            Objects.requireNonNull(this.getCommand("inv")).setExecutor( new InvCommand());
            Objects.requireNonNull(this.getCommand("heal")).setExecutor( new HealCommand());
            Objects.requireNonNull(this.getCommand("mode")).setExecutor( new ModeCommand());
            Objects.requireNonNull(this.getCommand("einv")).setExecutor( new EinvCommand());
            Objects.requireNonNull(this.getCommand("table")).setExecutor( new TableCommand());
//            this.getCommand("my").setExecutor( new MyCommand());
            Objects.requireNonNull(this.getCommand("home")).setExecutor( new HomeCommand());
            Objects.requireNonNull(this.getCommand("sethome")).setExecutor( new SethomeCommand());
            Objects.requireNonNull(this.getCommand("dehome")).setExecutor( new DehomeCommand());
            Objects.requireNonNull(this.getCommand("spawn")).setExecutor( new SpawnCommand());
            Objects.requireNonNull(this.getCommand("skull")).setExecutor( new SkullCommand());
            Objects.requireNonNull(this.getCommand("back")).setExecutor( new BackCommand());
            Objects.requireNonNull(this.getCommand("warp")).setExecutor( new WarpCommand());
            Objects.requireNonNull(this.getCommand("setwarp")).setExecutor( new SetwarpCommand());
//            this.getCommand("kit").setExecutor( new KitCommand());
            Objects.requireNonNull(this.getCommand("tpa")).setExecutor( new TpaCommand());
            Objects.requireNonNull(this.getCommand("tphere")).setExecutor( new TphereCommand());
            Objects.requireNonNull(this.getCommand("tpaccept")).setExecutor( new TpacceptCommand());
            Objects.requireNonNull(this.getCommand("tprefuse")).setExecutor( new TprefuseCommand());
            Objects.requireNonNull(this.getCommand("plugin")).setExecutor( new PluginCommand());
//            this.getCommand("delay").setExecutor( new DelayCommand());
            Objects.requireNonNull(this.getCommand("sudo")).setExecutor( new SudoCommand());
            Objects.requireNonNull(this.getCommand("hat")).setExecutor( new HatCommand());
            Objects.requireNonNull(this.getCommand("top")).setExecutor( new TopCommand());
            Objects.requireNonNull(this.getCommand("up")).setExecutor( new UpCommand());
            Objects.requireNonNull(this.getCommand("name")).setExecutor( new NameCommand());
            Objects.requireNonNull(this.getCommand("lore")).setExecutor( new LoreCommand());
            Objects.requireNonNull(this.getCommand("runtime")).setExecutor( new RuntimeCommand());
            Objects.requireNonNull(this.getCommand("mute")).setExecutor( new MuteCommand());
            Objects.requireNonNull(this.getCommand("dnd")).setExecutor( new DndCommand());
            Objects.requireNonNull(this.getCommand("message")).setExecutor( new MessageCommand());
            Objects.requireNonNull(this.getCommand("sun")).setExecutor( new SunCommand());
            Objects.requireNonNull(this.getCommand("suicide")).setExecutor( new SuicideCommand());
//            this.getCommand("interact").setExecutor( new InteractCommand());
    }

}
