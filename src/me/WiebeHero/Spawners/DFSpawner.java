package me.WiebeHero.Spawners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DFSpawner {
	private DFSpawnerManager spManager = CustomEnchantments.getInstance().spawnerManager;
	public UUID uuid;
	public int tier;
	public Location loc;
	public EntityType type;
	public DFSpawner(UUID uuid, Location loc, int tier, EntityType type) {
		this.uuid = uuid;
		this.type = type;
		this.tier = tier;
		this.loc = loc;
		spManager.add(uuid, this);
	}
	public DFSpawner(Location loc, EntityType type, int tier) {
		this.uuid = UUID.randomUUID();
		this.type = type;
		this.tier = tier;
		this.loc = loc;
		spManager.add(this.uuid, this);
	}
	public DFSpawner() {
		//Method to execute other methods
	}
	//---------------------------------------------------------
	//Tier Handler
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
	//Id Handler
	//---------------------------------------------------------
	public EntityType getType() {
		return this.type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
	//---------------------------------------------------------
	//Tier Handler
	//---------------------------------------------------------
	public Location getLocation() {
		return this.loc;
	}
	public void setLocation(Location loc) {
		this.loc = loc;
	}
}
