package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

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
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class ChronicalDisturbance extends SwordSwingProgress implements Listener{
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
											if(s1.contains(ChatColor.stripColor("Chronical Disturbance"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Chronical Disturbance")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 3.5 + 1.5 * level) {
												new BukkitRunnable() {
													int count = 0;
													public void run() {
														if(count <= 60 + 20 * level) {
															victim.damage(0.1 + 0.05 * level);
															count++;
														}
														else {
															cancel();
														}
													}
												}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
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
		entity.getWorld().spawnParticle(Particle.PORTAL, entity.getLocation().add(0, 1.7, 0), 80, 0.2, 0.2, 0.2, 0.1); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 2, (float) 1);
	}
}