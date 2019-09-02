package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class AbsorbingComeback implements Listener{
	public static HashMap<String, Double> extraDamage = new HashMap<String, Double>();
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMTank(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
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
												if(s1.contains(ChatColor.stripColor("Absorbing Comeback"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Absorbing Comeback ")){
												check = check.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(check) - 1;
												if(i <= 7 + level) {
													animation(victim, damager);
													extraDamage.put(victim.getName(), event.getDamage() * (0.10 + 0.05 * level));
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
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					LivingEntity victim = (LivingEntity) event.getEntity();
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK) {
						if(extraDamage.containsKey(damager.getName())) {
							if(extraDamage.get(damager.getName()) > 0) {
								Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 2D, damager.getLocation().getZ() + 0D);
								damager.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
								victim.sendMessage(new ColorCodeTranslator().colorize("&4Your enemy did extra damage to you with &4&lAbsorbing Comeback"));
								damager.sendMessage(new ColorCodeTranslator().colorize("&aYou did extra damage with &a&lAbsorbing Comeback!"));
								for(Player victim1 : Bukkit.getOnlinePlayers()) {
									((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 2, (float) 1.4);
								}
								double damage1 = event.getDamage();
								event.setDamage(damage1 + extraDamage.get(damager.getName()));
								extraDamage.put(damager.getName(), 0.00);
							}
						}
					}
				}
			}
		}
	}
	public void animation(Player victim, LivingEntity damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 2, (float) 1.3);
		}
	}
}