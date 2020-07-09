package me.WiebeHero.Party;

import java.util.ArrayList;
import java.util.UUID;

public class PartyManager {
	
	private ArrayList<Party> partyList;
	
	public PartyManager(ArrayList<Party> partyList) {
		this.partyList = partyList;
	}
	
	public PartyManager() {
		this.partyList = new ArrayList<Party>();
	}
	
	public ArrayList<Party> getPartyList(){
		return this.partyList;
	}
	
	public void setPartyList(ArrayList<Party> partyList) {
		this.partyList = partyList;
	}
	
	public void addParty(Party party) {
		if(!this.partyList.contains(party)) {
			this.partyList.add(party);
		}
	}
	
	public void removeParty(Party party) {
		if(this.partyList.contains(party)) {
			this.partyList.remove(party);
		}
	}
	
	public boolean conatinsParty(UUID uuid) {
		for(Party party : this.partyList) {
			if(party.getUniqueId().equals(uuid)) {
				return true;
			}
		}
		return false;
	}
	
	public void removeParty(UUID uuid) {
		for(Party party : this.partyList) {
			if(party != null) {
				if(party.getUniqueId().equals(uuid)) {
					this.partyList.remove(party);
				}
			}
		}
	}
	
	public Party getParty(UUID uuid) {
		for(Party party : this.partyList) {
			if(party.getUniqueId().equals(uuid)) {
				return party;
			}
		}
		return null;
	}
	
	public Party getPartyByPlayer(UUID uuid) {
		for(Party party : this.partyList) {
			if(party.getPartyMembers().containsKey(uuid)) {
				return party;
			}
		}
		return null;
	}
}
