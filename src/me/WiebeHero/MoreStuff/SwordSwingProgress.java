package me.WiebeHero.MoreStuff;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.minecraft.server.v1_13_R2.EntityHuman;

public class SwordSwingProgress implements Listener{
	public static ArrayList<String> names = new ArrayList<String>();
	public static HashMap<String, Float> swordSwingProgress = new HashMap<String, Float>();
	@EventHandler(priority = EventPriority.HIGHEST)
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
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
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
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
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
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
		}
	}
	@EventHandler
	public void swingProgressPickup(EntityPickupItemEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
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
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
			}
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
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
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
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
		}
	}
	public static float getSwingProgress(Player player) {
	    return player.getCooledAttackStrength(0.0F);
	}
	public float getAttackStrength(Player p) {
		float i = swordSwingProgress.get(p.getName());
		return i;
	}
}
