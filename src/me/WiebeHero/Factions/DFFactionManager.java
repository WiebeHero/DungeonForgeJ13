package me.WiebeHero.Factions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DFFactionManager {
	
	private HashMap<UUID, DFFaction> factionList;
	private DFFactionPlayerManager facPlayerManager;
	
	public DFFactionManager(DFFactionPlayerManager facPlayerManager) {
		this.factionList = new HashMap<UUID, DFFaction>();
		this.facPlayerManager = facPlayerManager;
	}
	
	public void add(DFFaction dfFaction) {
		this.factionList.put(dfFaction.getFactionId(), dfFaction);
	}
	
	public DFFaction getFaction(UUID facUuid) {
		if(this.contains(facUuid)) {
			return this.factionList.get(facUuid);
		}
		return null;
	}
	public DFFaction getFaction(String name) {
		for(DFFaction fac : this.factionList.values()) {
			if(fac.getName().equals(name)) {
				return this.factionList.get(fac.getFactionId());
			}
		}
		return null;
	}
	
	public boolean contains(UUID uuid) {
		return this.factionList.containsKey(uuid);
	}
	
	public void remove(UUID facUuid) {
		if(this.contains(facUuid)) {
			this.factionList.remove(facUuid);
		}
	}
	
	public void remove(String facName) {
		for(DFFaction fac : this.factionList.values()) {
			if(fac.getName().equals(facName)) {
				this.factionList.remove(fac.getFactionId());
			}
		}
	}
	
	public boolean isInAChunk(Player player) {
		long c = player.getChunk().getChunkKey();
		for(DFFaction fac : this.factionList.values()) {
			if(fac.getChunkList().contains(c)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInAChunk(Location loc) {
		long c = loc.getChunk().getChunkKey();
		for(DFFaction fac : this.factionList.values()) {
			if(fac.getChunkList().contains(c)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAMember(DFFactionPlayer p) {
		return p.getFactionId() != null;
	}
	public boolean isAMember(UUID uuid) {
		return this.facPlayerManager.getFactionPlayer(uuid).getFactionId() != null;
	}
	public boolean isFriendly(Entity check1, Entity check2) {
		if(check1 != null && check2 != null) {
			if(facPlayerManager.contains(check1.getUniqueId()) && facPlayerManager.contains(check2.getUniqueId())) {
				UUID uuid1 = check1.getUniqueId();
				UUID uuid2 = check2.getUniqueId();
				DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(uuid1);
				DFFaction faction = this.getFaction(facPlayer.getFactionId());
				DFFactionPlayer facP = facPlayerManager.getFactionPlayer(uuid2);
				DFFaction other = this.getFaction(facP.getFactionId());
				if(faction != null && other != null) {
					if(faction.isAlly(other.getName()) || faction.isMember(uuid2)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isNameAvailable(String name) {
		for(DFFaction fac : this.factionList.values()) {
			if(fac.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<String> getAllFactionNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(DFFaction fac : this.factionList.values()) {
			names.add(fac.getName());
		}
		return names;
	}
	
	public HashMap<UUID, DFFaction> getFactionMap(){
		return this.factionList;
	}
	
	public void activeEnergyTimer() {
		new BukkitRunnable() {
			public void run() {
				for(DFFaction fac : factionList.values()) {
					int count = 0;
					for(UUID uuid : facPlayerManager.getFactionPlayerMap().keySet()) {
						Player player = Bukkit.getPlayer(uuid);
						if(player != null) {
							count++;
						}
					}
					if(count > 5) {
						count = 5;
					}
					if(count != 0) {
						if(fac.getEnergy() + 0.025 * count <= fac.getMaxEnergy()) {
							fac.addEnergy(0.015 + 0.015 * count);
							count = 0;
						}
						else {
							fac.setEnergy(fac.getMaxEnergy());
						}
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
				UUID facId = UUID.fromString(list.get(i));
				String facName = yml.getString("Factions.List." + list.get(i) + ".Faction Name");
				Set<String> chec = yml.getConfigurationSection("Factions.List." + list.get(i) + ".Members").getKeys(false);
				DFFaction fac = new DFFaction(facName, facId, this, facPlayerManager);
				HashMap<String, Location> locs = new HashMap<String, Location>();//(Location) yml.get("Factions.List." + list.get(i) + ".Faction Home");
				if(yml.getConfigurationSection("Factions.List." + list.get(i) + ".Faction Homes") != null) {
					Set<String> facHomesList = yml.getConfigurationSection("Factions.List." + list.get(i) + ".Faction Homes").getKeys(false);
					ArrayList<String> names = new ArrayList<String>(facHomesList);
					for(int i1 = 0; i1 < names.size(); i1++) {
						locs.put(names.get(i1), (Location)yml.get("Factions.List." + list.get(i) + ".Faction Homes." + names.get(i1)));
					}
				}
				else if(yml.get("Factions.List." + list.get(i) + ".Faction Home") != null) {
					locs.put("Home", (Location)yml.get("Factions.List." + list.get(i) + ".Faction Home"));
				}
				fac.setFactionHomes(locs);
				ArrayList<String> chunks = new ArrayList<String>(yml.getStringList("Factions.List." + list.get(i) + ".Chunks List"));
				for(int i1 = 0; i1 < chunks.size(); i1++) {
					long key = Long.parseLong(chunks.get(i1));
					fac.addChunk(key);
				}
				ArrayList<String> tempfAllyList = new ArrayList<String>(yml.getStringList("Factions.List." + list.get(i) + ".Allies"));
				ArrayList<UUID> fAllyList = new ArrayList<UUID>();
				for(int i1 = 0; i1 < tempfAllyList.size(); i1++) {
					UUID uuid = UUID.fromString(tempfAllyList.get(i1));
					fAllyList.add(uuid);
				}
				ItemStack banner = yml.getItemStack("Factions.List." + list.get(i) + ".Faction Banner");
				if(banner == null) {
					fac.setBanner(new ItemStack(Material.BLACK_BANNER, 1));
				}
				else {
					fac.setBanner(banner);
				}
				fac.setAllyList(fAllyList);
				int fPoints = yml.getInt("Factions.List." + list.get(i) + ".Faction Points");
				fac.setFactionPoints(fPoints);
				fac.setEnergy(yml.getDouble("Factions.List." + list.get(i) + ".Energy"));
				fac.setBank(yml.getDouble("Factions.List." + list.get(i) + ".Bank"));
				ArrayList<String> facMembers = new ArrayList<String>(chec);
				for(int i1 = 0; i1 < facMembers.size(); i1++) {
					UUID uuid = UUID.fromString(facMembers.get(i1));
					DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(uuid);
					int rank = yml.getInt("Factions.List." + list.get(i) + ".Members." + uuid + ".Rank");
					facPlayer.setRank(rank);
					facPlayer.setFactionId(facId);
				}
				int level = yml.getInt("Factions.List." + list.get(i) + ".Level");
				if(level > 0) {
					fac.setLevel(level);
				}
				fac.setExperience(yml.getInt("Factions.List." + list.get(i) + ".Experience"));
				int maxxp = yml.getInt("Factions.List." + list.get(i) + ".Max Experience");
				if(maxxp != 0) {
					fac.setMaxExperience(maxxp);
				}
;				this.add(fac);
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
		yml.set("Factions.List", null);
		for(DFFaction fac : this.factionList.values()) {
			yml.createSection("Factions.List." + fac.getFactionId());
			for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
				if(entry.getValue().isInFaction()) {
					if(entry.getValue().getFactionId().equals(fac.getFactionId())) {
						yml.set("Factions.List." + fac.getFactionId() + ".Members." + entry.getKey() + ".Rank", entry.getValue().getRank());
						yml.set("Factions.List." + fac.getFactionId() + ".Members." + entry.getKey() + ".Name", Bukkit.getOfflinePlayer(entry.getKey()).getName());
					}
				}
			}
			ArrayList<Long> list = new ArrayList<Long>();
			if(!fac.getChunkList().isEmpty()) {
				for(long c : fac.getChunkList()) {
					list.add(c);
				}
			}
			ArrayList<String> uuids = new ArrayList<String>();
			if(!fac.getAllyList().isEmpty()) {
				for(UUID uuid : fac.getAllyList()) {
					uuids.add(uuid.toString());
				}
			}
			yml.set("Factions.List." + fac.getFactionId() + ".Faction Banner", fac.getBanner());
			for(Entry<String, Location> entry : fac.getFactionHomes().entrySet()) {
				yml.set("Factions.List." + fac.getFactionId() + ".Faction Homes." + entry.getKey(), entry.getValue());
			}
			yml.set("Factions.List." + fac.getFactionId() + ".Faction Name", fac.getName());
			yml.set("Factions.List." + fac.getFactionId() + ".Chunks List", list);
			yml.set("Factions.List." + fac.getFactionId() + ".Faction Points", fac.getFactionPoints());
			yml.set("Factions.List." + fac.getFactionId() + ".Allies", uuids);
			yml.set("Factions.List." + fac.getFactionId() + ".Energy", fac.getEnergy());
			yml.set("Factions.List." + fac.getFactionId() + ".Bank", fac.getBank());
			yml.set("Factions.List." + fac.getFactionId() + ".Level", fac.getLevel());
			yml.set("Factions.List." + fac.getFactionId() + ".Experience", fac.getExperience());
			yml.set("Factions.List." + fac.getFactionId() + ".Max Experience", fac.getMaxExperience());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
