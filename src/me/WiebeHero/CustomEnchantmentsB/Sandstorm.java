package me.WiebeHero.CustomEnchantmentsB;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Sandstorm implements Listener{
	public ArrayList<Player> activateList = new ArrayList<Player>();
	public void sandstormActivate(PlayerInteractEvent event) {
		Player damager = event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(damager.getInventory().getItemInMainHand() != null) {
				if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
					if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
						String check = "";
						for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
							if(s1.contains(ChatColor.stripColor("Sand Storm"))) {
								check = ChatColor.stripColor(s1);
							}
						}
						if(check.contains("Sand Storm")){
							check = check.replaceAll("[^\\d.]", "");
							int level = Integer.parseInt(check) - 1;
							if(!activateList.contains(damager)) {
								animation(damager);
								double range = 5.00 + 1.5 * level;
								for(Entity e : damager.getNearbyEntities(range, range, range)) {
									if(e != null) {
										if(e instanceof LivingEntity) {
											LivingEntity entity = (LivingEntity) e;
											if(entity != damager) {
												int amp = 0;
												int durationAdd = 200 + 40 * level;
												PotionEffectType type = PotionEffectType.BLINDNESS;
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
					}
				}
			}
		}
	}
	public void animation(Entity entity) {
		Location locCF1 = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 2.5D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.FALLING_DUST, locCF1, 80, 1, 1, 1, 0, new MaterialData(Material.SAND)); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_SKELETON_HURT, 2, (float) 1.1);
	}
}