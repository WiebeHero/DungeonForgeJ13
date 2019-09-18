package me.WiebeHero.DungeonInstances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DungeonParty {
	public HashMap<UUID, Integer> players;
	public ArrayList<UUID> invited;
	public int maxMembers = 5;
	public DungeonParty(Player player) {
		this.players = new HashMap<UUID, Integer>();
		this.players.put(player.getUniqueId(), 2);
	}
	public DungeonParty() {
		//Plain Constructor
	}
	public DungeonParty getDungeonParty(Player player) {
		if(CustomEnchantments.getInstance().dfDungeonParty.containsKey(player.getUniqueId())) {
			return CustomEnchantments.getInstance().dfDungeonParty.get(player.getUniqueId());
		}
		else {
			return null;
		}
	}
	public boolean isInAParty(Player player) {
		for(DungeonParty party : CustomEnchantments.getInstance().dfDungeonParty.values()) {
			if(party.getPartyMemberList().containsKey(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	public void createParty(Player player) {
		if(!CustomEnchantments.getInstance().dfDungeonParty.containsKey(player.getUniqueId())) {
			CustomEnchantments.getInstance().dfDungeonParty.put(player.getUniqueId(), new DungeonParty(player));
		}
	}
	public void deleteParty(Player player) {
		if(CustomEnchantments.getInstance().dfDungeonParty.containsKey(player.getUniqueId())) {
			CustomEnchantments.getInstance().dfDungeonParty.remove(player.getUniqueId());
		}
	}
	public HashMap<UUID, Integer> getPartyMemberList(){
		return this.players;
	}
	public ArrayList<UUID> getPartyInvitedList(){
		return this.invited;
	}
	public int getMaxMembers() {
		return this.maxMembers;
	}
	public int getRank(Player player){
		return this.getPartyMemberList().get(player.getUniqueId());
	}
	public void setRank(Player player, int rank){
		this.getPartyMemberList().put(player.getUniqueId(), rank);
	}
	public Player getLeader() {
		for(UUID uuid : this.getPartyMemberList().keySet()) {
			if(this.getPartyMemberList().get(uuid) == 2) {
				return Bukkit.getPlayer(uuid);
			}
		}
		return null;
	}
	public void addInvited(Player returner) {
		invited.add(returner.getUniqueId());
	}
	public void removeInvited(Player returner) {
		invited.remove(returner.getUniqueId());
	}
	public boolean isInvited(Player returner) {
		if(invited.contains(returner.getUniqueId())) {
			return true;
		}
		return false;
	}
	public void addMember(Player player) {
		this.getPartyMemberList().put(player.getUniqueId(), 1);
	}
	public void removeMember(Player player) {
		this.getPartyMemberList().remove(player.getUniqueId());
	}
	public boolean isMember(Player player) {
		if(this.getPartyMemberList().containsKey(player.getUniqueId())) {
			return true;
		}
		return false;
	}
	public void promote(Player player) {
		this.getPartyMemberList().put(this.getLeader().getUniqueId(), 1);
		this.getPartyMemberList().put(player.getUniqueId(), 2);
	}
	public void leaveParty(Player player) {
		this.getPartyMemberList().remove(player.getUniqueId());
	}
}
