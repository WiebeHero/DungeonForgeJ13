package me.WiebeHero.ArmoryPackage;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.EffectSkills;

public class DFArmorUpgrade implements Listener{
	public NovisEnchantmentGetting enchant = new NovisEnchantmentGetting();
	public String colorize(String msg)
    {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&')
                coloredMsg += '§';
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }
	EffectSkills sk = new EffectSkills();
	@EventHandler
	public void weapons(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		EntityDamageEvent ede = victim.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if(event.getEntity().getKiller() instanceof Player && (dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE)){
			Player damager = (Player) event.getEntity().getKiller();
			ItemStack items[] = damager.getInventory().getArmorContents();
			for(ItemStack i : items) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Upgradeable")) {
						if(item.hasKey("ArmorKey")) {
							int xp = item.getInteger("XP");
							int maxxp = item.getInteger("MAXXP");
				    		int totalxpearned = 0;
				    		Entity e = event.getEntity();
				    		e = NBTInjector.patchEntity(e);
				    		NBTCompound comp = NBTInjector.getNbtData(e);
				    		if(comp.hasKey("SpawnerUUID")) {
								int tier = comp.getInteger("Tier");
								int level = comp.getInteger("Level");
								int i1 = 0;
								if(tier == 0) {
									i1 = new Random().nextInt(5) + (level * 3);
								}
								else {
									i1 = new Random().nextInt(50 + 50 * tier) + (level * 3);
								}
								totalxpearned = i1 + xp;
							}
							else {
								if(victim.getType().equals(EntityType.ZOMBIE)) {
									totalxpearned = 2 + xp;
								}
								else if(victim.getType().equals(EntityType.SKELETON)) {
									totalxpearned = 3 + xp;
								}
								else if(victim.getType().equals(EntityType.SPIDER)) {
									totalxpearned = 2 + xp;
								}
								else if(victim.getType().equals(EntityType.CREEPER)) {
									totalxpearned = 3 + xp;
								}
								else if(victim.getType().equals(EntityType.ENDERMAN)) {
									totalxpearned = 6 + xp;
								}
								else if(victim.getType().equals(EntityType.BLAZE)) {
									totalxpearned = 4 + xp;
								}
								else if(victim.getType().equals(EntityType.WITCH)) {
									totalxpearned = 4 + xp;
								}
								else if(victim.getType().equals(EntityType.VILLAGER)) {
									totalxpearned = 2 + xp;
								}
								else if(victim.getType().equals(EntityType.IRON_GOLEM)) {
									totalxpearned = 8 + xp;
								}
								else if(victim.getType().equals(EntityType.CHICKEN)) {
									totalxpearned = 1 + xp;
								}
								else if(victim.getType().equals(EntityType.SHEEP)) {
									totalxpearned = 4 + xp;
								}
								else if(victim.getType().equals(EntityType.COW)) {
									totalxpearned = 4 + xp;
								}
								else if(victim.getType().equals(EntityType.PIG_ZOMBIE)) {
									totalxpearned = 3 + xp;
								}
								else if(victim.getType().equals(EntityType.HORSE)) {
									totalxpearned = 3 + xp;
								}
								else if(victim.getType() == EntityType.PLAYER) {
									Player player = (Player) victim;
									DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
									int levelVictim = dfPlayer.getLevel();
									if(levelVictim >= 0) {
										int i6 = new Random().nextInt(50) + 50;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 10) {
										int i6 = new Random().nextInt(100) + 60;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 20) {
										int i6 = new Random().nextInt(150) + 70;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 30) {
										int i6 = new Random().nextInt(200) + 80;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 40) {
										int i6 = new Random().nextInt(250) + 90;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 50) {
										int i6 = new Random().nextInt(300) + 100;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 60) {
										int i6 = new Random().nextInt(350) + 110;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 70) {
										int i6 = new Random().nextInt(400) + 120;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 80) {
										int i6 = new Random().nextInt(450) + 130;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 90) {
										int i6 = new Random().nextInt(500) + 140;
										totalxpearned = i6 + xp;
									}
									else if(levelVictim > 99) {
										int i6 = new Random().nextInt(550) + 150;
										totalxpearned = i6 + xp;
									}
								}
							}
							if(totalxpearned > 0){
				    			if(totalxpearned >= maxxp) {
									int itemLevel = item.getInteger("Level");
				    				if(itemLevel != 15) {
				    					int total = item.getInteger("TotalXP");	
										String name = item.getString("ItemName");
										String rarity = item.getString("Rarity");
										String enchantmentString = item.getString("EnchantmentString");
										double baseDamage = item.getDouble("Base Armor Defense");
										double baseSpeed = item.getDouble("Base Armor Toughness");
										double incDamage = item.getDouble("Inc Armor Defense");
										double incSpeed = item.getDouble("Inc Armor Toughness");
										damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, (float)2, (float)1);
										for(int x = itemLevel + 1; x <= 15; x++) {
											if(totalxpearned >= maxxp) {
												itemLevel = x;
												totalxpearned = totalxpearned - maxxp;
												total = total + maxxp;
												maxxp = maxxp / 100 * 120;
											}
											else {
												break;
											}
										}
										double value1 = baseDamage + incDamage * (itemLevel - 1);
				    	            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
					    				//Config Data
					    				//Weapon Data
				    	            	item.setInteger("Level", itemLevel);
				    	            	item.setInteger("XP", totalxpearned);
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
					    					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + totalxpearned + " &6/ &b&l" + maxxp + "&a]"));
					    				}
					    				else if(itemLevel == 15) {
					    					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
					    				}
					    				newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
					    				newLore.add(new CCT().colorize("&7-----------------------"));
					    				if(itemLevel >= 6) {
					    					int loreRequired = itemLevel - 4;
					    					int levelRequired = loreRequired * 5;
					    					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
					    				}
					    				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
					    				meta.setLore(newLore);
					    				i.setItemMeta(meta);
					    				if(i.getType().toString().contains("HELMET")) {
						    				damager.getInventory().setHelmet(i);
					    				}
					    				if(i.getType().toString().contains("CHESTPLATE")) {
					    					damager.getInventory().setChestplate(i);
					    				}
					    				if(i.getType().toString().contains("LEGGINGS")) {
					    					damager.getInventory().setLeggings(i);
					    				}
					    				if(i.getType().toString().contains("BOOTS")) {
					    					damager.getInventory().setBoots(i);
					    				}
					    				//Weapon Data
					    				sk.runDefense(damager);
				    				}
				    			}
					    		else {
					    			item.setInteger("XP", totalxpearned);
					    			i = item.getItem();
									ItemMeta im = i.getItemMeta();
						    		ArrayList<String> lore = new ArrayList<String>(im.getLore());
					    			lore.set(lore.size() - 4, new CCT().colorize("&7Upgrade Progress: " + "&a[&b&l" + (totalxpearned) + " &6/ " + "&b&l" + maxxp + "&a]"));
					    			double barprogress = (double) totalxpearned / (double) maxxp * 100.0;
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
			    					lore.set(lore.size() - 3, new CCT().colorize(loreString));
						    		im.setLore(lore);
						    		i.setItemMeta(im);
						    		if(i.getType().toString().contains("HELMET")) {
					    				damager.getInventory().setHelmet(i);
				    				}
				    				if(i.getType().toString().contains("CHESTPLATE")) {
				    					damager.getInventory().setChestplate(i);
				    				}
				    				if(i.getType().toString().contains("LEGGINGS")) {
				    					damager.getInventory().setLeggings(i);
				    				}
				    				if(i.getType().toString().contains("BOOTS")) {
				    					damager.getInventory().setBoots(i);
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

