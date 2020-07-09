package me.WiebeHero.FlyTicket;

import java.util.UUID;

public class FlyTicket {
	
	private UUID owner;
	private long flyTime;
	
	public FlyTicket(UUID owner, long flyTime) {
		this.owner = owner;
		this.flyTime = flyTime;
	}
	
	public FlyTicket(UUID owner) {
		this.owner = owner;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public long getFlyTime() {
		return this.flyTime;
	}
	
	public void setFlyTime(long flyTime) {
		this.flyTime = flyTime;
	}
}
