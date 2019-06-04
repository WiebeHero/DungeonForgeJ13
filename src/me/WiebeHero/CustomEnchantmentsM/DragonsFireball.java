package me.WiebeHero.CustomEnchantmentsM;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class DragonsFireball implements Listener{
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
							if(s1.contains(ChatColor.stripColor("Dragons Fireball"))) {
								check = ChatColor.stripColor(s1);
							}
						}
						if(check.contains("Dragons Fireball")){
							if(!playerStuff.contains(player.getName())) {
								playerStuff.add(player.getName()); // set to "true"
								check = check.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(check) - 1;
								player.launchProjectile(DragonFireball.class);
								animation(player);
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									  public void run() {
										  playerStuff.remove(player.getName()); // set to "false"
									  }
								}, 800 - 50 * level);
							}
						}
					}
				}
			}
		}
	}
	public void animation(Player player) {
		Location locCF = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1.7D, player.getLocation().getZ() + 0D);
		player.getWorld().spawnParticle(Particle.DRAGON_BREATH, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
		}
	}
}
