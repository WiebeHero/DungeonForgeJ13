package me.WiebeHero.CustomEnchantmentsM;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import NeededStuff.SwordSwingProgress;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Beserk extends SwordSwingProgress implements Listener{
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
											if(s1.contains(ChatColor.stripColor("Beserk"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Beserk")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 5 + level * 2) {
												animation(damager);
												int amp = 0 + level;
												int durationAdd = 60 + (30 * level);
												PotionEffectType type = PotionEffectType.INCREASE_DAMAGE;
												PotionEffectType type1 = PotionEffectType.SLOW_DIGGING;
												if(damager.hasPotionEffect(type) && damager.hasPotionEffect(type1) && damager.getPotionEffect(type).getAmplifier() == amp && damager.getPotionEffect(type1).getAmplifier() == amp) {
													int durationNow = damager.getPotionEffect(type).getDuration();
													damager.removePotionEffect(type);
													damager.removePotionEffect(type1);
													damager.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
													damager.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
												}
												else {
													damager.removePotionEffect(type);
													damager.removePotionEffect(type1);
													damager.addPotionEffect(new PotionEffect(type, durationAdd, amp));
													damager.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
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
		entity.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, (float) 1);
	}
}
