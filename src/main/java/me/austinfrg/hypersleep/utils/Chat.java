package me.austinfrg.hypersleep.utils;

import me.austinfrg.hypersleep.HyperSleep;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Chat {

	public static void log(String... messages) {
		for (final String message : messages)
			log(message);
	}

	public static void log(String messages) {
		tell(Bukkit.getConsoleSender(), "[" + HyperSleep.getInstance().getName() + "] " + messages);
	}

	public static void tell(CommandSender toWhom, String... messages) {
		for (final String message : messages)
			tell(toWhom, message);
	}

	public static void tell(CommandSender toWhom, List<String> messages) {
		for (final String message : messages)
			tell(toWhom, message);
	}

	public static void tell(CommandSender toWhom, String message) {
		if (!message.isEmpty())
			toWhom.sendMessage(colorize(message));
	}

	/**
	 * Sends a clickable message to a player that copies text when clicked.
	 *
	 * @param message The clickable message!
	 * @param copyText The text that can be copied
	 * @param player  player to send to.
	 */
	public static void sendCopyMessage(Player player, String message, String copyText) {
		TextComponent component = new TextComponent(TextComponent.fromLegacyText(colorize(message)));
		component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copyText));
		component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to Copy")));

		player.spigot().sendMessage(component);
	}

	public static void broadcast(String message) {
		Bukkit.broadcastMessage(colorize(message));
	}

	public static void broadcast(String... messages) {
		for (final String message : messages) {
			broadcast(message);
		}
	}

	public static void clearChat() {
		broadcast(StringUtils.repeat(" \n", 100));
	}

	public static void clearChat(Player player) {
		tell(player, StringUtils.repeat(" \n", 100));
	}

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static List<String> colorizeList(List<String> list) {
		List<String> temp = new ArrayList<>();
		for (String s : list)
			temp.add(colorize(s));
		return temp;
	}

	public static String strip(String text) {
		return ChatColor.stripColor(colorize(text));
	}

	public static List<String> strip(List<String> list) {
		List<String> temp = new ArrayList<>();
		for (String s : colorizeList(list)) {
			temp.add(ChatColor.stripColor(s));
		}
		return temp;
	}

	public static int getLength(String text, boolean ignoreColorCodes) {
		return ignoreColorCodes ? strip(text).length() : text.length();
	}

}
