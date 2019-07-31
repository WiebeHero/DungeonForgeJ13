package Skills;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import Skills.Enums.Classes;
import Skills.SkillEnum.Skills;
import Skills.SkillsUD.UD;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class PlayerClass implements Listener{
	//Skills
	public static HashMap<UUID, HashMap<Skills, Integer>> outerMapSkills = new HashMap<UUID, HashMap<Skills, Integer>>();
	public static HashMap<UUID, HashMap<Skills, UD>> outerMapUD = new HashMap<UUID, HashMap<Skills, UD>>();
	//Specific Class
	public static HashMap<UUID, Classes> classList = new HashMap<UUID, Classes>();
	//Calculated Skills
	public static HashMap<UUID, HashMap<Skills, Double>> outerMapCalculatedSkills = new HashMap<UUID, HashMap<Skills, Double>>();
	/**
	 * 
	 * @param uuid The players UUID
	 * @param classChosen The specific class the player has chosen for their DungeonForge playthrough
	 */
	public void createProfile(UUID uuid, Classes classChosen) {
		outerMapSkills.put(uuid, new HashMap<Skills, Integer>());
		outerMapCalculatedSkills.put(uuid, new HashMap<Skills, Double>());
		HashMap<Skills, Integer> innerMapSkills = outerMapSkills.get(uuid);
		HashMap<Skills, Double> innerMapCalculatedSkills = outerMapCalculatedSkills.get(uuid);
		innerMapSkills.put(Skills.LEVEL, 1);
		innerMapSkills.put(Skills.XP, 0);
		innerMapSkills.put(Skills.MAXXP, 1500);
		innerMapSkills.put(Skills.SKILL_POINTS, 3);
		innerMapSkills.put(Skills.ATTACK_DAMAGE, 0);
		innerMapSkills.put(Skills.ATTACK_SPEED, 0);
		innerMapSkills.put(Skills.CRITICAL_CHANCE, 0);
		innerMapSkills.put(Skills.RANGED_DAMAGE, 0);
		innerMapSkills.put(Skills.MAX_HEALTH, 0);
		innerMapSkills.put(Skills.ARMOR_DEFENSE, 0);
		innerMapSkills.put(Skills.ATTACK_DAMAGE_MODIFIER, 0);
		innerMapSkills.put(Skills.ATTACK_SPEED_MODIFIER, 0);
		innerMapSkills.put(Skills.CRITICAL_CHANCE_MODIFIER, 0);
		innerMapSkills.put(Skills.RANGED_DAMAGE_MODIFIER, 0);
		innerMapSkills.put(Skills.MAX_HEALTH_MODIFIER, 0);
		innerMapSkills.put(Skills.ARMOR_DEFENSE_MODIFIER, 0);
		innerMapCalculatedSkills.put(Skills.ATTACK_DAMAGE_CALC, 0.00);
		innerMapCalculatedSkills.put(Skills.ATTACK_SPEED_CALC, 0.00);
		innerMapCalculatedSkills.put(Skills.CRITICAL_CHANCE_CALC, 0.00);
		innerMapCalculatedSkills.put(Skills.RANGED_DAMAGE_CALC, 0.00);
		innerMapCalculatedSkills.put(Skills.MAX_HEALTH_CALC, 0.00);
		innerMapCalculatedSkills.put(Skills.ARMOR_DEFENSE_CALC, 0.00);
		innerMapCalculatedSkills.put(Skills.ATTACK_DAMAGE_EXTRA, 0.00);
		innerMapCalculatedSkills.put(Skills.ATTACK_SPEED_EXTRA, 0.00);
		innerMapCalculatedSkills.put(Skills.CRITICAL_CHANCE_EXTRA, 0.00);
		innerMapCalculatedSkills.put(Skills.RANGED_DAMAGE_EXTRA, 0.00);
		innerMapCalculatedSkills.put(Skills.MAX_HEALTH_EXTRA, 0.00);
		innerMapCalculatedSkills.put(Skills.ARMOR_DEFENSE_EXTRA, 0.00);
		outerMapSkills.put(uuid, innerMapSkills);
		CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(outerMapSkills.get(uuid) + "");
		CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(innerMapSkills + "");
		outerMapUD.put(uuid, new HashMap<Skills, UD>());
		HashMap<Skills, UD> innerMapUD = outerMapUD.get(uuid);
		if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.ENVY) {
			innerMapUD.put(Skills.ATTACK_DAMAGE, UD.UPSIDE);
		}
		else if(this.getClass(uuid) == Classes.LUST || this.getClass(uuid) == Classes.GLUTTONY) {
			innerMapUD.put(Skills.ATTACK_DAMAGE, UD.DOWNSIDE);
		}
		else {
			innerMapUD.put(Skills.ATTACK_DAMAGE, UD.NEUTRAL);
		}
		if(this.getClass(uuid) == Classes.PRIDE || this.getClass(uuid) == Classes.GREED) {
			innerMapUD.put(Skills.ATTACK_SPEED, UD.UPSIDE);
		}
		else if(this.getClass(uuid) == Classes.SLOTH || this.getClass(uuid) == Classes.ENVY) {
			innerMapUD.put(Skills.ATTACK_SPEED, UD.DOWNSIDE);
		}
		else {
			innerMapUD.put(Skills.ATTACK_SPEED, UD.NEUTRAL);
		}
		if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.SLOTH) {
			innerMapUD.put(Skills.CRITICAL_CHANCE, UD.UPSIDE);
		}
		else if(this.getClass(uuid) == Classes.PRIDE || this.getClass(uuid) == Classes.ENVY) {
			innerMapUD.put(Skills.CRITICAL_CHANCE, UD.DOWNSIDE);
		}
		else {
			innerMapUD.put(Skills.CRITICAL_CHANCE, UD.NEUTRAL);
		}
		if(this.getClass(uuid) == Classes.LUST || this.getClass(uuid) == Classes.GREED || this.getClass(uuid) == Classes.ENVY) {
			innerMapUD.put(Skills.RANGED_DAMAGE, UD.UPSIDE);
		}
		else if(this.getClass(uuid) == Classes.GLUTTONY || this.getClass(uuid) == Classes.SLOTH) {
			innerMapUD.put(Skills.RANGED_DAMAGE, UD.DOWNSIDE);
		}
		else {
			innerMapUD.put(Skills.RANGED_DAMAGE, UD.NEUTRAL);
		}
		if(this.getClass(uuid) == Classes.LUST || this.getClass(uuid) == Classes.GLUTTONY) {
			innerMapUD.put(Skills.MAX_HEALTH, UD.UPSIDE);
		}
		else if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.GREED || this.getClass(uuid) == Classes.PRIDE) {
			innerMapUD.put(Skills.MAX_HEALTH, UD.DOWNSIDE);
		}
		else {
			innerMapUD.put(Skills.MAX_HEALTH, UD.NEUTRAL);
		}
		if(this.getClass(uuid) == Classes.GLUTTONY || this.getClass(uuid) == Classes.SLOTH || this.getClass(uuid) == Classes.PRIDE) {
			innerMapUD.put(Skills.ARMOR_DEFENSE, UD.UPSIDE);
		}
		else if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.GREED) {
			innerMapUD.put(Skills.ARMOR_DEFENSE, UD.DOWNSIDE);
		}
		else {
			innerMapUD.put(Skills.ARMOR_DEFENSE, UD.NEUTRAL);
		}
		outerMapUD.put(uuid, innerMapUD);
		classList.put(uuid, classChosen);
	}
	public void registerProfiles(YamlConfiguration yml, File f) {
		ArrayList<String> idList = new ArrayList<String>(yml.getConfigurationSection("Skills.Players").getKeys(false));
		for(int i = 0; i < idList.size(); i++) {
			UUID uuid = UUID.fromString(idList.get(i));
			String className = yml.getString("Skills.Players." + uuid.toString() + ".Class");
			className = className.toUpperCase();
			Classes classChoice = Enum.valueOf(Classes.class, className);
			classList.put(uuid, classChoice);
			HashMap<Skills, Integer> innerMap = new HashMap<Skills, Integer>();
			innerMap.put(Skills.LEVEL, yml.getInt("Skills.Players." + uuid.toString() + ".Level"));
			innerMap.put(Skills.XP, yml.getInt("Skills.Players." + uuid.toString() + ".XP"));
			innerMap.put(Skills.MAXXP, yml.getInt("Skills.Players." + uuid.toString() + ".MXP"));
			innerMap.put(Skills.SKILL_POINTS, yml.getInt("Skills.Players." + uuid.toString() + ".Skill Points"));
			innerMap.put(Skills.ATTACK_DAMAGE, yml.getInt("Skills.Players." + uuid.toString() + ".Attack Damage"));
			innerMap.put(Skills.ATTACK_SPEED, yml.getInt("Skills.Players." + uuid.toString() + ".Attack Speed"));
			innerMap.put(Skills.CRITICAL_CHANCE, yml.getInt("Skills.Players." + uuid.toString() + ".Crtitical Chance"));
			innerMap.put(Skills.RANGED_DAMAGE, yml.getInt("Skills.Players." + uuid.toString() + ".Ranged Damage"));
			innerMap.put(Skills.MAX_HEALTH, yml.getInt("Skills.Players." + uuid.toString() + ".Health"));
			innerMap.put(Skills.ARMOR_DEFENSE, yml.getInt("Skills.Players." + uuid.toString() + ".Defense"));
			innerMap.put(Skills.ATTACK_DAMAGE_MODIFIER, yml.getInt("Skills.Players." + uuid.toString() + ".Attack Modifier"));
			innerMap.put(Skills.ATTACK_SPEED_MODIFIER, yml.getInt("Skills.Players." + uuid.toString() + ".Speed Modifier"));
			innerMap.put(Skills.CRITICAL_CHANCE_MODIFIER, yml.getInt("Skills.Players." + uuid.toString() + ".Critical Modifier"));
			innerMap.put(Skills.RANGED_DAMAGE_MODIFIER, yml.getInt("Skills.Players." + uuid.toString() + ".Ranged Modifier"));
			innerMap.put(Skills.MAX_HEALTH_MODIFIER, yml.getInt("Skills.Players." + uuid.toString() + ".Health Modifier"));
			innerMap.put(Skills.ARMOR_DEFENSE_MODIFIER, yml.getInt("Skills.Players." + uuid.toString() + ".Defense Modifier"));
			outerMapSkills.put(uuid, innerMap);
			HashMap<Skills, Double> innerMapCalculatedSkills = outerMapCalculatedSkills.get(uuid);
			if(innerMapCalculatedSkills == null) {
				innerMapCalculatedSkills = new HashMap<Skills, Double>();
			}
			outerMapUD.put(uuid, new HashMap<Skills, UD>());
			HashMap<Skills, UD> innerMapUD = outerMapUD.get(uuid);
			if(innerMapUD == null) {
				innerMapUD = new HashMap<Skills, UD>();
			}
			if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.ENVY) {
				innerMapUD.put(Skills.ATTACK_DAMAGE, UD.UPSIDE);
			}
			else if(this.getClass(uuid) == Classes.LUST || this.getClass(uuid) == Classes.GLUTTONY) {
				innerMapUD.put(Skills.ATTACK_DAMAGE, UD.DOWNSIDE);
			}
			else {
				innerMapUD.put(Skills.ATTACK_DAMAGE, UD.NEUTRAL);
			}
			if(this.getClass(uuid) == Classes.PRIDE || this.getClass(uuid) == Classes.GREED) {
				innerMapUD.put(Skills.ATTACK_SPEED, UD.UPSIDE);
			}
			else if(this.getClass(uuid) == Classes.SLOTH || this.getClass(uuid) == Classes.ENVY) {
				innerMapUD.put(Skills.ATTACK_SPEED, UD.DOWNSIDE);
			}
			else {
				innerMapUD.put(Skills.ATTACK_SPEED, UD.NEUTRAL);
			}
			if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.SLOTH) {
				innerMapUD.put(Skills.CRITICAL_CHANCE, UD.UPSIDE);
			}
			else if(this.getClass(uuid) == Classes.PRIDE || this.getClass(uuid) == Classes.ENVY) {
				innerMapUD.put(Skills.CRITICAL_CHANCE, UD.DOWNSIDE);
			}
			else {
				innerMapUD.put(Skills.CRITICAL_CHANCE, UD.NEUTRAL);
			}
			if(this.getClass(uuid) == Classes.LUST || this.getClass(uuid) == Classes.GREED || this.getClass(uuid) == Classes.ENVY) {
				innerMapUD.put(Skills.RANGED_DAMAGE, UD.UPSIDE);
			}
			else if(this.getClass(uuid) == Classes.GLUTTONY || this.getClass(uuid) == Classes.SLOTH) {
				innerMapUD.put(Skills.RANGED_DAMAGE, UD.DOWNSIDE);
			}
			else {
				innerMapUD.put(Skills.RANGED_DAMAGE, UD.NEUTRAL);
			}
			if(this.getClass(uuid) == Classes.LUST || this.getClass(uuid) == Classes.GLUTTONY) {
				innerMapUD.put(Skills.MAX_HEALTH, UD.UPSIDE);
			}
			else if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.GREED || this.getClass(uuid) == Classes.PRIDE) {
				innerMapUD.put(Skills.MAX_HEALTH, UD.DOWNSIDE);
			}
			else {
				innerMapUD.put(Skills.MAX_HEALTH, UD.NEUTRAL);
			}
			if(this.getClass(uuid) == Classes.GLUTTONY || this.getClass(uuid) == Classes.SLOTH || this.getClass(uuid) == Classes.PRIDE) {
				innerMapUD.put(Skills.ARMOR_DEFENSE, UD.UPSIDE);
			}
			else if(this.getClass(uuid) == Classes.WRATH || this.getClass(uuid) == Classes.GREED) {
				innerMapUD.put(Skills.ARMOR_DEFENSE, UD.DOWNSIDE);
			}
			else {
				innerMapUD.put(Skills.ARMOR_DEFENSE, UD.NEUTRAL);
			}
			outerMapUD.put(uuid, innerMapUD);
			double baseAD = 2.50;
			double baseAS = 0.40;
			double baseCC = 0.50;
			double baseRD = 3.00;
			double baseHH = 5.00;
			double baseDF = 2.00;
			double multiplier = 0.00;
			if(outerMapUD.get(uuid).get(Skills.ATTACK_DAMAGE) == UD.UPSIDE) {
				multiplier = 1.5;
			}
			if(outerMapUD.get(uuid).get(Skills.ATTACK_DAMAGE) == UD.NEUTRAL) {
				multiplier = 1.0;
			}
			if(outerMapUD.get(uuid).get(Skills.ATTACK_DAMAGE) == UD.DOWNSIDE) {
				multiplier = 0.5;
			}
			innerMapCalculatedSkills.put(Skills.ATTACK_DAMAGE_CALC, innerMap.get(Skills.ATTACK_DAMAGE) * baseAD * multiplier);
			if(outerMapUD.get(uuid).get(Skills.ATTACK_SPEED) == UD.UPSIDE) {
				multiplier = 1.5;
			}
			if(outerMapUD.get(uuid).get(Skills.ATTACK_SPEED) == UD.NEUTRAL) {
				multiplier = 1.0;
			}
			if(outerMapUD.get(uuid).get(Skills.ATTACK_SPEED) == UD.DOWNSIDE) {
				multiplier = 0.5;
			}
			innerMapCalculatedSkills.put(Skills.ATTACK_SPEED_CALC, innerMap.get(Skills.ATTACK_SPEED) * baseAS * multiplier);
			if(outerMapUD.get(uuid).get(Skills.CRITICAL_CHANCE) == UD.UPSIDE) {
				multiplier = 1.5;
			}
			if(outerMapUD.get(uuid).get(Skills.CRITICAL_CHANCE) == UD.NEUTRAL) {
				multiplier = 1.0;
			}
			if(outerMapUD.get(uuid).get(Skills.CRITICAL_CHANCE) == UD.DOWNSIDE) {
				multiplier = 0.5;
			}
			innerMapCalculatedSkills.put(Skills.CRITICAL_CHANCE_CALC, innerMap.get(Skills.CRITICAL_CHANCE) * baseCC * multiplier);
			if(outerMapUD.get(uuid).get(Skills.RANGED_DAMAGE) == UD.UPSIDE) {
				multiplier = 1.5;
			}
			if(outerMapUD.get(uuid).get(Skills.RANGED_DAMAGE) == UD.NEUTRAL) {
				multiplier = 1.0;
			}
			if(outerMapUD.get(uuid).get(Skills.RANGED_DAMAGE) == UD.DOWNSIDE) {
				multiplier = 0.5;
			}
			innerMapCalculatedSkills.put(Skills.RANGED_DAMAGE_CALC, innerMap.get(Skills.RANGED_DAMAGE) * baseRD * multiplier);
			if(outerMapUD.get(uuid).get(Skills.MAX_HEALTH) == UD.UPSIDE) {
				multiplier = 1.5;
			}
			if(outerMapUD.get(uuid).get(Skills.MAX_HEALTH) == UD.NEUTRAL) {
				multiplier = 1.0;
			}
			if(outerMapUD.get(uuid).get(Skills.MAX_HEALTH) == UD.DOWNSIDE) {
				multiplier = 0.5;
			}
			innerMapCalculatedSkills.put(Skills.MAX_HEALTH_CALC, innerMap.get(Skills.MAX_HEALTH) * baseHH * multiplier);
			if(outerMapUD.get(uuid).get(Skills.ARMOR_DEFENSE) == UD.UPSIDE) {
				multiplier = 1.5;
			}
			if(outerMapUD.get(uuid).get(Skills.ARMOR_DEFENSE) == UD.NEUTRAL) {
				multiplier = 1.0;
			}
			if(outerMapUD.get(uuid).get(Skills.ARMOR_DEFENSE) == UD.DOWNSIDE) {
				multiplier = 0.5;
			}
			innerMapCalculatedSkills.put(Skills.ARMOR_DEFENSE_CALC, innerMap.get(Skills.ARMOR_DEFENSE) * baseDF * multiplier);
			innerMapCalculatedSkills.put(Skills.ATTACK_DAMAGE_EXTRA, 0.00);
			innerMapCalculatedSkills.put(Skills.ATTACK_SPEED_EXTRA, 0.00);
			innerMapCalculatedSkills.put(Skills.CRITICAL_CHANCE_EXTRA, 0.00);
			innerMapCalculatedSkills.put(Skills.RANGED_DAMAGE_EXTRA, 0.00);
			innerMapCalculatedSkills.put(Skills.MAX_HEALTH_EXTRA, 0.00);
			innerMapCalculatedSkills.put(Skills.ARMOR_DEFENSE_EXTRA, 0.00);
			outerMapCalculatedSkills.put(uuid, innerMapCalculatedSkills);
		}
		
	}
	public void resetCalculations(UUID uuid) {
		HashMap<Skills, Integer> innerMap = outerMapSkills.get(uuid);
		HashMap<Skills, Double> innerMapCalculatedSkills = outerMapCalculatedSkills.get(uuid);
		double baseAD = 2.50;
		double baseAS = 0.40;
		double baseCC = 0.50;
		double baseRD = 3.00;
		double baseHH = 5.00;
		double baseDF = 2.00;
		double multiplier = 0.00;
		if(outerMapUD.get(uuid).get(Skills.ATTACK_DAMAGE) == UD.UPSIDE) {
			multiplier = 1.5;
		}
		if(outerMapUD.get(uuid).get(Skills.ATTACK_DAMAGE) == UD.NEUTRAL) {
			multiplier = 1.0;
		}
		if(outerMapUD.get(uuid).get(Skills.ATTACK_DAMAGE) == UD.DOWNSIDE) {
			multiplier = 0.5;
		}
		innerMapCalculatedSkills.put(Skills.ATTACK_DAMAGE_CALC, innerMap.get(Skills.ATTACK_DAMAGE) * baseAD * multiplier);
		if(outerMapUD.get(uuid).get(Skills.ATTACK_SPEED) == UD.UPSIDE) {
			multiplier = 1.5;
		}
		if(outerMapUD.get(uuid).get(Skills.ATTACK_SPEED) == UD.NEUTRAL) {
			multiplier = 1.0;
		}
		if(outerMapUD.get(uuid).get(Skills.ATTACK_SPEED) == UD.DOWNSIDE) {
			multiplier = 0.5;
		}
		innerMapCalculatedSkills.put(Skills.ATTACK_SPEED_CALC, innerMap.get(Skills.ATTACK_SPEED) * baseAS * multiplier);
		if(outerMapUD.get(uuid).get(Skills.CRITICAL_CHANCE) == UD.UPSIDE) {
			multiplier = 1.5;
		}
		if(outerMapUD.get(uuid).get(Skills.CRITICAL_CHANCE) == UD.NEUTRAL) {
			multiplier = 1.0;
		}
		if(outerMapUD.get(uuid).get(Skills.CRITICAL_CHANCE) == UD.DOWNSIDE) {
			multiplier = 0.5;
		}
		innerMapCalculatedSkills.put(Skills.CRITICAL_CHANCE_CALC, innerMap.get(Skills.CRITICAL_CHANCE) * baseCC * multiplier);
		if(outerMapUD.get(uuid).get(Skills.RANGED_DAMAGE) == UD.UPSIDE) {
			multiplier = 1.5;
		}
		if(outerMapUD.get(uuid).get(Skills.RANGED_DAMAGE) == UD.NEUTRAL) {
			multiplier = 1.0;
		}
		if(outerMapUD.get(uuid).get(Skills.RANGED_DAMAGE) == UD.DOWNSIDE) {
			multiplier = 0.5;
		}
		innerMapCalculatedSkills.put(Skills.RANGED_DAMAGE_CALC, innerMap.get(Skills.RANGED_DAMAGE) * baseRD * multiplier);
		if(outerMapUD.get(uuid).get(Skills.MAX_HEALTH) == UD.UPSIDE) {
			multiplier = 1.5;
		}
		if(outerMapUD.get(uuid).get(Skills.MAX_HEALTH) == UD.NEUTRAL) {
			multiplier = 1.0;
		}
		if(outerMapUD.get(uuid).get(Skills.MAX_HEALTH) == UD.DOWNSIDE) {
			multiplier = 0.5;
		}
		innerMapCalculatedSkills.put(Skills.MAX_HEALTH_CALC, innerMap.get(Skills.MAX_HEALTH) * baseHH * multiplier);
		if(outerMapUD.get(uuid).get(Skills.ARMOR_DEFENSE) == UD.UPSIDE) {
			multiplier = 1.5;
		}
		if(outerMapUD.get(uuid).get(Skills.ARMOR_DEFENSE) == UD.NEUTRAL) {
			multiplier = 1.0;
		}
		if(outerMapUD.get(uuid).get(Skills.ARMOR_DEFENSE) == UD.DOWNSIDE) {
			multiplier = 0.5;
		}
		innerMapCalculatedSkills.put(Skills.ARMOR_DEFENSE_CALC, innerMap.get(Skills.ARMOR_DEFENSE) * baseDF * multiplier);
		outerMapCalculatedSkills.put(uuid, innerMapCalculatedSkills);
	}
	public void saveProfiles(YamlConfiguration yml, File f) {
		for(Entry<UUID, Classes> entry : classList.entrySet()) {
			UUID id = entry.getKey();
			yml.set("Skills.Players." + id.toString() + ".Class", classList.get(id).toString());
			yml.set("Skills.Players." + id.toString() + ".Level", outerMapSkills.get(id).get(Skills.LEVEL));
			yml.set("Skills.Players." + id.toString() + ".XP", outerMapSkills.get(id).get(Skills.XP));
			yml.set("Skills.Players." + id.toString() + ".MXP", outerMapSkills.get(id).get(Skills.MAXXP));
			yml.set("Skills.Players." + id.toString() + ".Skill Points", outerMapSkills.get(id).get(Skills.SKILL_POINTS));
			yml.set("Skills.Players." + id.toString() + ".Attack Damage", outerMapSkills.get(id).get(Skills.ATTACK_DAMAGE));
			yml.set("Skills.Players." + id.toString() + ".Attack Speed", outerMapSkills.get(id).get(Skills.ATTACK_SPEED));
			yml.set("Skills.Players." + id.toString() + ".Critical Chance", outerMapSkills.get(id).get(Skills.CRITICAL_CHANCE));
			yml.set("Skills.Players." + id.toString() + ".Ranged Damage", outerMapSkills.get(id).get(Skills.RANGED_DAMAGE));
			yml.set("Skills.Players." + id.toString() + ".Health", outerMapSkills.get(id).get(Skills.MAX_HEALTH));
			yml.set("Skills.Players." + id.toString() + ".Defense", outerMapSkills.get(id).get(Skills.ARMOR_DEFENSE));
			yml.set("Skills.Players." + id.toString() + ".Attack Modifier", outerMapSkills.get(id).get(Skills.ATTACK_DAMAGE_MODIFIER));
			yml.set("Skills.Players." + id.toString() + ".Speed Modifier", outerMapSkills.get(id).get(Skills.ATTACK_SPEED_MODIFIER));
			yml.set("Skills.Players." + id.toString() + ".Critical Modifier", outerMapSkills.get(id).get(Skills.CRITICAL_CHANCE_MODIFIER));
			yml.set("Skills.Players." + id.toString() + ".Ranged Modifier", outerMapSkills.get(id).get(Skills.RANGED_DAMAGE_MODIFIER));
			yml.set("Skills.Players." + id.toString() + ".Health Modifier", outerMapSkills.get(id).get(Skills.MAX_HEALTH_MODIFIER));
			yml.set("Skills.Players." + id.toString() + ".Defense Modifier", outerMapSkills.get(id).get(Skills.ARMOR_DEFENSE_MODIFIER));
		}
		try{
			yml.save(f);
	    }
	    catch(IOException e){
	        e.printStackTrace();
	    }
	}
	public int getSkill(UUID uuid, Skills result) {
		int i = 0;
		HashMap<Skills, Integer> innerMap = outerMapSkills.get(uuid);
		if(innerMap != null) {
			if(innerMap.get(result) != null) {
				i = outerMapSkills.get(uuid).get(result);
			}
		}
		return i;
	}
	public void setSkill(UUID uuid, Skills result, int number) {
		int i = number;
		HashMap<Skills, Integer> innerMap = outerMapSkills.get(uuid);
		innerMap.put(result, i);
		outerMapSkills.put(uuid, innerMap);
	}
	public double getCalculation(UUID uuid, Skills result) {
		double i = 0.00;
		HashMap<Skills, Double> innerMap = outerMapCalculatedSkills.get(uuid);
		if(innerMap != null) {
			if(innerMap.get(result) != null) {
				i = outerMapCalculatedSkills.get(uuid).get(result);
			}
		}
		return i;
	}
	public void setCalculation(UUID uuid, Skills result, double number) {
		double i = number;
		HashMap<Skills, Double> innerMap = outerMapCalculatedSkills.get(uuid);
		innerMap.put(result, i);
		outerMapCalculatedSkills.put(uuid, innerMap);
	}
	public UD getResult(UUID uuid, Skills result) {
		HashMap<Skills, UD> innerMap = outerMapUD.get(uuid);
		if(innerMap != null) {
			if(innerMap.get(result) != null) {
				return outerMapUD.get(uuid).get(result);
			}
		}
		return null;
	}
	public void setResult(UUID uuid, Skills result, UD result1) {
		HashMap<Skills, UD> innerMap = outerMapUD.get(uuid);
		innerMap.put(result, result1);
		outerMapUD.put(uuid, innerMap);
	}
	public Classes getClass(UUID uuid) {
		if(classList.containsKey(uuid)) {
			return classList.get(uuid);
		}
		else {
			return Classes.NONE;
		}
	}
	/**
	 * @param uuid : The players current assigned UUID.
	 * @return boolean : Returns a value of true/false. This will indicate if the player has a class or not.
	 */
	public boolean hasClass(UUID uuid) {
		if(classList.get(uuid) != Classes.NONE || classList.containsKey(uuid)) {
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * @param uuid : The players current assigned UUID.
	 * @param playerClass : The class that the player needs to be assigned to.
	 */
	public void setClass(UUID uuid, Classes playerClass) {
		classList.put(uuid, playerClass);
	}
	public HashMap<UUID, Classes> getClassList(){
		return PlayerClass.classList;
	}
}
