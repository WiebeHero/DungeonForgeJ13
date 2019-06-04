package me.WiebeHero.CustomEnchantmentsB;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class BlackHeartB implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	int damage = 1;
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					Player shooter = (Player) arrow.getShooter();
					String name = arrow.getCustomName();
					double force = 0.00;
					Matcher matcher2 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(name));
					while(matcher2.find()) {
					    force = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
					}
					if(force == 1.00) {
						if(shooter.getInventory().getItemInMainHand() != null) {
							if(shooter.getInventory().getItemInMainHand().getItemMeta() != null) {
								if(shooter.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : shooter.getInventory().getItemInMainHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Black Heart"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Black Heart")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 5 + level) {
											animation(victim, shooter);
											new BukkitRunnable() {
												double range = 5.00 + 0.75 * level;
												double damage = 2.00 + 1.00 * level;
												int count = 0;
												public void run() {
													if(count < 6 + 2 * level) {
														count++;
														for(Entity e : shooter.getNearbyEntities(range, range, range)) {
															
														}
														damage = damage + 1;
														range = range + 1;
													}
													else {
														cancel();
														damage = 2.00 + 1.00 * level;
														range = 5.00 + 0.75 * level;
													}
												}
											}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 60 - 5 * level);
										}
									}
								}
							}
						}
						if(shooter.getInventory().getItemInOffHand() != null) {
							if(shooter.getInventory().getItemInOffHand().getItemMeta() != null) {
								if(shooter.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : shooter.getInventory().getItemInOffHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Black Heart"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Black Heart")){
										if(check.contains("Black Heart")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 5 + level) {
												animation(victim, shooter);
												new BukkitRunnable() {
													double range = 5.00 + 0.75 * level;
													double damage = 2.00 + 1.00 * level;
													int count = 0;
													public void run() {
														if(count < 6 + 2 * level) {
															count++;
															for(Entity e : shooter.getNearbyEntities(range, range, range)) {
																
															}
															damage = damage + 1;
															range = range + 1;
														}
														else {
															cancel();
															damage = 2.00 + 1.00 * level;
															range = 5.00 + 0.75 * level;
														}
													}
												}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 60 - 5 * level);
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
	public void animation(LivingEntity victim, Player shooter) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
		shooter.getWorld().spawnParticle(Particle.DRAGON_BREATH, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, (float) 1.4);
		}
	}
}

