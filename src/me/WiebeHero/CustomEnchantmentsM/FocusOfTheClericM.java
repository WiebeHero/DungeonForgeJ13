package me.WiebeHero.CustomEnchantmentsM;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class FocusOfTheClericM implements Listener{
	@EventHandler
	public void onHealActive(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItem(event.getNewSlot());
		if(item != null && item.getType() != Material.AIR) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasLore()) {
					if(item.getItemMeta().getLore().toString().contains("Focus of the Cleric")) {
						
					}
				}
			}
		}
	}
	public void animation(Player victim) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.0D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
		}
	}
	public void healPlayer(Player player) {
		new BukkitRunnable() {
			public void run() {
			String check = "";
			for(String s1 : player.getInventory().getItemInMainHand().getItemMeta().getLore()){
				if(s1.contains(ChatColor.stripColor("Focus of the Cleric"))) {
					check = ChatColor.stripColor(s1);
				}
			}
			if(check.contains("Focus of the Cleric")){
				check = check.replaceAll("[^\\d.]", "");
				int level = Integer.parseInt(check) - 1;
					animation(player);
					
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
	}
}

