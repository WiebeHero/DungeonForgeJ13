package me.WiebeHero.Skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerJoinEvent;

public class DFPlayerRegister implements Listener{
	@EventHandler(priority = EventPriority.LOWEST)
	public void dfPlayerJoinRegister(PlayerJoinEvent event) {
		DFPlayer method = new DFPlayer();
		if(!method.containsPlayer(event.getPlayer())){
			method.addPlayer(event.getPlayer());
			DFPlayer dfPlayer = new DFPlayer().getPlayer(event.getPlayer());
			dfPlayer.loadPlayer();
		}
	}
	@EventHandler
	public void dfPlayerEntitySpawn(CreatureSpawnEvent event) {
		if(event.getSpawnReason() != SpawnReason.CUSTOM) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity ent = (LivingEntity) event.getEntity();
				DFPlayer method = new DFPlayer();
				method.addPlayer(ent);
			}
		}
	}
}
