package me.WiebeHero.Factions;

import java.util.UUID;

public class DFFactionPlayer {
	
	private UUID facId;
	private int rank;
	
	public DFFactionPlayer() {
		this.facId = null;
		this.rank = 1;
	}
	public DFFactionPlayer(UUID facId) {
		this.facId = facId;
		this.rank = 1;
	}
	public DFFactionPlayer(int rank) {
		this.facId = null;
		this.rank = rank;
	}
	public DFFactionPlayer(UUID facId, int rank) {
		this.facId = facId;
		this.rank = rank;
	}
	
	public void setFactionId(UUID facId) {
		this.facId = facId;
	}
	
	public boolean isInFaction() {
		return this.facId != null;
	}
	
	public UUID getFactionId() {
		return this.facId;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getRank() {
		return this.rank;
	}
}
