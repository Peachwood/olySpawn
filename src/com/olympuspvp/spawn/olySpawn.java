package com.olympuspvp.spawn;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;


public class olySpawn extends JavaPlugin{

	public FileConfiguration config;
	public File configFile = new File("plugins" + File.separator + "olySpawn" + File.separator + "config.yml");
	protected Location spawn;
	public final String tag = ChatColor.GOLD + "[" + ChatColor.GRAY + "olySpawn" + ChatColor.GOLD + "] " + ChatColor.YELLOW;

	@Override
	public void onEnable(){
		config = getConfig();
		if(!configFile.exists()){
			config.set("world", "world");
			config.set("x", 0);
			config.set("y", 0);
			config.set("z", 0);
			config.set("pitch", 0);
			config.set("yaw", 0);
			saveConfig();
		}spawn = new Location(Bukkit.getWorld(config.getString("world")), config.getDouble("x"), config.getDouble("y"), config.getDouble("z"), config.getInt("yaw"), config.getInt("pitch"));		
		System.out.println("[olySpawn] Spawn loaded at " + spawn.getBlockX() + ", " + spawn.getBlockY() + ", " + spawn.getBlockZ() + " in " + spawn.getWorld());
		DamageListener damage = new DamageListener(this);
		RespawnListener rs = new RespawnListener(this, damage);
		Bukkit.getPluginManager().registerEvents(rs, this);
		Bukkit.getPluginManager().registerEvents(damage, this);
	}

	public boolean onCommand(CommandSender s, Command cc, String c, String[] args){
		if(!(s instanceof Player)) return true;
		Player p = (Player) s;
		if(c.equalsIgnoreCase("setspawn")){
			if(!(p.getName().equals("CalDaBeast"))){
				p.sendMessage(tag + "Only CalDaBeast can perform this command.");
				return true;
			}
			Location newSpawn = p.getLocation();
			config.set("world", newSpawn.getWorld().getName());
			config.set("x", newSpawn.getX());
			config.set("y", newSpawn.getY());
			config.set("z", newSpawn.getZ());
			config.set("pitch", newSpawn.getPitch());
			config.set("yaw", newSpawn.getYaw());
			spawn = newSpawn;
			saveConfig();
			System.out.println("[olySpawn] Spawn has been set to " + spawn.getBlockX() + ", " + spawn.getBlockY() + ", " + spawn.getBlockZ() + " in " + spawn.getWorld().getName());
			p.sendMessage(tag + "Spawn has been set to " + spawn.getBlockX() + ", " + spawn.getBlockY() + ", " + spawn.getBlockZ() + " in " + spawn.getWorld().getName());
		}else if(c.equalsIgnoreCase("spawn")){
			p.teleport(spawn, TeleportCause.COMMAND);
		}return true; 
	}


}
