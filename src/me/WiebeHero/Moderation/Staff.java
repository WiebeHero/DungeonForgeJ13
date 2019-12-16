package me.WiebeHero.Moderation;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class Staff {
	private StaffManager manager = CustomEnchantments.getInstance().staffManager;
	public UUID uuid;
	public boolean staff;
	public boolean spawner;
	public boolean loot;
	public boolean vanish;
	public int staffRank;
	public ItemStack contents[];
	
	public Staff(UUID uuid, int rank) {
		this.uuid = uuid;
		this.staff = false;
		this.spawner = false;
		this.loot = false;
		this.vanish = false;
		this.staffRank = rank;
		manager.add(uuid, this);
	}
	public Staff() {
		
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
	
	public void setRank(int rank) {
		this.staffRank = rank;
	}
	public int getRank() {
		return this.staffRank;
	}
	
	public void saveInv(ItemStack items[]) {
		this.contents = items;
	}
	public ItemStack[] getInv() {
		return this.contents;
	}
}
