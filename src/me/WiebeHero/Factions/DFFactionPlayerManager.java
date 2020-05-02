package me.WiebeHero.Factions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

public class DFFactionPlayerManager {

	private HashMap<UUID, DFFactionPlayer> factionPlayerList;
	
	public DFFactionPlayerManager() {
		this.factionPlayerList = new HashMap<UUID, DFFactionPlayer>();
	}
	
	public void loadPlayers() {
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
				Set<String> chec = yml.getConfigurationSection("Factions.List." + list.get(i) + ".Members").getKeys(false);
				ArrayList<String> facMembers = new ArrayList<String>(chec);
				for(int i1 = 0; i1 < facMembers.size(); i1++) {
					UUID uuid = UUID.fromString(facMembers.get(i1));
					this.add(uuid);
				}
			}
		}
	}
	
	public void add(UUID pUuid, DFFactionPlayer facPlayer) {
		this.factionPlayerList.put(pUuid, facPlayer);
	}
	
	public void add(UUID pUuid) {
		this.factionPlayerList.put(pUuid, new DFFactionPlayer());
	}
	
	public void add(UUID pUuid, UUID facId) {
		this.factionPlayerList.put(pUuid, new DFFactionPlayer(facId));
	}
	
	public void add(UUID pUuid, UUID facId, int rank) {
		this.factionPlayerList.put(pUuid, new DFFactionPlayer(facId, rank));
	}
	
	public void add(UUID pUuid, int rank) {
		this.factionPlayerList.put(pUuid, new DFFactionPlayer(rank));
	}
	
	public void remove(UUID uuid) {
		if(this.contains(uuid)) {
			this.factionPlayerList.remove(uuid);
		}
	}
	
	public boolean contains(UUID uuid) {
		return this.factionPlayerList.containsKey(uuid);
	}
	public boolean contains(LivingEntity e) {
		return this.factionPlayerList.containsKey(e.getUniqueId());
	}
	
	public DFFactionPlayer getFactionPlayer(UUID uuid) {
		if(this.contains(uuid)) {
			return this.factionPlayerList.get(uuid);
		}
		return null;
	}
	public DFFactionPlayer getFactionPlayer(LivingEntity e) {
		if(this.contains(e.getUniqueId())) {
			return this.factionPlayerList.get(e.getUniqueId());
		}
		return null;
	}
	public HashMap<UUID, DFFactionPlayer> getFactionPlayerMap(){
		return this.factionPlayerList;
	}
}
