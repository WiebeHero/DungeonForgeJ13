package me.WiebeHero.Moderation;

import org.bukkit.inventory.ItemStack;

public class Staff {
	private boolean staff;
	private boolean godMode;
	private boolean spawner;
	private boolean loot;
	private boolean vanish;
	private boolean capturePoints;
	private ItemStack contents[];
	
	public Staff() {
		this.staff = false;
		this.spawner = false;
		this.loot = false;
		this.vanish = false;
		this.godMode = false;
		this.capturePoints = false;
	}
	
	public void switchStaffMode(boolean s) {
		this.staff = s;
	}
	public boolean getStaffMode() {
		return this.staff;
	}
	
	public void switchGodModeMode(boolean s) {
		this.godMode = s;
	}
	public boolean getGodMode() {
		return this.godMode;
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
	
	public void switchCapturePointMode(boolean s) {
		this.capturePoints = s;
	}
	public boolean getCapturePointMode() {
		return this.capturePoints;
	}
	
	public void saveInv(ItemStack items[]) {
		this.contents = items;
	}
	public ItemStack[] getInv() {
		return this.contents;
	}
}
