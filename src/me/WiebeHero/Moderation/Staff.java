package me.WiebeHero.Moderation;

import org.bukkit.inventory.ItemStack;

public class Staff {
	public boolean staff;
	public boolean spawner;
	public boolean loot;
	public boolean vanish;
	public ItemStack contents[];
	
	public Staff() {
		this.staff = false;
		this.spawner = false;
		this.loot = false;
		this.vanish = false;
	}
	
	public void switchStaffMode(boolean s) {
		this.staff = s;
	}
	public boolean getStaffMode() {
		return this.staff;
	}
	
	public void switchSpawnerMode(boolean s) {
		this.spawner = s;
	}
	public boolean getSpawnerMode() {
		return this.spawner;
	}
	
	public void switchLootMode(boolean s) {
		this.loot = s;
	}
	public boolean getLootMode() {
		return this.loot;
	}
	
	public void switchVanishMode(boolean s) {
		this.vanish = s;
	}
	public boolean getVanishMode() {
		return this.vanish;
	}
	
	public void saveInv(ItemStack items[]) {
		this.contents = items;
	}
	public ItemStack[] getInv() {
		return this.contents;
	}
}
