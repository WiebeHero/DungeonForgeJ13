package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import NeededStuff.SwordSwingProgress;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class WolfPack extends SwordSwingProgress implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@SuppressWarnings("deprecation")
	@EventHandler
	public void CustomEnchantmentsMWolfPack(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			EntityDamageEvent ede = victim.getLastDamageCause();
			DamageCause dc = ede.getCause();
			if(event.getEntity().getKiller() instanceof Player && dc == DamageCause.ENTITY_ATTACK){
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) event.getEntity().getKiller();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					double d = swordSwingProgress.get(damager.getName());
					if(d == 1.00) {
						if(damager.getInventory().getItemInMainHand() != null) {
							if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Wolf Pack"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Wolf Pack")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 40 + 10 * level) {
											animation(victim, damager);
											Wolf wolf = (Wolf) damager.getWorld().spawnEntity(victim.getLocation(), EntityType.WOLF);
											wolf.setCustomName(new ColorCodeTranslator().colorize("&a&lWolf &6[&aLv 1&6]"));
											wolf.setCustomNameVisible(true);
											wolf.setOwner(damager);
											wolf.setMaxHealth(80);
											wolf.setHealth(80);
											wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0 + level));
											plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												  public void run() {
												      wolf.remove();
												  }
											}, 700 + 100 * level);
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
	public void animation(LivingEntity victim, Player damager) {
		Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc1, 60, 0, 0.3, 0, 0); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WOLF_HOWL, 2, (float) 1);
		}
	}
	@EventHandler
	public void CustomEnchantmentsMWolfPackDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Wolf){
			if(event.getEntity() instanceof LivingEntity) {
				Wolf damager = (Wolf) event.getDamager();
				LivingEntity victim = (LivingEntity) event.getEntity();
				DamageCause damageCause = event.getCause();
				if(damageCause == DamageCause.ENTITY_ATTACK) {
					String wolfcheck = ChatColor.stripColor(damager.getName());
					if(wolfcheck.contains("Wolf [Lv 1]")) {
						float damage = (float) event.getDamage();
						victim.damage(damage + (4));
					}
					else if(wolfcheck.contains("Wolf [Lv 2]")) {
						float damage = (float) event.getDamage();
						victim.damage(damage + 5.5);
					}
					else if(wolfcheck.contains("Wolf [Lv 3]")) {
						float damage = (float) event.getDamage();
						victim.damage(damage + 7);
					}
					else if(wolfcheck.contains("Wolf [Lv 4]")) {
						float damage = (float) event.getDamage();
						victim.damage(damage + 8.5);
					}
				}
			}
		}
	}
	@EventHandler
	public void CustomEnchantmentsMWolfPackDamaged(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Wolf){
			LivingEntity victim = (LivingEntity) event.getEntity();
			DamageCause damageCause = event.getCause();
			if(damageCause == DamageCause.ENTITY_ATTACK) {
				String wolfcheck = ChatColor.stripColor(victim.getName());
				if(wolfcheck.contains("Wolf [Lv 1]")) {
					float damage = (float) event.getDamage();
					victim.damage(damage - 1);
				}
				else if(wolfcheck.contains("Wolf [Lv 2]")) {
					float damage = (float) event.getDamage();
					victim.damage(damage - 2);
				}
				else if(wolfcheck.contains("Wolf [Lv 3]")) {
					float damage = (float) event.getDamage();
					victim.damage(damage - 3);
				}
				else if(wolfcheck.contains("Wolf [Lv 4]")) {
					float damage = (float) event.getDamage();
					victim.damage(damage - 4);
				}
			}
		}
	}
}
