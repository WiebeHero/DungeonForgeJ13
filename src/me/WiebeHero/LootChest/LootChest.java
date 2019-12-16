package me.WiebeHero.LootChest;

import java.util.UUID;

import org.bukkit.Location;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class LootChest {
	private LootChestManager lcManager = CustomEnchantments.getInstance().lootChestManager;
	public UUID uuid;
	public int tier;
	public int radius;
	public Location loc;
	public LootChest(UUID uuid, Location loc, int tier, int radius) {
		this.uuid = uuid;
		this.radius = radius;
		this.tier = tier;
		this.loc = loc;
		lcManager.add(uuid, this);
	}
	public LootChest(Location loc, int tier, int radius) {
		this.uuid = UUID.randomUUID();
		this.radius = radius;
		this.tier = tier;
		this.loc = loc;
		lcManager.add(this.uuid, this);
	}
	public LootChest() {
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
	public int getRadius() {
		return this.radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
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
