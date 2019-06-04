package me.WiebeHero.CustomEnchantmentsM;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class LargeFireball implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
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
							if(s1.contains(ChatColor.stripColor("Large Fireball"))) {
								check = ChatColor.stripColor(s1);
							}
						}
						if(check.contains("Large Fireball")){
							if(!playerStuff.contains(player.getName())) {
								check = check.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(check) - 1;
								playerStuff.add(player.getName()); // set to "true"
								player.launchProjectile(Fireball.class);
								animation(player);
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									  public void run() {
										  playerStuff.remove(player.getName()); // set to "false"
									  }
								}, 800 - 100 * level);
							}
						}
					}
				}
			}
		}
	}
	public void animation(Player player) {
		Location locCF = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1.7D, player.getLocation().getZ() + 0D);
		player.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
		}
	}
	@EventHandler
	public void LargeFireballBall(ProjectileHitEvent event){
        if(event.getEntity() instanceof Fireball){
            Fireball f = (Fireball) event.getEntity();
            LivingEntity victim = (LivingEntity) event.getHitEntity();
            Block hitBlock = (Block) event.getHitBlock();
            if(f.getShooter() instanceof Player){
                Player shooter = (Player) f.getShooter();
                if(shooter.getInventory().getItemInMainHand() != null) {
					if(shooter.getInventory().getItemInMainHand().hasItemMeta()) {
		                if(shooter.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
		                	String check = "";
							for(String s1 : shooter.getInventory().getItemInMainHand().getItemMeta().getLore()){
								if(s1.contains(ChatColor.stripColor("Large Fireball"))) {
									check = ChatColor.stripColor(s1);
								}
							}
		                	if(check.contains("Large Fireball")){
		                		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
								victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 60, 2, 2, 2, 1); 
								for(Player victim1 : Bukkit.getOnlinePlayers()) {
									((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1.25);
								}
								check = check.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(check) - 1;
								double range = 4 + 1 * level;
								if(victim instanceof LivingEntity) {
									if(victim != null) {
										for(Entity en : victim.getNearbyEntities(range, range, range)){
											LivingEntity victimsNearby = (LivingEntity) en;
											if(en == victim) {
												victim.damage(10 + 3 * level);
											}
											else {
												victimsNearby.damage(5 + 1.5 * level);	
											}
										}
									}
								}
								else if(hitBlock instanceof Block) {
									if(hitBlock != null) {
										for(Entity en : hitBlock.getWorld().getNearbyEntities(locCF, range, range, range)){
											if(en instanceof LivingEntity) {
												LivingEntity victimsNearby = (LivingEntity) en;
												victimsNearby.damage(5 + 1.5 * level);
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
