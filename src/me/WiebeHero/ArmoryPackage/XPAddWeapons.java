package me.WiebeHero.ArmoryPackage;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.Novis.NovisEnchantmentGetting;

public class XPAddWeapons implements Listener{
	public NovisEnchantmentGetting enchant = new NovisEnchantmentGetting();
	public ArrayList<String> nameList = new ArrayList<String>();
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		ItemStack c = event.getCursor();
		if(event.getClickedInventory() != null) {
			if(event.getClickedInventory().getType() == InventoryType.PLAYER && player.getGameMode().equals(GameMode.SURVIVAL) && event.getSlot() != 36 && event.getSlot() != 37 && event.getSlot() != 38 && event.getSlot() != 39) {
				if(i != null && c != null) {
					NBTItem item = new NBTItem(i);
					NBTItem cursor = new NBTItem(c);
					if(item.hasKey("Upgradeable") && cursor.hasKey("Upgradeable")) {
						boolean check = false;
						if(item.hasKey("WeaponKey") && cursor.hasKey("WeaponKey")){
							if(item.getString("WeaponKey").equals(cursor.getString("WeaponKey"))) {
								check = true;
							}
						}
						if(item.hasKey("ShieldKey") && cursor.hasKey("ShieldKey")){
							if(item.getString("ShieldKey").equals(cursor.getString("ShieldKey"))) {
								check = true;
							}
						}
						if(item.hasKey("BowKey") && cursor.hasKey("BowKey")){
							if(item.getString("BowKey").equals(cursor.getString("BowKey"))) {
								check = true;
							}
						}
						if(item.hasKey("ArmorKey") && cursor.hasKey("ArmorKey")){
							if(item.getString("ArmorKey").equals(cursor.getString("ArmorKey"))) {
								check = true;
							}
						}
						if(check) {
							int levelItem = item.getInteger("Level");
							int xpItem = item.getInteger("XP");
							int xpCursor = cursor.getInteger("XP") + 500;
							int maxxpItem = item.getInteger("MAXXP");
							int totalXpItem = item.getInteger("TotalXP");
							int totalXpCursor = cursor.getInteger("TotalXP");
							double value1 = 0.0;
	    	            	double value2 = 0.0;
	    	            	double value1Inc = 0.0;
	    	            	double value2Inc = 0.0;
							if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
								value1 = item.getDouble("Base Attack Damage");
								value2 = item.getDouble("Base Attack Speed");
								value1Inc = item.getDouble("Inc Attack Damage");
								value2Inc = item.getDouble("Inc Attack Speed");
							}
							else if(item.hasKey("ShieldKey")) {
								value1 = item.getDouble("Base Armor Toughness");
								value2 = item.getDouble("Base Cooldown");
								value1Inc = item.getDouble("Inc Armor Toughness");
								value2Inc = item.getDouble("Inc Cooldown");
							}
							else if(item.hasKey("ArmorKey")) {
								value1 = item.getDouble("Base Armor Defense");
								value2 = item.getDouble("Base Armor Toughness");
								value1Inc = item.getDouble("Inc Armor Defense");
								value2Inc = item.getDouble("Inc Armor Toughness");
							}
	    	            	String name = item.getString("ItemName");
	    	            	String enchantmentsString = item.getString("EnchantmentString");
	    	            	String rarity = item.getString("Rarity");
	    	            	int total = xpItem + totalXpCursor + xpCursor;
	    	            	if(total >= maxxpItem) {
	    	            		int addedOn = 0;
	    	            		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2.00, (float)1.00);
		    	            	for(int x = levelItem + 1; x <= 15; x++) {
		    	            		if(total >= maxxpItem) {
		    	            			total = total - maxxpItem;
		    	            			levelItem = x;
		    	            			addedOn = addedOn + maxxpItem;
		    	            			maxxpItem = maxxpItem / 100 * 120;
		    	            		}
		    	            	}
		    	            	item.setInteger("TotalXP", totalXpItem + addedOn);
	    	            	} 
	    	            	value1 = value1 + value1Inc * (levelItem - 1);
	    	            	value2 = value2 + value2Inc * (levelItem - 1);
	    	            	item.setInteger("Level", levelItem);
	    	            	if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
		    	            	item.setDouble("Attack Damage", value1);
		    	            	item.setDouble("Attack Speed", value2);
							}
							else if(item.hasKey("ShieldKey")) {
								item.setDouble("Armor Toughness", value1);
								value2 = value2 - value2Inc * (levelItem - 1);
		    	            	item.setDouble("Cooldown", value2);
							}
							else if(item.hasKey("ArmorKey")) {
								item.setDouble("Armor Defense", value1);
		    	            	item.setDouble("Armor Toughness", value2);
							}
	    	            	item.setInteger("XP", total);
	    	            	item.setInteger("MAXXP", maxxpItem);
							i = item.getItem();
							player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, (float)2.00, (float)1.00);
		    				//Config Data
		    				//Weapon Data
		    				ItemMeta meta = i.getItemMeta();
		    				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + levelItem + "&a]"));
		    				ArrayList<String> newLore = new ArrayList<String>();
		    				enchant.setEnchantments(levelItem, enchantmentsString, newLore);
		    				newLore.add(new CCT().colorize("&7-----------------------"));
	    					double roundOff1 = (double) Math.round((value1) * 100) / 100;
		    				double roundOff2 = (double) Math.round((value2) * 100) / 100;
		    				if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
		    					newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
			    				newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
							}
							else if(item.hasKey("ShieldKey")) {
								newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
			    				newLore.add(new CCT().colorize("&7Cooldown: &6" + roundOff2));
							}
							else if(item.hasKey("ArmorKey")) {
			    				newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
								newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
							}
		    				newLore.add(new CCT().colorize("&7-----------------------"));
		    				if(levelItem < 15) {
		    					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + total + " &6/ &b&l" + maxxpItem + "&a]"));
		    					double barprogress = (double) total / (double) maxxpItem * 100.0;
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
		    					newLore.add(new CCT().colorize(loreString));
		    				}
		    				else if(levelItem == 15) {
		    					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
		    				}
		    				newLore.add(new CCT().colorize("&7-----------------------"));
		    				if(levelItem >= 6) {
		    					int loreRequired = levelItem - 4;
		    					int levelRequired = loreRequired * 5;
		    					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
		    				}
		    				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
		    				meta.setLore(newLore);
		    				i.setItemMeta(meta);
							event.setCancelled(true);
							event.setCurrentItem(i);
							event.getCursor().setAmount(event.getCursor().getAmount() - 1);
							player.updateInventory();
						}			
					}
				}
			}
		}
	}
}