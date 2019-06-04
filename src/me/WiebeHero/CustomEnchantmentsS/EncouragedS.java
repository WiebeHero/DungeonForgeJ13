package me.WiebeHero.CustomEnchantmentsS;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class EncouragedS implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					DamageCause damageCause = event.getCause();
					File f =  new File("plugins/CustomEnchantments/factionsConfig.yml");
					YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getInventory().getItemInOffHand() != null) {
							if(victim.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
								if(victim.getInventory().getItemInOffHand().getItemMeta() != null) {
									if(victim.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
										String checkL = "";
										for(String s1 : victim.getInventory().getItemInOffHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Encourage"))) {
												checkL = ChatColor.stripColor(s1);
											}
										}
										if(checkL.contains("Encourage")){
											if(checkL.contains("1")){
												if(i <= 7) {
													animation(victim, damager);
													for(Entity e : victim.getNearbyEntities(7.5, 7.5, 7.5)) {
														Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
										    			ArrayList<String> facCheck = new ArrayList<String>();
										    			facCheck.addAll(set);
										    			String facN = "";
										    			String get = "";
									    				String id = e.getUniqueId().toString();
									    				ArrayList<String> check1 = new ArrayList<String>();
										    			for(int i1 = 0; i1 < facCheck.size(); i1++) {
										    				get = facCheck.get(i1);
										    				if(get == null) {
										    					continue;
										    				}
										    				Set<String> check = yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false);
										    				if(check.contains(id)) {
										    					check1.addAll(yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false));
										    					facN = get;
										    				}
										    			}
										    			if(check1.contains(e.getUniqueId().toString())) {
										    				e.sendMessage(new ColorCodeTranslator().colorize("&aSomeone in your faction encouraged you to fight!"));
										    				((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
															
										    			}
										    			if(check1.contains(victim.getUniqueId().toString())) {
										    				victim.sendMessage(new ColorCodeTranslator().colorize("&aYou &a&lEncouraged &ayourself and your allies to fight!"));
										    				victim.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
										    			}
													}
												}
											}
											else if(checkL.contains("2")){
												if(i <= 9.5) {
													animation(victim, damager);
													for(Entity e : victim.getNearbyEntities(8, 8, 8)) {
														Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
										    			ArrayList<String> facCheck = new ArrayList<String>();
										    			facCheck.addAll(set);
										    			String facN = "";
										    			String get = "";
									    				String id = e.getUniqueId().toString();
									    				ArrayList<String> check1 = new ArrayList<String>();
										    			for(int i1 = 0; i1 < facCheck.size(); i1++) {
										    				get = facCheck.get(i1);
										    				if(get == null) {
										    					continue;
										    				}
										    				Set<String> check = yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false);
										    				if(check.contains(id)) {
										    					check1.addAll(yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false));
										    					facN = get;
										    				}
										    			}
										    			if(check1.contains(e.getUniqueId().toString())) {
										    				e.sendMessage(new ColorCodeTranslator().colorize("&aSomeone in your faction encouraged you to fight!"));
										    				((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 150, 0));
															
										    			}
										    			if(check1.contains(victim.getUniqueId().toString())) {
										    				victim.sendMessage(new ColorCodeTranslator().colorize("&aYou &a&lEncouraged &ayourself and your allies to fight!"));
										    				victim.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 225, 0));
										    			}
													}
												}
											}
											else if(checkL.contains("3")){
												if(i <= 12) {
													Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
													victim.getWorld().spawnParticle(Particle.DRIP_LAVA, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
													damager.sendMessage(new ColorCodeTranslator().colorize("&4Your enemy has &4&lEncouraged &4themselves and their allies!"));
													victim.sendMessage(new ColorCodeTranslator().colorize("&aYou have &a&lEncouraged &ayourself and your allies!"));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 2, (float) 0.75);
													}
													for(Entity e : victim.getNearbyEntities(8.5, 8.5, 8.5)) {
														Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
										    			ArrayList<String> facCheck = new ArrayList<String>();
										    			facCheck.addAll(set);
										    			String facN = "";
										    			String get = "";
									    				String id = e.getUniqueId().toString();
									    				ArrayList<String> check1 = new ArrayList<String>();
										    			for(int i1 = 0; i1 < facCheck.size(); i1++) {
										    				get = facCheck.get(i1);
										    				if(get == null) {
										    					continue;
										    				}
										    				Set<String> check = yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false);
										    				if(check.contains(id)) {
										    					check1.addAll(yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false));
										    					facN = get;
										    				}
										    			}
										    			if(check1.contains(e.getUniqueId().toString())) {
										    				e.sendMessage(new ColorCodeTranslator().colorize("&aSomeone in your faction encouraged you to fight!"));
										    				((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
															
										    			}
										    			if(check1.contains(victim.getUniqueId().toString())) {
										    				victim.sendMessage(new ColorCodeTranslator().colorize("&aYou &a&lEncouraged &ayourself and your allies to fight!"));
										    				victim.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 250, 0));
										    			}
													}
												}
											}
											else if(checkL.contains("4")){
												if(i <= 14.5) {
													animation(victim, damager);
													for(Entity e : victim.getNearbyEntities(9, 9, 9)) {
														Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
										    			ArrayList<String> facCheck = new ArrayList<String>();
										    			facCheck.addAll(set);
										    			String facN = "";
										    			String get = "";
									    				String id = e.getUniqueId().toString();
									    				ArrayList<String> check1 = new ArrayList<String>();
										    			for(int i1 = 0; i1 < facCheck.size(); i1++) {
										    				get = facCheck.get(i1);
										    				if(get == null) {
										    					continue;
										    				}
										    				Set<String> check = yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false);
										    				if(check.contains(id)) {
										    					check1.addAll(yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false));
										    					facN = get;
										    				}
										    			}
										    			if(check1.contains(e.getUniqueId().toString())) {
										    				e.sendMessage(new ColorCodeTranslator().colorize("&aSomeone in your faction encouraged you to fight!"));
										    				((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 250, 1));
															
										    			}
										    			if(check1.contains(victim.getUniqueId().toString())) {
										    				victim.sendMessage(new ColorCodeTranslator().colorize("&aYou &a&lEncouraged &ayourself and your allies to fight!"));
										    				victim.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
															victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 275, 0));
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
				}
			}
		}
	}
	public void animation(Player victim, LivingEntity damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.DRIP_LAVA, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 2, (float) 0.75);
		}
	}
}
