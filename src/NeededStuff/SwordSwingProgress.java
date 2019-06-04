package NeededStuff;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class SwordSwingProgress implements Listener{
	public static ArrayList<String> names = new ArrayList<String>();
	public static HashMap<String, Float> swordSwingProgress = new HashMap<String, Float>();
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void swingProgressDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			Player player = (Player) event.getDamager();
			if(!(names.contains(player.getName()))) {
				names.add(player.getName());
				new BukkitRunnable() {
					@Override
					public void run() {
						if(swordSwingProgress.get(player.getName()) == null) {
							swordSwingProgress.put(player.getName(), (float) 0.0);
						}
						swordSwingProgress.put(player.getName(), getSwingProgress(player));
						if(swordSwingProgress.get(player.getName()) == 1) {
							cancel();
							names.remove(player.getName());
						}
					}
				}.runTaskTimer(plugin, 0L, 2L);
			}
		}
	}
	@EventHandler
	public void swingProgressSwitch(PlayerItemHeldEvent event) {
		Player player = (Player) event.getPlayer();
		if(!(names.contains(player.getName()))) {
			names.add(player.getName());
			new BukkitRunnable() {
				@Override
				public void run() {
					if(swordSwingProgress.get(player.getName()) == null) {
						swordSwingProgress.put(player.getName(), (float) 0.0);
					}
					swordSwingProgress.put(player.getName(), getSwingProgress(player));
					if(swordSwingProgress.get(player.getName()) == 1) {
						cancel();
						names.remove(player.getName());
					}
				}
			}.runTaskTimer(plugin, 0L, 2L);
		}
	}
	@EventHandler
	public void swingProgressDrop(PlayerDropItemEvent event) {
		Player player = (Player) event.getPlayer();
		if(!(names.contains(player.getName()))) {
			names.add(player.getName());
			new BukkitRunnable() {
				@Override
				public void run() {
					if(swordSwingProgress.get(player.getName()) == null) {
						swordSwingProgress.put(player.getName(), (float) 0.0);
					}
					swordSwingProgress.put(player.getName(), getSwingProgress(player));
					if(swordSwingProgress.get(player.getName()) == 1) {
						cancel();
						names.remove(player.getName());
					}
				}
			}.runTaskTimer(plugin, 0L, 2L);
		}
	}
	@EventHandler
	public void swingProgressPickup(@SuppressWarnings("deprecation") PlayerPickupItemEvent event) {
		Player player = (Player) event.getPlayer();
		if(!(names.contains(player.getName()))) {
			names.add(player.getName());
			new BukkitRunnable() {
				@Override
				public void run() {
					if(swordSwingProgress.get(player.getName()) == null) {
						swordSwingProgress.put(player.getName(), (float) 0.0);
					}
					swordSwingProgress.put(player.getName(), getSwingProgress(player));
					if(swordSwingProgress.get(player.getName()) == 1) {
						cancel();
						names.remove(player.getName());
					}
				}
			}.runTaskTimer(plugin, 0L, 2L);
		}
	}
	@EventHandler
	public void switchProgressMiss(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(!(names.contains(player.getName()))) {
				names.add(player.getName());
				new BukkitRunnable() {
					@Override
					public void run() {
						if(swordSwingProgress.get(player.getName()) == null) {
							swordSwingProgress.put(player.getName(), (float) 0.0);
						}
						swordSwingProgress.put(player.getName(), getSwingProgress(player));
						if(swordSwingProgress.get(player.getName()) == 1) {
							cancel();
							names.remove(player.getName());
						}
					}
				}.runTaskTimer(plugin, 0L, 2L);
			}
		}
	}
	@EventHandler
	public void joinEvent(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();
		if(!(names.contains(player.getName()))) {
			names.add(player.getName());
			new BukkitRunnable() {
				@Override
				public void run() {
					if(swordSwingProgress.get(player.getName()) == null) {
						swordSwingProgress.put(player.getName(), (float) 0.0);
					}
					swordSwingProgress.put(player.getName(), getSwingProgress(player));
					if(swordSwingProgress.get(player.getName()) == 1) {
						cancel();
						names.remove(player.getName());
					}
				}
			}.runTaskTimer(plugin, 0L, 2L);
		}
	}
	public final float getSwingProgress(Player player) {
	    return player.getCooledAttackStrength(0);
	}
}
