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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class NegativeHollow extends SwordSwingProgress implements Listener {
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
											if(s1.contains(ChatColor.stripColor("Negative Hollow"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Negative Hollow ")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 3 + 1 * level) {
												animation(victim, damager);
												if(victim.hasPotionEffect(PotionEffectType.SPEED) || victim.hasPotionEffect(PotionEffectType.SATURATION) || victim.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) || victim.hasPotionEffect(PotionEffectType.JUMP) || victim.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE) || victim.hasPotionEffect(PotionEffectType.REGENERATION) || victim.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) || victim.hasPotionEffect(PotionEffectType.FAST_DIGGING)|| victim.hasPotionEffect(PotionEffectType.ABSORPTION) || victim.hasPotionEffect(PotionEffectType.HEALTH_BOOST) || victim.hasPotionEffect(PotionEffectType.NIGHT_VISION) || victim.hasPotionEffect(PotionEffectType.WATER_BREATHING)){
													victim.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
													victim.removePotionEffect(PotionEffectType.SPEED);
													victim.removePotionEffect(PotionEffectType.SATURATION);
													victim.removePotionEffect(PotionEffectType.JUMP);
													victim.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
													victim.removePotionEffect(PotionEffectType.REGENERATION);
													victim.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
													victim.removePotionEffect(PotionEffectType.FAST_DIGGING);
													victim.removePotionEffect(PotionEffectType.ABSORPTION);
													victim.removePotionEffect(PotionEffectType.HEALTH_BOOST);
													victim.removePotionEffect(PotionEffectType.NIGHT_VISION);
													victim.removePotionEffect(PotionEffectType.WATER_BREATHING);
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
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.4D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 2, (float) 1);
		}
	}
}