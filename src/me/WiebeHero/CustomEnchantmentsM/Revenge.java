package me.WiebeHero.CustomEnchantmentsM;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Revenge implements Listener{
	public HashMap<String, String> hitList = new HashMap<String, String>();
	@EventHandler
	public void deathRegister(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = event.getEntity();
			EntityDamageEvent ede = victim.getLastDamageCause();
			DamageCause dc = ede.getCause();
			if(dc == DamageCause.ENTITY_ATTACK){
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(victim.getInventory().getItemInMainHand() != null) {
					if(victim.getInventory().getItemInMainHand().getItemMeta() != null) {
						if(victim.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
							String check = "";
							for(String s1 : victim.getInventory().getItemInMainHand().getItemMeta().getLore()){
								if(s1.contains(ChatColor.stripColor("Revenge"))) {
									check = ChatColor.stripColor(s1);
								}
							}
							if(check.contains("Revenge")){
								check = check.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(check) - 1;
								if(i <= 20 + 10 * level) {
									if(!hitList.containsKey(victim.getName())) {
										animation(victim);
										double mHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
										victim.setHealth(mHealth);
										hitList.put(victim.getName(), event.getEntity().getKiller().getCustomName());
										new BukkitRunnable() {
											public void run() {
												victim.damage(1000000);
												hitList.remove(victim.getName());
											}
										}.runTaskLater(CustomEnchantments.getInstance(), 200 + 20 * level);
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
	public void hitListDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				if(hitList.containsKey(event.getDamager().getName())) {
					if(hitList.get(event.getDamager().getName()).contains(event.getEntity().getCustomName())) {
						event.setDamage(event.getDamage() * 1.5);
					}
				}
			}
		}
	}
	public void animation(Player victim) {
		Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.MOB_APPEARANCE, loc1, 60, 0, 0.3, 0, 0); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 2, (float) 0.5);
		}
	}
}
