package me.WiebeHero.Factions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DFFaction {
	Methods method;
	public String facName;
	public HashMap<UUID, Integer> memberList;
	public ArrayList<Chunk> chunkList;
	public ArrayList<UUID> invitedList;
	public ArrayList<String> invitedAllyList;
	public ArrayList<String> allyList;
	public Location facHome;
	public int facP;
	public double energy;
	public DFFaction(String facName, Player p) {
		this.memberList = new HashMap<UUID, Integer>();
		this.memberList.put(p.getUniqueId(), 4);
		this.chunkList = new ArrayList<Chunk>();
		this.allyList = new ArrayList<String>();
		this.invitedAllyList = new ArrayList<String>();
		this.invitedList = new ArrayList<UUID>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
	}
	public DFFaction(String facName) {
		this.memberList = new HashMap<UUID, Integer>();
		this.chunkList = new ArrayList<Chunk>();
		this.allyList = new ArrayList<String>();
		this.invitedAllyList = new ArrayList<String>();
		this.invitedList = new ArrayList<UUID>();
		this.facName = facName;
		this.facP = 0;
		this.energy = 2.00;
	}
	public DFFaction() {
		//Empty Constructor
	}
	public void activeEnergyTimer() {
		new BukkitRunnable() {
			public void run() {
				for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
					int count = 0;
					for(UUID uuid : fac.getMemberList().keySet()) {
						Player player = Bukkit.getPlayer(uuid);
						if(player != null) {
							count++;
						}
					}
					if(count > 5) {
						count = 5;
					}
					if(fac.getEnergy() + 0.015 + 0.015 * count <= 30.00) {
						fac.addEnergy(0.015 + 0.015 * count);
						count = 0;
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 400L);
	}
	public void loadFactions() {
		File f1 =  new File("plugins/CustomEnchantments/factionsConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml.getConfigurationSection("Factions.List") != null) {
			Set<String> l = yml.getConfigurationSection("Factions.List").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(l);
			for(int i = 0; i < list.size(); i++) {
				DFFaction fac = new DFFaction(list.get(i));
				Set<String> chec = yml.getConfigurationSection("Factions.List." + list.get(i) + ".Members").getKeys(false);
				ArrayList<String> facMembers = new ArrayList<String>(chec);
				for(int i1 = 0; i1 < facMembers.size(); i1++) {
					UUID uuid = UUID.fromString(facMembers.get(i1));
					int rank = yml.getInt("Factions.List." + list.get(i) + ".Members." + uuid + ".Rank");
					fac.memberList.put(uuid, rank);
				}
				Location loc = (Location) yml.get("Factions.List." + list.get(i) + ".Faction Home");
				fac.facHome = loc;
				ArrayList<String> chunks = new ArrayList<String>(yml.getStringList("Factions.List." + list.get(i) + ".Chunks List"));
				for(int i1 = 0; i1 < chunks.size(); i1++) {
					Chunk chunk = Bukkit.getWorld("FactionWorld-1").getChunkAt(Long.parseLong(chunks.get(i1)));
					fac.chunkList.add(chunk);
				}
				ArrayList<String> fAllyList = new ArrayList<String>(yml.getStringList("Factions.List." + list.get(i) + ".Allies"));
				fac.allyList = fAllyList;
				for(int i1 = 0; i1 < list.size(); i1++) {
					int fPoints = yml.getInt("Factions.List." + list.get(i) + ".Faction Points");
					fac.facP = fPoints;
				}
				fac.setEnergy(yml.getDouble("Factions.List." + list.get(i) + ".Energy"));
				CustomEnchantments.getInstance().factionList.add(fac);
			}
		}
	}
	public void saveFactions() {
		File f1 =  new File("plugins/CustomEnchantments/factionsConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			yml.createSection("Factions.List." + fac.getName());
			for(Entry<UUID, Integer> entry : fac.getMemberList().entrySet()) {
				yml.set("Factions.List." + fac.getName() + ".Members." + entry.getKey() + ".Rank", entry.getValue());
				yml.set("Factions.List." + fac.getName() + ".Members." + entry.getKey() + ".Name", Bukkit.getOfflinePlayer(entry.getKey()).getName());
			}
			ArrayList<Long> list = new ArrayList<Long>();
			for(Chunk c : fac.getChunkList()) {
				list.add(c.getChunkKey());
			}
			yml.set("Factions.List." + fac.getName() + ".Faction Home", fac.getFactionHome());
			yml.set("Factions.List." + fac.getName() + ".Chunks List", list);
			yml.set("Factions.List." + fac.getName() + ".Faction Points", fac.facP);
			yml.set("Factions.List." + fac.getName() + ".Allies", fac.getAllyList());
			yml.set("Factions.List." + fac.getName() + ".Energy", fac.getEnergy());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void hardSaveFactions() {
		File f1 =  new File("plugins/CustomEnchantments/factionsConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.set("Factions.List", null);
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			yml.createSection("Factions.List." + fac.getName());
			for(Entry<UUID, Integer> entry : fac.getMemberList().entrySet()) {
				yml.set("Factions.List." + fac.getName() + ".Members." + entry.getKey() + ".Rank", entry.getValue());
				yml.set("Factions.List." + fac.getName() + ".Members." + entry.getKey() + ".Name", Bukkit.getOfflinePlayer(entry.getKey()).getName());
			}
			ArrayList<Long> list = new ArrayList<Long>();
			for(Chunk c : fac.getChunkList()) {
				list.add(c.getChunkKey());
			}
			yml.set("Factions.List." + fac.getName() + ".Faction Home", fac.getFactionHome());
			yml.set("Factions.List." + fac.getName() + ".Chunks List", list);
			yml.set("Factions.List." + fac.getName() + ".Faction Points", fac.facP);
			yml.set("Factions.List." + fac.getName() + ".Allies", fac.getAllyList());
			yml.set("Factions.List." + fac.getName() + ".Energy", fac.getEnergy());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public DFFaction getFaction(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		if(player != null) {
			for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
				if(fac.getMemberList().containsKey(uuid)) {
					return fac;
				}
			}
		}
		return null;
	}
	public DFFaction getFaction(String name) {
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(fac.getName().equals(name)) {
				return fac;
			}
		}
		return null;
	}
	public void deleteFaction(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		if(player != null) {
			for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
				if(fac.getMemberList().containsKey(uuid)) {
					CustomEnchantments.getInstance().factionList.remove(fac);
					break;
				}
			}
		}
	}
	public void deleteFaction(String name) {
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(fac.getName().equals(name)) {
				CustomEnchantments.getInstance().factionList.remove(fac);
				break;
			}
		}
	}
	
	public boolean isNameAvailable(String name) {
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(fac.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public HashMap<UUID, Integer> getMemberList(){
		return this.memberList;
	}
	public Player getMember(UUID uuid) {
		if(this.getMemberList().containsKey(uuid)) {
			Player p = method.convertOfflinePlayer(uuid);
			if(p.getName() != null) {
				return p;
			}
		}
		return null;
	}
	public void addMember(Player player) {
		this.getMemberList().put(player.getUniqueId(), 1);
	}
	public void removeMember(Player player) {
		this.getMemberList().remove(player.getUniqueId());
	}
	
	public int getRank(UUID uuid) {
		if(this.getMemberList().containsKey(uuid)) {
			return this.getMemberList().get(uuid);
		}
		return 1;
	}
	public void promoteMember(UUID uuid) {
		this.getMemberList().put(uuid, this.getRank(uuid) + 1);
	}
	public void demoteMember(UUID uuid) {
		this.getMemberList().put(uuid, this.getRank(uuid) - 1);
	}
	
	public ArrayList<Chunk> getChunkList(){
		return this.chunkList;
	}
	
	public ArrayList<String> getAllyList() {
		return this.allyList;
	}
	
	public ArrayList<UUID> getInvitedList() {
		return this.invitedList;
	}
	
	public String getName() {
		return this.facName;
	}
	public void setName(String facName) {
		this.facName = facName;
	}
	
	public boolean isMember(UUID uuid) {
		if(this.memberList.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public boolean isAMember(UUID uuid) {
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(!this.getMemberList().containsKey(uuid)) {
				if(fac.getMemberList().containsKey(uuid)) {
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
		this.chunkList.add(c);
	}
	public void removeChunk(Chunk c) {
		this.chunkList.remove(c);
	}
	public boolean isInAChunk(Player player) {
		Chunk c = player.getLocation().getChunk();
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(!fac.getMemberList().containsKey(player.getUniqueId())) {
				if(fac.getChunkList().contains(c)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean isInChunk(Player player) {
		Chunk c = player.getChunk();
		if(this.getChunkList().contains(c)) {
			return true;
		}
		return false;
	}
	public boolean isInAChunk(Player player, Location loc) {
		Chunk c = loc.getChunk();
		for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
			if(!fac.getMemberList().containsKey(player.getUniqueId())) {
				if(fac.getChunkList().contains(c)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean isInChunk(Location loc) {
		Chunk c = loc.getChunk();
		if(this.getChunkList().contains(c)) {
			return true;
		}
		return false;
	}
	
	public boolean isAlly(String fac) {
		if(this.allyList.contains(fac)) {
			return true;
		}
		return false;
	}
	
	public boolean isAlly(UUID uuid) {
		for(int i = 0; i < this.allyList.size(); i++) {
			for(Entry<UUID, Integer> entry : this.getFaction(this.allyList.get(i)).getMemberList().entrySet()) {
				if(entry.getKey().equals(uuid)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void addAlly(String fac) {
		this.allyList.add(fac);
	}
	public void removeAlly(String fac) {
		this.allyList.remove(fac);
	}
	public boolean isInvitedAlly(String fac) {
		if(this.invitedAllyList.contains(fac)) {
			return true;
		}
		return false;
	}
	public void addInvitedAlly(String fac) {
		this.invitedAllyList.add(fac);
	}
	public void removeInvitedAlly(String fac) {
		this.invitedAllyList.remove(fac);
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
