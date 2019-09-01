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

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class HeavyHand extends SwordSwingProgress implements Listener {
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
											if(s1.contains(ChatColor.stripColor("Heavy Hand"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Heavy Hand")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 0.5 + 0.5 * level) {
												animation(victim, damager);
					    				 		float damage = (float) event.getFinalDamage();
					    				 		event.setDamage(damage * (2.5 + 0.5 * level));
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
		Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.25D);
		Location loc2 = new Location(victim.getWorld(), victim.getLocation().getX() + 0.5D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.0D);
		Location loc3 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.5D);
	 	Location loc4 = new Location(victim.getWorld(), victim.getLocation().getX() + -0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.25D);
	 	Location loc5 = new Location(victim.getWorld(), victim.getLocation().getX() + -0.5D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0D);
	 	Location loc6 = new Location(victim.getWorld(), victim.getLocation().getX() + -0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + -0.25D);
	 	Location loc7 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + -0.5D);
	 	Location loc8 = new Location(victim.getWorld(), victim.getLocation().getX() + 0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + -0.25D);
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc1, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc2, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc3, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc4, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc5, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc6, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc7, 15, 0, 0.3, 0, 0); 
	 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc8, 15, 0, 0.3, 0, 0);
	 	for(Player victim1 : Bukkit.getOnlinePlayers()) {
	 		((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, (float) 1);
	 	}
	}
}
