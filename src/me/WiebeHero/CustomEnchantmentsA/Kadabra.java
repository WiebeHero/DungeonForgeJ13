package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Kadabra implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMKadabra(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
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
												if(s1.contains(ChatColor.stripColor("Kadabra"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Kadabra ")){
												check = check.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(check) - 1;
												if(i <= 6 + level) {
													animation(victim, damager);
													int amp = (int)Math.floor(0 + (level) / 2);
													int durationAdd = 140 + 40 * level;
													PotionEffectType type = PotionEffectType.WEAKNESS;
													if(damager.hasPotionEffect(type) && damager.getPotionEffect(type).getAmplifier() == amp) {
														int durationNow = damager.getPotionEffect(type).getDuration();
														damager.removePotionEffect(type);
														damager.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													}
													else {
														damager.removePotionEffect(type);
														damager.addPotionEffect(new PotionEffect(type, durationAdd, amp));
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
		Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 1.4);
		}
	}
}