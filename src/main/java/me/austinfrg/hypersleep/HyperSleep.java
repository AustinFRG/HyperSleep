package me.austinfrg.hypersleep;

import me.austinfrg.hypersleep.listeners.SleepListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperSleep extends JavaPlugin implements Listener {

    private static HyperSleep instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new SleepListener(this), this);
        new Metrics(this, 8845);
    }

    public static HyperSleep getInstance() {
        return instance;
    }

}