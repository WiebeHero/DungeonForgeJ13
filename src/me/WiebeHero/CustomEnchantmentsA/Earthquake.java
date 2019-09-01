package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
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

import net.md_5.bungee.api.ChatColor;

public class Earthquake implements Listener{
	@EventHandler
	public void CustomEnchantmentsMCactus(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getInventory().getArmorContents() != null) {
							ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getInventory().getArmorContents()));
							items.add(victim.getInventory().getItemInOffHand());
							for(ItemStack item : items) {
								if(item != null) {
									if(item.getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : item.getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Earthquake"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Earthquake")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 2 + level * 0.5) {
												animation(victim, damager);
												double range = 8.50 + level * 1.50;
												for(Entity e : victim.getNearbyEntities(range, range, range)) {
													if(e instanceof LivingEntity) {
														LivingEntity ent = (LivingEntity) e;
														ent.damage(2 * level);
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
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 60, 0.1, 0.1, 0.1, 0.1, Material.GRASS_BLOCK.createBlockData()); 
		victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_GRASS_BREAK, 2, (float) 1);
	}
}
