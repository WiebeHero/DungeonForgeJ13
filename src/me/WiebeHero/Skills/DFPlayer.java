package me.WiebeHero.Skills;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.EnumSkills.SkillState;
import me.WiebeHero.Skills.Enums.Classes;
import me.WiebeHero.Skills.State.States;

public class DFPlayer {
	Methods met = new Methods();
	EffectSkills sk = new EffectSkills();
	public UUID id;
	public LivingEntity player = null;
	public Classes cClass;
	public int xp;
	public int maxxp;
	public int sk_pt;
	public int lvl;
	public int atk;
	public int spd;
	public int crt;
	public int rnd;
	public int hp;
	public int df;
	public int atk_m;
	public int spd_m;
	public int crt_m;
	public int rnd_m;
	public int hp_m;
	public int df_m;
	public double atk_c;
	public double spd_c;
	public double crt_c;
	public double rnd_c;
	public double hp_c;
	public double df_c;
	public double move;
	public double dodge;
	public double block;
	public boolean active;
	public boolean use;
	
	public DFPlayer(LivingEntity _player) {
		if(this.player == null) {
			this.player = _player;
			this.id = _player.getUniqueId();
			this.cClass = Classes.NONE;
			this.lvl = 1;
			this.sk_pt = 3;
			this.xp = 0;
			this.maxxp = 1500;
			this.atk = 0;
			this.spd = 0;
			this.crt = 0;
			this.rnd = 0;
			this.hp = 0;
			this.df = 0;
			this.atk_m = 0;
			this.spd_m = 0;
			this.crt_m = 0;
			this.rnd_m = 0;
			this.hp_m = 0;
			this.df_m = 0;
			this.active = false;
			this.use = true;
			this.move = 0.2;
			this.id = _player.getUniqueId();
		}
	}
	public DFPlayer() {
		//Placeholder to execute other methods
	}
	public void addPlayer(LivingEntity p) {
		if(!CustomEnchantments.getInstance().dfPlayerList.containsKey(p.getUniqueId())) {
			CustomEnchantments.getInstance().dfPlayerList.put(p.getUniqueId(), new DFPlayer(p));
		}
	}
	public DFPlayer getPlayer(LivingEntity p){
		return CustomEnchantments.getInstance().dfPlayerList.get(p.getUniqueId());
	}
	public boolean containsPlayer(LivingEntity p){
		if(CustomEnchantments.getInstance().dfPlayerList.containsKey(p.getUniqueId())) {
			return true;
		}
		else {
			return false;
		}
	}
	public LivingEntity returnPlayer() {
		return player;
	}
	//---------------------------------------------------------
	//Saving + Loading player profiles
	//---------------------------------------------------------
	public void loadPlayer() {
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
				if(uuidList.contains(this.id)) {
					String className = yml.getString("Skills.Players." + this.id + ".Class");
					className = className.toUpperCase();
					Classes classChoice = Enum.valueOf(Classes.class, className);
					this.cClass = classChoice;
					this.lvl = yml.getInt("Skills.Players." + this.id + ".Level");
					this.xp = yml.getInt("Skills.Players." + this.id + ".XP");
					this.maxxp = yml.getInt("Skills.Players." + this.id + ".MXP");
					this.sk_pt = yml.getInt("Skills.Players." + this.id + ".Skill Points");
					this.atk = yml.getInt("Skills.Players." + this.id + ".Attack Damage");
					this.spd = yml.getInt("Skills.Players." + this.id + ".Attack Speed");
					this.crt = yml.getInt("Skills.Players." + this.id + ".Critical Chance");
					this.rnd = yml.getInt("Skills.Players." + this.id + ".Ranged Damage");
					this.hp = yml.getInt("Skills.Players." + this.id + ".Health");
					this.df = yml.getInt("Skills.Players." + this.id + ".Defense");
					this.atk_m = yml.getInt("Skills.Players." + this.id + ".Attack Modifier");
					this.spd_m = yml.getInt("Skills.Players." + this.id + ".Speed Modifier");
					this.crt_m = yml.getInt("Skills.Players." + this.id + ".Critical Modifier");
					this.rnd_m = yml.getInt("Skills.Players." + this.id + ".Ranged Modifier");
					this.hp_m = yml.getInt("Skills.Players." + this.id + ".Health Modifier");
					this.df_m = yml.getInt("Skills.Players." + this.id + ".Defense Modifier");
					this.active = false;
					this.use = true;
					this.move = 0.2;
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
		if(!CustomEnchantments.getInstance().dfPlayerList.isEmpty()) {
			for(Entry<UUID, DFPlayer> entry : CustomEnchantments.getInstance().dfPlayerList.entrySet()) {
				if(Bukkit.getOfflinePlayer(entry.getKey()).getName() != null) {
					Bukkit.broadcastMessage(Bukkit.getOfflinePlayer(entry.getKey()).getName());
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
	//---------------------------------------------------------
	//Handling Calculations
	//---------------------------------------------------------
	public void resetCalculations() {
		double multiplier = 1.0;
		for(Entry<SkillState, States> state: this.getSkillStates().entrySet()) {
			if(state.getValue() == States.UP) {
				multiplier = 1.5;
			}
			else if(state.getValue() == States.NM) {
				multiplier = 1.0;
			}
			else if(state.getValue() == States.DW) {
				multiplier = 0.5;
			}
			if(state.getKey() == SkillState.ATK) {
				this.atk_c = this.atk * (1.50 * multiplier);
			}
			else if(state.getKey() == SkillState.SPD) {
				this.spd_c = this.spd * (0.50 * multiplier);
			}
			else if(state.getKey() == SkillState.CRT) {
				this.crt_c = this.crt * (0.50 * multiplier);
			}
			else if(state.getKey() == SkillState.RND) {
				this.rnd_c = this.rnd * (2.0 * multiplier);
			}
			else if(state.getKey() == SkillState.HP) {
				this.hp_c = this.hp * (5.00 * multiplier);
			}
			else if(state.getKey() == SkillState.DF) {
				this.df_c = this.df * (1.00 * multiplier);
			}
		}
	}
	//---------------------------------------------------------
	//States Handler
	//---------------------------------------------------------
	public HashMap<SkillState, States> getSkillStates() {
		HashMap<SkillState, States> states = new HashMap<SkillState, States>();
		if(this.cClass == Classes.WRATH) {
			states.put(SkillState.ATK, States.UP);
			states.put(SkillState.SPD, States.NM);
			states.put(SkillState.CRT, States.UP);
			states.put(SkillState.RND, States.NM);
			states.put(SkillState.HP, States.DW);
			states.put(SkillState.DF, States.DW);
			return states;
		}
		else if(this.cClass == Classes.ENVY) {
			states.put(SkillState.ATK, States.UP);
			states.put(SkillState.SPD, States.DW);
			states.put(SkillState.CRT, States.DW);
			states.put(SkillState.RND, States.UP);
			states.put(SkillState.HP, States.NM);
			states.put(SkillState.DF, States.NM);
			return states;
		}
		else if(this.cClass == Classes.LUST) {
			states.put(SkillState.ATK, States.DW);
			states.put(SkillState.SPD, States.DW);
			states.put(SkillState.CRT, States.NM);
			states.put(SkillState.RND, States.UP);
			states.put(SkillState.HP, States.UP);
			states.put(SkillState.DF, States.NM);
			return states;
		}
		else if(this.cClass == Classes.GLUTTONY) {
			states.put(SkillState.ATK, States.DW);
			states.put(SkillState.SPD, States.NM);
			states.put(SkillState.CRT, States.NM);
			states.put(SkillState.RND, States.DW);
			states.put(SkillState.HP, States.UP);
			states.put(SkillState.DF, States.UP);
			return states;
		}
		else if(this.cClass == Classes.PRIDE) {
			states.put(SkillState.ATK, States.NM);
			states.put(SkillState.SPD, States.UP);
			states.put(SkillState.CRT, States.DW);
			states.put(SkillState.RND, States.NM);
			states.put(SkillState.HP, States.DW);
			states.put(SkillState.DF, States.UP);
			return states;
		}
		else if(this.cClass == Classes.SLOTH) {
			states.put(SkillState.ATK, States.NM);
			states.put(SkillState.SPD, States.DW);
			states.put(SkillState.CRT, States.UP);
			states.put(SkillState.RND, States.NM);
			states.put(SkillState.HP, States.DW);
			states.put(SkillState.DF, States.UP);
			return states;
		}
		else if(this.cClass == Classes.GREED) {
			states.put(SkillState.ATK, States.NM);
			states.put(SkillState.SPD, States.UP);
			states.put(SkillState.CRT, States.NM);
			states.put(SkillState.RND, States.UP);
			states.put(SkillState.HP, States.DW);
			states.put(SkillState.DF, States.DW);
			return states;
		}
		else {
			return null;
		}
	}
	public States getSkillState(SkillState state) {
		return this.getSkillStates().get(state);
	}
	//---------------------------------------------------------
	//Id Handler
	//---------------------------------------------------------
	public UUID getUUID() {
		return this.id;
	}
	//---------------------------------------------------------
	//Class Handler
	//---------------------------------------------------------
	public Classes getPlayerClass() {
		return this.cClass;
	}
	
	public void setPlayerClass(Classes newClass) {
		this.cClass = newClass;
	}
	
	public boolean hasPlayerClass() {
		if(this.cClass == Classes.NONE) {
			return false;
		}
		return true;
	}
	//---------------------------------------------------------
	//Level Handler
	//---------------------------------------------------------
	public int getLevel() {
		return this.lvl;
	}
	
	public void setLevel(int amount) {
		this.lvl = amount;
	}
	
	public void addLevel(int amount) {
		this.lvl = this.lvl + amount;
	}
	
	public void removeLevel(int amount) {
		this.lvl = this.lvl - amount;
	}
	//---------------------------------------------------------
	//Experience Handler
	//---------------------------------------------------------
	public int getExperience() {
		return this.xp;
	}
	
	public void setExperience(int amount) {
		this.xp = amount;
	}
	
	public void addExperience(int amount) {
		this.xp = this.xp + amount;
	}
	
	public void removeExprience(int amount) {
		this.xp = this.xp - amount;
	}
	//---------------------------------------------------------
	//Max Experience Handler
	//---------------------------------------------------------
	public int getMaxExperience() {
		return this.maxxp;
	}
	
	public void setMaxExperience(int amount) {
		this.maxxp = amount;
	}
	
	public void addMaxExperience(int amount) {
		this.maxxp = this.maxxp + amount;
	}
	
	public void removeMaxExperience(int amount) {
		this.maxxp = this.maxxp - amount;
	}
	//---------------------------------------------------------
	//Skill Points Handler
	//---------------------------------------------------------
	public int getSkillPoints() {
		return this.sk_pt;
	}
	
	public void setSkillPoints(int amount) {
		this.sk_pt = amount;
	}
	
	public void addSkillPoints(int amount) {
		this.sk_pt = this.sk_pt + amount;
	}
	
	public void removeSkillPoints(int amount) {
		this.sk_pt = this.sk_pt - amount;
	}
	//---------------------------------------------------------
	//Attack Damage Handler
	//---------------------------------------------------------
	public int getAtk() {
		return this.atk;
	}
	
	public void setAtk(int amount) {
		this.atk = amount;
	}
	
	public void addAtk(int amount) {
		this.atk = this.atk + amount;
	}
	
	public void removeAtk(int amount) {
		this.atk = this.atk - amount;
	}
	//---------------------------------------------------------
	//Attack Speed Handler
	//---------------------------------------------------------
	public int getSpd() {
		return this.spd;
	}
	
	public void setSpd(int amount) {
		this.spd = amount;
	}
	
	public void addSpd(int amount) {
		this.spd = this.spd + amount;
	}
	
	public void removeSpd(int amount) {
		this.spd = this.spd - amount;
	}
	//---------------------------------------------------------
	//Critical Chance Handler
	//---------------------------------------------------------
	public int getCrt() {
		return this.crt;
	}
	
	public void setCrt(int amount) {
		this.crt = amount;
	}
	
	public void addCrt(int amount) {
		this.crt = this.crt + amount;
	}
	
	public void removeCrt(int amount) {
		this.crt = this.crt - amount;
	}
	//---------------------------------------------------------
	//Ranged Damage Handler
	//---------------------------------------------------------
	public int getRnd() {
		return this.rnd;
	}
	
	public void setRnd(int amount) {
		this.rnd = amount;
	}
	
	public void addRnd(int amount) {
		this.rnd = this.rnd + amount;
	}
	
	public void removeRnd(int amount) {
		this.rnd = this.rnd - amount;
	}
	//---------------------------------------------------------
	//Max Health Handler
	//---------------------------------------------------------
	public int getHp() {
		return this.hp;
	}
	
	public void setHp(int amount) {
		this.hp = amount;
	}
	
	public void addHp(int amount) {
		this.hp = this.hp + amount;
	}
	
	public void removeHp(int amount) {
		this.hp = this.hp - amount;
	}
	//---------------------------------------------------------
	//Armor Defene Handler
	//---------------------------------------------------------
	public int getDf() {
		return this.df;
	}
	
	public void setDf(int amount) {
		this.df = amount;
	}
	
	public void addDf(int amount) {
		this.df = this.df + amount;
	}
	
	public void removeDf(int amount) {
		this.df = this.df - amount;
	}
	//---------------------------------------------------------
	//Attack Damage Modifier Handler
	//---------------------------------------------------------
	public int getAtkMod() {
		return this.atk_m;
	}
	
	public void setAtkMod(int amount) {
		this.atk_m = amount;
	}
	
	public void addAtkMod(int amount) {
		this.atk_m = this.atk_m + amount;
	}
	
	public void removeAtkMod(int amount) {
		this.atk_m = this.atk_m - amount;
	}
	//---------------------------------------------------------
	//Attack Speed Modifier Handler
	//---------------------------------------------------------
	public int getSpdMod() {
		return this.spd_m;
	}
	
	public void setSpdMod(int amount) {
		this.spd_m = amount;
	}
	
	public void addSpdMod(int amount) {
		this.spd_m = this.spd_m + amount;
	}
	public void removeSpdMod(int amount) {
		this.spd_m = this.spd_m - amount;
	}
	//---------------------------------------------------------
	//Critical Chance Modifier Handler
	//---------------------------------------------------------
	public int getCrtMod() {
		return this.crt_m;
	}
	
	public void setCrtMod(int amount) {
		this.crt_m = amount;
	}
	
	public void addCrtMod(int amount) {
		this.crt_m = this.crt_m + amount;
	}
	
	public void removeCrtMod(int amount) {
		this.crt_m = this.crt_m - amount;
	}
	//---------------------------------------------------------
	//Ranged Damage Modifer Handler
	//---------------------------------------------------------
	public int getRndMod() {
		return this.rnd_m;
	}
	
	public void setRndMod(int amount) {
		this.rnd_m = amount;
	}
	
	public void addRndMod(int amount) {
		this.rnd_m = this.rnd_m + amount;
	}
	
	public void removeRndMod(int amount) {
		this.rnd_m = this.rnd_m - amount;
	}
	//---------------------------------------------------------
	//Max Health Modifier Handler
	//---------------------------------------------------------
	public int getHpMod() {
		return this.hp_m;
	}
	
	public void setHpMod(int amount) {
		this.hp_m = amount;
	}
	
	public void addHpMod(int amount) {
		this.hp_m = this.hp_m + amount;
	}
	
	public void removeHpMod(int amount) {
		this.hp_m = this.hp_m - amount;
	}
	//---------------------------------------------------------
	//Armor Defene Modifer Handler
	//---------------------------------------------------------
	public int getDfMod() {
		return this.df_m;
	}
	
	public void setDfMod(int amount) {
		this.df_m = amount;
	}
	
	public void addDfMod(int amount) {
		this.df_m = this.df_m + amount;
	}
	
	public void removeDfMod(int amount) {
		this.df_m = this.df_m - amount;
	}
	//---------------------------------------------------------
	//Attack Damage Calculated Handler
	//---------------------------------------------------------
	public double getAtkCal() {
		double temp = 0.00;
		if(this.player.hasMetadata("Attack")) {
			temp = this.player.getMetadata("Attack").get(0).asDouble();
		}
		return this.atk_c + temp;
	}
	
	public void setAtkCal(double amount) {
		this.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), amount));
	}
	
	public void addAtkCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Attack")) {
				temp = this.player.getMetadata("Attack").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Attack")) {
						now = p.player.getMetadata("Attack").get(0).asDouble();
					}
					p.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Attack")) {
				temp = this.player.getMetadata("Attack").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
		}
	}
	
	public void removeAtkCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double now = this.player.getMetadata("Attack").get(0).asDouble();
			p.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Attack")) {
						now = p.player.getMetadata("Attack").get(0).asDouble();
					}
					p.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double now = this.player.getMetadata("Attack").get(0).asDouble();
			this.player.setMetadata("Attack", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
		}
	}
	//---------------------------------------------------------
	//Attack Speed Calculated Handler
	//---------------------------------------------------------
	public double getSpdCal() {
		double temp = 0.00;
		if(this.player.hasMetadata("Speed")) {
			temp = this.player.getMetadata("Speed").get(0).asDouble();
		}
		return this.spd_c + temp;
	}
	
	public void setSpdCal(double amount) {
		this.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), amount));
		sk.attackSpeed(this.player);
	}
	
	public void addSpdCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Speed")) {
				temp = this.player.getMetadata("Speed").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Speed")) {
						now = p.player.getMetadata("Speed").get(0).asDouble();
					}
					p.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Speed")) {
				temp = this.player.getMetadata("Speed").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
		}
		sk.attackSpeed(this.player);
	}
	
	public void removeSpdCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Speed")) {
				temp = this.player.getMetadata("Speed").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Speed")) {
						now = p.player.getMetadata("Speed").get(0).asDouble();
					}
					p.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Speed")) {
				temp = this.player.getMetadata("Speed").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Speed", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
		}
		sk.attackSpeed(this.player);
	}
	//---------------------------------------------------------
	//Critical Chance Calculated Handler
	//---------------------------------------------------------
	public double getCrtCal() {
		double temp = 0.00;
		if(this.player.hasMetadata("Critical")) {
			temp = this.player.getMetadata("Critical").get(0).asDouble();
		}
		return this.crt_c + temp;
	}
	
	public void setCrtCal(double amount) {
		this.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), amount));
	}
	
	public void addCrtCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Critical")) {
				temp = this.player.getMetadata("Critical").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Critical")) {
						now = p.player.getMetadata("Critical").get(0).asDouble();
					}
					p.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Critical")) {
				temp = this.player.getMetadata("Critical").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
		}
	}
	
	public void removeCrtCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Critical")) {
				temp = this.player.getMetadata("Critical").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Critical")) {
						now = p.player.getMetadata("Critical").get(0).asDouble();
					}
					p.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Critical")) {
				temp = this.player.getMetadata("Critical").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Critical", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
		}
	}
	//---------------------------------------------------------
	//Ranged Damage Calculated Handler
	//---------------------------------------------------------
	public double getRndCal() {
		double temp = 0.00;
		if(this.player.hasMetadata("Ranged")) {
			temp = this.player.getMetadata("Ranged").get(0).asDouble();
		}
		return this.rnd_c + temp;
	}
	
	public void setRndCal(double amount) {
		this.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), amount));
	}
	
	public void addRndCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Ranged")) {
				temp = this.player.getMetadata("Ranged").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Ranged")) {
						now = p.player.getMetadata("Ranged").get(0).asDouble();
					}
					p.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Ranged")) {
				temp = this.player.getMetadata("Ranged").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
		}
	}
	
	public void removeRndCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Ranged")) {
				temp = this.player.getMetadata("Ranged").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Ranged")) {
						now = p.player.getMetadata("Ranged").get(0).asDouble();
					}
					p.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Ranged")) {
				temp = this.player.getMetadata("Ranged").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Ranged", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
		}
	}
	//---------------------------------------------------------
	//Max Health Calculated Handler
	//---------------------------------------------------------
	public double getHpCal() {
		double temp = 0.00;
		if(this.player.hasMetadata("Health")) {
			temp = this.player.getMetadata("Health").get(0).asDouble();
		}
		return this.hp_c + temp;
	}
	
	public void setHpCal(double amount) {
		this.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), amount));
		sk.changeHealth(this.player);
	}
	
	public void addHpCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Health")) {
				temp = this.player.getMetadata("Health").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Health")) {
						now = p.player.getMetadata("Health").get(0).asDouble();
					}
					p.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Health")) {
				temp = this.player.getMetadata("Health").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
		}
		sk.changeHealth(this.player);
	}
	
	public void removeHpCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Health")) {
				temp = this.player.getMetadata("Health").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Health")) {
						now = p.player.getMetadata("Health").get(0).asDouble();
					}
					p.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Health")) {
				temp = this.player.getMetadata("Health").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Health", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
		}
		sk.changeHealth(this.player);
	}
	public double getHP() {
		return this.player.getHealth();
	}
	public double getMaxHp() {
		return this.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	}
	//---------------------------------------------------------
	//Armor Defene Calculated Handler
	//---------------------------------------------------------
	public double getDfCal() {
		double temp = 0.00;
		if(this.player.hasMetadata("Defense")) {
			temp = this.player.getMetadata("Defense").get(0).asDouble();
		}
		return this.df_c + temp;
	}
	
	public void setDfCal(double amount) {
		this.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), amount));
		sk.runDefense(this.player);
	}
	
	public void addDfCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Defense")) {
				temp = this.player.getMetadata("Defense").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Defense")) {
						now = p.player.getMetadata("Defense").get(0).asDouble();
					}
					p.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Defense")) {
				temp = this.player.getMetadata("Defense").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
		}
		sk.runDefense(this.player);
	}
	
	public void removeDfCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			double temp = 0.00;
			if(p.player.hasMetadata("Defense")) {
				temp = this.player.getMetadata("Defense").get(0).asDouble();
			}
			double now = temp;
			p.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
			new BukkitRunnable() {
				public void run() {
					double now = 0.00;
					if(p.player.hasMetadata("Defense")) {
						now = p.player.getMetadata("Defense").get(0).asDouble();
					}
					p.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), now + amount));
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			double temp = 0.00;
			if(this.player.hasMetadata("Defense")) {
				temp = this.player.getMetadata("Defense").get(0).asDouble();
			}
			double now = temp;
			this.player.setMetadata("Defense", new FixedMetadataValue(CustomEnchantments.getInstance(), now - amount));
		}
		sk.runDefense(this.player);
	}
	//---------------------------------------------------------
	//Ability Activation Handler
	//---------------------------------------------------------
	public boolean getActivation() {
		return this.active;
	}
	public void setActivation(boolean act) {
		this.active = act;
	}
	//---------------------------------------------------------
	//Ability Activation Handler
	//---------------------------------------------------------
	public boolean getUseable() {
		return this.use;
	}
	public void setUseable(boolean act) {
		this.use = act;
	}
	//---------------------------------------------------------
	//Armor Defene Calculated Handler
	//---------------------------------------------------------
	public double getMove() {
		return this.move;
	}
	
	public void setMove(double amount) {
		this.move = amount;
	}
	
	public void addMove(double amount, long time) {
		if(time != 0) {
			this.move = this.move + amount;
			DFPlayer p = this;
			p.returnPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.move);
			new BukkitRunnable() {
				public void run() {
					p.move = p.move - amount;
					p.returnPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.move);
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.move = this.move + amount;
		}
		sk.runDefense(this.player);
	}
	
	public void removeMove(double amount, long time) {
		if(time != 0) {
			this.move = this.move - amount;
			DFPlayer p = this;
			p.returnPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.move);
			new BukkitRunnable() {
				public void run() {
					p.move = p.move + amount;
					p.returnPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.move);
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.move = this.move - amount;
		}
		sk.runDefense(this.player);
	}
}
