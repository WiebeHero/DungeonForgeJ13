package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import NeededStuff.SwordSwingProgress;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Blizzard extends SwordSwingProgress implements Listener {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					LivingEntity victim = (LivingEntity) event.getEntity();
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
											if(s1.contains(ChatColor.stripColor("Blizzards"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Frostbite")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 4 + level * 0.5) {
												double range = 2.5 + level * 0.5;
												animation(victim);
												for(Entity entity : victim.getNearbyEntities(range, range, range)){
													if(entity instanceof LivingEntity) {
														if(entity != damager) {
															LivingEntity entity1 = (LivingEntity) entity;
															int amp = (int)Math.floor(0 + (level) / 2);
															int durationAdd = 100 + 20 * level;
															PotionEffectType type = PotionEffectType.SLOW;
															PotionEffectType type1 = PotionEffectType.BLINDNESS;
															if(entity1.hasPotionEffect(type) && entity1.hasPotionEffect(type1) && entity1.getPotionEffect(type).getAmplifier() == amp && entity1.getPotionEffect(type1).getAmplifier() == amp) {
																int durationNow = entity1.getPotionEffect(type).getDuration();
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
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.7D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.PORTAL, locCF, 60, 0.05, 0.05, 0.05, 0.1); 
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, (float) 1);
	}
}
