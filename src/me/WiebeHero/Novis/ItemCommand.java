package me.WiebeHero.Novis;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class ItemCommand  implements CommandExecutor{
	private NovisRewards rewards;
	private RankedManager rManager;
	private NovisEnchantmentGetting enchant;
	public ItemCommand(NovisRewards rewards, RankedManager rManager, NovisEnchantmentGetting enchant) {
		this.rewards = rewards;
		this.rManager = rManager;
		this.enchant = enchant;
	}
	public String itemCmd = "customitem";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(rManager.contains(player.getUniqueId())) {
				RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
				if(cmd.getName().equalsIgnoreCase(itemCmd)) {
					if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
						if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
							HashMap<String, ItemStack> stacks = this.rewards.getItemList();
							if(args.length == 1) {
								if(stacks.containsKey(args[0])) {
									ItemStack i = stacks.get(args[0]).clone();
									player.getInventory().addItem(i);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
								}
							}
							else if(args.length == 2) {
								if(stacks.containsKey(args[0])) {
									ItemStack i = stacks.get(args[0]).clone();
									NBTItem item = new NBTItem(i);
									boolean valid = true;
									int level = 0;
									try {
										level = Integer.parseInt(args[1]);
									}
									catch(NumberFormatException ex) {
										valid = false;
										ex.printStackTrace();
									}
									if(valid) {
										if(level > 0 && level <= 15) {
											if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
												int itemLevel = item.getInteger("Level");
												int maxxp = item.getInteger("MAXXP");
												int total = item.getInteger("TotalXP");	
												String name = item.getString("ItemName");
												String rarity = item.getString("Rarity");
												String enchantmentString = item.getString("EnchantmentString");
												double baseDamage = item.getDouble("Base Attack Damage");
												double baseSpeed = item.getDouble("Base Attack Speed");
												double incDamage = item.getDouble("Inc Attack Damage");
												double incSpeed = item.getDouble("Inc Attack Speed");
												for(int x = itemLevel + 1; x <= level; x++) {
													itemLevel = x;
													total = total + maxxp;
													maxxp = maxxp / 100 * 135;
												}
												double value1 = baseDamage + incDamage * (itemLevel - 1);
								            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
												//Config Data
												//Weapon Data
								            	item.setInteger("Level", itemLevel);
								            	item.setInteger("XP", 0);
								            	item.setInteger("MAXXP", maxxp);
								            	item.setInteger("TotalXP", total);
								            	item.setDouble("Attack Damage", value1);
								            	item.setDouble("Attack Speed", value2);
								            	i = item.getItem();
												ItemMeta meta = i.getItemMeta();
												meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
												ArrayList<String> newLore = new ArrayList<String>();
												newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
												double roundOff1 = (double) Math.round(value1 * 100) / 100;
												double roundOff2 = (double) Math.round(value2 * 100) / 100;
												newLore.add(new CCT().colorize("&7-----------------------"));
												newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
												newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel < 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
													newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
												}
												else if(itemLevel == 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
												}
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel >= 6) {
													int loreRequired = itemLevel - 4;
													int levelRequired = loreRequired * 5;
													newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
												}
												newLore.add(new CCT().colorize("&7Rarity: " + rarity));
												meta.setLore(newLore);
												i.setItemMeta(meta);
											}
											else if(item.hasKey("ArmorKey")) {
												if(i.getType().toString().contains("HELMET")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(i.getType().toString().contains("CHESTPLATE")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(i.getType().toString().contains("LEGGINGS")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(i.getType().toString().contains("BOOTS")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
											}
											else if(item.hasKey("ShieldKey")) {
												int itemLevel = item.getInteger("Level");
												int maxxp = item.getInteger("MAXXP");
												int total = item.getInteger("TotalXP");	
												String name = item.getString("ItemName");
												String rarity = item.getString("Rarity");
												String enchantmentString = item.getString("EnchantmentString");
												double baseDamage = item.getDouble("Base Armor Toughness");
												double baseSpeed = item.getDouble("Base Cooldown");
												double incDamage = item.getDouble("Inc Armor Toughness");
												double incSpeed = item.getDouble("Inc Cooldown");
												for(int x = itemLevel + 1; x <= level; x++) {
													itemLevel = x;
													total = total + maxxp;
													maxxp = maxxp / 100 * 135;
												}
												double value1 = baseDamage + incDamage * (itemLevel - 1);
								            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
												//Config Data
												//Weapon Data
								            	item.setInteger("Level", itemLevel);
								            	item.setInteger("XP", 0);
								            	item.setInteger("MAXXP", maxxp);
								            	item.setInteger("TotalXP", total);
								            	item.setDouble("Armor Toughness", value1);
								            	item.setDouble("Cooldown", value2);
								            	i = item.getItem();
												ItemMeta meta = i.getItemMeta();
												meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
												ArrayList<String> newLore = new ArrayList<String>();
												newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
												double roundOff1 = (double) Math.round(value1 * 100) / 100;
												double roundOff2 = (double) Math.round(value2 * 100) / 100;
												newLore.add(new CCT().colorize("&7-----------------------"));
												newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
												newLore.add(new CCT().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel < 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
													newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
												}
												else if(itemLevel == 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
												}
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel >= 6) {
													int loreRequired = itemLevel - 4;
													int levelRequired = loreRequired * 5;
													newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
												}
												newLore.add(new CCT().colorize("&7Rarity: " + rarity));
												meta.setLore(newLore);
												i.setItemMeta(meta);
											}
											player.getInventory().addItem(i);
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level must be in between 1 and 15!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis input as level is not valid! The level needs to be a number!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
								}
							}
							else if(args.length == 3) {
								if(stacks.containsKey(args[0])) {
									ItemStack i = stacks.get(args[0]).clone();
									NBTItem item = new NBTItem(i);
									boolean validLevel = true;
									boolean validXp = true;
									int level = 0;
									int xp = 0;
									try {
										level = Integer.parseInt(args[1]);
									}
									catch(NumberFormatException ex) {
										validLevel = false;
										ex.printStackTrace();
									}
									try {
										xp = Integer.parseInt(args[2]);
									}
									catch(NumberFormatException ex) {
										validXp = false;
										ex.printStackTrace();
									}
									if(validLevel) {
										if(level > 0 && level <= 15) {
											if(validXp) {
												if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Attack Damage");
													double baseSpeed = item.getDouble("Base Attack Speed");
													double incDamage = item.getDouble("Inc Attack Damage");
													double incSpeed = item.getDouble("Inc Attack Speed");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
									            	if(xp >= maxxp) {
									            		xp = maxxp - 1;
									            	}
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", xp);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Attack Damage", value1);
									            	item.setDouble("Attack Speed", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(item.hasKey("ArmorKey")) {
													if(i.getType().toString().contains("HELMET")) {
														int itemLevel = item.getInteger("Level");
														int maxxp = item.getInteger("MAXXP");
														int total = item.getInteger("TotalXP");	
														String name = item.getString("ItemName");
														String rarity = item.getString("Rarity");
														String enchantmentString = item.getString("EnchantmentString");
														double baseDamage = item.getDouble("Base Armor Defense");
														double baseSpeed = item.getDouble("Base Armor Toughness");
														double incDamage = item.getDouble("Inc Armor Defense");
														double incSpeed = item.getDouble("Inc Armor Toughness");
														for(int x = itemLevel + 1; x <= level; x++) {
															itemLevel = x;
															total = total + maxxp;
															maxxp = maxxp / 100 * 135;
														}
														double value1 = baseDamage + incDamage * (itemLevel - 1);
										            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
														//Config Data
														//Weapon Data
										            	if(xp >= maxxp) {
										            		xp = maxxp - 1;
										            	}
										            	item.setInteger("Level", itemLevel);
										            	item.setInteger("XP", xp);
										            	item.setInteger("MAXXP", maxxp);
										            	item.setInteger("TotalXP", total);
										            	item.setDouble("Armor Defense", value1);
										            	item.setDouble("Armor Toughness", value2);
										            	i = item.getItem();
														ItemMeta meta = i.getItemMeta();
														meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
														ArrayList<String> newLore = new ArrayList<String>();
														newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
														double roundOff1 = (double) Math.round(value1 * 100) / 100;
														double roundOff2 = (double) Math.round(value2 * 100) / 100;
														newLore.add(new CCT().colorize("&7-----------------------"));
														newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
														newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel < 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
															newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
														}
														else if(itemLevel == 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
														}
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel >= 6) {
															int loreRequired = itemLevel - 4;
															int levelRequired = loreRequired * 5;
															newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
														}
														newLore.add(new CCT().colorize("&7Rarity: " + rarity));
														meta.setLore(newLore);
														i.setItemMeta(meta);
													}
													if(i.getType().toString().contains("CHESTPLATE")) {
														int itemLevel = item.getInteger("Level");
														int maxxp = item.getInteger("MAXXP");
														int total = item.getInteger("TotalXP");	
														String name = item.getString("ItemName");
														String rarity = item.getString("Rarity");
														String enchantmentString = item.getString("EnchantmentString");
														double baseDamage = item.getDouble("Base Armor Defense");
														double baseSpeed = item.getDouble("Base Armor Toughness");
														double incDamage = item.getDouble("Inc Armor Defense");
														double incSpeed = item.getDouble("Inc Armor Toughness");
														for(int x = itemLevel + 1; x <= level; x++) {
															itemLevel = x;
															total = total + maxxp;
															maxxp = maxxp / 100 * 135;
														}
														double value1 = baseDamage + incDamage * (itemLevel - 1);
										            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
														//Config Data
														//Weapon Data
										            	if(xp >= maxxp) {
										            		xp = maxxp - 1;
										            	}
										            	item.setInteger("Level", itemLevel);
										            	item.setInteger("XP", xp);
										            	item.setInteger("MAXXP", maxxp);
										            	item.setInteger("TotalXP", total);
										            	item.setDouble("Armor Defense", value1);
										            	item.setDouble("Armor Toughness", value2);
										            	i = item.getItem();
														ItemMeta meta = i.getItemMeta();
														meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
														ArrayList<String> newLore = new ArrayList<String>();
														newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
														double roundOff1 = (double) Math.round(value1 * 100) / 100;
														double roundOff2 = (double) Math.round(value2 * 100) / 100;
														newLore.add(new CCT().colorize("&7-----------------------"));
														newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
														newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel < 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
															newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
														}
														else if(itemLevel == 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
														}
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel >= 6) {
															int loreRequired = itemLevel - 4;
															int levelRequired = loreRequired * 5;
															newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
														}
														newLore.add(new CCT().colorize("&7Rarity: " + rarity));
														meta.setLore(newLore);
														i.setItemMeta(meta);
													}
													if(i.getType().toString().contains("LEGGINGS")) {
														int itemLevel = item.getInteger("Level");
														int maxxp = item.getInteger("MAXXP");
														int total = item.getInteger("TotalXP");	
														String name = item.getString("ItemName");
														String rarity = item.getString("Rarity");
														String enchantmentString = item.getString("EnchantmentString");
														double baseDamage = item.getDouble("Base Armor Defense");
														double baseSpeed = item.getDouble("Base Armor Toughness");
														double incDamage = item.getDouble("Inc Armor Defense");
														double incSpeed = item.getDouble("Inc Armor Toughness");
														for(int x = itemLevel + 1; x <= level; x++) {
															itemLevel = x;
															total = total + maxxp;
															maxxp = maxxp / 100 * 135;
														}
														double value1 = baseDamage + incDamage * (itemLevel - 1);
										            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
														//Config Data
														//Weapon Data
										            	if(xp >= maxxp) {
										            		xp = maxxp - 1;
										            	}
										            	item.setInteger("Level", itemLevel);
										            	item.setInteger("XP", xp);
										            	item.setInteger("MAXXP", maxxp);
										            	item.setInteger("TotalXP", total);
										            	item.setDouble("Armor Defense", value1);
										            	item.setDouble("Armor Toughness", value2);
										            	i = item.getItem();
														ItemMeta meta = i.getItemMeta();
														meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
														ArrayList<String> newLore = new ArrayList<String>();
														newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
														double roundOff1 = (double) Math.round(value1 * 100) / 100;
														double roundOff2 = (double) Math.round(value2 * 100) / 100;
														newLore.add(new CCT().colorize("&7-----------------------"));
														newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
														newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel < 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
															newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
														}
														else if(itemLevel == 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
														}
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel >= 6) {
															int loreRequired = itemLevel - 4;
															int levelRequired = loreRequired * 5;
															newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
														}
														newLore.add(new CCT().colorize("&7Rarity: " + rarity));
														meta.setLore(newLore);
														i.setItemMeta(meta);
													}
													if(i.getType().toString().contains("BOOTS")) {
														int itemLevel = item.getInteger("Level");
														int maxxp = item.getInteger("MAXXP");
														int total = item.getInteger("TotalXP");	
														String name = item.getString("ItemName");
														String rarity = item.getString("Rarity");
														String enchantmentString = item.getString("EnchantmentString");
														double baseDamage = item.getDouble("Base Armor Defense");
														double baseSpeed = item.getDouble("Base Armor Toughness");
														double incDamage = item.getDouble("Inc Armor Defense");
														double incSpeed = item.getDouble("Inc Armor Toughness");
														for(int x = itemLevel + 1; x <= level; x++) {
															itemLevel = x;
															total = total + maxxp;
															maxxp = maxxp / 100 * 135;
														}
														double value1 = baseDamage + incDamage * (itemLevel - 1);
										            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
														//Config Data
														//Weapon Data
										            	if(xp >= maxxp) {
										            		xp = maxxp - 1;
										            	}
										            	item.setInteger("Level", itemLevel);
										            	item.setInteger("XP", xp);
										            	item.setInteger("MAXXP", maxxp);
										            	item.setInteger("TotalXP", total);
										            	item.setDouble("Armor Defense", value1);
										            	item.setDouble("Armor Toughness", value2);
										            	i = item.getItem();
														ItemMeta meta = i.getItemMeta();
														meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
														ArrayList<String> newLore = new ArrayList<String>();
														newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
														double roundOff1 = (double) Math.round(value1 * 100) / 100;
														double roundOff2 = (double) Math.round(value2 * 100) / 100;
														newLore.add(new CCT().colorize("&7-----------------------"));
														newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
														newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel < 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
															newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
														}
														else if(itemLevel == 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
														}
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel >= 6) {
															int loreRequired = itemLevel - 4;
															int levelRequired = loreRequired * 5;
															newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
														}
														newLore.add(new CCT().colorize("&7Rarity: " + rarity));
														meta.setLore(newLore);
														i.setItemMeta(meta);
													}
												}
												else if(item.hasKey("ShieldKey")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Toughness");
													double baseSpeed = item.getDouble("Base Cooldown");
													double incDamage = item.getDouble("Inc Armor Toughness");
													double incSpeed = item.getDouble("Inc Cooldown");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	if(xp >= maxxp) {
									            		xp = maxxp - 1;
									            	}
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", xp);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Toughness", value1);
									            	item.setDouble("Cooldown", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												player.getInventory().addItem(i);
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis input as xp is not valid! The xp needs to be a number!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level must be in between 1 and 15!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis input as level is not valid! The level needs to be a number!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cWrong ussage: /customitem (Item Name) (Level)"));
							}
						}
					}
				}
			}
		}
		return true;
	}
	private String getNewXPBar(double xp, double maxxp) {
		double barprogress = (double) xp / (double) maxxp * 100.0;
		String loreString = "&7[&a";
		boolean canStop = true;
		for(double x = 0.00; x <= 100.00; x+=2.00) {
			if(barprogress >= x) {
				loreString = loreString + ":";
			}
			else if(canStop) {
				loreString = loreString + "&7:";
				canStop = false;
			}
			else {
				loreString = loreString + ":";
			}
			if(x == 100) {
				loreString = loreString + "&7] &a" + String.format("%.2f", barprogress) + "%";
			}
		}
		return new CCT().colorize(loreString);
	}
}
