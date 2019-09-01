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

public class Escape implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMEscape(EntityDamageByEntityEvent event) {
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
											if(s1.contains(ChatColor.stripColor("Escape"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Escape")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 13 + level) {
												if(victim.getHealth() <= 12 + level) {
													animation(victim, damager);
													int amp = 0 + level;
													int durationAdd = 200 + 50 * level;
													PotionEffectType type = PotionEffectType.SPEED;
													PotionEffectType type1 = PotionEffectType.ABSORPTION;
													if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp && victim.hasPotionEffect(type1) && victim.getPotionEffect(type1).getAmplifier() == amp) {
														int durationNow = victim.getPotionEffect(type).getDuration();
														victim.removePotionEffect(type);
														victim.removePotionEffect(type1);
														victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
														victim.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
													}
													else {
														victim.removePotionEffect(type);
														victim.removePotionEffect(type1);
														victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
														victim.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
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
		victim.getWorld().spawnParticle(Particle.SPELL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2, (float) 1.1);
		}
	}
}