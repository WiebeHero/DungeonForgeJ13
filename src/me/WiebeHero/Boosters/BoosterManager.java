package me.WiebeHero.Boosters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.Boosters.BoosterTypes.BoosterType;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFaction;

public class BoosterManager {
	
	private HashMap<UUID, Booster> boosterList;
	
	public BoosterManager() {
		this.boosterList = new HashMap<UUID, Booster>();
	}
	
	public void addBooster(UUID owner, BoosterType type, double multiplier, long endTime) {
		Booster booster = new Booster(owner, type, multiplier, endTime);
		this.boosterList.put(booster.getUniqueId(), booster);
	}
	
	public void addBooster(Booster booster) {
		this.boosterList.put(booster.getUniqueId(), booster);
	}
	
	public boolean containsBooster(UUID uuid) {
		return this.boosterList.containsKey(uuid);
	}
	
	public boolean containsBooster(Booster booster) {
		return this.boosterList.containsKey(booster.getUniqueId());
	}
	
	public void removeBooster(UUID uuid) {
		if(this.containsBooster(uuid)) {
			this.boosterList.remove(uuid);
		}
	}
	
	public void removeBooster(Booster booster) {
		if(this.containsBooster(booster)) {
			this.boosterList.remove(booster.getUniqueId());
		}
	}
	
	public boolean isPersonalActive(UUID uuid) {
		for(Booster booster : this.boosterList.values()) {
			if(booster != null) {
				if(booster.getType() == BoosterType.PERSONAL && booster.getOwner().equals(uuid)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Booster getPersonalBooster(UUID owner) {
		for(Booster booster : this.boosterList.values()) {
			if(booster != null) {
				if(booster.getType() == BoosterType.PERSONAL && booster.getOwner().equals(owner)) {
					return booster;
				}
			}
		}
		return null;
	}
	
	public boolean isFactionActive(DFFaction faction) {
		for(Booster booster : this.boosterList.values()) {
			if(booster != null) {
				if(booster.getType() == BoosterType.FACTION && faction.isMember(booster.getOwner())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Booster getFactionBooster(DFFaction faction) {
		for(Booster booster : this.boosterList.values()) {
			if(booster != null) {
				if(booster.getType() == BoosterType.FACTION && faction.isMember(booster.getOwner())) {
					return booster;
				}
			}
		}
		return null;
	}
	
	public boolean isAllActive() {
		for(Booster booster : this.boosterList.values()) {
			if(booster != null) {
				if(booster.getType() == BoosterType.ALL) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Booster getAllBooster() {
		for(Booster booster : this.boosterList.values()) {
			if(booster != null) {
				if(booster.getType() == BoosterType.ALL) {
					return booster;
				}
			}
		}
		return null;
	}
	
	public void runRemoval() {
		new BukkitRunnable() {
			public void run() {
				if(!boosterList.isEmpty()) {
					for(Entry<UUID, Booster> entry : boosterList.entrySet()) {
						if(entry != null) {
							if(System.currentTimeMillis() >= entry.getValue().getEndTime()) {
								boosterList.remove(entry.getKey());
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1200L);
	}
	
	public void loadBoosters() {
		File f1 =  new File("plugins/CustomEnchantments/Boosters.yml");
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
		if(yml.getConfigurationSection("Boosters") != null) {
			Set<String> l = yml.getConfigurationSection("Boosters").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(l);
			for(int i = 0; i < list.size(); i++) {
				UUID uuid = UUID.fromString(list.get(i));
				long duration = yml.getLong("Boosters." + uuid + ".End");
				if(duration > System.currentTimeMillis()) {
					CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage("Succeeded!");
					UUID owner = UUID.fromString(yml.getString("Boosters." + uuid + ".Owner"));
					double multiplier = yml.getDouble("Boosters." + uuid + ".Multiplier");
					BoosterType type = BoosterType.valueOf(yml.getString("Boosters." + uuid + ".BoosterType"));
					Booster booster = new Booster(uuid, owner, type, multiplier, duration);
					this.boosterList.put(uuid, booster);
				}
			}
		}
	}
	public void saveBoosters() {
		File f1 =  new File("plugins/CustomEnchantments/Boosters.yml");
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
		yml.set("Boosters", null);
		for(Entry<UUID, Booster> entry : this.boosterList.entrySet()) {
			yml.createSection("Boosters." + entry.getKey());
			yml.set("Boosters." + entry.getKey(), null);
			UUID uuid = entry.getKey();
			Booster booster = entry.getValue();
			yml.set("Boosters." + uuid + ".Owner", booster.getOwner().toString());
			yml.set("Boosters." + uuid + ".Multiplier", booster.getMultiplier());
			yml.set("Boosters." + uuid + ".BoosterType", booster.getType().toString());
			yml.set("Boosters." + uuid + ".End", booster.getEndTime());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void saveBoostersBackup(String folder) {
		File f1 = new File("plugins/CustomEnchantments/Data-Backups/" + folder + "/Boosters.yml");
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
		yml.set("Boosters", null);
		for(Entry<UUID, Booster> entry : this.boosterList.entrySet()) {
			yml.createSection("Boosters." + entry.getKey());
			yml.set("Boosters." + entry.getKey(), null);
			UUID uuid = entry.getKey();
			Booster booster = entry.getValue();
			yml.set("Boosters." + uuid + ".Owner", booster.getOwner().toString());
			yml.set("Boosters." + uuid + ".Multiplier", booster.getMultiplier());
			yml.set("Boosters." + uuid + ".BoosterType", booster.getType().toString());
			yml.set("Boosters." + uuid + ".End", booster.getEndTime());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
