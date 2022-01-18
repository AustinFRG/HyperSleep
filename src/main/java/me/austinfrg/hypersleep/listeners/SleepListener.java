package me.austinfrg.hypersleep.listeners;

import me.austinfrg.hypersleep.HyperSleep;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.Objects;

public class SleepListener implements Listener {

	private final HyperSleep main;

	public SleepListener(HyperSleep main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		if (!e.getPlayer().hasPermission("hypersleep.use") && main.getConfig().getBoolean("permissions-enabled")) return;
		String player = e.getPlayer().getDisplayName();
		World world = e.getPlayer().getWorld();
		if (world.getTime() >= 12500 || world.hasStorm() || world.isThundering() || (e.getPlayer().hasPermission("hypersleep.bypass") && main.getConfig().getBoolean("permissions-enabled"))) {
			String reason;
			if (world.getTime() >= 12500) reason = "time";
			else reason = "weather";
			world.setTime(0);
			world.setThundering(false);
			world.setStorm(false);
			e.setCancelled(main.getConfig().getBoolean("stop-entering-bed"));
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(main.getConfig().getString(reason + "-changed-msg")).replace("%player%", player))));
		} else {
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(main.getConfig().getString("already-day-msg")).replace("%player%", player))));
		}
	}

}
