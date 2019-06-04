package me.WiebeHero.CustomEnchantmentsB;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ParalyzeB implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					Player damager = (Player) arrow.getShooter();
					String name = arrow.getCustomName();
					double force = 0.00;
					Matcher matcher2 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(name));
					while(matcher2.find()) {
					    force = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
					}
					if(force == 1.00) {
						if(damager.getInventory().getItemInMainHand() != null) {
							if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Paralyze"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Paralyze")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 4  + level) {
											animation(victim, damager);
											int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
												  public void run() {
													  victim.playEffect(EntityEffect.HURT); 
													  victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100 + 20 * level, 20));
													  victim.damage(0.5 + 0.1 * level);
												  }
											}, 0L, 1L);
											plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												  public void run() {
													  Bukkit.getScheduler().cancelTask(taskID);
												  }
											}, 100 + 20 * level);
										}
									}
								}
							}
						}
						if(damager.getInventory().getItemInOffHand() != null) {
							if(damager.getInventory().getItemInOffHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInOffHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Paralyze"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Paralyze")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 4  + level) {
											animation(victim, damager);
											int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
												  public void run() {
													  victim.playEffect(EntityEffect.HURT); 
													  victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100 + 20 * level, 20));
													  victim.damage(0.5 + 0.1 * level);
												  }
											}, 0L, 1L);
											plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												  public void run() {
													  Bukkit.getScheduler().cancelTask(taskID);
												  }
											}, 100 + 20 * level);
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
		Location location = victim.getLocation();
        location.getWorld().strikeLightningEffect(location);
	}
}
