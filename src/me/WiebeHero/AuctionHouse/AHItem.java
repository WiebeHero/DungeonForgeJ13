package me.WiebeHero.AuctionHouse;

import java.util.UUID;

public class AHItem {
	public UUID uuid;
	public double price;
	public long period;
	public String name;
	public boolean selling;
	public UUID unique;
	
	public AHItem(UUID uuid, double price, long period, String name){
		this.uuid = uuid;
		this.price = price;
		this.period = period;
		this.name = name;
		this.selling = true;
		this.unique = UUID.randomUUID();
	}
	
	public UUID getSellerUuid() {
		return this.uuid;
	}
	
	public void setSellerUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double money) {
		this.price = money;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public String getName() {
		return this.name;
	}
	
	public UUID getUniqueId() {
		return this.unique;
	}
	
	public long getEndPeriod() {
		return this.period;
	}
	
	public void setEndPeriod(long period) {
		this.period = period;
	}
	
	public long getTimeLeft() {
		long timeLeft = this.period - System.currentTimeMillis();
		if(timeLeft > 0) {
			return timeLeft;
		}
		return 0;
	}
}
