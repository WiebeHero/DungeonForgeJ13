package me.WiebeHero.CustomEnchantmentsS;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class EnlightenedS implements Listener{
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
											if(s1.contains(ChatColor.stripColor("Enlightened"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Enlightened")){
											if(check.contains("1")){
												if(i < 7) {
													animation(victim, damager);
													double heal = victim.getHealth() + 1.5;
													double attribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
													if(heal < attribute) {
														victim.setHealth(heal);
													}
													else {
														victim.setHealth(attribute);
													}
												}
											}
											else if(check.contains("2")){
												if(i <= 8) {
													animation(victim, damager);
													double heal = victim.getHealth() + 3.0;
													double attribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
													if(heal < attribute) {
														victim.setHealth(heal);
													}
													else {
														victim.setHealth(attribute);
													}
												}
											}
											else if(check.contains("3")){
												if(i <= 9) {
													animation(victim, damager);
													double heal = victim.getHealth() + 4.5;
													double attribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
													if(heal < attribute) {
														victim.setHealth(heal);
													}
													else {
														victim.setHealth(attribute);
													}
												}
											}
											else if(check.contains("4")){
												if(i <= 10) {
													animation(victim, damager);
													double heal = victim.getHealth() + 6;
													double attribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
													if(heal < attribute) {
														victim.setHealth(heal);
													}
													else {
														victim.setHealth(attribute);
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
		damager.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, (float) 1.1);
		}
	}
}
