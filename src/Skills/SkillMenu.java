package Skills;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SkillMenu {
	SkillJoin join = new SkillJoin();
	ClassMenu menu = new ClassMenu();
	public void SkillMenuInv(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, 36, new ColorCodeTranslator().colorize("&7Skills of: &6" + player.getName()));
		i.setItem(0, skillBook(player));
		i.setItem(1, empty());
		i.setItem(2, empty());
		i.setItem(3, empty());
		int level = join.getLevelList().get(player.getUniqueId());
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Wrath
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		if(join.getClassList().get(player.getUniqueId()).equals("Wrath")) {
			i.setItem(4, menu.classWrath(level));
			if(join.getADList().get(player.getUniqueId()) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getADMODList().get(player.getUniqueId());
				double calcNow = cLevel * 0.1;
				double calcNext = (cLevel + 1) * 0.1;
				int pLevelReq = 10 + (10 * join.getADMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getADMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getADMODList().get(player.getUniqueId()));
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
			if(join.getASList().get(player.getUniqueId()) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getASMODList().get(player.getUniqueId());
				double calcNow = cLevel;
				double calcNext = cLevel + 1;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getCCList().get(player.getUniqueId()) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getCCMODList().get(player.getUniqueId());
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) + 5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getRDList().get(player.getUniqueId()) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getRDMODList().get(player.getUniqueId());
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		else if(join.getClassList().get(player.getUniqueId()).equals("Lust")) {
			i.setItem(4, menu.classLust(level));
			i.setItem(19, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			
			i.setItem(20, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(join.getCCList().get(player.getUniqueId()) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getCCMODList().get(player.getUniqueId());
				double calcNow = cLevel * 1;
				double calcNext = (cLevel + 1) * 1;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getRDList().get(player.getUniqueId()) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getRDMODList().get(player.getUniqueId());
				double calcNow = cLevel * 2;
				double calcNext = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getHHList().get(player.getUniqueId()) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getHHMODList().get(player.getUniqueId());
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) + 5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getDFList().get(player.getUniqueId()) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getDFMODList().get(player.getUniqueId());
				double calcNow = cLevel * 8;
				double calcNext = (cLevel + 1) * 8;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		else if(join.getClassList().get(player.getUniqueId()).equals("Gluttony")) {
			i.setItem(4, menu.classGluttony(level));
			i.setItem(19, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(join.getASList().get(player.getUniqueId()) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getASMODList().get(player.getUniqueId());
				double calcNow = cLevel * 1;
				double calcNext = (cLevel + 1) * 1;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getCCList().get(player.getUniqueId()) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getCCMODList().get(player.getUniqueId());
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getHHList().get(player.getUniqueId()) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getHHMODList().get(player.getUniqueId());
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getDFList().get(player.getUniqueId()) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getDFMODList().get(player.getUniqueId());
				double calcNow = cLevel * 4;
				double calcNext = (cLevel + 1) * 4;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		else if(join.getClassList().get(player.getUniqueId()).equals("Greed")) {
			i.setItem(4, menu.classGreed(level));
			if(join.getADList().get(player.getUniqueId()) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getADMODList().get(player.getUniqueId());
				double calcNow = cLevel * 2;
				double calcNext = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getASList().get(player.getUniqueId()) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getASMODList().get(player.getUniqueId());
				double calcNow = cLevel * 10;
				double calcNext = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getCCList().get(player.getUniqueId()) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getCCMODList().get(player.getUniqueId());
				double calcNow = 15 + (cLevel * 5);
				double calcNext = 15 + ((cLevel + 1) * 5);
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getRDList().get(player.getUniqueId()) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getRDMODList().get(player.getUniqueId());
				double calcNow = cLevel * 0.5;
				double calcNext = (cLevel + 1) * 0.5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		else if(join.getClassList().get(player.getUniqueId()).equals("Sloth")) {
			i.setItem(4, menu.classSloth(level));
			if(join.getADList().get(player.getUniqueId()) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getADMODList().get(player.getUniqueId());
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getCCList().get(player.getUniqueId()) < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getCCMODList().get(player.getUniqueId());
				double calcNow = cLevel * 10;
				double calcNext = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(join.getHHList().get(player.getUniqueId()) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getHHMODList().get(player.getUniqueId());
				double calcNow = cLevel;
				double calcNext = cLevel + 1;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getDFList().get(player.getUniqueId()) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getDFMODList().get(player.getUniqueId());
				double calcNow = 90 - cLevel * 10;
				double calcNext = 90 - (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		else if(join.getClassList().get(player.getUniqueId()).equals("Envy")) {
			i.setItem(4, menu.classEnvy(level));
			if(join.getADList().get(player.getUniqueId()) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getADMODList().get(player.getUniqueId());
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getRDList().get(player.getUniqueId()) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getRDMODList().get(player.getUniqueId());
				double calcNow = cLevel * 10;
				double calcNext = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getHHList().get(player.getUniqueId()) < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getHHMODList().get(player.getUniqueId());
				double calcNow = 1 + cLevel * 0.75;
				double calcNext = 1 + (cLevel + 1) * 0.75;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getDFList().get(player.getUniqueId()) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getDFMODList().get(player.getUniqueId());
				double calcNow = cLevel * 7.5;
				double calcNext = (cLevel + 1) * 7.5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		else if(join.getClassList().get(player.getUniqueId()).equals("Pride")) {
			i.setItem(4, menu.classPride(level));
			if(join.getADList().get(player.getUniqueId()) < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getADMODList().get(player.getUniqueId());
				double calcNow = cLevel;
				double calcNext = (cLevel + 1);
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getASList().get(player.getUniqueId()) < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getASMODList().get(player.getUniqueId());
				double calcNow = cLevel * 0.5;
				double calcNext = (cLevel + 1) * 0.5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getRDList().get(player.getUniqueId()) < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getRDMODList().get(player.getUniqueId());
				double calcNow = cLevel * 2;
				double calcNext = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
			if(join.getDFList().get(player.getUniqueId()) < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = join.getDFMODList().get(player.getUniqueId());
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * join.getASMODList().get(player.getUniqueId()));
				int levelReq = 20 + (20 * join.getASMODList().get(player.getUniqueId()));
				double moneyReq = 20000.00 + (20000.00 * join.getASMODList().get(player.getUniqueId()));
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
		int skillPoints = join.getSkillPoints().get(p.getUniqueId());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&7Skill Points: &6" + skillPoints));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&7Here, you can view how many &6Skill Points &7You have."));
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
		int level = join.getLevelList().get(p.getUniqueId());
		int xp = join.getXPList().get(p.getUniqueId());
		int maxxp = join.getMXPList().get(p.getUniqueId());
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
		int levelNow = join.getADList().get(p.getUniqueId());
		double calcNow = join.getADList().get(p.getUniqueId()) * 2.5;
		double calcNext = (join.getADList().get(p.getUniqueId()) + 1.00) * 2.5;
		if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
			calcNow = join.getADList().get(p.getUniqueId()) * 3.75;
			calcNext = (join.getADList().get(p.getUniqueId()) + 1.00) * 3.75;
		}
		else if(join.getClassList().get(p.getUniqueId()).equals("Lust") || join.getClassList().get(p.getUniqueId()).equals("Gluttony")) {
			calcNow = join.getADList().get(p.getUniqueId()) * 1.25;
			calcNext = (join.getADList().get(p.getUniqueId()) + 1.00) * 1.25;
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
		int levelNow = join.getASList().get(p.getUniqueId());
		double calcNow = join.getASList().get(p.getUniqueId()) * 0.40;
		double calcNext = (join.getASList().get(p.getUniqueId()) + 1.00) * 0.40;
		if(join.getClassList().get(p.getUniqueId()).equals("Greed") || join.getClassList().get(p.getUniqueId()).equals("Pride")) {
			calcNow = join.getASList().get(p.getUniqueId()) * 0.60;
			calcNext = (join.getASList().get(p.getUniqueId()) + 1.00) * 0.60;
		}
		else if(join.getClassList().get(p.getUniqueId()).equals("Lust") || join.getClassList().get(p.getUniqueId()).equals("Envy") || join.getClassList().get(p.getUniqueId()).equals("Sloth")) {
			calcNow = join.getASList().get(p.getUniqueId()) * 0.20;
			calcNext = (join.getASList().get(p.getUniqueId()) + 1.00) * 0.20;
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
		int levelNow = join.getCCList().get(p.getUniqueId());
		double calcNow = join.getCCList().get(p.getUniqueId()) * 0.50;
		double calcNext = (join.getCCList().get(p.getUniqueId()) + 1.00) * 0.50;
		if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
			calcNow = join.getCCList().get(p.getUniqueId()) * 0.75;
			calcNext = (join.getCCList().get(p.getUniqueId()) + 1.00) * 0.75;
		}
		else if(join.getClassList().get(p.getUniqueId()).equals("Pride") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
			calcNow = join.getCCList().get(p.getUniqueId()) * 0.25;
			calcNext = (join.getCCList().get(p.getUniqueId()) + 1.00) * 0.25;
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
		int levelNow = join.getRDList().get(p.getUniqueId());
		double calcNow = join.getRDList().get(p.getUniqueId()) * 3.00;
		double calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 3.00;
		if(join.getClassList().get(p.getUniqueId()).equals("Greed") || join.getClassList().get(p.getUniqueId()).equals("Lust") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
			calcNow = join.getRDList().get(p.getUniqueId()) * 4.50;
			calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 4.50;
		}
		else if(join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Gluttony")) {
			calcNow = join.getRDList().get(p.getUniqueId()) * 1.50;
			calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 1.50;
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
		int levelNow = join.getRDList().get(p.getUniqueId());
		double calcNow = join.getRDList().get(p.getUniqueId()) * 5.00;
		double calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 5.00;
		if(join.getClassList().get(p.getUniqueId()).equals("Lust") || join.getClassList().get(p.getUniqueId()).equals("Gluttony")) {
			calcNow = join.getRDList().get(p.getUniqueId()) * 7.50;
			calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 7.50;
		}
		else if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
			calcNow = join.getRDList().get(p.getUniqueId()) * 2.50;
			calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 2.50;
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
		int levelNow = join.getRDList().get(p.getUniqueId());
		double calcNow = join.getRDList().get(p.getUniqueId()) * 3.33;
		double calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 3.33;
		if(join.getClassList().get(p.getUniqueId()).equals("Gluttony") || join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Pride")) {
			calcNow = join.getRDList().get(p.getUniqueId()) * 5.00;
			calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 5.00;
		}
		else if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
			calcNow = join.getRDList().get(p.getUniqueId()) * 1.66;
			calcNext = (join.getRDList().get(p.getUniqueId()) + 1.00) * 1.66;
		}
		meta.setDisplayName(new ColorCodeTranslator().colorize("&8Defense: [&7&b" + levelNow + " &6/ &b100&7]"));
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
