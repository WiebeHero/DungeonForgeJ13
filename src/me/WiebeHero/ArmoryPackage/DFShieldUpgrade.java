package me.WiebeHero.ArmoryPackage;

import java.util.ArrayList;
import java.util.Random;

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

public class DFShieldUpgrade implements Listener{
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
			ItemStack i = damager.getInventory().getItemInOffHand();
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Upgradeable")) {
					if(item.hasKey("ShieldKey")) {
						int xp = item.getInteger("XP");
						int maxxp = item.getInteger("MAXXP");
			    		int totalxpearned = 0;
			    		if(!(victim instanceof Player)) {
							Entity ent = NBTInjector.patchEntity(victim);
							NBTCompound comp = NBTInjector.getNbtData(ent);
							if(comp.hasKey("SpawnerUUID")) {
								int tier = comp.getInteger("Tier");
								int levelMob = comp.getInteger("Level");
								//-----------------------------------------------------------------------------------------------------------------------------------------
								//XP Adding
								//-----------------------------------------------------------------------------------------------------------------------------------------
								int i1 = 0;
								if(tier == 0) {
									i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
								}
								if(tier == 1) {
									i1 = new Random().nextInt(50) + 50 + 4 * levelMob;
								}
								else if(tier == 2) {
									i1 = new Random().nextInt(70) + 70 + 5 * levelMob;
								}
								else if(tier == 3) {
									i1 = new Random().nextInt(90) + 90 + 6 * levelMob;
								}
								else if(tier == 4) {
									i1 = new Random().nextInt(110) + 110 + 7 * levelMob;
								}
								else if(tier == 5) {
									i1 = new Random().nextInt(130) + 130 + 8 * levelMob;
								}
								else {
									i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
								}
								totalxpearned = i1 + xp;
							}
						}
						else {
							DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
							int level = dfPlayer.getLevel();
							int i1 = 0;
							i1 = new Random().nextInt(7 * level) + 4 * level;
							totalxpearned = i1 + xp;
						}
						if(totalxpearned > 0){
			    			if(totalxpearned >= maxxp) {
								int itemLevel = item.getInteger("Level");
			    				if(itemLevel != 15) {
			    					int total = item.getInteger("TotalXP");	
									String name = item.getString("ItemName");
									String rarity = item.getString("Rarity");
									String enchantmentString = item.getString("EnchantmentString");
									double baseDamage = item.getDouble("Base Armor Toughness");
									double baseSpeed = item.getDouble("Base Cooldown");
									double incDamage = item.getDouble("Inc Armor Toughness");
									double incSpeed = item.getDouble("Inc Cooldown");
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
			    	            	double value2 = baseSpeed - incSpeed * (itemLevel - 1);
				    				//Config Data
				    				//Weapon Data
			    	            	item.setInteger("Level", itemLevel);
			    	            	item.setInteger("XP", totalxpearned);
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
				    				damager.getInventory().setItemInOffHand(i);
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
					    		damager.getInventory().setItemInOffHand(i);
				    		}
				    	}
					}
			    }
			}
		}
	}
}

