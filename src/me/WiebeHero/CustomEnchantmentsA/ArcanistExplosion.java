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

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class ArcanistExplosion implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAbsorbing(EntityDamageByEntityEvent event) {
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
											if(s1.contains(ChatColor.stripColor("Arcanist Explosion"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Arcanist Explosion")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 13 + 2 * level) {
												double range = 3 + 0.5 * level;
												animation(victim, damager);
												for(Entity entity : victim.getNearbyEntities(range, range, range)){
													if(entity instanceof LivingEntity) {
														if(entity != victim) {
															LivingEntity entity1 = (LivingEntity) entity;
															entity1.damage(1.25 + 0.25 * level);
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
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 10, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
		}
	}
}

