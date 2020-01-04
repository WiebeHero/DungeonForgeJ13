package me.WiebeHero.MoreStuff;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DisableThings implements Listener{
	@EventHandler
	public void disableBoats(EntitySpawnEvent event) {
		if(event.getEntityType() == EntityType.BOAT) {
			event.setCancelled(true);
		}
	}
}
