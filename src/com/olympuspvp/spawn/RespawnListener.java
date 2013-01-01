package com.olympuspvp.spawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;


public class RespawnListener implements Listener{
	
	olySpawn olyspawn;
	DamageListener damage;
	
	protected RespawnListener(olySpawn os, DamageListener dl){
		olyspawn = os;
		damage = dl;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e){
		e.setRespawnLocation(olyspawn.spawn);
		DamageListener.protect.add(e.getPlayer().getName());
		e.getPlayer().sendMessage(olyspawn.tag + "You now have Spawn Protection!");
	}
	
}
