package NeededStuff;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class AFKSystem implements Listener{
	public HashMap<UUID, Location> locationList = new HashMap<UUID, Location>();
	public HashMap<UUID, Vector> directionList = new HashMap<UUID, Vector>();
	@EventHandler
	public void activateAFK(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		locationList.put(player.getUniqueId(), player.getLocation());
		directionList.put(player.getUniqueId(), player.getLocation().getDirection());
		if(player.hasPermission("bronze")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
					locationList.put(player.getUniqueId(), player.getLocation());
					directionList.put(player.getUniqueId(), player.getLocation().getDirection());
				}
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 36000L);
		}
		else if(player.hasPermission("silver")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
					locationList.put(player.getUniqueId(), player.getLocation());
					directionList.put(player.getUniqueId(), player.getLocation().getDirection());
				}
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 54000L);
		}
		else if(player.hasPermission("gold")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
					locationList.put(player.getUniqueId(), player.getLocation());
					directionList.put(player.getUniqueId(), player.getLocation().getDirection());
				}
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 72000L);
		}
		else if(player.hasPermission("platinum")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
					locationList.put(player.getUniqueId(), player.getLocation());
					directionList.put(player.getUniqueId(), player.getLocation().getDirection());
				}
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 90000L);
		}
		else if(player.hasPermission("diamond")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
					locationList.put(player.getUniqueId(), player.getLocation());
					directionList.put(player.getUniqueId(), player.getLocation().getDirection());
				}
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 108000L);
		}
		else if(player.hasPermission("emerald")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
					locationList.put(player.getUniqueId(), player.getLocation());
					directionList.put(player.getUniqueId(), player.getLocation().getDirection());
				}
			}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 144000L);
		}
		else {
			if(!player.hasPermission("owner") || !player.hasPermission("manager") || !player.hasPermission("head admin") || !player.hasPermission("admin")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						afkKickCheck(player, locationList.get(player.getUniqueId()), directionList.get(player.getUniqueId()));
						locationList.put(player.getUniqueId(), player.getLocation());
						directionList.put(player.getUniqueId(), player.getLocation().getDirection());
					}
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 18000L);
			}
		}
	}
	public void afkKickCheck(Player player, Location locCheck, Vector vecCheck) {
		Location locNow = player.getLocation();
		Vector vecNow = player.getLocation().getDirection();
		if(locNow == locCheck || vecNow == vecCheck) {
			player.kickPlayer(new ColorCodeTranslator().colorize("&4&lYou have been AFK for to long! This resulted in a kick."));
		}
	}
}
