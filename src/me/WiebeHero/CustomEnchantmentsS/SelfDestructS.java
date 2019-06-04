package me.WiebeHero.CustomEnchantmentsS;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class SelfDestructS implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				LivingEntity damager = (LivingEntity) event.getEntity().getKiller();
				Player victim = (Player) event.getEntity();
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				EntityDamageEvent ede = victim.getLastDamageCause();
				DamageCause damageCause = ede.getCause();
				if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
					if(victim.getInventory().getItemInOffHand() != null) {
						if(victim.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
							if(victim.getInventory().getItemInOffHand().getItemMeta() != null) {
								if(victim.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : victim.getInventory().getItemInOffHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Self Destruct"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Self Destruct")){
										if(check.contains("1")){
											if(i < 40) {
												animation(victim, damager);
												int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
													  public void run() {
														  Entity tnt = victim.getWorld().spawn(victim.getLocation().add(0, 2, 0), TNTPrimed.class); 
														  ((TNTPrimed)tnt).setFuseTicks(110);
													  }
												}, 0L, 1L);
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													  public void run() {
														  Bukkit.getScheduler().cancelTask(taskID);
													  }
												}, 10L);
											}
										}
										else if(check.contains("2")){
											if(i <= 50) {
												animation(victim, damager);
												int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
													  public void run() {
														  Entity tnt = victim.getWorld().spawn(victim.getLocation().add(0, 2, 0), TNTPrimed.class); 
														  ((TNTPrimed)tnt).setFuseTicks(95);
													  }
												}, 0L, 1L);
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													  public void run() {
														  Bukkit.getScheduler().cancelTask(taskID);
													  }
												}, 13L);  
											}
										}
										else if(check.contains("3")){
											if(i <= 60) {
												animation(victim, damager);
												int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
													  public void run() {
														  Entity tnt = victim.getWorld().spawn(victim.getLocation().add(0, 2, 0), TNTPrimed.class); 
														  ((TNTPrimed)tnt).setFuseTicks(80);
													  }
												}, 0L, 1L);
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													  public void run() {
														  Bukkit.getScheduler().cancelTask(taskID);
													  }
												}, 16L);
											}
										}
										else if(check.contains("4")){
											if(i <= 70) {
												animation(victim, damager);
												int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
													  public void run() {
														  Entity tnt = victim.getWorld().spawn(victim.getLocation().add(0, 2, 0), TNTPrimed.class); 
														  ((TNTPrimed)tnt).setFuseTicks(65);
													  }
												}, 0L, 1L);
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													  public void run() {
														  Bukkit.getScheduler().cancelTask(taskID);
													  }
												}, 20L);
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
		victim.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_TNT_PRIMED, 2, (float) 1);
		}
	}
}
