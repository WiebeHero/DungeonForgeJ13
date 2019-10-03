package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DFFaction {
	Methods method;
	public String facName;
	public ArrayList<FPlayer> memberList;
	public ArrayList<Chunk> chunkList;
	public ArrayList<UUID> invitedList;
	public ArrayList<DFFaction> allyList;
	public int facP;
	public double energy;
	public DFFaction(Player player) {
		this.memberList = new ArrayList<FPlayer>(Arrays.asList((FPlayer) player.getMetadata("FPlayer").get(0).value()));
		this.chunkList = new ArrayList<Chunk>();
		this.allyList = new ArrayList<DFFaction>();
		this.invitedList = new ArrayList<UUID>();
		this.facP = 0;
		this.energy = 0.00;
		player.setMetadata("FPlayer", new FixedMetadataValue(CustomEnchantments.getInstance(), new FPlayer(player.getUniqueId(), true)));
	}
	public DFFaction() {
		//Empty Constructor
	}
	public DFFaction getFaction(UUID uuid) {
		Player player = method.getOfflinePlayer(uuid);
		if(player != null) {
			FPlayer fPlayer = (FPlayer) player.getMetadata("FPlayer").get(0).value();
			DFFaction fac = fPlayer.getFaction();
			if(fac != null) {
				return fac;
			}
		}
		return null;
	}
	public String getName() {
		return this.facName;
	}
	public void setName(String facName) {
		this.facName = facName;
	}
	public ArrayList<FPlayer> getMemberList(){
		return this.memberList;
	}
	public void isMember(UUID uuid) {
		
	}
}
