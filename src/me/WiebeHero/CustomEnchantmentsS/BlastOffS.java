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
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class BlastOffS implements Listener{
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
											if(s1.contains(ChatColor.stripColor("Blast Off"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Blast Off")){
											if(check.contains("1")){
												if(i < 9) {
													animation(victim, damager);
													damager.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
													damager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 20));
													plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
														  public void run() {
															  victim.setVelocity(new Vector(0, 1.2, 0));
														  }
													}, 1L);
												}
											}
											else if(check.contains("2")){
												if(i <= 10) {
													animation(victim, damager);
													damager.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
													damager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 220, 20));
													plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
														  public void run() {
															  victim.setVelocity(new Vector(0, 1.4, 0));
														  }
													}, 1L);
												}
											}
											else if(check.contains("3")){
												if(i <= 11) {
													animation(victim, damager);
													damager.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
													damager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 240, 20));
													plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
														  public void run() {
															  victim.setVelocity(new Vector(0, 1.6, 0));
														  }
													}, 1L);
												}
											}
											else if(check.contains("4")){
												if(i <= 12) {
													animation(victim, damager);
													damager.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
													damager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 260, 20));
													plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
														public void run() {
															victim.setVelocity(new Vector(0, 1.8, 0));
														}
													}, 1L);
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
		victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 80, 0.2, 0.2, 0.2, 0.2); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2, (float) 1);
		}
	}
}