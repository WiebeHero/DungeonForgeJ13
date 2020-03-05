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
			this.dfPlayerList.put(e.getUniqueId(), new DFPlayer(e));
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
					dfPlayer.cClass = classChoice;
					dfPlayer.lvl = yml.getInt("Skills.Players." + uuid + ".Level");
					dfPlayer.xp = yml.getInt("Skills.Players." + uuid + ".XP");
					dfPlayer.maxxp = yml.getInt("Skills.Players." + uuid + ".MXP");
					dfPlayer.sk_pt = yml.getInt("Skills.Players." + uuid + ".Skill Points");
					dfPlayer.atk = yml.getInt("Skills.Players." + uuid + ".Attack Damage");
					dfPlayer.spd = yml.getInt("Skills.Players." + uuid + ".Attack Speed");
					dfPlayer.crt = yml.getInt("Skills.Players." + uuid + ".Critical Chance");
					dfPlayer.rnd = yml.getInt("Skills.Players." + uuid + ".Ranged Damage");
					dfPlayer.hp = yml.getInt("Skills.Players." + uuid + ".Health");
					dfPlayer.df = yml.getInt("Skills.Players." + uuid + ".Defense");
					dfPlayer.atk_m = yml.getInt("Skills.Players." + uuid + ".Attack Modifier");
					dfPlayer.spd_m = yml.getInt("Skills.Players." + uuid + ".Speed Modifier");
					dfPlayer.crt_m = yml.getInt("Skills.Players." + uuid + ".Critical Modifier");
					dfPlayer.rnd_m = yml.getInt("Skills.Players." + uuid + ".Ranged Modifier");
					dfPlayer.hp_m = yml.getInt("Skills.Players." + uuid + ".Health Modifier");
					dfPlayer.df_m = yml.getInt("Skills.Players." + uuid + ".Defense Modifier");
					dfPlayer.money = yml.getDouble("Skills.Players." + uuid + ".Money");
					dfPlayer.active = false;
					dfPlayer.use = true;
					dfPlayer.move = 0.2F;
					dfPlayer.atk_ct = 0.00;
					dfPlayer.spd_ct = 0.00;
					dfPlayer.crt_ct = 0.00;
					dfPlayer.rnd_ct = 0.00;
					dfPlayer.hp_ct = 0.00;
					dfPlayer.df_ct = 0.00;
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
