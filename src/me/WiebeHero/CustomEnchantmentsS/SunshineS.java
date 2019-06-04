package me.WiebeHero.CustomEnchantmentsS;

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

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class SunshineS implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					LivingEntity victim = (LivingEntity) event.getEntity();
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(damager.getWorld().getTime() < 12300 && damager.getWorld().getTime() > 0) {
							if(damager.getInventory().getItemInOffHand() != null) {
								if(damager.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
									if(damager.getInventory().getItemInOffHand().getItemMeta() != null) {
										if(damager.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
											String check = "";
											for(String s1 : damager.getInventory().getItemInOffHand().getItemMeta().getLore()){
												if(s1.contains(ChatColor.stripColor("Sunshine"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Sunshine")){
												if(check.contains("1")){
													animation(victim, damager);
													double damage = event.getFinalDamage();
													event.setDamage(damage * 1.05);
												}
												else if(check.contains("2")){
													animation(victim, damager);
													double damage = event.getFinalDamage();
													event.setDamage(damage * 1.10);
												}
												else if(check.contains("3")){
													animation(victim, damager);
													double damage = event.getFinalDamage();
													event.setDamage(damage * 1.15);
												}
												else if(check.contains("4")){
													animation(victim, damager);
													double damage = event.getFinalDamage();
													event.setDamage(damage * 1.20);
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
		damager.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
		}
	}
}