package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class FossilBlaze implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMTank(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getInventory().getArmorContents() != null && victim.getInventory().getItemInOffHand() != null) {
							ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getInventory().getArmorContents()));
							items.add(victim.getInventory().getItemInOffHand());
							for(ItemStack item : items) {
								if(item != null) {
									if(item.hasItemMeta()) {
										if(item.getItemMeta().hasLore()) {
											String check = "";
											for(String s1 : item.getItemMeta().getLore()){
												if(s1.contains(ChatColor.stripColor("Fossil Blaze"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Fossil Blaze")){
												check = check.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(check) - 1;
												if(i <= 3 + 1.3 * level) {
													animation(victim);
													double range = 3.00 + 1.25 * level;
													for(Entity entity : victim.getNearbyEntities(range, range, range)) {
														if(entity != null) {
															if(entity instanceof LivingEntity) {
																LivingEntity attacked = (LivingEntity) entity;
																if(attacked != victim) {
																	attacked.setFireTicks(attacked.getFireTicks() + (140 + 20 * level));
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
	}
	public void animation(Entity entity) {
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.5D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
	}
}