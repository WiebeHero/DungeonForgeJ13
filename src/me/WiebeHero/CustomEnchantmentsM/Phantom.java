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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import NeededStuff.SwordSwingProgress;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Phantom extends SwordSwingProgress implements Listener {
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
											if(s1.contains(ChatColor.stripColor("Phantom"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Phantom")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 7 + level) {
												animation(victim, damager);
												int amp = (int)Math.floor(0 + (level) / 2);;
												int durationAdd = 200 + 30 * level;
												PotionEffectType type = PotionEffectType.INVISIBILITY;
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
	public void animation(LivingEntity victim, Player damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, locCF, 50, 0.2, 0.2, 0.2, 0.2);
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 2, (float) 0.5);
		}
	}
}
