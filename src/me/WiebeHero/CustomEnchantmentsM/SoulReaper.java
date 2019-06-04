package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import NeededStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class SoulReaper extends SwordSwingProgress implements Listener{
	@EventHandler
	public void soulReaper(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			EntityDamageEvent ede = victim.getLastDamageCause();
			DamageCause dc = ede.getCause();
			if(event.getEntity().getKiller() instanceof Player && dc == DamageCause.ENTITY_ATTACK){
				Player damager = (Player) event.getEntity().getKiller();
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				double d = swordSwingProgress.get(damager.getName());
				if(d == 1.00) {
					if(damager.getInventory().getItemInMainHand() != null) {
						if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
							if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
								String check = "";
								for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
									if(s1.contains(ChatColor.stripColor("Soul Reaper"))) {
										check = ChatColor.stripColor(s1);
									}
								}
								if(check.contains("Soul Reaper")){
									check = check.replaceAll("[^\\d.]", "");
									int level = Integer.parseInt(check) - 1;
									if(i <= 40 + 10 * level) {
										animation(victim, damager);
										int amp = 0 + level;
										int durationAdd = 150 + 50 * level;
										PotionEffectType type = PotionEffectType.REGENERATION;
										PotionEffectType type1 = PotionEffectType.INCREASE_DAMAGE;
										if(damager.hasPotionEffect(type) && damager.hasPotionEffect(type1) && damager.getPotionEffect(type).getAmplifier() == amp && damager.getPotionEffect(type1).getAmplifier() == amp) {
											int durationNow = damager.getPotionEffect(type).getDuration();
											damager.removePotionEffect(type);
											damager.removePotionEffect(type1);
											damager.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
											damager.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
										}
										else {
											damager.removePotionEffect(type);
											damager.removePotionEffect(type1);
											damager.addPotionEffect(new PotionEffect(type, durationAdd, amp));
											damager.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
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
	public void animation(Player victim, Player damager) {
		Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc1, 60, 0, 0.3, 0, 0, new MaterialData(Material.NETHER_BRICK)); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_HURT, 2, (float) 0.5);
		}
	} 
}
