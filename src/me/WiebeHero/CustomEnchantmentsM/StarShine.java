package me.WiebeHero.CustomEnchantmentsM;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class StarShine implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	Set<String> starShine = new HashSet<String>();
	Set<String> playerStuff = new HashSet<String>();
	
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR){
			Player player = event.getPlayer();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
					if(player.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
						String check = "";
						for(String s1 : player.getInventory().getItemInMainHand().getItemMeta().getLore()){
							if(s1.contains(ChatColor.stripColor("Star Shine"))) {
								check = ChatColor.stripColor(s1);
							}
						}
						if(check.contains("Star Shine ")){
							if(!playerStuff.contains(player.getName())) {
								check = check.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(check) - 1;
								playerStuff.add(player.getName()); // set to "true"
								for(Player victim1 : Bukkit.getOnlinePlayers()) {
									((Player) victim1).playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 2, (float) 1.5);
								}
								new BukkitRunnable() {
									  public void run() {
										  playerStuff.remove(player.getName()); // set to "false"
									  }
								}.runTaskLater(CustomEnchantments.getInstance(), 800 - 80 * level);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
								new BukkitRunnable() {
									int counter = 60 + 2 * level;
									@Override
									public void run() {
										Location locCF1 = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1D, player.getLocation().getZ() + 0D);
										player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF1, 60, 0.2, 0.2, 0.2, 0); 
										counter--;
										if(counter == 0) {
											cancel();
										}
										else if(starShine.contains(player.getName())) {
											cancel();
											starShine.remove(player.getName());
										}
									}
								}.runTaskTimer(plugin, 0L, 1L);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void CustomEnchantmentsMBleed(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					DamageCause damageCause = event.getCause();
					if(!starShine.contains(damager.getName())) {
						starShine.add(damager.getName());
						if(damageCause == DamageCause.ENTITY_ATTACK) {
							if(damager.getInventory().getItemInMainHand() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
									if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Star Shine"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Star Shine")){
											if(damager.hasPotionEffect(PotionEffectType.SPEED)) {
												int amp = damager.getPotionEffect(PotionEffectType.SPEED).getAmplifier() + 1;
												double damage = event.getDamage();
												event.setDamage(damage + (3 * amp));
												damager.removePotionEffect(PotionEffectType.SPEED);
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

