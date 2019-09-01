package me.WiebeHero.Spawners;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DFSpawner {
	int id;
	int tier;
	Location loc;
	public DFSpawner(Player player, int tier, int id) {
		if(player != null) {
			this.id = id;
			this.tier = tier;
			this.loc = player.getLocation();
		}
	}
	public DFSpawner() {
		//Method to execute other methods
	}
	
	//---------------------------------------------------------
	//Saving and loading Spawners
	//---------------------------------------------------------
	
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
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
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
