package me.WiebeHero.CustomEnchantmentsB;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DisarmorB implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@SuppressWarnings("deprecation")
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					if(!event.isCancelled()) {
						Player victim = (Player) event.getEntity();
						float i = ThreadLocalRandom.current().nextFloat() * 100;
						Player damager = (Player) arrow.getShooter();
						String name = arrow.getCustomName();
						double force = 0.00;
						Matcher matcher2 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(name));
						while(matcher2.find()) {
						    force = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
						}
						if(force == 1.00) {
							if(damager.getInventory().getItemInMainHand() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
									if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Disarmor"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Disarmor")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 3 + 0.5 * level) {
												animation(victim, damager);
												ItemStack helm = victim.getInventory().getHelmet();
												ItemStack chest = victim.getInventory().getChestplate();
												ItemStack legs = victim.getInventory().getLeggings();
												ItemStack boots = victim.getInventory().getBoots();
												int rand = new Random().nextInt (3) + 1;
												if(rand == 1) {
													if(helm != null) {
														victim.getInventory().addItem(helm);
														victim.getInventory().setHelmet(null);
													}
												}
												else if(rand == 2) {
													if(chest != null) {
														victim.getInventory().addItem(chest);
														victim.getInventory().setHelmet(null);
													}
												}
												else if(rand == 3) {
													if(legs != null) {
														victim.getInventory().addItem(legs);
														victim.getInventory().setHelmet(null);
													}
												}
												else if(rand == 4) {
													if(boots != null) {
														victim.getInventory().addItem(boots);
														victim.getInventory().setHelmet(null);
													}
												}
											}
										}
									}
								}
							}
							if(damager.getInventory().getItemInOffHand() != null) {
								if(damager.getInventory().getItemInOffHand().getItemMeta() != null) {
									if(damager.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : damager.getInventory().getItemInOffHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Disarmor"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Disarmor")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 3 + 0.5 * level) {
												animation(victim, damager);
												ItemStack helm = victim.getInventory().getHelmet();
												ItemStack chest = victim.getInventory().getChestplate();
												ItemStack legs = victim.getInventory().getLeggings();
												ItemStack boots = victim.getInventory().getBoots();
												int rand = new Random().nextInt (3) + 1;
												if(rand == 1) {
													if(helm != null) {
														victim.getInventory().addItem(helm);
														victim.getInventory().setHelmet(null);
													}
												}
												else if(rand == 2) {
													if(chest != null) {
														victim.getInventory().addItem(chest);
														victim.getInventory().setHelmet(null);
													}
												}
												else if(rand == 3) {
													if(legs != null) {
														victim.getInventory().addItem(legs);
														victim.getInventory().setHelmet(null);
													}
												}
												else if(rand == 4) {
													if(boots != null) {
														victim.getInventory().addItem(boots);
														victim.getInventory().setHelmet(null);
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
	public void animation(LivingEntity victim, Player damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.3D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.ITEM_CRACK, locCF, 50, 0.2, 0.2, 0.2, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 0.9);
		}
	}
}
