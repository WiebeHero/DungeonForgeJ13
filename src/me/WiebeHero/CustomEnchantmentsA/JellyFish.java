package me.WiebeHero.CustomEnchantmentsA;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class JellyFish implements Listener{
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
							ItemStack[] items = victim.getInventory().getArmorContents();
							for(ItemStack item : items) {
								if(item != null) {
									if(item.getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : item.getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Jellyfish"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Jellyfish")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 1.5 + 0.75 * level) {
												animation(victim);
												int amp = (int)Math.floor(0 + (level) / 2);
												int durationAdd = 180 + 40 * level;
												PotionEffectType type = PotionEffectType.SLOW;
												PotionEffectType type1 = PotionEffectType.POISON;
												PotionEffectType type2 = PotionEffectType.SLOW_DIGGING;
												if(damager.hasPotionEffect(type) && damager.getPotionEffect(type).getAmplifier() == amp && damager.hasPotionEffect(type1) && damager.getPotionEffect(type1).getAmplifier() == amp && damager.hasPotionEffect(type2) && damager.getPotionEffect(type2).getAmplifier() == amp) {
													int durationNow = damager.getPotionEffect(type).getDuration();
													damager.removePotionEffect(type);
													damager.removePotionEffect(type1);
													damager.removePotionEffect(type2);
													damager.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													damager.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
													damager.addPotionEffect(new PotionEffect(type2, durationNow + durationAdd, amp));
												}
												else {
													damager.removePotionEffect(type);
													damager.removePotionEffect(type1);
													damager.removePotionEffect(type2);
													damager.addPotionEffect(new PotionEffect(type, durationAdd, amp));
													damager.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
													damager.addPotionEffect(new PotionEffect(type2, durationAdd + durationAdd, amp));
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
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 0.2D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GUARDIAN_DEATH, 2, (float) 1.5);
	}
}