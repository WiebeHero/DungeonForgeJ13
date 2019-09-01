package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class Freeze extends SwordSwingProgress implements Listener {
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
											if(s1.contains(ChatColor.stripColor("Freeze"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Freeze")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 4 + level) {
												animation(victim, damager);
												int amp1 = 20;
												int amp2 = (int)Math.floor(0 + (level) / 2);
												int durationAdd = 80 + 20 * level;
												PotionEffectType type = PotionEffectType.SLOW;
												PotionEffectType type1 = PotionEffectType.SLOW_DIGGING;
												if(victim.hasPotionEffect(type) && victim.hasPotionEffect(type1) && victim.getPotionEffect(type).getAmplifier() == amp1 && victim.getPotionEffect(type1).getAmplifier() == amp2) {
													int durationNow = victim.getPotionEffect(type).getDuration();
													victim.removePotionEffect(type);
													victim.removePotionEffect(type1);
													victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp1));
													victim.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp2));
												}
												else {
													victim.removePotionEffect(type);
													victim.removePotionEffect(type1);
													victim.addPotionEffect(new PotionEffect(type, durationAdd, amp1));
													victim.addPotionEffect(new PotionEffect(type1, durationAdd, amp2));
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
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.PORTAL, locCF, 50, 0, 0, 0, 4);
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, (float) 1);
		}
	}
}