package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
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
import org.bukkit.scheduler.BukkitRunnable;

import NeededStuff.SwordSwingProgress;
import Skills.SkillJoin;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Confusion extends SwordSwingProgress implements Listener{
	SkillJoin join = new SkillJoin();
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
											if(s1.contains(ChatColor.stripColor("Confusion"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Confusion")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 10 + level * 2) {	
												animation(victim);
												int amp = 0 + level;
												int durationAdd = 180 + 50 * level;
												PotionEffectType type = PotionEffectType.CONFUSION;
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
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 2D, entity.getLocation().getZ() + 0D);
		Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 1);
		entity.getWorld().spawnParticle(Particle.REDSTONE, locCF, 60, 0.15, 0.15, 0.15, 0.1, dustOptions); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GHAST_HURT, 2, (float) 0.5);
	}
}