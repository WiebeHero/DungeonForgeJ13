package me.WiebeHero.DFPlayerPackage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerJoinEvent;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;

public class DFPlayerRegister implements Listener{
	private DFPlayerManager dfManager;
	public DFPlayerRegister(DFPlayerManager dfManager) {
		this.dfManager = dfManager;
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void dfPlayerJoinRegister(PlayerJoinEvent event) {
		if(!dfManager.contains(event.getPlayer())){
			dfManager.addEntity(event.getPlayer());
		}
	}
	@EventHandler
	public void dfPlayerEntitySpawn(CreatureSpawnEvent event) {
		if(event.getSpawnReason() != SpawnReason.CUSTOM) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity ent = (LivingEntity) event.getEntity();
				Entity e = NBTInjector.patchEntity(ent);
				NBTCompound comp = NBTInjector.getNbtData(e);
				comp.setString("SpawnerUUID", "");
				comp.setInteger("Level", 1);
				comp.setInteger("Tier", 0);
				dfManager.addEntity(ent);
			}
		}
	}
}
