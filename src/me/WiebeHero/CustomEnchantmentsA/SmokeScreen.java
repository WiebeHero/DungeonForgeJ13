package me.WiebeHero.CustomEnchantmentsA;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class SmokeScreen implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMSmokeScreen(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getInventory().getArmorContents() != null) {
							ItemStack[] items = victim.getInventory().getArmorContents();
							for(ItemStack item : items) {
								if(item != null) {
									if(item.getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : item.getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Smoke Screen"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Smoke Screen")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i < 25) {
												if(victim.getHealth() < 13 + level) {
													double range = 3 + level;
													animation(victim, damager);
													for (Entity entity : victim.getNearbyEntities(range, range, range)){
														if(entity instanceof LivingEntity) {
															if(entity != victim) {
																LivingEntity entity1 = (LivingEntity) entity;
																int amp = (int)Math.floor(0 + (level) / 2);
																int durationAdd = 150 + 50 * level;
																PotionEffectType type = PotionEffectType.SLOW;
																PotionEffectType type1 = PotionEffectType.SLOW_DIGGING;
																if(entity1.hasPotionEffect(type) && entity1.getPotionEffect(type).getAmplifier() == amp || entity1.hasPotionEffect(type1) && entity1.getPotionEffect(type1).getAmplifier() == amp) {
																	int durationNow = victim.getPotionEffect(type).getDuration();
																	entity1.removePotionEffect(type);
																	entity1.removePotionEffect(type1);
																	entity1.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
																	entity1.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
																}
																else {
																	entity1.removePotionEffect(type);
																	entity1.removePotionEffect(type1);
																	entity1.addPotionEffect(new PotionEffect(type, durationAdd, amp));
																	entity1.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
																}
																victim.removePotionEffect(type);
																victim.removePotionEffect(type1);
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
	}
	public void animation(Player victim, LivingEntity damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 100, 4.5, 4.5, 4.5, 4.5); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, (float) 0.7);
		}
	}
}
