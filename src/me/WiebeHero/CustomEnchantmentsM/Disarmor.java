package me.WiebeHero.CustomEnchantmentsM;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class Disarmor extends SwordSwingProgress implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					double d = swordSwingProgress.get(damager.getName());
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK) {
						if(d == 1.00) {
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
											if(i <= 2 + level * 0.5) {
												animation(victim);
												ItemStack helm = victim.getInventory().getHelmet();
												ItemStack chest = victim.getInventory().getChestplate();
												ItemStack legs = victim.getInventory().getLeggings();
												ItemStack boots = victim.getInventory().getBoots();
												int rand = new Random().nextInt (4) + 1;
												if(rand == 1) {
													if(helm != null) {
														if(victim.getInventory().firstEmpty() == -1) {
															victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), helm);
															victim.getInventory().setHelmet(null);
														}
														else {
															victim.getInventory().addItem(helm);
															victim.getInventory().setHelmet(null);
														}
													}
												}
												else if(rand == 2) {
													if(chest != null) {
														if(victim.getInventory().firstEmpty() == -1) {
															victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), chest);
															victim.getInventory().setChestplate(null);
														}
														else {
															victim.getInventory().addItem(helm);
															victim.getInventory().setChestplate(null);
														}
													}
												}
												else if(rand == 3) {
													if(legs != null) {
														if(victim.getInventory().firstEmpty() == -1) {
															victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), legs);
															victim.getInventory().setLeggings(null);
														}
														else {
															victim.getInventory().addItem(helm);
															victim.getInventory().setLeggings(null);
														}
													}
												}
												else if(rand == 4) {
													if(boots != null) {
														if(victim.getInventory().firstEmpty() == -1) {
															victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), boots);
															victim.getInventory().setBoots(null);
														}
														else {
															victim.getInventory().addItem(boots);
															victim.getInventory().setBoots(null);
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
	public void animation(Entity entity) {
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.3D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.ITEM_CRACK, locCF, 50, 0.2, 0.2, 0.2, 0.1);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 0.9);
	}
}