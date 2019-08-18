package me.WiebeHero.XpTrader;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import net.md_5.bungee.api.ChatColor;

public class XPAddWeapons implements Listener{
	public NovisEnchantmentGetting enchant = new NovisEnchantmentGetting();
	public ArrayList<String> nameList = new ArrayList<String>();
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		ItemStack cursor = event.getCursor();
		if(event.getClickedInventory() != null) {
			if(event.getClickedInventory().getType() == InventoryType.PLAYER && player.getGameMode().equals(GameMode.SURVIVAL) && event.getSlot() != 36 && event.getSlot() != 37 && event.getSlot() != 38 && event.getSlot() != 39) {
				if(item != null && cursor != null) {
					if(item.hasItemMeta() && cursor.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName() && cursor.getItemMeta().hasDisplayName()) {
							if(item.getItemMeta().hasLore() && cursor.getItemMeta().hasLore()) {
								if(item.getItemMeta().getLore().toString().contains("Upgrade Progress:") && cursor.getItemMeta().getLore().toString().contains("Upgrade Progress:")) {
									String split1[] = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split("\\[");
									String split2[] = ChatColor.stripColor(cursor.getItemMeta().getDisplayName()).split("\\[");
									if(ChatColor.stripColor(split1[0]).equals(ChatColor.stripColor(split2[0]))) {
										String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
										String cursorName = ChatColor.stripColor(cursor.getItemMeta().getDisplayName());
										FileConfiguration config = CustomEnchantments.getInstance().getConfig();
										String xpProgressItem = "";
										String xpProgressCursor = "";
										for(String s : item.getItemMeta().getLore()) {
											if(s.contains(ChatColor.stripColor("Upgrade Progress:"))) {
												xpProgressItem = ChatColor.stripColor(s);
											}
										}
										for(String s : cursor.getItemMeta().getLore()) {
											if(s.contains(ChatColor.stripColor("Upgrade Progress:"))) {
												xpProgressCursor = ChatColor.stripColor(s);
											}
										}
										String lore = item.getItemMeta().getLore().toString();
										Set<String> configList = null;
										String type = "";
										if(lore.contains(ChatColor.stripColor("Attack Damage:")) && lore.contains(ChatColor.stripColor("Attack Speed:")) && !lore.contains(ChatColor.stripColor("Attack Range:"))){
											configList = config.getConfigurationSection("Items.Weapons").getKeys(false);
											type = "Weapons";
										}
										else if(lore.contains(ChatColor.stripColor("Attack Damage:")) && !lore.contains(ChatColor.stripColor("Attack Speed:")) && !lore.contains(ChatColor.stripColor("Attack Range:"))){
											configList = config.getConfigurationSection("Items.Bows").getKeys(false);
											type = "Bows";
										}
										else if(!lore.contains(ChatColor.stripColor("Armor Defense:")) && lore.contains(ChatColor.stripColor("Armor Toughness:"))){
											configList = config.getConfigurationSection("Items.Shields").getKeys(false);
											type = "Shields";
										}
										else if(lore.contains(ChatColor.stripColor("Armor Defense:")) && lore.contains(ChatColor.stripColor("Armor Toughness:"))){
											configList = config.getConfigurationSection("Items.Armor").getKeys(false);
											type = "Armor";
										}
										String confirm = "";
										for(String s : configList) {
											if(itemName.contains(s)) {
												confirm = s;
											}
										}
										String itemSplitLevel[] = itemName.split("Lv |\\]");
										String cursorSplitLevel[] = cursorName.split("Lv |\\]");
										int levelItem = Integer.parseInt(itemSplitLevel[1]);
										int levelCursor = Integer.parseInt(cursorSplitLevel[1]);
										int xpItem = 0;
										int xpCursor = 0;
										Matcher matcher = Pattern.compile("(\\d+) / (\\d+)").matcher(ChatColor.stripColor(xpProgressItem));
										while(matcher.find()) {
										    xpItem = Integer.parseInt(matcher.group(1));
										}
										Matcher matcher1 = Pattern.compile("(\\d+) / (\\d+)").matcher(ChatColor.stripColor(xpProgressCursor));
										while(matcher1.find()) {
										    xpCursor = Integer.parseInt(matcher1.group(1));
										}
										int totalXP = 0;
										int level = 0;
										double damageWeapon = 0.00;
				    	            	double speedWeapon = 0.00;
				    	            	double wandRange = 0.00;
				    	            	double armorDefense = 0.00;
				    	            	double armorToughness = 0.00;
				    	            	double cooldown = 0.00;
				    	            	double tempAD = 0.00;
				    	            	double tempAS = 0.00;
				    	            	double tempCD = 0.00;
				    	            	double tempWR = 0.00;
				    	            	double tempDF = 0.00;
				    	            	double tempT = 0.00;
				    	            	NBTItem tempItem = null;
										if(levelCursor == 1 && xpCursor < 500) {
											totalXP = xpItem + 500;
											level = levelItem;
											tempItem = new NBTItem(item);
										}
										else if(levelItem == 1 && xpItem < 500) {
											totalXP = xpCursor + 500;
											level = levelCursor;
											tempItem = new NBTItem(item);
										}
										else if(levelCursor < levelItem) {
											totalXP = xpItem + (3000 * (levelCursor - 1) + xpCursor);
											level = levelItem;
											tempItem = new NBTItem(item);
										}
										else if(levelCursor > levelItem) {
											totalXP = (3000 * (levelItem - 1) + xpItem) + xpCursor;
											level = levelCursor;
											tempItem = new NBTItem(cursor);
										}
										else {
											totalXP = xpItem + (3000 * (levelCursor - 1) + xpCursor);
											level = levelItem;
											tempItem = new NBTItem(item);
										}
										int xpNeeded = 0;
										for(int i = level; i < 15; i++) {
											xpNeeded = config.getInt("XPValue." + i);
											if(totalXP >= xpNeeded) {
												totalXP = totalXP - xpNeeded;
												totalXP = Math.abs(totalXP);
												level = i + 1;
							    	    		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 1.0);
							    	    		if(type.equals("Weapons")) {
						    	            		damageWeapon = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncDamage") + damageWeapon;
						    	            		speedWeapon = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncSpeed") + speedWeapon;
						    	            	}
						    	            	if(type.equals("Bows")) {
						    	            		damageWeapon = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncDamage") + damageWeapon;
						    	            		speedWeapon = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncSpeed") + speedWeapon;
						    	            	}
						    	            	if(type.equals("Armor")) {
						    	            		armorDefense = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncDefense") + armorDefense;
						    	            		armorToughness = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncToughness") + armorToughness;
						    	            	}
						    	            	if(type.equals("Shields")) {
						    	            		armorToughness = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncToughness") + armorToughness;
						    	            		cooldown = CustomEnchantments.getInstance().getConfig().getDouble("Items." + type + "." + confirm + ".IncCooldown") + armorToughness;
						    	            	}
											}
											else {
												break;
											}
										}
										if(type.equals("Weapons")) {
				    	            		tempAD = tempItem.getDouble("Attack Damage");
				    	            		tempAS = tempItem.getDouble("Attack Speed");
					    				}
					    				else if(type.equals("Armor")) {
					    					tempDF = tempItem.getDouble("Defense");
				    	            		tempT = tempItem.getDouble("Toughness");
					    				}
					    				else if(type.equals("Shields")) {
				    	            		tempAS = tempItem.getDouble("Toughness");
				    	            		tempCD = tempItem.getDouble("Cooldown");
					    				}
					    				else if(type.equals("Bows")) {
				    	            		tempAD = tempItem.getDouble("Attack Damage");
				    	            		tempAS = tempItem.getDouble("Attack Speed");
					    				}
										ItemStack item1 = item;
										player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, (float)2.00, (float)1.00);
				    	            	String enchantmentsString = CustomEnchantments.getInstance().getConfig().getString("Items." + type + "." + confirm + ".Enchantments");
				    	            	String rarity = CustomEnchantments.getInstance().getConfig().getString("Items." + type + "." + confirm + ".Rarity");
					    				//Config Data
					    				//Weapon Data
					    				ItemMeta meta = item1.getItemMeta();
					    				String translator = "";
					    				if(rarity.contains("Common")) {
					    					translator = "&7";
					    				}
					    				else if(rarity.contains("Rare")) {
					    					translator = "&a";
					    				}
					    				else if(rarity.contains("Epic")) {
					    					translator = "&b";
					    				}
					    				else if(rarity.contains("Legendary")) {
					    					translator = "&c";
					    				}
					    				else if(rarity.contains("Mythic")) {
					    					translator = "&5";
					    				}
					    				else if(rarity.contains("Heroic")) {
					    					translator = "&e";
					    				}
					    				meta.setDisplayName(new ColorCodeTranslator().colorize(translator + confirm + " &a[&6Lv " + level + "&a]"));
					    				ArrayList<String> newLore = new ArrayList<String>();
					    				enchant.setEnchantments(level, enchantmentsString, rarity, newLore);
					    				newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
					    				if(type.equals("Weapons")) {
					    					double roundOff1 = (double) Math.round((tempAD + damageWeapon) * 100) / 100;
						    				double roundOff2 = (double) Math.round((tempAS + speedWeapon) * 100) / 100;
						    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + roundOff1));
						    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + roundOff2));
					    				}
					    				if(type.equals("Armor")) {
					    					double roundOff1 = (double) Math.round((tempDF + armorDefense) * 100) / 100;
						    				double roundOff2 = (double) Math.round((tempT + armorToughness) * 100) / 100;
					    					newLore.add(new ColorCodeTranslator().colorize("&7Armor Defense: &6" + roundOff1));
						    				newLore.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &6" + roundOff2));
					    				}
					    				else if(type.equals("Wands")) {
					    					double roundOff1 = (double) Math.round((tempAD + damageWeapon) * 100) / 100;
						    				double roundOff2 = (double) Math.round((tempAS + speedWeapon) * 100) / 100;
						    				double roundOff3 = (double) Math.round((tempWR + wandRange) * 100) / 100;
					    					newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + roundOff1));
						    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + roundOff2));
						    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Range: &6" + roundOff3));
					    				}
					    				else if(type.equals("Shields")) {
						    				double roundOff1 = (double) Math.round((tempT + armorToughness) * 100) / 100;
						    				double roundOff2 = (double) Math.round((tempCD - cooldown) * 100) / 100;
					    					newLore.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &6" + roundOff1));
					    					newLore.add(new ColorCodeTranslator().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
					    				}
					    				else if(type.equals("Bows")) {
					    					double roundOff1 = (double) Math.round((tempAD + damageWeapon) * 100) / 100;
						    				double roundOff2 = (double) Math.round((tempAS + speedWeapon) * 100) / 100;
					    					newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + roundOff1));
					    					newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + roundOff2));
					    				}
					    				newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
					    				if(level < 15) {
					    					int xp = CustomEnchantments.getInstance().getConfig().getInt("XPValue." + level);
					    					newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l" + totalXP + " &6/ &b&l" + xp + "&a]"));
					    				}
					    				else if(level == 15) {
					    					newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
					    				}
						    			double barprogress = (double) totalXP / (double) xpNeeded * 100.0;
						    			if(barprogress >= 0 && barprogress <= 2) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:&7:::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 2 && barprogress <= 4) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::&7::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 4 && barprogress <= 6) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::&7:::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 6 && barprogress <= 8) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::&7::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 8 && barprogress <= 10) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::&7:::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 10 && barprogress <= 12) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::&7::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 12 && barprogress <= 14) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::&7:::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 14 && barprogress <= 16) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::&7::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 16 && barprogress <= 18) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::&7:::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 18 && barprogress <= 20) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::&7::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 20 && barprogress <= 22) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::&7:::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 22 && barprogress <= 24) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::&7::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 24 && barprogress <= 26) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::&7:::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 26 && barprogress <= 28) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::&7::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 28 && barprogress <= 30) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::&7:::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 30 && barprogress <= 32) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::&7::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 32 && barprogress <= 34) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::&7:::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 34 && barprogress <= 36) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::&7::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 36 && barprogress <= 38) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::&7:::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 38 && barprogress <= 40) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::&7::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 40 && barprogress <= 42) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::&7:::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 42 && barprogress <= 44) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::&7::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 44 && barprogress <= 46) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::&7:::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 46 && barprogress <= 48) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::&7::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 48 && barprogress <= 50) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::&7:::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 50 && barprogress <= 52) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::&7::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 52 && barprogress <= 54) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::&7:::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 54 && barprogress <= 56) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::&7::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 56 && barprogress <= 58) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::&7:::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 58 && barprogress <= 60) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::&7::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 60 && barprogress <= 62) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::&7:::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 62 && barprogress <= 64) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::&7::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 64 && barprogress <= 66) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::&7:::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 66 && barprogress <= 68) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::&7::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 68 && barprogress <= 70) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::&7:::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 70 && barprogress <= 72) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::&7::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 72 && barprogress <= 74) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::&7:::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 74 && barprogress <= 76) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::&7::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 76 && barprogress <= 78) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::&7:::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 78 && barprogress <= 80) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::&7::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 80 && barprogress <= 82) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::&7:::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 82 && barprogress <= 84) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::&7::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 84 && barprogress <= 86) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::&7:::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 86 && barprogress <= 88) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::&7::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 88 && barprogress <= 90) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::&7:::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 90 && barprogress <= 92) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::&7::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 92 && barprogress <= 94) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::::&7:::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 94 && barprogress <= 96) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::::&7::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 96 && barprogress <= 98) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::::::&7:&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
						    			else if(barprogress >= 98 && barprogress <= 100) {
						    				newLore.add(new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
						    			}
					    				newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
					    				if(level >= 6) {
					    					int loreRequired = level - 4;
					    					int levelRequired = loreRequired * 5;
					    					newLore.add(new ColorCodeTranslator().colorize("&7Level Required: &6" + levelRequired));
					    				}
					    				newLore.add(new ColorCodeTranslator().colorize("&7Rarity: " + rarity));
					    				meta.setLore(newLore);
					    				item1.setItemMeta(meta);
					    				NBTItem newItem = new NBTItem(item1);
					    				if(type.equals("Weapons")) {
					    					newItem.setDouble("Attack Damage", tempAD + damageWeapon);
						    				newItem.setDouble("Attack Speed", tempAS + speedWeapon);
					    				}
					    				else if(type.equals("Armor")) {
					    					newItem.setDouble("Defense", tempDF + armorDefense);
						    				newItem.setDouble("Toughness", tempT + armorToughness);
						    				tempDF = newItem.getDouble("Defense");
				    	            		tempT = newItem.getDouble("Toughness");
					    				}
					    				else if(type.equals("Wands")) {
					    					newItem.setDouble("Attack Damage", tempAD + damageWeapon);
						    				newItem.setDouble("Attack Speed", tempAS + speedWeapon);
						    				newItem.setDouble("Attack Range", tempWR + wandRange);
					    				}
					    				else if(type.equals("Shields")) {
					    					newItem.setDouble("Toughness", tempT + armorToughness);
					    					newItem.setDouble("Cooldown", tempCD - cooldown);
					    				}
					    				else if(type.equals("Bows")) {
					    					newItem.setDouble("Attack Damage", tempAD + armorToughness);
					    					newItem.setDouble("Attack Speed", tempAS + speedWeapon);
					    				}
					    				item1 = newItem.getItem();
										event.setCancelled(true);
										event.setCurrentItem(item1);
										event.getCursor().setAmount(event.getCursor().getAmount() - 1);
										player.updateInventory();
									}
								}
							}
						}
					}
				}
			}
		}
	}
}