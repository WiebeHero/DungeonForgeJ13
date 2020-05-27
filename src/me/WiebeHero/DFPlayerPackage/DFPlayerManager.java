package me.WiebeHero.DFPlayerPackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.WiebeHero.DFPlayerPackage.Enums.Classes;

public class DFPlayerManager {
	public HashMap<UUID, DFPlayer> dfPlayerList;
	public DFPlayerManager() {
		this.dfPlayerList = new HashMap<UUID, DFPlayer>();
	}
	public HashMap<UUID, DFPlayer> getDFEntityList(){
		return this.dfPlayerList;
	}
	public void addEntity(LivingEntity entity) {
		this.dfPlayerList.put(entity.getUniqueId(), new DFPlayer(entity));
	}
	public void addEntity(UUID uuid) {
		this.dfPlayerList.put(uuid, new DFPlayer(uuid));
	}
	public void addEntity(UUID uuid, DFPlayer dfPlayer) {
		this.dfPlayerList.put(uuid, dfPlayer);
	}
	public void removeEntity(UUID uuid) {
		if(this.contains(uuid)) {
			this.dfPlayerList.remove(uuid);
		}
	}
	public DFPlayer getEntity(UUID uuid) {
		if(this.contains(uuid)) {
			return this.dfPlayerList.get(uuid);
		}
		return null;
	}
	public DFPlayer getEntity(Entity ent) {
		if(this.contains(ent.getUniqueId())) {
			return this.dfPlayerList.get(ent.getUniqueId());
		}
		return null;
	}
	public boolean contains(LivingEntity ent) {
		return this.dfPlayerList.containsKey(ent.getUniqueId());
	}
	public boolean contains(UUID uuid) {
		return this.dfPlayerList.containsKey(uuid);
	}
	public void clear() {
		this.dfPlayerList.clear();
	}
	public void resetEntity(LivingEntity e) {
		if(this.contains(e)) {
			this.dfPlayerList.remove(e.getUniqueId());
			DFPlayer dfPlayer = new DFPlayer(e);
			this.dfPlayerList.put(e.getUniqueId(), dfPlayer);
			dfPlayer.runDefense();
			dfPlayer.attackSpeed();
		}
	}
	public void softResetEntity(LivingEntity e) {
		if(this.contains(e)) {
			DFPlayer dfPlayer = this.dfPlayerList.get(e.getUniqueId());
			dfPlayer.setLevel(1);
			dfPlayer.setAtk(0);
			dfPlayer.setSpd(0);
			dfPlayer.setCrt(0);
			dfPlayer.setRnd(0);
			dfPlayer.setHp(0);
			dfPlayer.setDf(0);
			dfPlayer.setSkillPoints(3);
			dfPlayer.setExperience(0);
			dfPlayer.setMaxExperience(750);
			dfPlayer.changeHealth();
			dfPlayer.runDefense();
			dfPlayer.attackSpeed();
		}
	}
	public void softResetEntity(UUID uuid) {
		if(this.contains(uuid)) {
			DFPlayer dfPlayer = this.dfPlayerList.get(uuid);
			dfPlayer.setLevel(1);
			dfPlayer.setAtk(0);
			dfPlayer.setSpd(0);
			dfPlayer.setCrt(0);
			dfPlayer.setRnd(0);
			dfPlayer.setHp(0);
			dfPlayer.setDf(0);
			dfPlayer.setSkillPoints(3);
			dfPlayer.setExperience(0);
			dfPlayer.setMaxExperience(750);
			dfPlayer.changeHealth();
			dfPlayer.runDefense();
			dfPlayer.attackSpeed();
		}
	}
	public void resetEntity(UUID uuid) {
		if(this.contains(uuid)) {
			this.dfPlayerList.remove(uuid);
			this.dfPlayerList.put(uuid, new DFPlayer(uuid));
		}
	}
	//---------------------------------------------------------
	//Saving + Loading player profiles
	//---------------------------------------------------------
	public void loadPlayers() {
		File f = new File("plugins/CustomEnchantments/playerskillsDF.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml.getConfigurationSection("Skills.Players") != null) {
			if(!yml.get("Skills.Players").equals("{}")) {
				ArrayList<String> uuidStrings = new ArrayList<String>(yml.getConfigurationSection("Skills.Players").getKeys(false));
				ArrayList<UUID> uuidList = new ArrayList<UUID>();
				for(int i = 0; i < uuidStrings.size(); i++) {
					UUID uuid = UUID.fromString(uuidStrings.get(i));
					uuidList.add(uuid);
				}
				for(UUID uuid : uuidList) {
					DFPlayer dfPlayer = new DFPlayer(uuid);
					String className = yml.getString("Skills.Players." + uuid + ".Class");
					className = className.toUpperCase();
					Classes classChoice = Enum.valueOf(Classes.class, className);
					dfPlayer.setPlayerClass(classChoice);
					dfPlayer.setLevel(yml.getInt("Skills.Players." + uuid + ".Level"));
					dfPlayer.setExperience(yml.getInt("Skills.Players." + uuid + ".XP"));
					dfPlayer.setMaxExperience(yml.getInt("Skills.Players." + uuid + ".MXP"));
					dfPlayer.setSkillPoints(yml.getInt("Skills.Players." + uuid + ".Skill Points"));
					dfPlayer.setAtk(yml.getInt("Skills.Players." + uuid + ".Attack Damage"));
					dfPlayer.setSpd(yml.getInt("Skills.Players." + uuid + ".Attack Speed"));
					dfPlayer.setCrt(yml.getInt("Skills.Players." + uuid + ".Critical Chance"));
					dfPlayer.setRnd(yml.getInt("Skills.Players." + uuid + ".Ranged Damage"));
					dfPlayer.setHp(yml.getInt("Skills.Players." + uuid + ".Health"));
					dfPlayer.setDf(yml.getInt("Skills.Players." + uuid + ".Defense"));
					dfPlayer.setAtkMod(yml.getInt("Skills.Players." + uuid + ".Attack Modifier"));
					dfPlayer.setSpdMod(yml.getInt("Skills.Players." + uuid + ".Speed Modifier"));
					dfPlayer.setCrtMod(yml.getInt("Skills.Players." + uuid + ".Critical Modifier"));
					dfPlayer.setRndMod(yml.getInt("Skills.Players." + uuid + ".Ranged Modifier"));
					dfPlayer.setHpMod(yml.getInt("Skills.Players." + uuid + ".Health Modifier"));
					dfPlayer.setDfMod(yml.getInt("Skills.Players." + uuid + ".Defense Modifier"));
					dfPlayer.setMoney(yml.getDouble("Skills.Players." + uuid + ".Money"));
					dfPlayer.setActivation(false);
					dfPlayer.setUseable(true);
					dfPlayer.setMove(0.2F);
					dfPlayer.setAtkCal(0.00);
					dfPlayer.setSpdCal(0.00);
					dfPlayer.setCrtCal(0.00);
					dfPlayer.setRndCal(0.00);
					dfPlayer.setHpCal(0.00);
					dfPlayer.setDfCal(0.00);
					this.addEntity(uuid, dfPlayer);
				}
			}
		}
	}
	public void savePlayers() {
		File f = new File("plugins/CustomEnchantments/playerskillsDF.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.set("Skills.Players", null);
		if(!this.dfPlayerList.isEmpty()) {
			for(Entry<UUID, DFPlayer> entry : this.dfPlayerList.entrySet()) {
				if(Bukkit.getOfflinePlayer(entry.getKey()).getName() != null) {
					DFPlayer player = entry.getValue();
					yml.set("Skills.Players." + entry.getKey().toString() + ".Class", player.getPlayerClass().toString());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Level", player.getLevel());
					yml.set("Skills.Players." + entry.getKey().toString() + ".XP", player.getExperience());
					yml.set("Skills.Players." + entry.getKey().toString() + ".MXP", player.getMaxExperience());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Skill Points", player.getSkillPoints());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Attack Damage", player.getAtk());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Attack Speed", player.getSpd());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Critical Chance", player.getCrt());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Ranged Damage", player.getRnd());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Health", player.getHp());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Defense", player.getDf());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Attack Modifier", player.getAtkMod());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Speed Modifier", player.getSpdMod());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Critical Modifier", player.getCrtMod());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Ranged Modifier", player.getRndMod());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Health Modifier", player.getHpMod());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Defense Modifier", player.getDfMod());
					yml.set("Skills.Players." + entry.getKey().toString() + ".Money", player.getMoney());
				}
			}
		}
		try{
			yml.save(f);
	    }
	    catch(IOException e){
	        e.printStackTrace();
	    }
	}
}
