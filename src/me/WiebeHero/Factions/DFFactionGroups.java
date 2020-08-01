package me.WiebeHero.Factions;

import java.util.ArrayList;

import com.google.common.base.Enums;

import javafx.util.Pair;

public class DFFactionGroups {
	
	public FactionGroup getIfGroupPresent(String name) {
		return Enums.getIfPresent(FactionGroup.class, name).orNull();
	}
	
	public FactionGroup getRankFactionGroup(int rank) {
		switch(rank) {
			case 1:
				return FactionGroup.RECRUIT;
			case 2:
				return FactionGroup.MEMBER;
			case 3:
				return FactionGroup.OFFICER;
			case 4:
				return FactionGroup.LEADER;
			default:
				return FactionGroup.RECRUIT;	
		}
	}
	
	public enum FactionGroup{
		RECRUIT(1, "Recruit"),
		MEMBER(2, "Member"),
		OFFICER(3, "Officer"),
		LEADER(4, "Leader");
		private int rank;
		private String display;
		private FactionGroup(int rank, String display) {
			this.rank = rank;
			this.display = display;
		}
		public int getRank() {
			return this.rank;
		}
		public String getDisplay() {
			return this.display;
		}
		public ArrayList<Pair<FactionPermission, Boolean>> getDefaultPerms(FactionGroup group){
			switch(group) {
				case RECRUIT:
					ArrayList<Pair<FactionPermission, Boolean>> perms = new ArrayList<Pair<FactionPermission, Boolean>>();
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_DOORS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PRESS_BUTTONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FLICK_LEVERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_PRESSURE_PLATE, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_TRIPWIRE_HOOKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_CONTAINERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.BREAK_BLOCKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PLACE_BLOCKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PROMOTE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.DEMOTE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.INVITE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.KICK_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.SET_FACTION_HOMES, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.USE_FACTION_HOMES, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.CLAIM_FACTION_CHUNKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNCLAIM_FACTION_CHUNKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ALLY_FACTIONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNALLY_FACTIONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FACTION_VAULT_ACCES, false));
					return perms;
				case MEMBER:
					perms = new ArrayList<Pair<FactionPermission, Boolean>>();
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_DOORS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PRESS_BUTTONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FLICK_LEVERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_PRESSURE_PLATE, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_TRIPWIRE_HOOKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_CONTAINERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.BREAK_BLOCKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PLACE_BLOCKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PROMOTE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.DEMOTE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.INVITE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.KICK_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.SET_FACTION_HOMES, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.USE_FACTION_HOMES, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.CLAIM_FACTION_CHUNKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNCLAIM_FACTION_CHUNKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ALLY_FACTIONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNALLY_FACTIONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FACTION_VAULT_ACCES, false));
					return perms;
				case OFFICER:
					perms = new ArrayList<Pair<FactionPermission, Boolean>>();
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_DOORS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PRESS_BUTTONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FLICK_LEVERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_PRESSURE_PLATE, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_TRIPWIRE_HOOKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_CONTAINERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.BREAK_BLOCKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PLACE_BLOCKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PROMOTE_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.DEMOTE_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.INVITE_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.KICK_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.SET_FACTION_HOMES, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.USE_FACTION_HOMES, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.CLAIM_FACTION_CHUNKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNCLAIM_FACTION_CHUNKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ALLY_FACTIONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNALLY_FACTIONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FACTION_VAULT_ACCES, true));
					return perms;
				case LEADER:
					perms = new ArrayList<Pair<FactionPermission, Boolean>>();
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_DOORS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PRESS_BUTTONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FLICK_LEVERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_PRESSURE_PLATE, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_TRIPWIRE_HOOKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_CONTAINERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.BREAK_BLOCKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PLACE_BLOCKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PROMOTE_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.DEMOTE_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.INVITE_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.KICK_FACTION_MEMBERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.SET_FACTION_HOMES, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.USE_FACTION_HOMES, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.CLAIM_FACTION_CHUNKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNCLAIM_FACTION_CHUNKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ALLY_FACTIONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNALLY_FACTIONS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FACTION_VAULT_ACCES, true));
					return perms;
				default:
					perms = new ArrayList<Pair<FactionPermission, Boolean>>();
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_DOORS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PRESS_BUTTONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FLICK_LEVERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_PRESSURE_PLATE, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ACTIVATE_TRIPWIRE_HOOKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.OPEN_CONTAINERS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.BREAK_BLOCKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PLACE_BLOCKS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.PROMOTE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.DEMOTE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.INVITE_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.KICK_FACTION_MEMBERS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.SET_FACTION_HOMES, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.USE_FACTION_HOMES, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.CLAIM_FACTION_CHUNKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNCLAIM_FACTION_CHUNKS, true));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.ALLY_FACTIONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.UNALLY_FACTIONS, false));
					perms.add(new Pair<FactionPermission, Boolean>(FactionPermission.FACTION_VAULT_ACCES, false));
					return perms;
			}
			
		}
	}
	
	public FactionPermission getIfPermissionPresent(String name) {
		return Enums.getIfPresent(FactionPermission.class, name).orNull();
	}
	
	public enum FactionPermission{
		OPEN_DOORS("Open Doors"),
		PRESS_BUTTONS("Press Buttons"),
		FLICK_LEVERS("Flick Levers"),
		ACTIVATE_PRESSURE_PLATE("Activate Pressure Plates"),
		ACTIVATE_TRIPWIRE_HOOKS("Activate Tripwire Hooks"),
		OPEN_CONTAINERS("Open Containers"),
		BREAK_BLOCKS("Break Blocks"),
		PLACE_BLOCKS("Place Blocks"),
		PROMOTE_FACTION_MEMBERS("Promote Faction Members"),
		DEMOTE_FACTION_MEMBERS("Demote Faction Members"),
		INVITE_FACTION_MEMBERS("Invite Faction Members"),
		KICK_FACTION_MEMBERS("Kick Faction Members"),
		SET_FACTION_HOMES("Set Faction Homes"),
		USE_FACTION_HOMES("Use Faction Homes"),
		CLAIM_FACTION_CHUNKS("Claim Faction Chunks"),
		UNCLAIM_FACTION_CHUNKS("Unclaim Faction Chunks"),
		ALLY_FACTIONS("Ally Factions"),
		UNALLY_FACTIONS("Unally Factions"),
		FACTION_VAULT_ACCES("Faction Vault Acces");
		
		private String display;
		private FactionPermission(String display) {
			this.display = display;
		}
		public String getDisplay() {
			return this.display;
		}
	}
}
