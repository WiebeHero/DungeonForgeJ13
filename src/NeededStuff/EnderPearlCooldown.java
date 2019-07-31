package NeededStuff;

import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class EnderPearlCooldown implements Listener{
	@EventHandler
	public void enderPearlCooldown(ProjectileLaunchEvent event) {
		if(event.getEntity() instanceof EnderPearl) {
			EnderPearl ePearl = (EnderPearl) event.getEntity();
			if(ePearl.getShooter() instanceof Player) {
				Player player = (Player) ePearl.getShooter();
				new BukkitRunnable() {
					@Override
					public void run() {
						player.setCooldown(Material.ENDER_PEARL, 160);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 1L);
			}
		}
	}
}
