package me.WiebeHero.Spawners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class DFSpawner {
	public UUID uuid;
	public int tier;
	public Location loc;
	public EntityType type;
	public boolean canSpawn;
	public DFSpawner(UUID uuid, Location loc, int tier, EntityType type) {
		this.uuid = uuid;
		this.type = type;
		this.tier = tier;
		this.loc = loc;
		this.canSpawn = true;
	}
	public DFSpawner(Location loc, EntityType type, int tier) {
		this.uuid = UUID.randomUUID();
		this.type = type;
		this.tier = tier;
		this.loc = loc;
		this.canSpawn = true;
	}
	public DFSpawner() {
		//Method to execute other methods
	}
	//---------------------------------------------------------
	//UUID Handler
	//---------------------------------------------------------
	public UUID getUUID() {
		return this.uuid;
	}
	public void setId(UUID uuid) {
		this.uuid = uuid;
	}
	//---------------------------------------------------------
	//Tier Handler
	//---------------------------------------------------------
	public int getTier() {
		return this.tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	//---------------------------------------------------------
	//Type Handler
	//---------------------------------------------------------
	public EntityType getType() {
		return this.type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
	//---------------------------------------------------------
	//Location Handler Handler
	//---------------------------------------------------------
	public Location getLocation() {
		return this.loc;
	}
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	//---------------------------------------------------------
	//Can Spawner Handler
	//---------------------------------------------------------
	public boolean getCanSpawn() {
		return this.canSpawn;
	}
	public void setCanSpawn(boolean canSpawn) {
		this.canSpawn = canSpawn;
	}
}
