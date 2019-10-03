package me.WiebeHero.Factions;

import java.util.UUID;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class FPlayer {
	public UUID uuid;
	public int rank;
	public FPlayer(UUID uuid, boolean creator) {
		if(creator) {
			this.uuid = uuid;
			this.rank = 4;
		}
		else {
			this.uuid = uuid;
			this.rank = 1;
		}
	}
	public FPlayer() {
		//Empty Constructor
	}
	public UUID getUUID() {
		return this.uuid;
	}
	public int getRank() {
		return this.rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public DFFaction getFaction() {
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(fac.getMemberList().contains(this)) {
				return fac;
			}
		}
		return null;
	}
	public boolean inAFaction() {
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(fac.getMemberList().contains(this)) {
				return true;
			}
		}
		return false;
	}
}
