package Skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Skills.Enums.Classes;
import Skills.SkillEnum.Skills;
import Skills.SkillsUD.UD;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SkillMenu {
	public PlayerClass pc = new PlayerClass();
	SkillJoin join = new SkillJoin();
	ClassMenu menu = new ClassMenu();
	public void SkillMenuInv(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, 36, new ColorCodeTranslator().colorize("&7Skills of: &6" + player.getName()));
		pc.resetCalculations(player.getUniqueId());
		i.setItem(0, skillBook(player));
		i.setItem(1, empty());
		i.setItem(2, empty());
		i.setItem(3, empty());
		UUID uuid = player.getUniqueId();
		int level = pc.getSkill(uuid, Skills.LEVEL);
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Wrath
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		if(pc.getClass(uuid) == Classes.WRATH) {
			i.setItem(4, menu.classWrath(level));
			if(pc.getSkill(uuid, Skills.ATTACK_DAMAGE) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_DAMAGE);
				double calcNow = cLevel * 0.1;
				double calcNext = (cLevel + 1) * 0.1;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE));
				i.setItem(19, modCreator(player, Material.BLACK_WOOL, "&4Attack Modifier, Extra Hatred: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your ability deals an additional amount of damage to your enemy",
				"&7Appropiate to how many enemies were struck by your ability.",
				"",
				"                             &7&lCurrent: " + String.format("%.2f", calcNow),
				"                                    &7|",
				"                                    &7|",
				"                                    &7|",
				"                             &6&lNext: " + String.format("%.2f", calcNext),
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ATTACK_SPEED) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER);
				double calcNow = cLevel;
				double calcNext = cLevel + 1;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				i.setItem(20, modCreator(player, Material.COOKED_CHICKEN, "&9Speed Modifier, Electrocute: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your ability slows your enemy down by 10% (+2% Per Enemy Struck)",
				"&7This effect will last for a few seconds.",
				"",
				"                          &7&lCurrent: " + String.format("%.2f", calcNow) + " Seconds",
				"                                 &7|",
				"                                 &7|",
				"                                 &7|",
				"                          &6&lNext: " + String.format("%.2f", calcNext) + " Seconds",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.CRITICAL_CHANCE) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER);
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) + 5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				i.setItem(21, modCreator(player, Material.DIAMOND_SWORD, "&5Critical Modifier, Thunderstrike: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your ability has a chance to deal double damage.",
				"",
				"                  &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                         &7|",
				"                         &7|",
				"                         &7|",
				"                  &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.RANGED_DAMAGE) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER);
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				i.setItem(23, modCreator(player, Material.GRASS_BLOCK, "&dRanged Modifier, Globular: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your abilities range increases.",
				"",
				"         &7&lCurrent: " + String.format("%.2f", calcNow) + " Blocks",
				"                &7|",
				"                &7|",
				"                &7|",
				"         &6&lNext: " + String.format("%.2f", calcNext) + " Blocks",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(24, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			
			i.setItem(25, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Lust
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		else if(pc.getClass(player.getUniqueId()) == Classes.LUST) {
			i.setItem(4, menu.classLust(level));
			i.setItem(19, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			
			i.setItem(20, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.CRITICAL_CHANCE) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER);
				double calcNow = cLevel * 1;
				double calcNext = (cLevel + 1) * 1;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				i.setItem(21, modCreator(player, Material.BLAZE_ROD, "&5Critical Modifier, Critical State: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your ability deals an additional % of damage to your enemy.",
				"",
				"                         &7&lCurrent: " + String.format("%.2f", calcNow) + "%",
				"                                &7|",
				"                                &7|",
				"                                &7|",
				"                         &6&lNext: " + String.format("%.2f", calcNext) + "%",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.RANGED_DAMAGE) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER);
				double calcNow = cLevel * 2;
				double calcNext = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				i.setItem(23, modCreator(player, Material.ARMOR_STAND, "&dRanged Modifier, All Around: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increase the range of your ability by a certain amount of blocks.",
				"",
				"                           &7&lCurrent: " + String.format("%.2f", calcNow) + " Blocks",
				"                                  &7|",
				"                                  &7|",
				"                                  &7|",
				"                           &6&lNext: " + String.format("%.2f", calcNext) + " Blocks",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.MAX_HEALTH) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER);
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) + 5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				i.setItem(24, modCreator(player, Material.ROSE_RED, "&cHealth Modifier, Heartbeat: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your abilities recovery is increased by a certain %.",
				"",
				"                    &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                           &7|",
				"                           &7|",
				"                           &7|",
				"                    &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ARMOR_DEFENSE) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER);
				double calcNow = cLevel * 8;
				double calcNext = (cLevel + 1) * 8;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				i.setItem(25, modCreator(player, Material.DIAMOND_CHESTPLATE, "&7Defense Modifier, Maximum Overdrive: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increase your MAX HP for 30 seconds.",
				"",
				"           &7&lCurrent: " + String.format("%.2f", calcNow) + " + MAX HP",
				"                  &7|",
				"                  &7|",
				"                  &7|",
				"           &6&lNext: " + String.format("%.2f", calcNext) + " + MAX HP",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Gluttony
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		else if(pc.getClass(player.getUniqueId()) == Classes.GLUTTONY) {
			i.setItem(4, menu.classGluttony(level));
			i.setItem(19, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.ATTACK_SPEED) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER);
				double calcNow = cLevel * 1;
				double calcNext = (cLevel + 1) * 1;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				i.setItem(20, modCreator(player, Material.LEATHER_BOOTS, "&9Speed Modifier, Gotta Go Fast: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you activate your ability, your movement speed is increased",
				"&7by a certain %.",
				"",
				"                             &7&lCurrent: " + String.format("%.2f", calcNow) + "%",
				"                                    &7|",
				"                                    &7|",
				"                                    &7|",
				"                             &6&lNext: " + String.format("%.2f", calcNext) + "%",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.CRITICAL_CHANCE) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER);
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				i.setItem(21, modCreator(player, Material.POTION, "&5Critical Modifier, Critical Extension: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increase the duration by a %",
				"",
				"              &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                     &7|",
				"                     &7|",
				"                     &7|",
				"              &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(23, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.MAX_HEALTH) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER);
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				i.setItem(24, modCreator(player, Material.POPPY, "&cHealth Modifier, Healing Stand: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your abilities recovery is increased by a certain %.",
				"",
				"                    &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                           &7|",
				"                           &7|",
				"                           &7|",
				"                    &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ARMOR_DEFENSE) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER);
				double calcNow = cLevel * 4;
				double calcNext = (cLevel + 1) * 4;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				i.setItem(25, modCreator(player, Material.IRON_BLOCK, "&7Defense Modifier, Reflection: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7While your ability is active, reflect some % of the damage",
				"&7done to you, back to the attacker.",
				"",
				"                       &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                              &7|",
				"                              &7|",
				"                              &7|",
				"                       &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Greed
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		else if(pc.getClass(player.getUniqueId()) == Classes.GREED) {
			i.setItem(4, menu.classGreed(level));
			if(pc.getSkill(uuid, Skills.ATTACK_DAMAGE) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER);
				double calcNow = cLevel * 2;
				double calcNext = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				i.setItem(19, modCreator(player, Material.ARROW, "&4Attack Modifier, Hunters Eye: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your ability deals additional damage to an enemy.",
				"",
				"                   &7&lCurrent: " + String.format("%.2f", calcNow),
				"                          &7|",
				"                          &7|",
				"                          &7|",
				"                   &6&lNext: " + String.format("%.2f", calcNext),
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ATTACK_SPEED) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER);
				double calcNow = cLevel * 10;
				double calcNext = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				i.setItem(20, modCreator(player, Material.ARMOR_STAND, "&9Speed Modifier, Hunt Down: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you shoot someone with your ability, your movement speed",
				"&7Increases by a certain %",
				"",
				"                           &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                                  &7|",
				"                                  &7|",
				"                                  &7|",
				"                           &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.CRITICAL_CHANCE) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER);
				double calcNow = 15 + (cLevel * 5);
				double calcNext = 15 + ((cLevel + 1) * 5);
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				i.setItem(21, modCreator(player, Material.WITHER_SKELETON_SKULL, "&5Critical Modifier, Death Shot: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You wither your enemy for a certain amount of seconds.",
				"",
				"                  &7&lCurrent: " + String.format("%.2f", calcNow) + " Seconds",
				"                         &7|",
				"                         &7|",
				"                         &7|",
				"                  &6&lNext: " + String.format("%.2f", calcNext) + " Seconds",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.RANGED_DAMAGE) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER);
				double calcNow = cLevel * 0.5;
				double calcNext = (cLevel + 1) * 0.5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				i.setItem(23, modCreator(player, Material.REDSTONE, "&dRanged Modifier, Hunters Blood: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increase the duration of your ability.",
				"",
				"       &7&lCurrent: " + String.format("%.2f", calcNow) + " Seconds",
				"              &7|",
				"              &7|",
				"              &7|",
				"       &6&lNext: " + String.format("%.2f", calcNext) + " Seconds",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(24, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			i.setItem(25, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Sloth
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		else if(pc.getClass(player.getUniqueId()) == Classes.SLOTH) {
			i.setItem(4, menu.classSloth(level));
			if(pc.getSkill(uuid, Skills.ATTACK_DAMAGE) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER);
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				i.setItem(19, modCreator(player, Material.ENCHANTED_BOOK, "&4Attack Modifier, Sharpened Thorns: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your ability deals additional damage to an enemy.",
				"",
				"                   &7&lCurrent: " + String.format("%.2f", calcNow),
				"                          &7|",
				"                          &7|",
				"                          &7|",
				"                   &6&lNext: " + String.format("%.2f", calcNext)
				,
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(20, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.CRITICAL_CHANCE) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER);
				double calcNow = cLevel * 10;
				double calcNext = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.CRITICAL_CHANCE_MODIFIER));
				i.setItem(21, modCreator(player, Material.FIRE_CHARGE, "&5Critical Modifier, Critical Charge: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your thorns gain an additional % of force.",
				"",
				"              &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                     &7|",
				"                     &7|",
				"                     &7|",
				"              &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(23, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, be  cause this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.MAX_HEALTH) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER);
				double calcNow = cLevel;
				double calcNext = cLevel + 1;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				i.setItem(24, modCreator(player, Material.RED_STAINED_GLASS, "&cHealth Modifier, Combo Shot: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your thorns heal you for a % amount of HP per enemy shot.",
				"",
				"                        &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                               &7|",
				"                               &7|",
				"                               &7|",
				"                        &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ARMOR_DEFENSE) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER);
				double calcNow = 90 - cLevel * 10;
				double calcNext = 90 - (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				i.setItem(25, modCreator(player, Material.CREEPER_HEAD, "&7Defense Modifier, Reflex: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you get hit, there is a small chance that &6Hedge of the Thorn",
				"&7will activate. This will not reset the cooldown, however it shoots",
				"&7a % amount less of arrows.",
				"",
				"                           &7&lCurrent: " + String.format("%.2f", calcNow) + "-%",
				"                                  &7|",
				"                                  &7|",
				"                                  &7|",
				"                           &6&lNext: " + String.format("%.2f", calcNext) + "-%",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				)))); 
			}
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Envy
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		else if(pc.getClass(player.getUniqueId()) == Classes.ENVY) {
			i.setItem(4, menu.classEnvy(level));
			if(pc.getSkill(uuid, Skills.ATTACK_DAMAGE) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER);
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				i.setItem(19, modCreator(player, Material.GOLDEN_SWORD, "&4Attack Modifier, Sharp Edge: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7After you succesfully performed your ability, your next",
				"&7attack against the same enemy deals an additional amount of damage.",
				"",
				"                               &7&lCurrent: " + String.format("%.2f", calcNow),
				"                                      &7|",
				"                                      &7|",
				"                                      &7|",
				"                               &6&lNext: " + String.format("%.2f", calcNext),
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(20, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			i.setItem(21, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.RANGED_DAMAGE) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER);
				double calcNow = cLevel * 10;
				double calcNext = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				i.setItem(23, modCreator(player, Material.GOLDEN_BOOTS, "&5Critical Modifier, Retreatfull: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7After you succesfully executed this attack, your",
				"&7Movement Speed increases by a certain % for 15 seconds",
				"",
				"                       &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                              &7|",
				"                              &7|",
				"                              &7|",
				"                       &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.MAX_HEALTH) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER);
				double calcNow = 1 + cLevel * 0.75;
				double calcNext = 1 + (cLevel + 1) * 0.75;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.MAX_HEALTH_MODIFIER));
				i.setItem(24, modCreator(player, Material.RED_STAINED_GLASS, "&cHealth Modifier, Regenerative Comeback: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You heal some HP after you succesfully performed your ability",
				"&7For a few seconds.",
				"",
				"                           &7&lCurrent: " + String.format("%.2f", calcNow) + " HP",
				"                                  &7|",
				"                                  &7|",
				"                                  &7|",
				"                           &6&lNext: " + String.format("%.2f", calcNext) + " HP",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ARMOR_DEFENSE) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER);
				double calcNow = cLevel * 7.5;
				double calcNext = (cLevel + 1) * 7.5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				i.setItem(25, modCreator(player, Material.GOLDEN_CHESTPLATE, "&7Defense Modifier, Guard Up: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When your ability runs out before you could perform it, the next",
				"&7attacks damage you recieve will be decreased by a certain %",
				"",
				"                            &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                                   &7|",
				"                                   &7|",
				"                                   &7|",
				"                            &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Pride
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		else if(pc.getClass(player.getUniqueId()) == Classes.PRIDE) {
			i.setItem(4, menu.classPride(level));
			if(pc.getSkill(uuid, Skills.ATTACK_DAMAGE) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER);
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_DAMAGE_MODIFIER));
				i.setItem(19, modCreator(player, Material.FERMENTED_SPIDER_EYE, "&4Attack Modifier, Weakening Strike: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When your ability is active, your strikes apply weakness 1",
				"&7to the enemy for a few seconds",
				"",
				"                      &7&lCurrent: " + String.format("%.2f", calcNow),
				"                             &7|",
				"                             &7|",
				"                             &7|",
				"                      &6&lNext: " + String.format("%.2f", calcNext),
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(pc.getSkill(uuid, Skills.ATTACK_SPEED) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER);
				double calcNow = cLevel * 0.5;
				double calcNext = (cLevel + 1) * 0.5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ATTACK_SPEED_MODIFIER));
				i.setItem(20, modCreator(player, Material.BONE, "&9Speed Modifier, Quick Attack: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Every hit you deal while your ability is active increases",
				"&7your Movement Speed by a certain %",
				"",
				"                     &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                            &7|",
				"                            &7|",
				"                            &7|",
				"                     &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(21, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.RANGED_DAMAGE) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER);
				double calcNow = cLevel * 2;
				double calcNext = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.RANGED_DAMAGE_MODIFIER));
				i.setItem(23, modCreator(player, Material.ICE, "&cHealth Modifier, Cooling: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your abilities cooldown decreases by a few seconds.",
				"",
				"               &7&lCurrent: " + String.format("%.2f", calcNow) + " Seconds",
				"                      &7|",
				"                      &7|",
				"                      &7|",
				"               &6&lNext: " + String.format("%.2f", calcNext) + " Seconds",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(24, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(pc.getSkill(uuid, Skills.ARMOR_DEFENSE) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER);
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				int levelReq = 20 + (20 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				double moneyReq = 20000.00 + (20000.00 * pc.getSkill(uuid, Skills.ARMOR_DEFENSE_MODIFIER));
				i.setItem(25, modCreator(player, Material.IRON_CHESTPLATE, "&7Defense Modifier, Absorb: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7While your ability is active, you absorb part of the incoming damage",
				"&7and return this damage back to the attacker on your next attacker",
				"",
				"                            &7&lCurrent: " + String.format("%.2f", calcNow) + " %",
				"                                   &7|",
				"                                   &7|",
				"                                   &7|",
				"                            &6&lNext: " + String.format("%.2f", calcNext) + " %",
				"",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
		}
		i.setItem(5, empty());
		i.setItem(6, empty());
		i.setItem(7, empty());
		i.setItem(8, progression(player));
		i.setItem(9, empty());
		i.setItem(10, ad(player));
		i.setItem(11, as(player));
		i.setItem(12, cc(player));
		i.setItem(13, empty());
		i.setItem(14, rd(player));
		i.setItem(15, hh(player));
		i.setItem(16, df(player));
		i.setItem(17, empty());
		i.setItem(18, empty());
		i.setItem(22, empty());
		i.setItem(26, empty());
		i.setItem(27, empty());
		i.setItem(28, empty());
		i.setItem(29, empty());
		i.setItem(30, empty());
		i.setItem(31, empty());
		i.setItem(32, empty());
		i.setItem(33, empty());
		i.setItem(34, empty());
		i.setItem(35, empty());
		player.openInventory(i);
	}
	public ItemStack empty() {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack skillBook(Player p) {
		ItemStack item = new ItemStack(Material.BOOK);
		int skillPoints = pc.getSkill(p.getUniqueId(), Skills.SKILL_POINTS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&7Skill Points: &6" + skillPoints));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Here, you can view how many &6Skill Points &7you have."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack modCreator(Player p, Material mat, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new ColorCodeTranslator().colorize(display));
		ArrayList<String> replaceLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			replaceLore.add(new ColorCodeTranslator().colorize(lore.get(i)));
		}
		meta.setLore(replaceLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack progression(Player p) {
		ItemStack item = new ItemStack(Material.ENCHANTING_TABLE);
		int level = pc.getSkill(p.getUniqueId(), Skills.LEVEL);
		int xp = pc.getSkill(p.getUniqueId(), Skills.XP);
		int maxxp = pc.getSkill(p.getUniqueId(), Skills.MAXXP);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&6Progression Table"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Level: &b" + level));
		lore.add(new ColorCodeTranslator().colorize("&7Next Level: &7[&b" + xp + " &6/ &b" + maxxp + "&7]"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack ad(Player p) {
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		ItemMeta meta = item.getItemMeta();
		UUID uuid = p.getUniqueId();
		int levelNow = pc.getSkill(uuid, Skills.ATTACK_DAMAGE);
		double calcNow = pc.getCalculation(uuid, Skills.ATTACK_DAMAGE_CALC);
		double calcNext = pc.getCalculation(uuid, Skills.ATTACK_DAMAGE_CALC);
		if(pc.getResult(uuid, Skills.ATTACK_DAMAGE) == UD.UPSIDE) {
			calcNext = calcNext + 3.75;
		}
		else if(pc.getResult(uuid, Skills.ATTACK_DAMAGE) == UD.DOWNSIDE) {
			calcNext = calcNext + 1.25;
		}
		else if(pc.getResult(uuid, Skills.ATTACK_DAMAGE) == UD.NEUTRAL){
			calcNext = calcNext + 2.5;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&4Attack Damage: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Increase the damage you deal through melee."));
		lore.add(new ColorCodeTranslator().colorize(""));
		lore.add(new ColorCodeTranslator().colorize("                 &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new ColorCodeTranslator().colorize("                        &7|"));
		lore.add(new ColorCodeTranslator().colorize("                        &7|"));
		lore.add(new ColorCodeTranslator().colorize("                        &7|"));
		lore.add(new ColorCodeTranslator().colorize("                 &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack as(Player p) {
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		UUID uuid = p.getUniqueId();
		int levelNow = pc.getSkill(uuid, Skills.ATTACK_SPEED);
		double calcNow = pc.getCalculation(uuid, Skills.ATTACK_SPEED_CALC);
		double calcNext = pc.getCalculation(uuid, Skills.ATTACK_SPEED_CALC);
		if(pc.getResult(uuid, Skills.ATTACK_SPEED) == UD.UPSIDE) {
			calcNext = calcNext + 0.60;
		}
		else if(pc.getResult(uuid, Skills.ATTACK_SPEED) == UD.DOWNSIDE) {
			calcNext = calcNext + 0.20;
		}
		else if(pc.getResult(uuid, Skills.ATTACK_SPEED) == UD.NEUTRAL){
			calcNext = calcNext + 0.40;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&9Attack Speed: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7You can attack faster."));
		lore.add(new ColorCodeTranslator().colorize(""));
		lore.add(new ColorCodeTranslator().colorize("    &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new ColorCodeTranslator().colorize("           &7|"));
		lore.add(new ColorCodeTranslator().colorize("           &7|"));
		lore.add(new ColorCodeTranslator().colorize("           &7|"));
		lore.add(new ColorCodeTranslator().colorize("    &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack cc(Player p) {
		ItemStack item = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta meta = item.getItemMeta();
		UUID uuid = p.getUniqueId();
		int levelNow = pc.getSkill(uuid, Skills.CRITICAL_CHANCE);
		double calcNow = pc.getCalculation(uuid, Skills.CRITICAL_CHANCE_CALC);
		double calcNext = pc.getCalculation(uuid, Skills.CRITICAL_CHANCE_CALC);
		if(pc.getResult(uuid, Skills.CRITICAL_CHANCE) == UD.UPSIDE) {
			calcNext = calcNext + 0.75;
		}
		else if(pc.getResult(uuid, Skills.CRITICAL_CHANCE) == UD.DOWNSIDE) {
			calcNext = calcNext + 0.25;
		}
		else if(pc.getResult(uuid, Skills.CRITICAL_CHANCE) == UD.NEUTRAL){
			calcNext = calcNext + 0.50;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&5Critical Chance: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Have a chance to deal double damage."));
		lore.add(new ColorCodeTranslator().colorize(""));
		lore.add(new ColorCodeTranslator().colorize("              &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new ColorCodeTranslator().colorize("                     &7|"));
		lore.add(new ColorCodeTranslator().colorize("                     &7|"));
		lore.add(new ColorCodeTranslator().colorize("                     &7|"));
		lore.add(new ColorCodeTranslator().colorize("              &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack rd(Player p) {
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta meta = item.getItemMeta();
		UUID uuid = p.getUniqueId();
		int levelNow = pc.getSkill(uuid, Skills.RANGED_DAMAGE);
		double calcNow = pc.getCalculation(uuid, Skills.RANGED_DAMAGE_CALC);
		double calcNext = pc.getCalculation(uuid, Skills.RANGED_DAMAGE_CALC);
		if(pc.getResult(uuid, Skills.RANGED_DAMAGE) == UD.UPSIDE) {
			calcNext = calcNext + 4.5;
		}
		else if(pc.getResult(uuid, Skills.RANGED_DAMAGE) == UD.DOWNSIDE) {
			calcNext = calcNext + 1.5;
		}
		else if(pc.getResult(uuid, Skills.RANGED_DAMAGE) == UD.NEUTRAL){
			calcNext = calcNext + 3.0;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&dRanged Damage: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Increase the damage you deal through ranged."));
		lore.add(new ColorCodeTranslator().colorize(""));
		lore.add(new ColorCodeTranslator().colorize("                   &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new ColorCodeTranslator().colorize("                          &7|"));
		lore.add(new ColorCodeTranslator().colorize("                          &7|"));
		lore.add(new ColorCodeTranslator().colorize("                          &7|"));
		lore.add(new ColorCodeTranslator().colorize("                   &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack hh(Player p) {
		ItemStack item = new ItemStack(Material.APPLE);
		ItemMeta meta = item.getItemMeta();
		UUID uuid = p.getUniqueId();
		int levelNow = pc.getSkill(uuid, Skills.MAX_HEALTH);
		double calcNow = pc.getCalculation(uuid, Skills.MAX_HEALTH_CALC);
		double calcNext = pc.getCalculation(uuid, Skills.MAX_HEALTH_CALC);
		if(pc.getResult(uuid, Skills.MAX_HEALTH) == UD.UPSIDE) {
			calcNext = calcNext + 7.5;
		}
		else if(pc.getResult(uuid, Skills.MAX_HEALTH) == UD.DOWNSIDE) {
			calcNext = calcNext + 2.5;
		}
		else if(pc.getResult(uuid, Skills.MAX_HEALTH) == UD.NEUTRAL){
			calcNext = calcNext + 5.0;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&cHealth: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Increase your MAX HP."));
		lore.add(new ColorCodeTranslator().colorize(""));
		lore.add(new ColorCodeTranslator().colorize("     &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new ColorCodeTranslator().colorize("            &7|"));
		lore.add(new ColorCodeTranslator().colorize("            &7|"));
		lore.add(new ColorCodeTranslator().colorize("            &7|"));
		lore.add(new ColorCodeTranslator().colorize("     &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack df(Player p) {
		ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta meta = item.getItemMeta();
		UUID uuid = p.getUniqueId();
		int levelNow = pc.getSkill(uuid, Skills.ARMOR_DEFENSE);
		double calcNow = pc.getCalculation(uuid, Skills.ARMOR_DEFENSE_CALC);
		double calcNext = pc.getCalculation(uuid, Skills.ARMOR_DEFENSE_CALC);
		if(pc.getResult(uuid, Skills.ARMOR_DEFENSE) == UD.UPSIDE) {
			calcNext = calcNext + 1.50;
		}
		else if(pc.getResult(uuid, Skills.ARMOR_DEFENSE) == UD.DOWNSIDE) {
			calcNext = calcNext + 0.5;
		}
		else if(pc.getResult(uuid, Skills.ARMOR_DEFENSE) == UD.NEUTRAL){
			calcNext = calcNext + 1.0;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&8Defense: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Increase armor points you recieve from armor."));
		lore.add(new ColorCodeTranslator().colorize(""));
		lore.add(new ColorCodeTranslator().colorize("                   &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new ColorCodeTranslator().colorize("                          &7|"));
		lore.add(new ColorCodeTranslator().colorize("                          &7|"));
		lore.add(new ColorCodeTranslator().colorize("                          &7|"));
		lore.add(new ColorCodeTranslator().colorize("                   &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
