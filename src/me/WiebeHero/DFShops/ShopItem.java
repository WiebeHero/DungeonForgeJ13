package me.WiebeHero.DFShops;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class ShopItem {
	
	private UUID uuid;
	private double cost;
	private double sell;
	private int amount;
	
	public ShopItem(UUID uuid, double cost, double sell, ItemStack stack) {
		this.uuid = uuid;
		this.cost = cost;
		this.sell = sell;
	}
	
	public ShopItem(double cost, double sell, ItemStack stack) {
		this.uuid = UUID.randomUUID();
		this.cost = cost;
		this.sell = sell;
	}
	
	public ShopItem() {
		this.uuid = UUID.randomUUID();
		this.cost = 0.00;
		this.sell = 0.00;
	}
	
	public double getCostPrice() {
		return this.cost;
	}
	
	public void setCostPrice(double cost) {
		this.cost = cost;
	}
	
	public double getSellPrice() {
		return this.sell;
	}
	
	public void setSellPrice(double sell) {
		this.sell = sell;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public UUID getItemId() {
		return this.uuid;
	}
}
