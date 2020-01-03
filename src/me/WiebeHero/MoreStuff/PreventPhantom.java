package me.WiebeHero.MoreStuff;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class PreventPhantom implements Listener{
	@EventHandler
	public void prevent(EntitySpawnEvent event) {
		if(event.getEntityType() == EntityType.PHANTOM) {
			event.setCancelled(true);
		}
	}
}
