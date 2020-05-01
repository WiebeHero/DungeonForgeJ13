package me.WiebeHero.DFPlayerPackage;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.EnumSkills.SkillState;
import me.WiebeHero.DFPlayerPackage.EnumSkills.Stats;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;
import me.WiebeHero.DFPlayerPackage.State.States;
import net.md_5.bungee.api.ChatColor;

public class DFPlayer {
	private UUID id;
	private Classes cClass;
	private double money;
	private int xp;
	private int maxxp;
	private int sk_pt;
	private int lvl;
	private int atk;
	private int spd;
	private int crt;
	private int rnd;
	private int hp;
	private int df;
	private int atk_m;
	private int spd_m;
	private int crt_m;
	private int rnd_m;
	private int hp_m;
	private int df_m;
	private double atk_c;
	private double spd_c;
	private double crt_c;
	private double rnd_c;
	private double hp_c;
	private double df_c;
	private float move;
	private boolean active;
	private boolean use;
	private double atk_ct;
	private double spd_ct;
	private double crt_ct;
	private double rnd_ct;
	private double df_ct;
	private double hp_ct;
	
	private double bAtkInc = 1.50;
	private double bSpdInc = 0.50;
	private double bCrtInc = 0.50;
	private double bRndInc = 2.0;
	private double bHpInc = 5.00;
	private double bDfInc = 1.25;
	
	private double mxpMultiplier = 1.08;
	
	private double atkInc;
	private double spdInc;
	private double crtInc;
	private double rndInc;
	private double hpInc;
	private double dfInc;
	
	private HashMap<Stats, Double> statList;
	
	public DFPlayer(LivingEntity _player) {
		if(_player != null) {
			this.id = _player.getUniqueId();
			this.cClass = Classes.NONE;
			this.lvl = 1;
			this.sk_pt = 3;
			this.xp = 0;
			this.maxxp = 750;
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
			this.move = 0.2F;
			this.money = 1000.00;
			this.atk_ct = 0.00;
			this.spd_ct = 0.00;
			this.crt_ct = 0.00;
			this.rnd_ct = 0.00;
			this.hp_ct = 0.00;
			this.df_ct = 0.00;
			this.statList = new HashMap<Stats, Double>();
		}
	}
	public DFPlayer(UUID uuid) {
		this.id = uuid;
		this.cClass = Classes.NONE;
		this.lvl = 1;
		this.sk_pt = 3;
		this.xp = 0;
		this.maxxp = 750;
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
		this.move = 0.2F;
		this.money = 1000.00;
		this.atk_ct = 0.00;
		this.spd_ct = 0.00;
		this.crt_ct = 0.00;
		this.rnd_ct = 0.00;
		this.hp_ct = 0.00;
		this.df_ct = 0.00;
		this.statList = new HashMap<Stats, Double>();
	}
	public void resetIncreases() {
		for(Entry<SkillState, States> state: this.getSkillStates().entrySet()) {
			double multiplier = 1.0;
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
				this.atkInc = this.bAtkInc * multiplier;
				this.atk_c = this.atkInc * this.getAtk();
			}
			else if(state.getKey() == SkillState.SPD) {
				this.spdInc = this.bSpdInc * multiplier;
				this.spd_c = this.spdInc * this.getSpd();
			}
			else if(state.getKey() == SkillState.CRT) {
				this.crtInc = this.bCrtInc * multiplier;
				this.crt_c = this.crtInc * this.getCrt();
			}
			else if(state.getKey() == SkillState.RND) {
				this.rndInc = this.bRndInc * multiplier;
				this.rnd_c = this.rndInc * this.getRnd();
			}
			else if(state.getKey() == SkillState.HP) {
				this.hpInc = this.bHpInc * multiplier;
				this.hp_c = this.hpInc * this.getHp();
			}
			else if(state.getKey() == SkillState.DF) {
				this.dfInc = this.bDfInc * multiplier;
				this.df_c = this.dfInc * this.getDf();
			}
		}
		this.changeHealth();
		this.runDefense();
		this.attackSpeed();
	}
	//---------------------------------------------------------
	//Handling Calculations
	//---------------------------------------------------------
	public void resetAbilityStats() {
		Classes c = this.cClass;
		if(c == Classes.WRATH) {
			this.statList.put(Stats.DAMAGE, c.baseList.get(Stats.DAMAGE) + c.incList.get(Stats.DAMAGE) * this.lvl);
			this.statList.put(Stats.RANGE, c.baseList.get(Stats.RANGE) + c.incList.get(Stats.RANGE) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
		}
		else if(c == Classes.ENVY) {
			this.statList.put(Stats.ATK_INC, c.baseList.get(Stats.ATK_INC) + c.incList.get(Stats.ATK_INC) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
			this.statList.put(Stats.DURATION, c.baseList.get(Stats.DURATION) + c.incList.get(Stats.DURATION) * this.lvl);
		}
		else if(c == Classes.LUST) {
			this.statList.put(Stats.HEAL, c.baseList.get(Stats.HEAL) + c.incList.get(Stats.HEAL) * this.lvl);
			this.statList.put(Stats.DAMAGE, c.baseList.get(Stats.DAMAGE) + c.incList.get(Stats.DAMAGE) * this.lvl);
			this.statList.put(Stats.RANGE, c.baseList.get(Stats.RANGE) + c.incList.get(Stats.RANGE) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
		}
		else if(c == Classes.GLUTTONY) {
			this.statList.put(Stats.HEAL, c.baseList.get(Stats.HEAL) + c.incList.get(Stats.HEAL) * this.lvl);
			this.statList.put(Stats.DF_INC, c.baseList.get(Stats.DF_INC) + c.incList.get(Stats.DF_INC) * this.lvl);
			this.statList.put(Stats.DURATION, c.baseList.get(Stats.DURATION) + c.incList.get(Stats.DURATION) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
		}
		else if(c == Classes.PRIDE) {
			this.statList.put(Stats.SPD_INC, c.baseList.get(Stats.SPD_INC) + c.incList.get(Stats.SPD_INC) * this.lvl);
			this.statList.put(Stats.DF_INC, c.baseList.get(Stats.DF_INC) + c.incList.get(Stats.DF_INC) * this.lvl);
			this.statList.put(Stats.DURATION, c.baseList.get(Stats.DURATION) + c.incList.get(Stats.DURATION) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
		}
		else if(c == Classes.SLOTH) {
			this.statList.put(Stats.ARROW_COUNT, c.baseList.get(Stats.ARROW_COUNT) + c.incList.get(Stats.ARROW_COUNT) * this.lvl);
			this.statList.put(Stats.DAMAGE, c.baseList.get(Stats.DAMAGE) + c.incList.get(Stats.DAMAGE) * this.lvl);
			this.statList.put(Stats.DF_INC, c.baseList.get(Stats.DF_INC) + c.incList.get(Stats.DF_INC) * this.lvl);
			this.statList.put(Stats.DURATION, c.baseList.get(Stats.DURATION) + c.incList.get(Stats.DURATION) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
		}
		else if(c == Classes.GREED) {
			this.statList.put(Stats.DF_DEC, c.baseList.get(Stats.DF_DEC) + c.incList.get(Stats.DF_DEC) * this.lvl);
			this.statList.put(Stats.SPD_INC, c.baseList.get(Stats.SPD_INC) + c.incList.get(Stats.SPD_INC) * this.lvl);
			this.statList.put(Stats.COOLDOWN, c.baseList.get(Stats.COOLDOWN) - c.incList.get(Stats.COOLDOWN) * this.lvl);
			this.statList.put(Stats.DURATION, c.baseList.get(Stats.DURATION) + c.incList.get(Stats.DURATION) * this.lvl);
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
			states.put(SkillState.RND, States.DW);
			states.put(SkillState.HP, States.NM);
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
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.id);
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
	
	public double getExperienceMultiplier() {
		return this.mxpMultiplier;
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
	//Get increases
	//---------------------------------------------------------
	public double getAtkIncrease() {
		return this.atkInc;
	}
	public double getSpdIncrease() {
		return this.spdInc;
	}
	public double getCrtIncrease() {
		return this.crtInc;
	}
	public double getRndIncrease() {
		return this.rndInc;
	}
	public double getHpIncrease() {
		return this.hpInc;
	}
	public double getDfIncrease() {
		return this.dfInc;
	}
	//---------------------------------------------------------
	//Get base increases
	//---------------------------------------------------------
	public double getBaseAtkIncrease() {
		return this.bAtkInc;
	}
	public double getBaseSpdIncrease() {
		return this.bSpdInc;
	}
	public double getBaseCrtIncrease() {
		return this.bCrtInc;
	}
	public double getBaseRndIncrease() {
		return this.bRndInc;
	}
	public double getBaseHpIncrease() {
		return this.bHpInc;
	}
	public double getBaseDfIncrease() {
		return this.bDfInc;
	}
	//---------------------------------------------------------
	//Get stats
	//---------------------------------------------------------
	public HashMap<Stats, Double> getStatList(){
		return this.statList;
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
	//Money Handler
	//---------------------------------------------------------
	public double getMoney() {
		return this.money;
	}
	
	public void setMoney(double amount) {
		this.money = amount;
	}
	
	public void addMoney(double amount) {
		this.money = this.money + amount;
	}
	
	public void removeMoney(double amount) {
		this.money = this.money - amount;
	}
	//---------------------------------------------------------
	//Attack Damage Calculated Handler
	//---------------------------------------------------------
	public double getAtkCal() {
		return this.atk_c + this.atk_ct;
	}
	
	public void setAtkCal(double amount) {
		this.atk_ct = amount;
	}
	
	public void addAtkCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.atk_ct += amount;
			new BukkitRunnable() {
				public void run() {
					p.atk_ct -= amount;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.atk_ct += amount;
		}
	}
	
	public void removeAtkCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.atk_ct -= amount;
			new BukkitRunnable() {
				public void run() {
					p.atk_ct += amount;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.atk_ct -= amount;
		}
	}
	//---------------------------------------------------------
	//Attack Speed Calculated Handler
	//---------------------------------------------------------
	public double getSpdCal() {
		return this.spd_c + this.spd_ct;
	}
	
	public void setSpdCal(double amount) {
		this.spd_ct = amount;
		this.attackSpeed();
	}
	
	public void addSpdCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.spd_ct += amount;
			this.attackSpeed();
			new BukkitRunnable() {
				public void run() {
					p.spd_ct -= amount;
					p.attackSpeed();
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.spd_ct += amount;
			this.attackSpeed();
		}
	}
	
	public void removeSpdCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.spd_ct -= amount;
			this.attackSpeed();
			new BukkitRunnable() {
				public void run() {
					p.spd_ct += amount;
					p.attackSpeed();
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.spd_ct -= amount;
			this.attackSpeed();
		}
	}
	//---------------------------------------------------------
	//Critical Chance Calculated Handler
	//---------------------------------------------------------
	public double getCrtCal() {
		return this.crt_c + this.crt_ct;
	}
	
	public void setCrtCal(double amount) {
		this.crt_ct = amount;
	}
	
	public void addCrtCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.crt_ct += amount;
			new BukkitRunnable() {
				public void run() {
					p.crt_ct -= amount;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.crt_ct += amount;
		}
	}
	
	public void removeCrtCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.crt_ct -= amount;
			new BukkitRunnable() {
				public void run() {
					p.crt_ct += amount;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.crt_ct -= amount;
		}
	}
	//---------------------------------------------------------
	//Ranged Damage Calculated Handler
	//---------------------------------------------------------
	public double getRndCal() {
		return this.rnd_c + this.rnd_ct;
	}
	
	public void setRndCal(double amount) {
		this.rnd_ct = amount;
	}
	
	public void addRndCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.rnd_ct += amount;
			new BukkitRunnable() {
				public void run() {
					p.rnd_ct -= amount;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.rnd_ct += amount;
		}
	}
	
	public void removeRndCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.rnd_ct -= amount;
			new BukkitRunnable() {
				public void run() {
					p.rnd_ct += amount;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.rnd_ct -= amount;
		}
	}
	//---------------------------------------------------------
	//Max Health Calculated Handler
	//---------------------------------------------------------
	public double getHpCal() {
		return this.hp_c + this.hp_ct;
	}
	
	public void setHpCal(double amount) {
		this.hp_ct = amount;
		this.health();
	}
	
	public void addHpCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.hp_ct += amount;
			this.health();
			new BukkitRunnable() {
				public void run() {
					p.hp_ct -= amount;
					p.health();
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.hp_ct += amount;
			this.health();
		}
	}
	
	public void removeHpCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.hp_ct -= amount;
			this.health();
			new BukkitRunnable() {
				public void run() {
					p.hp_ct += amount;
					p.health();
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.hp_ct -= amount;
			this.health();
		}
	}
	private void health() {
		Entity e = Bukkit.getEntity(this.id);
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			double newHealth = 20.00 / 100.00 * (this.getHpCal() + 100.00);
			double roundOff1 = (double) Math.round(newHealth * 100.00) / 100.00;
			ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(roundOff1);
		}
	}
	//---------------------------------------------------------
	//Armor Defene Calculated Handler
	//---------------------------------------------------------
	public double getDfCal() {
		return this.df_c + this.df_ct;
	}
	
	public void setDfCal(double amount) {
		this.df_ct = amount;
		this.runDefense();
	}
	
	public void addDfCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.df_ct += amount;
			this.runDefense();
			new BukkitRunnable() {
				public void run() {
					p.df_ct -= amount;
					p.runDefense();
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.runDefense();
			this.df_ct += amount;
		}
	}
	
	public void removeDfCal(double amount, long time) {
		if(time != 0) {
			DFPlayer p = this;
			this.df_ct -= amount;
			this.runDefense();
			new BukkitRunnable() {
				public void run() {
					p.df_ct += amount;
					p.runDefense();
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.df_ct -= amount;
			this.runDefense();
		}
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
	public float getMove() {
		return this.move;
	}
	
	public void setMove(float amount) {
		this.move = amount;
		Entity entity = Bukkit.getEntity(this.id);
		if(entity != null) {
			if(entity instanceof LivingEntity) {
				LivingEntity lEnt = (LivingEntity) entity;
				if(lEnt instanceof Player) {
					Player p = (Player) lEnt;
					p.setWalkSpeed(this.move);
				}
				else {
					lEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.move);
				}
			}
		}
	}
	
	public void addMove(float amount, long time) {
		if(time != 0) {
			this.move = this.move + amount;
			Entity entity = Bukkit.getEntity(this.id);
			if(entity != null) {
				if(entity instanceof LivingEntity) {
					LivingEntity lEnt = (LivingEntity) entity;
					if(lEnt instanceof Player) {
						Player p = (Player) lEnt;
						p.setWalkSpeed(this.move);
					}
					else {
						lEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.move);
					}
				}
			}
			DFPlayer p = this;
			new BukkitRunnable() {
				public void run() {
					p.move = p.move - amount;
					Entity entity = Bukkit.getEntity(p.id);
					if(entity != null) {
						if(entity instanceof LivingEntity) {
							LivingEntity lEnt = (LivingEntity) entity;
							if(lEnt instanceof Player) {
								Player pl = (Player) lEnt;
								pl.setWalkSpeed(p.move);
							}
							else {
								lEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.move);
							}
						}
					}
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.move += amount;
			Entity entity = Bukkit.getEntity(this.id);
			if(entity != null) {
				if(entity instanceof LivingEntity) {
					LivingEntity lEnt = (LivingEntity) entity;
					if(lEnt instanceof Player) {
						Player p = (Player) lEnt;
						p.setWalkSpeed(this.move);
					}
					else {
						lEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.move);
					}
				}
			}
		}
	}
	
	public void removeMove(float amount, long time) {
		if(time != 0) {
			this.move = this.move - amount;
			DFPlayer p = this;
			new BukkitRunnable() {
				public void run() {
					p.move = p.move + amount;
					Entity entity = Bukkit.getEntity(p.id);
					if(entity != null) {
						if(entity instanceof LivingEntity) {
							LivingEntity lEnt = (LivingEntity) entity;
							if(lEnt instanceof Player) {
								Player pl = (Player) lEnt;
								pl.setWalkSpeed(p.move);
							}
							else {
								lEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.move);
							}
						}
					}
				}
			}.runTaskLater(CustomEnchantments.getInstance(), time);
		}
		else {
			this.move -= amount;
			Entity entity = Bukkit.getEntity(this.id);
			if(entity != null) {
				if(entity instanceof LivingEntity) {
					LivingEntity lEnt = (LivingEntity) entity;
					if(lEnt instanceof Player) {
						Player p = (Player) lEnt;
						p.setWalkSpeed(this.move);
					}
					else {
						lEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.move);
					}
				}
			}
		}
	}
	public double getHealth() {
		Player p = Bukkit.getPlayer(this.getUUID());
		if(p != null) {
			return p.getHealth();
		}
		return 0.00;
	}
	public double getMaxHealth() {
		Player p = Bukkit.getPlayer(this.getUUID());
		if(p != null) {
			return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		}
		return 20.00;
	}
	public void attackSpeed() {
		DFPlayer dfPlayer = this;
		new BukkitRunnable() {
			public void run() {
				//-------------------------------------------------------
				//Player Attack Speed Handler
				//-------------------------------------------------------
				Player p = Bukkit.getPlayer(dfPlayer.getUUID());
				if(p != null && p.isOnline()) {
					ItemStack item = p.getEquipment().getItemInMainHand();
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								if(ChatColor.stripColor(item.getItemMeta().getLore().toString()).contains("Attack Speed:")) {
									if(item.getType() != Material.BOW) {
										String check1 = "";
										String check2 = "";
										for(String s : item.getItemMeta().getLore()) {
											if(ChatColor.stripColor(s).contains("Attack Speed:")) {
												check1 = ChatColor.stripColor(s);
											}
											else if(ChatColor.stripColor(s).contains("Attack Damage:")) {
												check2 = ChatColor.stripColor(s);
											}
										}
										check1 = check1.replaceAll("[^\\d.]", "");
										double attackSpeed = Double.parseDouble(check1);
										check2 = check2.replaceAll("[^\\d.]", "");
										double attackDamage = Double.parseDouble(check2);
										p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
										if(p instanceof Player) {
											p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100.00 * (dfPlayer.getSpdCal() + 100.00));
										}
									}
									else {
										String check1 = "";
										for(String s : item.getItemMeta().getLore()) {
											if(s.contains("Attack Speed")) {
												check1 = ChatColor.stripColor(s);
											}
										}
										check1 = check1.replaceAll("[^\\d.]", "");
										double attackSpeed = Double.parseDouble(check1);
										p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
										if(p instanceof Player) {
											p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100.00 * (dfPlayer.getSpdCal() + 100.00));
										}
									}
								}
								else {
									p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
									if(p instanceof Player) {
										p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
									}
								}
							}
							else {
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								if(p instanceof Player) {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								}
							}
						}
						else {
							p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
							if(p instanceof Player) {
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							}
						}
					}
					else {
						p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
						if(p instanceof Player) {
							p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
						}
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	public void changeHealth() {
		Entity entity = Bukkit.getEntity(this.getUUID());
		if(entity instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) entity;
			if(!ent.isDead()) {
				double baseHealth = 20.00;
				if(entity instanceof Player) {
					baseHealth = 20.00;
				}
				else {
					baseHealth = 25.00;
				}
				double newHealth = baseHealth;
				newHealth = baseHealth / 100.00 * (this.getHpCal() + 100.00);
				double roundOff1 = (double) Math.round(newHealth * 100.00) / 100.00;
				ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(roundOff1);
			}
		}
	}
	public void runDefense() {
		DFPlayer dfPlayer = this;
		new BukkitRunnable() {
			public void run() {
				Entity entity = Bukkit.getEntity(dfPlayer.getUUID());
				if(entity instanceof LivingEntity) {
					LivingEntity ent = (LivingEntity) entity;
					double armorD = 0.0;
					double armorT = 0.0;
					for(ItemStack item : ent.getEquipment().getArmorContents()) {
						if(item != null) {
							if(item.hasItemMeta()) {
								if(item.getItemMeta().hasLore()) {
									if(item.getItemMeta().getLore().toString().contains("Armor Defense") && item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
										String check1 = "";
										String check2 = "";
										for(String s : item.getItemMeta().getLore()) {
											if(s.contains("Armor Defense")) {
												check1 = ChatColor.stripColor(s);
											}
											else if(s.contains("Armor Toughness")){
												check2 = ChatColor.stripColor(s);
											}
										}
										check1 = check1.replaceAll("[^\\d.]", "");
										check2 = check2.replaceAll("[^\\d.]", "");
										double armorDT = Double.parseDouble(check1);
										double armorTT = Double.parseDouble(check2);
										armorD = armorD + armorDT / 100.00 * (dfPlayer.getDfCal() + 100.00);
										armorT = armorT + armorTT / 100.00 * (dfPlayer.getDfCal() + 100.00);
									}
								}
							}
						}
					}
					ItemStack item = ent.getEquipment().getItemInOffHand();
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								if(item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
									String check1 = "";
									for(String s : item.getItemMeta().getLore()) {
										if(s.contains("Armor Toughness")) {
											check1 = ChatColor.stripColor(s);
										}
									}
									check1 = check1.replaceAll("[^\\d.]", "");
									double armorTT = Double.parseDouble(check1);
									armorT = armorT + armorTT / 100.00 * (dfPlayer.getDfCal() + 100.00);
								}
							}
						}
					}
					ent.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armorD);
					ent.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(armorT);
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	public Pair<Double, Double> getAbsoluteArmorPoints() {
		DFPlayer dfPlayer = this;
		Entity entity = Bukkit.getEntity(dfPlayer.getUUID());
		double armorD = 0.0;
		double armorT = 0.0;
		if(entity instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) entity;
			for(ItemStack item : ent.getEquipment().getArmorContents()) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							if(item.getItemMeta().getLore().toString().contains("Armor Defense") && item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
								String check1 = "";
								String check2 = "";
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Armor Defense")) {
										check1 = ChatColor.stripColor(s);
									}
									else if(s.contains("Armor Toughness")){
										check2 = ChatColor.stripColor(s);
									}
								}
								check1 = check1.replaceAll("[^\\d.]", "");
								check2 = check2.replaceAll("[^\\d.]", "");
								double armorDT = Double.parseDouble(check1);
								double armorTT = Double.parseDouble(check2);
								armorD = armorD + armorDT / 100.00 * (dfPlayer.getDfCal() + 100.00);
								armorT = armorT + armorTT / 100.00 * (dfPlayer.getDfCal() + 100.00);
							}
						}
					}
				}
			}
			ItemStack item = ent.getEquipment().getItemInOffHand();
			if(item != null) {
				if(item.hasItemMeta()) {
					if(item.getItemMeta().hasLore()) {
						if(item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
							String check1 = "";
							for(String s : item.getItemMeta().getLore()) {
								if(s.contains("Armor Toughness")) {
									check1 = ChatColor.stripColor(s);
								}
							}
							check1 = check1.replaceAll("[^\\d.]", "");
							double armorTT = Double.parseDouble(check1);
							armorT = armorT + armorTT / 100.00 * (dfPlayer.getDfCal() + 100.00);
						}
					}
				}
			}
		}
		return new Pair<Double, Double>(armorD, armorT);
	}
}
