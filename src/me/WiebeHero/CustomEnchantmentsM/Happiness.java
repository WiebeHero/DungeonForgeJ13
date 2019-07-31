package me.WiebeHero.CustomEnchantmentsM;

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
import org.bukkit.scheduler.BukkitRunnable;

import NeededStuff.SwordSwingProgress;
import Skills.Enums.Classes;
import Skills.PlayerClass;
import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Happiness extends SwordSwingProgress implements Listener {
	PlayerClass pc = new PlayerClass();
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					Player victim = (Player) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					double d = swordSwingProgress.get(damager.getName());
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK) {
						if(d == 1.00) {
							if(damager.getInventory().getItemInMainHand() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
									if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Happiness"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Happiness")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 4 + level) {
												if(pc.getClass(victim.getUniqueId()) == Classes.WRATH) {
													animation(victim, damager);
													double extra = level * 5;
													pc.setCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA, pc.getSkill(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA) + extra);
													new BukkitRunnable() {
														public void run() {
															pc.setCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA, pc.getSkill(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA) - extra);
														}
													}.runTaskLater(CustomEnchantments.getInstance(), 1L);
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
	public void animation(LivingEntity victim, Player damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, locCF, 50, 0, 0, 0, 4);
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_SCREAM, 2, (float) 1.5);
		}
	}
}
