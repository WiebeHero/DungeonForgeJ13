package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.EnumSkills.SkillState;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;
import me.WiebeHero.DFPlayerPackage.State.States;

public class SkillMenu {
	private DFPlayerManager dfManager;
	private ItemStackBuilder builder;
	private ClassMenu menu;
	public SkillMenu(DFPlayerManager manager, ItemStackBuilder builder, ClassMenu menu) {
		this.dfManager = manager;
		this.builder = builder;
		this.menu = menu;
	}
	public void SkillMenuInv(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, 36, new CCT().colorize("&7Skills of: &6" + player.getName()));
		DFPlayer dfPlayer = dfManager.getEntity(player);
		dfPlayer.resetCalculations();
		i.setItem(0, skillBook(player));
		i.setItem(1, empty());
		i.setItem(2, empty());
		i.setItem(3, empty());
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		//Wrath
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		if(dfPlayer.getPlayerClass() == Classes.WRATH) {
			i.setItem(4, menu.classWrath(dfPlayer.getLevel()));
			if(dfPlayer.getAtk() < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getAtkMod();
				double inc = cLevel * 1.5;
				double inc1 = (cLevel + 1) * 1.5;
				double dur = cLevel * 2.5;
				double dur1 = (cLevel + 1) * 2.5;
				int pLevelReq = 10 + (10 * dfPlayer.getAtkMod());
				int levelReq = 20 + (20 * dfPlayer.getAtkMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getAtkMod());
				i.setItem(19, modCreator(player, Material.CHAINMAIL_CHESTPLATE, "&4Passive Modifier, Chain Strikes: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your Attack Damage increases by a certain % per enemy struck",
				"&7for a few seconds.",
				"&7-------------------",
				"&4Damage Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getSpd() < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getSpdMod();
				double inc = cLevel * 0.75;
				double inc1 = (cLevel + 1) * 0.75;
				double dur = cLevel * 2;
				double dur1 = (cLevel + 1) * 2;
				int pLevelReq = 10 + (10 * dfPlayer.getSpdMod());
				int levelReq = 20 + (20 * dfPlayer.getSpdMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getSpdMod());
				i.setItem(20, modCreator(player, Material.GOLDEN_HOE, "&9Passive Ability, Combo Strike: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Hitting an enemy increases your Attack Damage and Critical Chance",
				"&7by a certain % for a few seconds",
				"&7-------------------",
				"&4Damage Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&5Critical Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getCrt() < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getCrtMod();
				double inc = cLevel * 1;
				double inc1 = (cLevel + 1) * 1;
				double dur = cLevel * 2.5;
				double dur1 = (cLevel + 1) * 2.5;
				int pLevelReq = 10 + (10 * dfPlayer.getCrtMod());
				int levelReq = 20 + (20 * dfPlayer.getCrtMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getCrtMod());
				i.setItem(21, modCreator(player, Material.COOKED_CHICKEN, "&5Passive Modifier, Electrical Surge: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your Critical Chance increases by a certain % per enemy struck",
				"&7for a few seconds.",
				"&7-------------------",
				"&5Critical Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getRnd() < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getRndMod();
				double dur = cLevel * 3;
				double dur1 = (cLevel + 1) * 3;
				int pLevelReq = 10 + (10 * dfPlayer.getRndMod());
				int levelReq = 20 + (20 * dfPlayer.getRndMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getRndMod());
				i.setItem(23, modCreator(player, Material.BOW, "&dPassive Modifier, Banish: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Enemies who have been struck by your ability won't be able",
				"&7to use ranged weapons for a few seconds",
				"&7-------------------",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
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
		else if(dfPlayer.getPlayerClass() == Classes.LUST) {
			i.setItem(4, menu.classLust(dfPlayer.getLevel()));
			i.setItem(19, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			
			i.setItem(20, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(dfPlayer.getCrt() < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getCrtMod();
				double inc = cLevel * 0.5;
				double inc1 = (cLevel + 1) * 0.5;
				double dur = cLevel * 2.5;
				double dur1 = (cLevel + 1) * 2.5;
				int pLevelReq = 10 + (10 * dfPlayer.getCrtMod());
				int levelReq = 20 + (20 * dfPlayer.getCrtMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getCrtMod());
				i.setItem(21, modCreator(player, Material.BLAZE_ROD, "&5Passive Ability, Critical Down: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When your attack hits your enemy, their Critical Chance is decreased",
				"&7By a certain % for a few seconds.",
				"&7-------------------",
				"&5Critical Decrease: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getRnd() < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getRndMod();
				double inc = cLevel * 10;
				double inc1 = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * dfPlayer.getRndMod());
				int levelReq = 20 + (20 * dfPlayer.getRndMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getRndMod());
				i.setItem(23, modCreator(player, Material.ARMOR_STAND, "&dPassive Ability, Overdrive: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increases your ranged damage as your HP decreases.",
				"&7-------------------",
				"&dRanged Increase (MAX): " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getHp() < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getHpMod();
				double sh = cLevel * 10;
				double sh1 = (cLevel + 1) * 10;
				double dur = cLevel * 5;
				double dur1 = (cLevel + 1) + 5;
				int pLevelReq = 10 + (10 * dfPlayer.getHpMod());
				int levelReq = 20 + (20 * dfPlayer.getHpMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getHpMod());
				i.setItem(24, modCreator(player, Material.ROSE_RED, "&cPassive Modifier, Overheal: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Exceeded amount of health healed will be put up as",
				"&7an absorption shield for a few seconds.",
				"&7-------------------",
				"&dShield Percentage: " + String.format("%.2f", sh) + "% ---> " + String.format("%.2f", sh1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getDf() < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getDfMod();
				double df = cLevel * 10;
				double df1 = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * dfPlayer.getDfMod());
				int levelReq = 20 + (20 * dfPlayer.getDfMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getDfMod());
				i.setItem(25, modCreator(player, Material.DIAMOND_CHESTPLATE, "&8Passive Ability, Dire Situation: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increases your defense as your HP decreases.",
				"&7-------------------",
				"&8Defense Increase (MAX): " + String.format("%.2f", df) + "% ---> " + String.format("%.2f", df1) + "%",
				"&7-------------------",
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
		else if(dfPlayer.getPlayerClass() == Classes.GLUTTONY) {
			i.setItem(4, menu.classGluttony(dfPlayer.getLevel()));
			i.setItem(19, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(dfPlayer.getSpd() < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getSpdMod();
				double hp = cLevel * 4;
				double hp1 = (cLevel + 1) * 4;
				double de = cLevel * 1;
				double de1 = (cLevel + 1) * 1;
				int pLevelReq = 10 + (10 * dfPlayer.getSpdMod());
				int levelReq = 20 + (20 * dfPlayer.getSpdMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getSpdMod());
				i.setItem(20, modCreator(player, Material.LEATHER_BOOTS, "&9Passive Modifier, Spirited: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increases the duration of your ability and increases your recovery rate",
				"&7by a certain % for the duration of your ability.",
				"&7-------------------",
				"&cRecovery Increase: " + String.format("%.2f", hp) + "% ---> " + String.format("%.2f", hp1) + "%",
				"&bDecrease Ability: " + String.format("%.2f", de) + " Seconds ---> " + String.format("%.2f", de1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getCrt() < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getCrtMod();
				double calcNow = cLevel * 1.5;
				double calcNext = (cLevel + 1) * 1.5;
				double dur = cLevel * 3;
				double dur1 = (cLevel + 1) * 3;
				int pLevelReq = 10 + (10 * dfPlayer.getCrtMod());
				int levelReq = 20 + (20 * dfPlayer.getCrtMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getCrtMod());
				i.setItem(21, modCreator(player, Material.POTION, "&5Passive Modifier, Intimidation: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you are hit when your ability is active, you decrease",
				"&7the enemies Critical Chance by a certain % for a few seconds.",
				"&7-------------------",
				"&5Critical Decrease: " + String.format("%.2f", calcNow) + "% ---> " + String.format("%.2f", calcNext) + "%",
				"&bDuration: " + String.format("%.2f", dur) + "% ---> " + String.format("%.2f", dur1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(23, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(dfPlayer.getHp() < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getHpMod();
				double calcNow = cLevel * 5;
				double calcNext = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * dfPlayer.getHpMod());
				int levelReq = 20 + (20 * dfPlayer.getHpMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getHpMod());
				i.setItem(24, modCreator(player, Material.POPPY, "&cPassive Modifier, Fast Recovery: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your recovery rate is increased by a certain % during your ability.",
				"&7-------------------",
				"&cRecovery Increase: " + String.format("%.2f", calcNow) + "% ---> " + String.format("%.2f", calcNext) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getDf() < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getDfMod();
				double inc = cLevel * 30;
				double inc1 = (cLevel + 1) * 30;
				double dur = cLevel * 2.5;
				double dur1 = (cLevel + 1) * 2.5;
				int pLevelReq = 10 + (10 * dfPlayer.getDfMod());
				int levelReq = 20 + (20 * dfPlayer.getDfMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getDfMod());
				i.setItem(25, modCreator(player, Material.IRON_BLOCK, "&8Passive Ability, Iron Wall: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you drop bellow 40% of your MAX HP, your defense is increased",
				"&7for a few seconds.",
				"&7-------------------",
				"&8Defense Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
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
		else if(dfPlayer.getPlayerClass() == Classes.GREED) {
			i.setItem(4, menu.classGreed(dfPlayer.getLevel()));
			if(dfPlayer.getAtk() < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getAtkMod();
				double dec = cLevel * 15;
				double dec1 = (cLevel + 1) * 15;
				int pLevelReq = 10 + (10 * dfPlayer.getAtkMod());
				int levelReq = 20 + (20 * dfPlayer.getAtkMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getAtkMod());
				i.setItem(19, modCreator(player, Material.ARROW, "&4Passive Modifier, Secret Serum: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you hit the enemy with your ability, their Attack Damage decreases",
				"&7for the same duration as your ability.",
				"&7-------------------",
				"&4Attack Decrease: " + String.format("%.2f", dec) + "% ---> " + String.format("%.2f", dec1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getSpd() < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getSpdMod();
				double inc = cLevel * 8;
				double inc1 = (cLevel + 1) * 8;
				int pLevelReq = 10 + (10 * dfPlayer.getSpdMod());
				int levelReq = 20 + (20 * dfPlayer.getSpdMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getSpdMod());
				i.setItem(20, modCreator(player, Material.ARMOR_STAND, "&9Passive Modifier, Hunt Down: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you shoot someone with your ability, your movement speed",
				"&7Increases by a certain % for the same duration as your ability.",
				"&7-------------------",
				"&9Move Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getCrt() < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getCrtMod();
				double inc = cLevel + 8;
				double inc1 = (cLevel + 1) * 8;
				int pLevelReq = 10 + (10 * dfPlayer.getCrtMod());
				int levelReq = 20 + (20 * dfPlayer.getCrtMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getCrtMod());
				i.setItem(21, modCreator(player, Material.POTION, "&5Passive Modifier, Alchaholic Arrow: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7When you hit the enemy with your ability, their movement speed decreases",
				"&7and they will get nausea for the same duration as your ability.",
				"&7-------------------",
				"&9Move Decrease: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getRnd() < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getRndMod();
				double inc = cLevel * 1;
				double inc1 = (cLevel + 1) * 1;
				int pLevelReq = 10 + (10 * dfPlayer.getRndMod());
				int levelReq = 20 + (20 * dfPlayer.getRndMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getRndMod());
				i.setItem(23, modCreator(player, Material.REDSTONE, "&dPassive Ability, Courage: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increase your Ranged Damage and attack speed every shot you hit",
				"&7by a certain % for the same duration as your ability",
				"&7-------------------",
				"&5Ranged/Speed Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
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
		else if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
			i.setItem(4, menu.classSloth(dfPlayer.getLevel()));
			if(dfPlayer.getAtk() < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getAtkMod();
				double inc = cLevel * 0.4;
				double inc1 = (cLevel + 1) * 0.4;
				int pLevelReq = 10 + (10 * dfPlayer.getAtkMod());
				int levelReq = 20 + (20 * dfPlayer.getAtkMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getAtkMod());
				i.setItem(19, modCreator(player, Material.ENCHANTED_BOOK, "&4Passive Modifier, What Counts: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your next attack's damage is increased by the amount of arrows you shot",
				"&7and per enemy you have hit with your ability. This only works when your ability is active.",
				"&7-------------------",
				"&4Attack Increase Per Shot: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(20, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(dfPlayer.getCrt() < 20) {
				i.setItem(21, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getCrtMod();
				double inc = cLevel * 5;
				double inc1 = (cLevel + 1) * 5;
				int pLevelReq = 10 + (10 * dfPlayer.getCrtMod());
				int levelReq = 20 + (20 * dfPlayer.getCrtMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getCrtMod());
				i.setItem(21, modCreator(player, Material.FIRE_CHARGE, "&5Passive Ability, Turn the Table: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your Critical Chance increases when your HP is above 50%",
				"&7-------------------",
				"&5Critical Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(23, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, be  cause this",
			"&7is one of your downsides."))));
			if(dfPlayer.getHp() < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getHpMod();
				double inc = cLevel * 0.5;
				double inc1 = (cLevel + 1) * 0.5;
				int pLevelReq = 10 + (10 * dfPlayer.getHpMod());
				int levelReq = 20 + (20 * dfPlayer.getHpMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getHpMod());
				i.setItem(24, modCreator(player, Material.RED_STAINED_GLASS, "&cPassive Modifier, Drain: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You heal HP per enemy hit by your ability.",
				"&7-------------------",
				"&cHealth healed: " + String.format("%.2f", inc) + " ---> " + String.format("%.2f", inc1) + "",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getDf() < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getDfMod();
				double inc = cLevel * 25;
				double inc1 = (cLevel + 1) * 25;
				int pLevelReq = 10 + (10 * dfPlayer.getDfMod());
				int levelReq = 20 + (20 * dfPlayer.getDfMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getDfMod());
				i.setItem(25, modCreator(player, Material.CREEPER_HEAD, "&8Passive Ability, Full of It: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your defense is increased by a % when your health is below 50%",
				"&7-------------------",
				"&8Defense Increase: " + String.format("%.2f", inc) + " ---> " + String.format("%.2f", inc1) + "",
				"&7-------------------",
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
		else if(dfPlayer.getPlayerClass() == Classes.ENVY) {
			i.setItem(4, menu.classEnvy(dfPlayer.getLevel()));
			if(dfPlayer.getAtk() < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getAtkMod();
				double weak = cLevel * 5;
				double weak1 = (cLevel + 1) * 5;
				double dur = cLevel * 3;
				double dur1 = cLevel * 3;
				int pLevelReq = 10 + (10 * dfPlayer.getAtkMod());
				int levelReq = 20 + (20 * dfPlayer.getAtkMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getAtkMod());
				i.setItem(19, modCreator(player, Material.GOLDEN_SWORD, "&4Passive Modifier, Weaken: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You hit the enemy in such a way that their arms feel numb.",
				"&7Their attack damage and their attack speed",
				"&7are decreased by a certain % for a few seconds",
				"&7-------------------",
				"&4Decrease: " + String.format("%.2f", weak) + "% ---> " + String.format("%.2f", weak1) + " %",
				"&bDuration: " + String.format("%.2f", dur) + "Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
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
			if(dfPlayer.getRnd() < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getRndMod();
				double weak = cLevel * 2.5;
				double weak1 = (cLevel + 1) * 2.5;
				double dur = cLevel * 3;
				double dur1 = (cLevel + 1) * 3;
				int pLevelReq = 10 + (10 * dfPlayer.getRndMod());
				int levelReq = 20 + (20 * dfPlayer.getRndMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getRndMod());
				i.setItem(23, modCreator(player, Material.GOLDEN_BOOTS, "&dPassive Modifier, Unlucky: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You hit the enemy, some unfortonate events",
				"&7follow. Their critical chance and ranged damage are decreased by a",
				"&7certain % for a few seconds.",
				"&7-------------------",
				"&dDecrease: " + String.format("%.2f", weak) + "% ---> " + String.format("%.2f", weak1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getHp() < 20) {
				i.setItem(24, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getHpMod();
				double hp = 1 + cLevel * 7;
				double hp1 = 1 + (cLevel + 1) * 7;
				double dur = cLevel * 3;
				double dur1 = (cLevel + 1) * 3;
				int pLevelReq = 10 + (10 * dfPlayer.getHpMod());
				int levelReq = 20 + (20 * dfPlayer.getHpMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getHpMod());
				i.setItem(24, modCreator(player, Material.RED_STAINED_GLASS, "&cPassive Modifier, Disturb Recovery: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You hit the enemy in thier soul, and their recovery is disturbed.",
				"&7The enemy's hp recovery is decreased by a certain % for a few seconds.",
				"&7-------------------",
				"&cRecovery Decrease: " + String.format("%.2f", hp) + "% ---> " + String.format("%.2f", hp1) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getDf() < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getDfMod();
				double break1 = cLevel * 10;
				double break2 = (cLevel + 1) * 10;
				double dur = cLevel * 3;
				double dur1 = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * dfPlayer.getDfMod());
				int levelReq = 20 + (20 * dfPlayer.getDfMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getDfMod());
				i.setItem(25, modCreator(player, Material.GOLDEN_CHESTPLATE, "&8Passive Modifier, Armor Break: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7You hit the enemy in their weak spot, their defense",
				"&7is decreased by a certain %",
				"&7-------------------",
				"&cDefense Decrease: " + String.format("%.2f", break1) + "% ---> " + String.format("%.2f", break2) + "%",
				"&bDuration: " + String.format("%.2f", dur) + " Seconds ---> " + String.format("%.2f", dur1) + " Seconds",
				"&7-------------------",
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
		else if(dfPlayer.getPlayerClass() == Classes.PRIDE) {
			i.setItem(4, menu.classPride(dfPlayer.getLevel()));
			if(dfPlayer.getAtk() < 20) {
				i.setItem(19, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getAtkMod();
				double inc = cLevel * 1.5;
				double inc1 = (cLevel + 1) * 1.5;
				int pLevelReq = 10 + (10 * dfPlayer.getAtkMod());
				int levelReq = 20 + (20 * dfPlayer.getAtkMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getAtkMod());
				i.setItem(19, modCreator(player, Material.FERMENTED_SPIDER_EYE, "&4Passive Ability, Terminator: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Your attack damage increases per armor defense/toughness you have",
				"&7-------------------",
				"&4Attack Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			if(dfPlayer.getSpd() < 20) {
				i.setItem(20, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getSpdMod();
				double inc = cLevel * 10;
				double inc1 = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * dfPlayer.getSpdMod());
				int levelReq = 20 + (20 * dfPlayer.getSpdMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getSpdMod());
				i.setItem(20, modCreator(player, Material.BONE, "&9Passive Modifier, Be Efficient: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increases your attack speed when activating your ability.",
				"&7-------------------",
				"&9Speed Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(21, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(dfPlayer.getRnd() < 20) {
				i.setItem(23, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getRndMod();
				double inc = cLevel * 10;
				double inc1 = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * dfPlayer.getRndMod());
				int levelReq = 20 + (20 * dfPlayer.getRndMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getRndMod());
				i.setItem(23, modCreator(player, Material.ICE, "&dPassive Ability, Deflection: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Decrease damage from ranged attacks.",
				"&7-------------------",
				"&dRanged Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
				"&cRequirements:",
				"&a" + moneyReq + "$",
				"&4Attack Damage: " + levelReq,
				"&6Player Level: " + pLevelReq
				))));
			}
			i.setItem(24, modCreator(player, Material.BARRIER, "&4&lUndefined", new ArrayList<String>(Arrays.asList(
			"&7This ability modifier doesn't exist, because this",
			"&7is one of your downsides."))));
			if(dfPlayer.getDf() < 20) {
				i.setItem(25, modCreator(player, Material.IRON_BARS, "&c&lLOCKED", new ArrayList<String>(Arrays.asList(
				"&7This ability modifier is still locked! Reach level 20",
				"&7to unlock this ability modifier!"))));
			}
			else {
				int cLevel = dfPlayer.getDfMod();
				double inc = cLevel * 10;
				double inc1 = (cLevel + 1) * 10;
				int pLevelReq = 10 + (10 * dfPlayer.getDfMod());
				int levelReq = 20 + (20 * dfPlayer.getDfMod());
				double moneyReq = 20000.00 + (20000.00 * dfPlayer.getDfMod());
				i.setItem(25, modCreator(player, Material.IRON_CHESTPLATE, "&8Passive Modifier, Metalic: &7[&b" + cLevel + " &6/ &b5&7]", new ArrayList<String>(Arrays.asList(
				"&7Increases your defense when activating your ability",
				"&7-------------------",
				"&8Defense Increase: " + String.format("%.2f", inc) + "% ---> " + String.format("%.2f", inc1) + "%",
				"&7-------------------",
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
		i.setItem(31, builder.constructItem(
				Material.PAPER,
				"&cReset",
				new ArrayList<String>(Arrays.asList(
					"&cClick this to reset your character.",
					"&cThis requires level 10."
				)),
				"Reset"
		));
		i.setItem(32, empty());
		i.setItem(33, empty());
		i.setItem(34, empty());
		i.setItem(35, empty());
		player.openInventory(i);
	}
	public void ResetSure(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, InventoryType.HOPPER, new CCT().colorize("&7Reset Profile: &6" + player.getName()));
		i.setItem(0, builder.constructItem(
				Material.LIME_STAINED_GLASS_PANE,
				"&aConfirm resetting your profile",
				new ArrayList<String>(Arrays.asList(
						"&7By pressing this button, you agree",
						"&7to reset your player profile.",
						"&4&lWARNING, this will:",
						"  &cSet your level to 1",
						"  &cReset your class",
						"  &cSet all of your skills to 0"
				)),
				"Confirm"
		));
		i.setItem(1, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(2, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(3, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(4, builder.constructItem(
				Material.RED_STAINED_GLASS_PANE,
				"&aCancel resetting your profile",
				new ArrayList<String>(Arrays.asList(
						"&7By pressing this button, you cancel",
						"&7resetting your profile."
				)),
				"Cancel"
		));
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
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.BOOK);
		int skillPoints = dfPlayer.getSkillPoints();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&7Skill Points: &6" + skillPoints));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Here, you can view how many &6Skill Points &7you have."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack resetButton() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&cReset"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Click this to reset."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack warning() {
		ItemStack item = new ItemStack(Material.RED_WOOL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&l&cWARNING"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7You are now in the reset menu."));
		lore.add(new CCT().colorize("&7If you want to reset, meaning you reset you'r skills"));
		lore.add(new CCT().colorize("&7to 0 and resetting your class to none. Then click &aYes."));
		lore.add(new CCT().colorize("&7However, if you do not desire this, then click on &cNo"));
		lore.add(new CCT().colorize("&7or exit the inventory."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack modCreator(Player p, Material mat, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> replaceLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			replaceLore.add(new CCT().colorize(lore.get(i)));
		}
		meta.setLore(replaceLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack progression(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.ENCHANTING_TABLE);
		int level = dfPlayer.getLevel();
		int xp = dfPlayer.getExperience();
		int maxxp = dfPlayer.getMaxExperience();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&6Progression Table"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Level: &b" + level));
		lore.add(new CCT().colorize("&7Next Level: &7[&b" + xp + " &6/ &b" + maxxp + "&7]"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack ad(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		ItemMeta meta = item.getItemMeta();
		int levelNow = dfPlayer.getAtk();
		double calcNow = dfPlayer.getAtkCal();
		double calcNext = dfPlayer.getAtkCal();
		if(dfPlayer.getSkillState(SkillState.ATK) == States.UP) {
			calcNext = calcNext + 2.25;
		}
		else if(dfPlayer.getSkillState(SkillState.ATK) == States.DW) {
			calcNext = calcNext + 0.75;
		}
		else if(dfPlayer.getSkillState(SkillState.ATK) == States.NM){
			calcNext = calcNext + 1.5;
		}
		meta.setDisplayName(new CCT().colorize("&4Attack Damage: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Increase the damage you deal through melee."));
		lore.add(new CCT().colorize(""));
		lore.add(new CCT().colorize("                 &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new CCT().colorize("                        &7|"));
		lore.add(new CCT().colorize("                        &7|"));
		lore.add(new CCT().colorize("                        &7|"));
		lore.add(new CCT().colorize("                 &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack as(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		int levelNow = dfPlayer.getSpd();
		double calcNow = dfPlayer.getSpdCal();
		double calcNext = dfPlayer.getSpdCal();
		if(dfPlayer.getSkillState(SkillState.SPD) == States.UP) {
			calcNext = calcNext + 0.75;
		}
		else if(dfPlayer.getSkillState(SkillState.SPD) == States.DW) {
			calcNext = calcNext + 0.25;
		}
		else if(dfPlayer.getSkillState(SkillState.SPD) == States.NM){
			calcNext = calcNext + 0.50;
		}
		meta.setDisplayName(new CCT().colorize("&9Attack Speed: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7You can attack faster."));
		lore.add(new CCT().colorize(""));
		lore.add(new CCT().colorize("    &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new CCT().colorize("           &7|"));
		lore.add(new CCT().colorize("           &7|"));
		lore.add(new CCT().colorize("           &7|"));
		lore.add(new CCT().colorize("    &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack cc(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta meta = item.getItemMeta();
		int levelNow = dfPlayer.getCrt();
		double calcNow = dfPlayer.getCrtCal();
		double calcNext = dfPlayer.getCrtCal();
		if(dfPlayer.getSkillState(SkillState.CRT) == States.UP) {
			calcNext = calcNext + 0.75;
		}
		else if(dfPlayer.getSkillState(SkillState.CRT) == States.DW) {
			calcNext = calcNext + 0.25;
		}
		else if(dfPlayer.getSkillState(SkillState.CRT) == States.NM){
			calcNext = calcNext + 0.50;
		}
		meta.setDisplayName(new CCT().colorize("&5Critical Chance: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Have a chance to deal double damage."));
		lore.add(new CCT().colorize(""));
		lore.add(new CCT().colorize("              &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new CCT().colorize("                     &7|"));
		lore.add(new CCT().colorize("                     &7|"));
		lore.add(new CCT().colorize("                     &7|"));
		lore.add(new CCT().colorize("              &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack rd(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta meta = item.getItemMeta();
		int levelNow = dfPlayer.getRnd();
		double calcNow = dfPlayer.getRndCal();
		double calcNext = dfPlayer.getRndCal();
		if(dfPlayer.getSkillState(SkillState.RND) == States.UP) {
			calcNext = calcNext + 3.0;
		}
		else if(dfPlayer.getSkillState(SkillState.RND) == States.DW) {
			calcNext = calcNext + 1.0;
		}
		else if(dfPlayer.getSkillState(SkillState.RND) == States.NM){
			calcNext = calcNext + 2.0;
		}
		meta.setDisplayName(new CCT().colorize("&dRanged Damage: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Increase the damage you deal through ranged."));
		lore.add(new CCT().colorize(""));
		lore.add(new CCT().colorize("                   &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new CCT().colorize("                          &7|"));
		lore.add(new CCT().colorize("                          &7|"));
		lore.add(new CCT().colorize("                          &7|"));
		lore.add(new CCT().colorize("                   &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack hh(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.APPLE);
		ItemMeta meta = item.getItemMeta();
		int levelNow = dfPlayer.getHp();
		double calcNow = dfPlayer.getHpCal();
		double calcNext = dfPlayer.getHpCal();
		if(dfPlayer.getSkillState(SkillState.HP) == States.UP) {
			calcNext = calcNext + 7.5;
		}
		else if(dfPlayer.getSkillState(SkillState.HP) == States.DW) {
			calcNext = calcNext + 2.5;
		}
		else if(dfPlayer.getSkillState(SkillState.HP) == States.NM){
			calcNext = calcNext + 5.0;
		}
		meta.setDisplayName(new CCT().colorize("&cHealth: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Increase your MAX HP."));
		lore.add(new CCT().colorize(""));
		lore.add(new CCT().colorize("     &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new CCT().colorize("            &7|"));
		lore.add(new CCT().colorize("            &7|"));
		lore.add(new CCT().colorize("            &7|"));
		lore.add(new CCT().colorize("     &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack df(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta meta = item.getItemMeta();
		int levelNow = dfPlayer.getDf();
		double calcNow = dfPlayer.getDfCal();
		double calcNext = dfPlayer.getDfCal();
		if(dfPlayer.getSkillState(SkillState.DF) == States.UP) {
			calcNext = calcNext + 2.25;
		}
		else if(dfPlayer.getSkillState(SkillState.DF) == States.DW) {
			calcNext = calcNext + 0.75;
		}
		else if(dfPlayer.getSkillState(SkillState.DF) == States.NM){
			calcNext = calcNext + 1.50;
		}
		meta.setDisplayName(new CCT().colorize("&8Defense: &7[&b" + levelNow + " &6/ &b100&7]"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Increase armor points you recieve from armor."));
		lore.add(new CCT().colorize(""));
		lore.add(new CCT().colorize("                   &7&lCurrent: " + String.format("%.2f", calcNow) + "%"));
		lore.add(new CCT().colorize("                          &7|"));
		lore.add(new CCT().colorize("                          &7|"));
		lore.add(new CCT().colorize("                          &7|"));
		lore.add(new CCT().colorize("                   &6&lNext: " + String.format("%.2f", calcNext) + "%"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
