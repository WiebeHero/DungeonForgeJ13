package me.WiebeHero.Party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import me.WiebeHero.CustomEnchantments.CCT;

public class Party {
	
	private UUID uuid;
	private ArrayList<UUID> invited;
	private HashMap<UUID, Integer> partyMembers;
	
	public Party() {
		this.uuid = UUID.randomUUID();
		this.partyMembers = new HashMap<UUID, Integer>();
		this.invited = new ArrayList<UUID>();
	}
	
	public Party(UUID uuid, int rank, UUID... uuids) {
		this.uuid = UUID.randomUUID();
		this.partyMembers = new HashMap<UUID, Integer>();
		this.invited = new ArrayList<UUID>();
		this.partyMembers.put(uuid, rank);
		for(UUID id : uuids) {
			this.partyMembers.put(id, 1);
		}
	}
	
	public Party(UUID uuid, int rank) {
		this.uuid = UUID.randomUUID();
		this.partyMembers = new HashMap<UUID, Integer>();
		this.invited = new ArrayList<UUID>();
		this.partyMembers.put(uuid, rank);
	}
	
	public Party(HashMap<UUID, Integer> partyMembers) {
		this.uuid = UUID.randomUUID();
		this.partyMembers = partyMembers;
		this.invited = new ArrayList<UUID>();
	}
	
	public UUID getUniqueId() {
		return this.uuid;
	}
	
	public HashMap<UUID, Integer> getPartyMembers(){
		return this.partyMembers;
	}
	
	public void setPartyMembers(HashMap<UUID, Integer> partyMembers){
		this.partyMembers = partyMembers;
	}
	
	public void addPartyMember(UUID uuid, int rank) {
		this.partyMembers.put(uuid, rank);
	}
	
	public void addInvitedPartyMember(UUID uuid) {
		if(!this.invited.contains(uuid)) {
			this.invited.add(uuid);
		}
	}
	
	public void removeInvitedPartyMember(UUID uuid) {
		if(this.invited.contains(uuid)) {
			this.invited.remove(uuid);
		}
	}
	
	public boolean containsInvitedPartyMember(UUID uuid) {
		return this.invited.contains(uuid);
	}
	
	public void removePartyMember(UUID uuid) {
		if(this.partyMembers.containsKey(uuid)) {
			this.partyMembers.remove(uuid);
		}
	}
	
	public boolean containsPartyMember(UUID uuid) {
		return this.partyMembers.containsKey(uuid);
	}
	
	public int getRank(UUID uuid) {
		if(this.partyMembers.containsKey(uuid)) {
			return this.partyMembers.get(uuid);
		}
		return 1;
	}
	
	public String getDisplay(UUID uuid) {
		if(this.getRank(uuid) == 1) {
			return new CCT().colorize("Member");
		}
		else if(this.getRank(uuid) == 2) {
			return new CCT().colorize("Officer");
		}
		else if(this.getRank(uuid) == 3) {
			return new CCT().colorize("Leader");
		}
		return "";
	}
	
	public UUID getLeader() {
		for(Entry<UUID, Integer> entry : this.partyMembers.entrySet()) {
			if(entry.getValue() == 3) {
				return entry.getKey();
			}
		}
		return null;
	}
	
}
