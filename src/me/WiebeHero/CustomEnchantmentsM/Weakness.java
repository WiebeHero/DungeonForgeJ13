package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
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

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class Weakness extends SwordSwingProgress implements Listener {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void customEnchantmentsMWeakness(EntityDamageByEntityEvent event) {
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
											if(s1.contains(ChatColor.stripColor("Weakness"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Weakness")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 6 + level) {
												animation(victim);
												int amp = (int)Math.floor(0 + (level) / 2);
												int durationAdd = 120 + 40 * level;
												PotionEffectType type = PotionEffectType.WEAKNESS;
												if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
													int durationNow = victim.getPotionEffect(type).getDuration();
													victim.removePotionEffect(type);
													victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
												}
												else {
													victim.removePotionEffect(type);
													victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
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
		Location locCF1 = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 2.5D, entity.getLocation().getZ() + 0D);
		BlockData block = Material.COAL_BLOCK.createBlockData();
		entity.getWorld().spawnParticle(Particle.FALLING_DUST, locCF1, 80, 0.15, 0.15, 0.15, 0, block); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_SKELETON_HURT, 2, (float) 1.1);
	}
}