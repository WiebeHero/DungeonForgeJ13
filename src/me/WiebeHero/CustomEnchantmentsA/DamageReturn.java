package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
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

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class DamageReturn implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMCactus(EntityDamageByEntityEvent event) {
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
												if(s1.contains(ChatColor.stripColor("Damage Return"))) {
													check = ChatColor.stripColor(s1);
												}
											}
											if(check.contains("Damage Return")){
												check = check.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(check) - 1;
												if(i <= 6 + level) {
													animation(victim, damager);
													double damage = event.getDamage() * (0.10 + 0.05 * level);
													damager.damage(damage);
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
	public void animation(Player victim, LivingEntity damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_HURT, 2, (float) 1.5);
		}
	}
}