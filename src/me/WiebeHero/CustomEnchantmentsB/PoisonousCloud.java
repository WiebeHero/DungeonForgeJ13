package me.WiebeHero.CustomEnchantmentsB;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class PoisonousCloud implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					Player damager = (Player) arrow.getShooter();
					String name = arrow.getCustomName();
					double force = 0.00;
					Matcher matcher2 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(name));
					while(matcher2.find()) {
					    force = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
					}
					if(force == 1.00) {
						if(damager.getInventory().getItemInMainHand() != null) {
							if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Poisonous Cloud"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Poisonous Cloud")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 2.5 + level) {
											animation(victim, damager);
											new BukkitRunnable() {
												int count = 0;
												int check = 5 + level;
												double range = 3 + 0.75 * level;
												public void run() {
													if(count == check) {
														for(Entity e : victim.getNearbyEntities(range, range, range)) {
															if(e instanceof LivingEntity) {
																if(e != damager) {
																	count++;
																	LivingEntity entity = (LivingEntity) e;
																	int amp = (int)Math.floor(0 + (level) / 2);
																	int durationAdd = 100 + 20 * level;
																	PotionEffectType type = PotionEffectType.POISON;
																	if(entity.hasPotionEffect(type) && entity.getPotionEffect(type).getAmplifier() == amp) {
																		int durationNow = entity.getPotionEffect(type).getDuration();
																		entity.removePotionEffect(type);
																		entity.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
																	}
																	else {
																		entity.removePotionEffect(type);
																		entity.addPotionEffect(new PotionEffect(type, durationAdd, amp));
																	}
																}
															}
														}
													}
													else {
														cancel();
													}
												}
											}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
										}
									}
								}
							}
						}
						if(damager.getInventory().getItemInOffHand() != null) {
							if(damager.getInventory().getItemInOffHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInOffHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Poisonous Cloud"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Poisonous Cloud")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 2.5 + level) {
											animation(victim, damager);
											new BukkitRunnable() {
												int count = 0;
												int check = 5 + level;
												double range = 3 + 0.75 * level;
												public void run() {
													if(count == check) {
														for(Entity e : victim.getNearbyEntities(range, range, range)) {
															if(e instanceof LivingEntity) {
																if(e != damager) {
																	LivingEntity entity = (LivingEntity) e;
																	int amp = (int)Math.floor(0 + (level) / 2);
																	int durationAdd = 100 + 20 * level;
																	PotionEffectType type = PotionEffectType.POISON;
																	if(entity.hasPotionEffect(type) && entity.getPotionEffect(type).getAmplifier() == amp) {
																		int durationNow = entity.getPotionEffect(type).getDuration();
																		entity.removePotionEffect(type);
																		entity.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
																	}
																	else {
																		entity.removePotionEffect(type);
																		entity.addPotionEffect(new PotionEffect(type, durationAdd, amp));
																	}
																}
															}
														}
													}
												}
											}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
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
	@EventHandler
	public void hitProjectileEvent(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if(arrow.getShooter() instanceof Player) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				Player damager = (Player) arrow.getShooter();
				String name = arrow.getCustomName();
				double force = 0.00;
				Matcher matcher2 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(name));
				while(matcher2.find()) {
				    force = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
				}
				if(force == 1.00) {
					if(damager.getInventory().getItemInMainHand() != null) {
						if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
							if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
								String check = "";
								for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
									if(s1.contains(ChatColor.stripColor("Poisonous Cloud"))) {
										check = ChatColor.stripColor(s1);
									}
								}
								if(check.contains("Poisonous Cloud")){
									check = check.replaceAll("[^\\d.]", "");
									int level = Integer.parseInt(check) - 1;
									if(i <= 2.5 + level) {
										new BukkitRunnable() {
											int count = 0;
											int check = 5 + level;
											double range = 3 + 0.75 * level;
											public void run() {
												if(count == check) {
													if(!arrow.isDead()) {
														for(Entity e : arrow.getWorld().getNearbyEntities(arrow.getLocation(), range, range, range)) {
															if(e instanceof LivingEntity) {
																if(e != damager) {
																	LivingEntity entity = (LivingEntity) e;
																	int amp = (int)Math.floor(0 + (level) / 2);
																	int durationAdd = 100 + 20 * level;
																	PotionEffectType type = PotionEffectType.POISON;
																	if(entity.hasPotionEffect(type) && entity.getPotionEffect(type).getAmplifier() == amp) {
																		int durationNow = entity.getPotionEffect(type).getDuration();
																		entity.removePotionEffect(type);
																		entity.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
																	}
																	else {
																		entity.removePotionEffect(type);
																		entity.addPotionEffect(new PotionEffect(type, durationAdd, amp));
																	}
																}
															}
														}
													}
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
									}
								}
							}
						}
					}
					if(damager.getInventory().getItemInOffHand() != null) {
						if(damager.getInventory().getItemInOffHand().getItemMeta() != null) {
							if(damager.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
								String check = "";
								for(String s1 : damager.getInventory().getItemInOffHand().getItemMeta().getLore()){
									if(s1.contains(ChatColor.stripColor("Poisonous Cloud"))) {
										check = ChatColor.stripColor(s1);
									}
								}
								if(check.contains("Poisonous Cloud")){
									check = check.replaceAll("[^\\d.]", "");
									int level = Integer.parseInt(check) - 1;
									if(i <= 2.5 + level) {
										new BukkitRunnable() {
											int count = 0;
											int check = 5 + level;
											double range = 3 + 0.75 * level;
											public void run() {
												if(count == check) {
													for(Entity e : arrow.getWorld().getNearbyEntities(arrow.getLocation(), range, range, range)) {
														if(e instanceof LivingEntity) {
															if(e != damager) {
																LivingEntity entity = (LivingEntity) e;
																int amp = (int)Math.floor(0 + (level) / 2);
																int durationAdd = 100 + 20 * level;
																PotionEffectType type = PotionEffectType.POISON;
																if(entity.hasPotionEffect(type) && entity.getPotionEffect(type).getAmplifier() == amp) {
																	int durationNow = entity.getPotionEffect(type).getDuration();
																	entity.removePotionEffect(type);
																	entity.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
																}
																else {
																	entity.removePotionEffect(type);
																	entity.addPotionEffect(new PotionEffect(type, durationAdd, amp));
																}
															}
														}
													}
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
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
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.SPELL, locCF, 60, 0, 0, 0, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2, (float) 1);
		}
	}
}