package me.austinfrg.hypersleep;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class HyperSleep extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        int pluginId = 8845;
        new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent e) {
        if (!e.getPlayer().hasPermission("hypersleep.use") && this.getConfig().getBoolean("permissions-enabled")) return;
        String player = e.getPlayer().getDisplayName();
        World world = e.getPlayer().getWorld();
        if (world.getTime() >= 12500 || world.hasStorm() || world.isThundering() || (e.getPlayer().hasPermission("hypersleep.bypass") && this.getConfig().getBoolean("permissions-enabled"))) {
            String reason;
            if (world.getTime() >= 12500) reason = "time";
            else reason = "weather";
            world.setTime(0);
            world.setThundering(false);
            world.setStorm(false);
            e.setCancelled(this.getConfig().getBoolean("stop-entering-bed"));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(this.getConfig().getString(reason + "-changed-msg")).replace("%player%", player))));
        } else {
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(this.getConfig().getString("already-day-msg")).replace("%player%", player))));
        }
    }
}