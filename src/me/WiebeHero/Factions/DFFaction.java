package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFactionGroups.FactionGroup;
import me.WiebeHero.Factions.DFFactionGroups.FactionPermission;

public class DFFaction {
	
	private UUID facId;
	private DFFactionManager facManager;
	private DFFactionPlayerManager memberManager;
	private String facName;
	private ArrayList<Long> chunkList;
	private ArrayList<UUID> invitedList;
	private ArrayList<UUID> invitedAllyList;
	private ArrayList<UUID> allyList;
	private HashMap<String, Location> facHomes;
	private HashMap<FactionGroup, ArrayList<Pair<FactionPermission, Boolean>>> factionPermissions;
	private int level;
	private int maxLevel;
	private int xp;
	private int maxxp;
	private int facP;
	private double bank;
	private double energy;
	private double maxEnergy;
	private double maxpMultiplier = 1.13;
	private double mixpMultiplier = 1.0001;
	private ItemStack banner;
	private Inventory fv;
	private ItemStack stackList[];
	
	public DFFaction(String facName, Player p, DFFactionManager facManager, DFFactionPlayerManager memberManager) {
		this.facId = UUID.randomUUID();
		this.chunkList = new ArrayList<Long>();
		this.allyList = new ArrayList<UUID>();
		this.invitedAllyList = new ArrayList<UUID>();
		this.invitedList = new ArrayList<UUID>();
		this.facHomes = new HashMap<String, Location>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
		this.maxEnergy = 30.00;
		this.bank = 0.00;
		this.facManager = facManager;
		this.memberManager = memberManager;
		this.stackList = new ItemStack[54];
		this.xp = 0;
		this.maxxp = 200;
		this.level = 1;
		this.maxLevel = 100;
		this.fv = CustomEnchantments.getInstance().getServer().createInventory(null, this.getVaultSize(), new CCT().colorize("&a" + this.getName() + "'s Vault"));
		this.banner = new ItemStack(Material.BLACK_BANNER);
		this.factionPermissions = new HashMap<FactionGroup, ArrayList<Pair<FactionPermission, Boolean>>>();
		for(FactionGroup group : FactionGroup.values()) {
			this.factionPermissions.put(group, new ArrayList<Pair<FactionPermission, Boolean>>());
			this.factionPermissions.get(group).addAll(group.getDefaultPerms(group));
		}
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
		this.facHomes = new HashMap<String, Location>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
		this.maxEnergy = 30.00;
		this.bank = 0.00;
		this.facManager = facManager;
		this.memberManager = memberManager;
		this.stackList = new ItemStack[54];
		this.xp = 0;
		this.maxxp = 200;
		this.level = 1;
		this.maxLevel = 100;
		this.fv = CustomEnchantments.getInstance().getServer().createInventory(null, this.getVaultSize(), new CCT().colorize("&a" + this.getName() + "'s Vault"));
		this.banner = new ItemStack(Material.BLACK_BANNER);
		this.factionPermissions = new HashMap<FactionGroup, ArrayList<Pair<FactionPermission, Boolean>>>();
		for(FactionGroup group : FactionGroup.values()) {
			this.factionPermissions.put(group, new ArrayList<Pair<FactionPermission, Boolean>>());
			this.factionPermissions.get(group).addAll(group.getDefaultPerms(group));
		}
	}
	public DFFaction(String facName, UUID facId, DFFactionManager facManager, DFFactionPlayerManager memberManager) {
		this.facId = facId;
		this.chunkList = new ArrayList<Long>();
		this.allyList = new ArrayList<UUID>();
		this.invitedAllyList = new ArrayList<UUID>();
		this.invitedList = new ArrayList<UUID>();
		this.facHomes = new HashMap<String, Location>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
		this.maxEnergy = 30.00;
		this.bank = 0.00;
		this.facManager = facManager;
		this.memberManager = memberManager;
		this.stackList = new ItemStack[54];
		this.xp = 0;
		this.maxxp = 200;
		this.level = 1;
		this.maxLevel = 100;
		this.fv = CustomEnchantments.getInstance().getServer().createInventory(null, this.getVaultSize(), new CCT().colorize("&a" + this.getName() + "'s Vault"));
		this.banner = new ItemStack(Material.BLACK_BANNER);
		this.factionPermissions = new HashMap<FactionGroup, ArrayList<Pair<FactionPermission, Boolean>>>();
		for(FactionGroup group : FactionGroup.values()) {
			this.factionPermissions.put(group, new ArrayList<Pair<FactionPermission, Boolean>>());
			this.factionPermissions.get(group).addAll(group.getDefaultPerms(group));
		}
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
	
	public void setFactionHomes(HashMap<String, Location> facHomes) {
		this.facHomes = facHomes;
	}
	public void addFactionHome(String name, Location loc) {
		this.facHomes.put(name, loc);
	}
	public void removeFactionHome(String name) {
		this.facHomes.remove(name);
	}
	public boolean hasFactionHome(String name) {
		return this.facHomes.containsKey(name);
	}
	public Location getFactionHome(String name) {
		return this.facHomes.get(name);
	}
	public HashMap<String, Location> getFactionHomes() {
		return this.facHomes;
	}
	public void clearFactionHomes() {
		this.facHomes.clear();
	}
	public int getFactionHomesAmount() {
		return this.facHomes.size();
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
	
	public boolean hasChunk(Long key){
		return this.chunkList.contains(key);
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
	public double getMaxEnergy() {
		return this.maxEnergy;
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
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void addLevel(int level) {
		this.level += level;
	}
	public void removeLevel(int level) {
		this.level -= level;
	}
	public int getMaxLevel() {
		return this.maxLevel;
	}
	public int getExperience() {
		return this.xp;
	}
	public void setExperience(int xp) {
		this.xp = xp;
	}
	public void addExperience(int xp) {
		this.xp += xp;
	}
	public void removeExperience(int xp) {
		this.xp -= xp;
	}
	public int getMaxExperience() {
		return this.maxxp;
	}
	public void setMaxExperience(int maxxp) {
		this.maxxp = maxxp;
	}
	public void addMaxExperience(int maxxp) {
		this.maxxp += maxxp;
	}
	public void removeMaxExperience(int maxxp) {
		this.maxxp = maxxp;
	}
	
	public ArrayList<Pair<FactionPermission, Boolean>> getPermissions(FactionGroup group){
		return this.factionPermissions.get(group);
	}
	
	public boolean getPermission(FactionGroup group, FactionPermission perm){
		ArrayList<Pair<FactionPermission, Boolean>> perms = this.factionPermissions.get(group);
		for(int i = 0; i < perms.size(); i++) {
			Pair<FactionPermission, Boolean> pair = perms.get(i);
			if(pair.getKey() == perm) {
				return pair.getValue();
			}
		}
		return false;
	}
	
	public void setPermission(FactionGroup group, FactionPermission perm, boolean bool) {
		ArrayList<Pair<FactionPermission, Boolean>> perms = this.factionPermissions.get(group);
		for(int i = 0; i < perms.size(); i++) {
			Pair<FactionPermission, Boolean> pair = perms.get(i);
			if(pair.getKey() == perm) {
				perms.set(i, new Pair<FactionPermission, Boolean>(perm, bool));
			}
		}
	}
	
	public HashMap<FactionGroup, ArrayList<Pair<FactionPermission, Boolean>>> getFactionPermissionsList(){
		return this.factionPermissions;
	}
	
	public int getVaultSize() {
		int mult = (int)((double)this.level / 20.00);
		return (int)(9.00 + 9.00 * (double)mult);
	}
	
	public double getExperienceMultiplier() {
		double x = this.maxpMultiplier - this.mixpMultiplier;
		double y = x / 100.00;
		double z = y * this.getLevel();
		return this.maxpMultiplier - z;
	}
	public double getExperienceGainMultiplier() {
		double multiplier = 0.00;
		double defMult = 1.00;
		multiplier = this.level / 5.00 + defMult;
		return multiplier;
	}
	public int getMaxFactionHomes() {
		int max = 1 + (int)Math.ceil((double)this.level / 25.00);
		return max;
	}
	public int getMaxMembers() {
		int max = 5;
		int addOn = (int)Math.ceil((double)this.level / 10.00) * 2;
		return max += addOn;
	}
	
	public HashMap<UUID, DFFactionPlayer> getMemberMap(){
		return this.memberManager.getFactionPlayerMap();
	}
	
	public ArrayList<UUID> getMembers(){
		ArrayList<UUID> members = new ArrayList<UUID>();
		for(Entry<UUID, DFFactionPlayer> entry : this.memberManager.getFactionPlayerMap().entrySet()) {
			DFFactionPlayer facPlayer = entry.getValue();
			if(facPlayer.getFactionId() != null) {
				if(facPlayer.getFactionId().equals(this.facId)) {
					members.add(entry.getKey());
				}
			}
		}
		return members;
	}
	
	public ArrayList<DFFactionPlayer> getDFFactionMembers(){
		ArrayList<DFFactionPlayer> members = new ArrayList<DFFactionPlayer>();
		for(Entry<UUID, DFFactionPlayer> entry : this.memberManager.getFactionPlayerMap().entrySet()) {
			DFFactionPlayer facPlayer = entry.getValue();
			if(facPlayer.getFactionId() != null) {
				if(facPlayer.getFactionId().equals(this.facId)) {
					members.add(entry.getValue());
				}
			}
		}
		return members;
	}
	
	public OfflinePlayer getLeader() {
		for(Entry<UUID, DFFactionPlayer> entry : this.memberManager.getFactionPlayerMap().entrySet()) {
			DFFactionPlayer facPlayer = entry.getValue();
			if(facPlayer.getFactionId() != null) {
				if(facPlayer.getFactionId().equals(this.facId)) {
					if(facPlayer.getRank() == 4) {
						return Bukkit.getOfflinePlayer(entry.getKey());
					}
				}
			}
		}
		return null;
	}
	
	public void setStackList(ItemStack stacks[]) {
		this.stackList = stacks;
	}
	
	public ItemStack[] getStackList() {
		return this.stackList;
	}
	
	public void setFactionVault(Inventory inv) {
		this.fv = inv;
	}
	
	public Inventory getFactionVault() {
		return this.fv;
	}
}
