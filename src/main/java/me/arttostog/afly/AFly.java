package me.arttostog.afly;

import me.arttostog.afly.command.Fly;
import me.arttostog.afly.event.onTabComplete_;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AFly extends JavaPlugin {
	public static FileConfiguration config;

	@Override
	public void onEnable () {
		if (!new File("plugins/AFly/config.yml").exists()) {
			this.saveResource("config.yml", false);
		}
		config = this.getConfig();
		this.getCommand("fly").setExecutor(new Fly());
		this.getCommand("fly").setTabCompleter(new onTabComplete_());
		this.getLogger().info("§aAfly successfully enabled!");
	}

	@Override
	public void onDisable() {
		this.getLogger().info("§cAfly successfully disabled!");
	}
}
