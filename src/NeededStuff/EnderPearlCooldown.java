package NeededStuff;

import org.bukkit.Bukkit;
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
		Bukkit.getServer().getConsoleSender().sendMessage("Hi1");
		if(event.getEntity() instanceof EnderPearl) {
			Bukkit.getServer().getConsoleSender().sendMessage("Hi2");
			EnderPearl ePearl = (EnderPearl) event.getEntity();
			if(ePearl.getShooter() instanceof Player) {
				Bukkit.getServer().getConsoleSender().sendMessage("Hi3");
				Player player = (Player) ePearl.getShooter();
				new BukkitRunnable() {
					@Override
					public void run() {
						player.setCooldown(Material.ENDER_PEARL, 160);
						player.sendMessage(player.getCooldown(Material.ENDER_PEARL) + "");
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 1L);
			}
		}
	}
}
