package me.arttostog.afly.command;

import me.arttostog.afly.AFly;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Fly implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (SenderIsPlayer(sender, args)) {
				return true;
			}
		} else {
			if (SenderIsConsole(args)) {
				return true;
			}
		}

		sender.sendMessage(AFly.config.getString("Prefix") + AFly.config.getString("Error"));
		return true;
	}

	private static boolean SenderIsPlayer(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0 && player.hasPermission("afly.fly")) {
			return Fly_(player);
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("reload") && player.hasPermission("afly.reload")) {
			return ReloadConfiguration(sender);
		}

		if (args.length >= 1 && args.length <= 2 && player.hasPermission("afly.flyothers")) {
			Player target = getPlayer(args[0]);
			if (target != null) {
				return FlyOthers(player, target, args);
			}
		}
		return false;
	}

	private static boolean Fly_(Player player) {
		if(player.getAllowFlight()) {
			ChangeFlyStatus(player, false);
			player.sendMessage(AFly.config.getString("Prefix") + AFly.config.getString("Fly_disabled_message"));
			return true;
		}

		ChangeFlyStatus(player, true);
		player.sendMessage(AFly.config.getString("Prefix") + AFly.config.getString("Fly_enabled_message"));
		return true;
	}

	private static boolean ReloadConfiguration(CommandSender sender) {
		sender.sendMessage(AFly.config.getString("Prefix") + AFly.config.getString("Plugin_reload"));
		AFly.getPlugin(AFly.class).reloadConfig();
		AFly.config = JavaPlugin.getPlugin(AFly.class).getConfig();
		sender.sendMessage(AFly.config.getString("Prefix") + AFly.config.getString("Plugin_reload_done"));
		return true;
	}

	private static boolean FlyOthers(Player player, Player target, String[] args) {
		String FlyDisMesBy = AFly.config.getString("Fly_disabled_message_by").replaceAll("%sender%", player.getName());
		String FlyDisMesFor = AFly.config.getString("Fly_disabled_message_for").replaceAll("%target%", target.getName());
		String FlyEnMesBy = AFly.config.getString("Fly_enabled_message_by").replaceAll("%sender%", player.getName());
		String FlyEnMesFor = AFly.config.getString("Fly_enabled_message_for").replaceAll("%target%", target.getName());

		if (args.length == 1) {
			if(target.getAllowFlight()) {
				ChangeFlyStatus(target, false);
				target.sendMessage(AFly.config.getString("Prefix") + FlyDisMesBy);
				player.sendMessage(AFly.config.getString("Prefix") + FlyDisMesFor);
				return true;
			}

			ChangeFlyStatus(target, true);
			target.sendMessage(AFly.config.getString("Prefix") + FlyEnMesBy);
			player.sendMessage(AFly.config.getString("Prefix") + FlyEnMesFor);
			return true;
		}
		switch (args[1].toLowerCase()) {
			case "off": {
				ChangeFlyStatus(target, false);
				target.sendMessage(AFly.config.getString("Prefix") + FlyDisMesBy);
				player.sendMessage(AFly.config.getString("Prefix") + FlyDisMesFor);
				return true;
			}
			case "on": {
				ChangeFlyStatus(target, true);
				target.sendMessage(AFly.config.getString("Prefix") + FlyEnMesBy);
				player.sendMessage(AFly.config.getString("Prefix") + FlyEnMesFor);
				return true;
			}
		}
		return false;
	}

	public static boolean SenderIsConsole(String[] args) {
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			return ReloadConfiguration();
		}

		if (args.length >= 1 && args.length <= 2) {
			Player target = getPlayer(args[0]);
			if (target != null) {
				return FlyOthers(target, args);
			}
		}
		return false;
	}

	private static boolean ReloadConfiguration() {
		Bukkit.getLogger().info(AFly.config.getString("Prefix") + AFly.config.getString("Plugin_reload"));
		AFly.getPlugin(AFly.class).reloadConfig();
		AFly.config = JavaPlugin.getPlugin(AFly.class).getConfig();
		Bukkit.getLogger().info(AFly.config.getString("Prefix") + AFly.config.getString("Plugin_reload_done"));
		return true;
	}

	private static boolean FlyOthers(Player target, String[] args) {
		String FlyDisMesBy = AFly.config.getString("Fly_disabled_message_by").replaceAll("%sender%", "CONSOLE");
		String FlyDisMesFor = AFly.config.getString("Fly_disabled_message_for").replaceAll("%target%", target.getName());
		String FlyEnMesBy = AFly.config.getString("Fly_enabled_message_by").replaceAll("%sender%", "CONSOLE");
		String FlyEnMesFor = AFly.config.getString("Fly_enabled_message_for").replaceAll("%target%", target.getName());

		if (args.length == 1) {
			if(target.getAllowFlight()) {
				ChangeFlyStatus(target, false);
				target.sendMessage(AFly.config.getString("Prefix") + FlyDisMesBy);
				Bukkit.getLogger().info(AFly.config.getString("Prefix") + FlyDisMesFor);
				return true;
			}

			ChangeFlyStatus(target, true);
			target.sendMessage(AFly.config.getString("Prefix") + FlyEnMesBy);
			Bukkit.getLogger().info(AFly.config.getString("Prefix") + FlyEnMesFor);
			return true;
		}

		switch (args[1].toLowerCase()) {
			case "off": {
				ChangeFlyStatus(target, false);
				target.sendMessage(AFly.config.getString("Prefix") + FlyDisMesBy);
				Bukkit.getLogger().info(AFly.config.getString("Prefix") + FlyDisMesFor);
				return true;
			}
			case "on": {
				ChangeFlyStatus(target, true);
				target.sendMessage(AFly.config.getString("Prefix") + FlyEnMesBy);
				Bukkit.getLogger().info(AFly.config.getString("Prefix") + FlyEnMesFor);
				return true;
			}
		}
		return false;
	}

	private static Player getPlayer(String name) {
		Player player = null;
		for (Player ps: Bukkit.getOnlinePlayers()) {
			if (ps.getName().equals(name)) {
				player = ps;
				break;
			}
		}
		return player;
	}

	private static void ChangeFlyStatus(Player target, boolean status) {
		target.setAllowFlight(status);
		target.setFlying(status);
	}
}