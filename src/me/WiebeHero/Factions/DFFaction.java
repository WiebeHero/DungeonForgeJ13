package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DFFaction {
	private UUID facId;
	private DFFactionManager facManager;
	private DFFactionPlayerManager memberManager;
	private String facName;
	private ArrayList<Long> chunkList;
	private ArrayList<UUID> invitedList;
	private ArrayList<UUID> invitedAllyList;
	private ArrayList<UUID> allyList;
	private Location facHome;
	private int facP;
	private double bank;
	private double energy;
	private ItemStack banner;
	public DFFaction(String facName, Player p, DFFactionManager facManager, DFFactionPlayerManager memberManager) {
		this.facId = UUID.randomUUID();
		this.chunkList = new ArrayList<Long>();
		this.allyList = new ArrayList<UUID>();
		this.invitedAllyList = new ArrayList<UUID>();
		this.invitedList = new ArrayList<UUID>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
		this.bank = 0.00;
		this.facManager = facManager;
		this.memberManager = memberManager;
		this.banner = new ItemStack(Material.BLACK_BANNER);
		DFFactionPlayer df = memberManager.getFactionPlayer(p.getUniqueId());
		df.setFactionId(this.getFactionId());
		df.setRank(4);
	}
	public DFFaction(String facName, DFFactionManager facManager, DFFactionPlayerManager memberManager) {
		this.facId = UUID.randomUUID();
		this.chunkList = new ArrayList<Long>();
		this.allyList = new ArrayList<UUID>();
		this.invitedAllyList = new ArrayList<UUID>();
		this.invitedList = new ArrayList<UUID>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
		this.bank = 0.00;
		this.facManager = facManager;
		this.memberManager = memberManager;
		this.banner = new ItemStack(Material.BLACK_BANNER);
	}
	public DFFaction(String facName, UUID facId, DFFactionManager facManager, DFFactionPlayerManager memberManager) {
		this.facId = facId;
		this.chunkList = new ArrayList<Long>();
		this.allyList = new ArrayList<UUID>();
		this.invitedAllyList = new ArrayList<UUID>();
		this.invitedList = new ArrayList<UUID>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
		this.bank = 0.00;
		this.facManager = facManager;
		this.memberManager = memberManager;
		this.banner = new ItemStack(Material.BLACK_BANNER);
	}
	public void addMember(Player player) {
		if(memberManager.contains(player.getUniqueId())){
			DFFactionPlayer df = memberManager.getFactionPlayer(player.getUniqueId());
			df.setFactionId(this.getFactionId());
			df.setRank(1);
		}
	}
	public void addMember(UUID uuid) {
		if(memberManager.contains(uuid)){
			DFFactionPlayer df = memberManager.getFactionPlayer(uuid);
			df.setFactionId(this.getFactionId());
			df.setRank(1);
		}
	}
	public void addMember(UUID uuid, DFFactionPlayer facP) {
		if(memberManager.contains(uuid)){
			DFFactionPlayer df = facP;
			df.setFactionId(this.getFactionId());
		}
	}
	public void removeMember(Player player) {
		if(memberManager.contains(player.getUniqueId())){
			DFFactionPlayer df = memberManager.getFactionPlayer(player.getUniqueId());
			df.setFactionId(null);
			df.setRank(1);
		}
	}
	public void removeMember(UUID uuid) {
		if(memberManager.contains(uuid)){
			DFFactionPlayer df = memberManager.getFactionPlayer(uuid);
			df.setFactionId(null);
			df.setRank(1);
		}
	}
	public void promoteMember(UUID uuid) {
		DFFactionPlayer df = this.memberManager.getFactionPlayer(uuid);
		df.setRank(df.getRank() + 1);
	}
	public void demoteMember(UUID uuid) {
		DFFactionPlayer df = this.memberManager.getFactionPlayer(uuid);
		df.setRank(df.getRank() - 1);
	}
	public UUID getFactionId() {
		return this.facId;
	}
	
	public ArrayList<Long> getChunkList(){
		return this.chunkList;
	}
	
	public void setAllyList(ArrayList<UUID> allyList) {
		this.allyList = allyList;
	}
	
	public ArrayList<UUID> getAllyList() {
		return this.allyList;
	}
	
	public ArrayList<UUID> getInvitedList() {
		return this.invitedList;
	}
	
	public void setBanner(ItemStack banner) {
		this.banner = banner;
	}
	
	public ItemStack getBanner() {
		return this.banner;
	}
	
	public String getName() {
		return this.facName;
	}
	public void setName(String facName) {
		this.facName = facName;
	}
	
	public boolean isMember(UUID uuid) {
		if(this.memberManager.contains(uuid)) {
			DFFactionPlayer df = this.memberManager.getFactionPlayer(uuid);
			if(df.getFactionId() != null) {
				if(df.getFactionId().equals(this.getFactionId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setFactionHome(Location loc) {
		this.facHome = loc;
	}
	public Location getFactionHome() {
		return this.facHome;
	}
	
	public void addInvite(UUID uuid) {
		this.invitedList.add(uuid);
	}
	public void removeInvite(UUID uuid) {
		this.invitedList.remove(uuid);
	}
	public boolean isInvited(UUID uuid) {
		if(this.invitedList.contains(uuid)) {
			return true;
		}
		return false;
	}
	
	public void addChunk(Chunk c) {
		this.chunkList.add(c.getChunkKey());
	}
	public void addChunk(long key) {
		this.chunkList.add(key);
	}
	public void removeChunk(Chunk c) {
		this.chunkList.remove(c.getChunkKey());
	}
	public void removeChunk(long key) {
		this.chunkList.remove(key);
	}
	public void clearChunks() {
		this.chunkList.clear();
	}
	public boolean isInChunk(Player player) {
		long c = player.getChunk().getChunkKey();
		if(this.getChunkList().contains(c)) {
			return true;
		}
		return false;
	}
	
	public boolean isInChunk(Location loc) {
		long c = loc.getChunk().getChunkKey();
		if(this.getChunkList().contains(c)) {
			return true;
		}
		return false;
	}
	
	public boolean isAlly(String fac) {
		DFFaction faction = facManager.getFaction(fac);
		return this.allyList.contains(faction.getFactionId());
	}
	
	public boolean isAlly(UUID uuid) {
		return this.allyList.contains(uuid);
	}
	
	public void addAlly(UUID uuid) {
		this.allyList.add(uuid);
	}
	public void removeAlly(UUID uuid) {
		this.allyList.remove(uuid);
	}
	public boolean isInvitedAlly(UUID uuid) {
		if(this.invitedAllyList.contains(uuid)) {
			return true;
		}
		return false;
	}
	public void addInvitedAlly(UUID uuid) {
		this.invitedAllyList.add(uuid);
	}
	public void removeInvitedAlly(UUID uuid) {
		this.invitedAllyList.remove(uuid);
	}
	
	public int getFactionPoints() {
		return this.facP;
	}
	public void setFactionPoints(int facP) {
		this.facP = facP;
	}
	public void addFactionPoints(int facP) {
		this.facP += facP;
	}
	public void removeFactionPoints(int facP) {
		this.facP -= facP;
	}

	public double getBank() {
		return this.bank;
	}
	public void setBank(double money) {
		this.bank = money;
	}
	public void addBank(double money) {
		this.bank += money;
	}
	public void removeBank(double money) {
		this.bank -= money;
	}
	
	public double getEnergy() {
		return this.energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	public void addEnergy(double energy) {
		if(this.energy + energy <= 30.00) {
			this.energy += energy;
		}
	}
	public void removeEnergy(double energy) {
		if(this.energy - energy >= 0.00) {
			this.energy -= energy;
		}
	}
}
