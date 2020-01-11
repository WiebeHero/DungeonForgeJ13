package me.WiebeHero.MoreStuff;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.DFPlayer;

public class Disparitys implements Listener{
	DFPlayer method = new DFPlayer();
	public static HashMap<String, BossBar> disparityList = new HashMap<String, BossBar>();
	public static HashMap<String, Integer> listPlayers = new HashMap<String, Integer>();
	public static ArrayList<String> names = new ArrayList<String>();
	@EventHandler
	public void onWalk(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		BossBar bar = disparityList.get(player.getName());
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
			if(player.getLocation().getZ() <= 773 && player.getLocation().getZ() >= -1022) {
				if(!names.contains(player.getName())) {
					names.add(player.getName());
					if(bar != null) {
						if(!bar.getPlayers().contains(player)) {
							bar.addPlayer(player);
						}
						int disparity = this.calculateDisparity(player.getLocation());
						if(bar != null) {
							bar.setVisible(true);
							double d = (double) disparity / 100;
							bar.setProgress(d);
							if(disparity >= 0 && disparity < 33) {
								bar.setColor(BarColor.GREEN);
								bar.setTitle(new CCT().colorize("&7Disparity: &a" + disparity));
							}
							else if(disparity >= 33 && disparity < 66) {
								bar.setColor(BarColor.YELLOW);
								bar.setTitle(new CCT().colorize("&7Disparity: &e" + disparity));
							}
							else if(disparity >= 66 && disparity <= 100) {
								bar.setColor(BarColor.RED);
								bar.setTitle(new CCT().colorize("&7Disparity: &c" + disparity));
							}
							listPlayers.put(player.getName(), disparity);
						}
						new BukkitRunnable() {
							public void run(){
								names.remove(player.getName());
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 10L);
						
					}		
				}
			}
			else {
				if(bar != null) {
					if(bar.getPlayers().contains(player)) {
						bar.removePlayer(player);
					}
				}
			}
		}
		else {
			if(bar != null) {
				if(bar.getPlayers().contains(player)) {
					bar.removePlayer(player);
				}
			}
		}
	}
	@EventHandler
	public void disparityBarRegister(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!disparityList.containsKey(player.getName())) {
			BossBar bar = Bukkit.getServer().createBossBar("Disparity: 1", BarColor.GREEN, BarStyle.SOLID);
			bar.addPlayer(player);
			bar.setVisible(false);
			disparityList.put(player.getName(), bar);
		}
	}
	@EventHandler
	public void disparityBarRegister(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(disparityList.containsKey(player.getName())) {
			disparityList.remove(player.getName());
		}
	}
	@EventHandler
	public void disparityAttack(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) event.getDamager();
					Player victim = (Player) event.getEntity();
					if(damager.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
						if(method.containsPlayer(victim) && method.containsPlayer(damager)) {
							DFPlayer dfPlayerD = new DFPlayer().getPlayer(damager);
							DFPlayer dfPlayerV = new DFPlayer().getPlayer(victim);
							int levelD = dfPlayerD.getLevel();
							int levelV = dfPlayerV.getLevel();
							int disparity = 0;
							if(listPlayers.containsKey(victim.getName())) {
								disparity = listPlayers.get(victim.getName());
							}
							int lowerL = levelV - levelD;
							int higherL = levelD - levelV;
							if(Math.abs(lowerL) > disparity) {
								damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't damage this person here, they are too low level."));
								event.setCancelled(true);
							}
							else if(higherL > disparity) {
								damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't damage this person here, they are too low level."));
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void disparityBowAttack(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Projectile) {
				if(event.getEntity() instanceof Player) {
					Projectile damager = (Projectile) event.getDamager();
					Player victim = (Player) event.getEntity();
					if(damager.getShooter() instanceof Player) {
						Player shooter = (Player) damager.getShooter();
						if(shooter.getWorld().getName().equalsIgnoreCase("dfwn-1")) {
							if(method.containsPlayer(victim) && method.containsPlayer(shooter)) {
								DFPlayer dfPlayerD = new DFPlayer().getPlayer(shooter);
								DFPlayer dfPlayerV = new DFPlayer().getPlayer(victim);
								int levelD = dfPlayerD.getLevel();
								int levelV = dfPlayerV.getLevel();
								int disparity = 0;
								if(listPlayers.containsKey(victim.getName())) {
									disparity = listPlayers.get(victim.getName());
								}
								int lowerL = levelV - levelD;
								int higherL = levelD - levelV;
								if(Math.abs(lowerL) > disparity) {
									shooter.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't damage this person here, they are too low level."));
									event.setCancelled(true);
								}
								else if(higherL > disparity) {
									shooter.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't damage this person here, they are too low level."));
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void disparityPotionSplash(PotionSplashEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity().getShooter() instanceof Player) {
				for (LivingEntity livingEntities : event.getAffectedEntities()) {
					if (livingEntities instanceof Player) {
						Player damager = (Player) event.getEntity().getShooter();
						Player victim = (Player) livingEntities;
						if(damager.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
							if(method.containsPlayer(victim) && method.containsPlayer(damager)) {
								DFPlayer dfPlayerD = new DFPlayer().getPlayer(damager);
								DFPlayer dfPlayerV = new DFPlayer().getPlayer(victim);
								int levelD = dfPlayerD.getLevel();
								int levelV = dfPlayerV.getLevel();
								int disparity = 0;
								if(listPlayers.containsKey(victim.getName())) {
									disparity = listPlayers.get(victim.getName());
								}
								int lowerL = levelV - levelD;
								int higherL = levelD - levelV;
								if(Math.abs(lowerL) > disparity) {
									damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't damage this person here, they are too low level."));
									event.setCancelled(true);
								}
								else if(higherL > disparity) {
									damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't damage this person here, they are too low level."));
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}
	public int calculateDisparity(Location loc) {
		int min = -1023;
		int calc = 0;
		if(loc.getZ() >= 0 && loc.getZ() <= 772) {
			calc = Math.abs((int)loc.getZ() - 772);
		}
		else {
			calc = min + 1023 + 772 + (int)Math.abs(loc.getZ());
		}
		double disparity = (double)calc / 1796.00 * 100.00 + 1.0;
		return (int)disparity;
	}
}
