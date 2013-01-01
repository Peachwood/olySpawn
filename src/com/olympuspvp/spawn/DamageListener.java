package com.olympuspvp.spawn;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DamageListener implements Listener{
	
	olySpawn olyspawn;
	protected static List<String> protect = new ArrayList<String>();
	
	
	protected DamageListener(olySpawn os){
		olyspawn = os;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageEvent e){
		Entity ent = e.getEntity();
		if(!(ent instanceof Player)) return;
		Player p = (Player) ent;
		if(e.getCause() == DamageCause.FALL){
			if(!protect.contains(p.getName())) return;
			p.sendMessage(olyspawn.tag + "You have lost spawn protection.");
			e.setCancelled(true);
			protect.remove(p.getName());
		}else{
			if(protect.contains(p.getName())) e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerTeleport(PlayerTeleportEvent e){
		Player p = e.getPlayer();
		if(e.getTo().equals(olyspawn.spawn)) return;
		if(protect.contains(p.getName())){
			protect.remove(p.getName());
			p.sendMessage(olyspawn.tag + "You lost spawn protection due to a teleport.");
		}
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE) return;
		Location l = p.getLocation();
		if(l.getBlockY() > 100 || l.getBlockY() < 90) return;
		if(l.getBlockX() > 100 || l.getBlockX() < 33) return;
		if(l.getBlockZ() > 320 || l.getBlockZ() < 250) return;
		if(protect.contains(p.getName())) return;
		protect.add(p.getName());
		p.sendMessage(olyspawn.tag + "You now have spawn protection.");
	}
	
}
