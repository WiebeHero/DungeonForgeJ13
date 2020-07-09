package me.WiebeHero.DungeonInstances;

import java.util.UUID;

import org.bukkit.Location;

public class DungeonCheckpoint {
	
	private UUID uuid;
	private Location point;
	
	public DungeonCheckpoint(Location point) {
		this.point = point;
		this.uuid = UUID.randomUUID();
	}
	
	public Location getCheckpointLocation() {
		return this.point;
	}
	
	public void setCheckpointLocation(Location point) {
		this.point = point;
	}
	
	public UUID getUuid() {
		return this.uuid;
	}
	
}
