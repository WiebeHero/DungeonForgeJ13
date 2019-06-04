package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
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

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class PowerfullStrike implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	ArrayList<String> strike = new ArrayList<String>();
	@EventHandler
	public void CustomEnchantmentsMAbsorbing(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(damager.getFallDistance() > 0) {
							if(victim.getInventory().getArmorContents() != null) {
								ItemStack[] items = victim.getInventory().getArmorContents();
								for(ItemStack item : items) {
									if(item != null) {
										if(item.getItemMeta().getLore() != null) {
											String check = "";
											for(String s1 : item.getItemMeta().getLore()){
												if(s1.contains(ChatColor.stripColor("Powerfull Strike"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Powerfull Strike")){
												check = check.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(check) - 1;
												if(i <= 15 + 2.5 * level) {
													Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
													victim.getWorld().spawnParticle(Particle.DRAGON_BREATH, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
													victim.sendMessage(new ColorCodeTranslator().colorize("&aYou have activated &a&lPowerfull Strike!"));
													damager.sendMessage(new ColorCodeTranslator().colorize("&4Your enemy has activated their &4&lPowerfull Strike"));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 1.4);
													}
													if(!strike.contains(damager.getName())) {
														strike.add(damager.getName());
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
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof LivingEntity) {
				Player damager = (Player) event.getDamager();
				LivingEntity victim = (LivingEntity) event.getEntity();
				DamageCause damageCause = event.getCause();
				if(damageCause == DamageCause.ENTITY_ATTACK) {
					if(strike.contains(damager.getName())) {
						Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
						damager.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 2, (float) 1.4);
						}
						double damage1 = event.getDamage();
						event.setDamage(damage1 * 1.5);
						strike.remove(damager.getName());
						}
					}
				}
			}
		}
	}