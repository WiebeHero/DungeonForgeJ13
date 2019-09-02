package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Dummy implements Listener{
	public HashMap<ArmorStand, Player> mapDummy = new HashMap<ArmorStand, Player>();
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@SuppressWarnings("deprecation")
	@EventHandler
	public void customEnchantmentsMTank(EntityDamageByEntityEvent event) {
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
												if(s1.contains(ChatColor.stripColor("Dummy"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Dummy")){
												check = check.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(check) - 1;
												if(i <= 10 + 3.5 * level) {
													double maxHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
													if(victim.getHealth() < maxHealth * (0.15 + 0.025 * level)) {
														animation(victim);
														int amp = (int)Math.floor(0 + (level) / 2);
														int durationAdd = 400 + 40 * level;
														PotionEffectType type = PotionEffectType.INVISIBILITY;
														if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
															int durationNow = victim.getPotionEffect(type).getDuration();
															victim.removePotionEffect(type);
															victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
														}
														else {
															victim.removePotionEffect(type);
															victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
														}
														ArmorStand dummy = (ArmorStand) victim.getWorld().spawnEntity(victim.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
														dummy.setCustomName(new ColorCodeTranslator().colorize(victim.getName()));
														for(Player p : Bukkit.getOnlinePlayers()) {
															p.hidePlayer(victim);
														}
														mapDummy.put(dummy, victim);
														new BukkitRunnable() {
															public void run() {
																if(!dummy.isDead()) {
																	dummy.remove();
																	mapDummy.remove(dummy);
																	for(Player p : Bukkit.getOnlinePlayers()) {
																		p.showPlayer(victim);
																	}
																}
															}
														}.runTaskLater(CustomEnchantments.getInstance(), 400 + 40 * level);
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
	public void dummyDestroyed(EntityDeathEvent event) {
		if(event.getEntity() instanceof ArmorStand) {
			ArmorStand dummy = (ArmorStand) event.getEntity();
			if(mapDummy.containsKey(dummy)) {
				Player player = mapDummy.get(dummy);
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.showPlayer(player);
				}
				mapDummy.remove(dummy);
			}
		}
	}
	public void animation(Entity entity) {
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.5D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 0.5);
	}
}
