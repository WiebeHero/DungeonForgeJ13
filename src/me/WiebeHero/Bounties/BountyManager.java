package me.WiebeHero.Bounties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BountyManager {
	
	private HashMap<UUID, ArrayList<Bounty>> bountyList;
	
	public BountyManager() {
		this.bountyList = new HashMap<UUID, ArrayList<Bounty>>();
	}
	
	public void addBounty(UUID playerId, Bounty bounty) {
		if(this.bountyList.containsKey(playerId)) {
			if(!this.hasBounty(playerId, bounty.getUUID())) {
				this.bountyList.get(playerId).add(bounty);
			}
		}
	}
	
	public boolean hasBounty(UUID playerId, UUID bountyId) {
		if(this.bountyList.containsKey(playerId)) {
			for(int i = 0; i < this.bountyList.get(playerId).size(); i++) {
				if(this.bountyList.get(playerId).get(i).getUUID().equals(bountyId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Bounty getBounty(UUID playerId, UUID bountyId) {
		if(this.bountyList.containsKey(playerId)) {
			for(int i = 0; i < this.bountyList.get(playerId).size(); i++) {
				if(this.bountyList.get(playerId).get(i).getUUID().equals(bountyId)) {
					return this.bountyList.get(playerId).get(i);
				}
			}
		}
		return null;
	}
	
	public ArrayList<Bounty> getBountyList(UUID playerId){
		if(this.bountyList.containsKey(playerId)) {
			return this.bountyList.get(playerId);
		}
		return null;
	}
}
