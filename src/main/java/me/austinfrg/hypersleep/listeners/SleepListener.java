package me.austinfrg.hypersleep.listeners;

import me.austinfrg.hypersleep.HyperSleep;
import me.austinfrg.hypersleep.utils.Chat;
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
			String reason = (world.getTime() >= 12500) ? "time" : "weather";
			world.setTime(0);
			world.setThundering(false);
			world.setStorm(false);
			e.setCancelled(main.getConfig().getBoolean("stop-entering-bed"));
			Chat.broadcast(Objects.requireNonNull(Objects.requireNonNull(main.getConfig().getString(reason + "-changed-msg")).replace("%player%", player)));
		} else {
			Chat.tell(e.getPlayer(), Objects.requireNonNull(main.getConfig().getString("already-day-msg")).replace("%player%", player));
		}
	}

}
