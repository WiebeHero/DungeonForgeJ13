package me.WiebeHero.CustomEnchantmentsS;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class ValorS implements Listener{
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
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getInventory().getItemInOffHand() != null) {
							if(victim.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
								if(victim.getInventory().getItemInOffHand().getItemMeta() != null) {
									if(victim.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : victim.getInventory().getItemInOffHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Valor"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Valor")){
											if(check.contains("1")){
												if(i <= 7) {
													animation(victim, damager);
													int amp = 0;
													int durationAdd = 250;
													PotionEffectType type = PotionEffectType.DAMAGE_RESISTANCE;
													if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
														int durationNow = victim.getPotionEffect(type).getDuration();
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													}
													else {
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
													}
												}
											}
											else if(check.contains("2")){
												if(i <= 8) {
													animation(victim, damager);
													int amp = 0;
													int durationAdd = 300;
													PotionEffectType type = PotionEffectType.DAMAGE_RESISTANCE;
													if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
														int durationNow = victim.getPotionEffect(type).getDuration();
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													}
													else {
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
													}
												}
											}
											else if(check.contains("3")){
												if(i <= 9) {
													animation(victim, damager);
													int amp = 1;
													int durationAdd = 350;
													PotionEffectType type = PotionEffectType.DAMAGE_RESISTANCE;
													if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
														int durationNow = victim.getPotionEffect(type).getDuration();
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													}
													else {
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
													}
												}
											}
											else if(check.contains("4")){
												if(i <= 10) {
													animation(victim, damager);
													int amp = 1;
													int durationAdd = 400;
													PotionEffectType type = PotionEffectType.DAMAGE_RESISTANCE;
													if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
														int durationNow = victim.getPotionEffect(type).getDuration();
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													}
													else {
														victim.removePotionEffect(type);
														victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
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
		victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
		}
	}
}
