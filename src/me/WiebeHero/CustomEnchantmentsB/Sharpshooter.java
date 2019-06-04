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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class Sharpshooter implements Listener{
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
										if(s1.contains(ChatColor.stripColor("Sharpshooter"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Sharpshooter")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(damager.getLocation().distance(victim.getLocation()) < 5.50 + 1.50 * level) {
											animation(victim, damager);
											event.setDamage(event.getFinalDamage() + 5.50 + 1.50 * level - damager.getLocation().distance(victim.getLocation()));
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
										if(s1.contains(ChatColor.stripColor("Sharpshooter"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Sharpshooter")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(damager.getLocation().distance(victim.getLocation()) < 5.50 + 1.50 * level) {
											animation(victim, damager);
											event.setDamage(event.getFinalDamage() + 5.50 + 1.50 * level - damager.getLocation().distance(victim.getLocation()));
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
		Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF1, 40, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 2, (float) 1.5);
		}
	}
}
