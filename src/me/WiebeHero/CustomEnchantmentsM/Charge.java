package me.WiebeHero.CustomEnchantmentsM;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Charge implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	boolean fireball = true;
	Set<String> playerStuff = new HashSet<String>();
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR){
			Player player = event.getPlayer();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						String check = "";
						for(String s1 : player.getInventory().getItemInMainHand().getItemMeta().getLore()){
							if(s1.contains(ChatColor.stripColor("Charge"))) {
								check = ChatColor.stripColor(s1);
							}
						}
						if(check.contains("Charge")){
							check = check.replaceAll("[^\\d.]", "");
							int level = Integer.parseInt(check) - 1;
							if(!playerStuff.contains(player.getName())) {
								playerStuff.add(player.getName()); // set to "true"
								animation(player);
								int amp = (int)Math.floor(0 + (level) / 2);
								int durationAdd = 100 + 33 * level;
								PotionEffectType type = PotionEffectType.SPEED;
								PotionEffectType type1 = PotionEffectType.INCREASE_DAMAGE;
								if(player.hasPotionEffect(type) && player.hasPotionEffect(type1) && player.getPotionEffect(type).getAmplifier() == amp && player.getPotionEffect(type1).getAmplifier() == amp) {
									int durationNow = player.getPotionEffect(type).getDuration();
									player.removePotionEffect(type);
									player.removePotionEffect(type1);
									player.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
									player.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
								}
								else {
									player.removePotionEffect(type);
									player.removePotionEffect(type1);
									player.addPotionEffect(new PotionEffect(type, durationAdd, amp));
									player.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
								}
								new BukkitRunnable() {
									public void run() {
										playerStuff.remove(player.getName());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 800 - 120 * level);
							}
						}
					}
				}
			}
		}
	}
	public void animation(Entity entity) {
		Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.7D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.2, 0.2, 0.2, 0); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, (float) 1.5);
		
	}
}
