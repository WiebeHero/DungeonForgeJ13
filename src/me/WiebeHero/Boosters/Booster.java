package me.WiebeHero.Boosters;

import java.util.UUID;

import me.WiebeHero.Boosters.BoosterTypes.BoosterType;

public class Booster {
	
	private UUID uuid;
	private UUID owner;
	private BoosterType type;
	private double multiplier;
	private long end;
	
	public Booster(UUID uuid, UUID owner, BoosterType type, double multiplier, long endTime) {
		this.uuid = uuid;
		this.type = type;
		this.owner = owner;
		this.multiplier = multiplier;
		this.end = endTime;
	}
	
	public Booster(UUID owner, BoosterType type, double multiplier, long endTime) {
		this.uuid = UUID.randomUUID();
		this.type = type;
		this.owner = owner;
		this.multiplier = multiplier;
		this.end = endTime;
	}
	
	public UUID getUniqueId() {
		return this.uuid;
	}
	
	public void setOwner(UUID uuid) {
		this.owner = uuid;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public void setBoosterType(BoosterType type) {
		this.type = type;
	}
	
	public BoosterType getType() {
		return this.type;
	}
	
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
	
	public double getMultiplier() {
		return this.multiplier;
	}
	
	public void setEndTime(long end) {
		this.end = end;
	}
	
	public long getEndTime() {
		return this.end;
	}
}
