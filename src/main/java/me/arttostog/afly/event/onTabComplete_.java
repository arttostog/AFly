package me.arttostog.afly.event;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onTabComplete_ implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) return null;
		Player player = (Player) sender;
		ArrayList<String> arguments = new ArrayList<>();

		if (cmd.getName().equals("fly")) {
			if (player.hasPermission("afly.flyothers")) {
				if (args.length == 1) {
					arguments.addAll(getOnlinePlayerNames());
				}
				if (args.length == 2 && !args[0].equals("reload")) arguments.addAll(Arrays.asList("on", "off"));
			}
			if (player.hasPermission("afly.reload") && args.length == 1) {
				arguments.add("reload");
			}
		}

		return arguments;
	}

	private static List<String> getOnlinePlayerNames() {
		ArrayList<String> names = new ArrayList<>();

		for (Player p: Bukkit.getOnlinePlayers()) {
			names.add(p.getName());
		}

		return names;
	}
}
