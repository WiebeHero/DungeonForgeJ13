package me.WiebeHero.MoreStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFPlayerSkillChangeEvent;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;

public class Disparitys implements Listener{
	private DFPlayerManager dfManager;
	public static HashMap<UUID, BossBar> disparityList = new HashMap<UUID, BossBar>();
	private HashMap<UUID, BossBar> iconBar = new HashMap<UUID, BossBar>();
	private HashMap<UUID, BossBar> skillBar = new HashMap<UUID, BossBar>();
	private HashMap<UUID, BossBar> placeHolder = new HashMap<UUID, BossBar>();
	public static HashMap<UUID, Integer> listPlayers = new HashMap<UUID, Integer>();
	public static ArrayList<String> names = new ArrayList<String>();
	public Disparitys(DFPlayerManager dfManager) {
		this.dfManager = dfManager;
		this.iconBar = new HashMap<UUID, BossBar>();
		this.skillBar = new HashMap<UUID, BossBar>();
		this.placeHolder = new HashMap<UUID, BossBar>();
	}
	@EventHandler
	public void onWalk(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		BossBar bar = disparityList.get(player.getUniqueId());
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
			if(player.getLocation().getZ() <= 772 && player.getLocation().getZ() >= -1023) {
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
							listPlayers.put(player.getUniqueId(), disparity);
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
		if(!disparityList.containsKey(player.getUniqueId())) {
			BossBar bar = Bukkit.getServer().createBossBar("Disparity: 1", BarColor.GREEN, BarStyle.SOLID);
			bar.addPlayer(player);
			bar.setVisible(false);
			disparityList.put(player.getUniqueId(), bar);
		}
	}
	@EventHandler
	public void disparityBarRegister(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(disparityList.containsKey(player.getUniqueId())) {
			disparityList.remove(player.getUniqueId());
		}
	}
	
	@EventHandler
	public void updateBossBar(DFPlayerSkillChangeEvent event) {
		DFPlayer dfPlayer = event.getDFPlayer();
		if(dfPlayer != null) {
			UUID uuid = dfPlayer.getUUID();
			if(this.placeHolder.containsKey(uuid) && this.iconBar.containsKey(uuid) && this.skillBar.containsKey(uuid)) {
				BossBar bar = this.skillBar.get(uuid);
				new BukkitRunnable() {
					public void run() {
						ArrayList<Integer> skills = dfPlayer.getSkillList();
						String lines = new CCT().colorize("&6");
						for(int i = 0; i < skills.size(); i++) {
							String length = skills.get(i) + "";
							String s = "";
							if(length.length() == 1) {
								s += "00";
							}
							else if(length.length() == 2) {
								s += "0";
							}
							s += skills.get(i);
							lines += s;
							if(i != 5) {
								lines += "\uF803    ";
							}
						}
						bar.setTitle(lines);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 1L);
			}
		}
	}
	
	@EventHandler
	public void resourcePackRequest(PlayerResourcePackStatusEvent event) {
		Player player = event.getPlayer();
		if(event.getStatus() == Status.SUCCESSFULLY_LOADED) {
			UUID uuid = player.getUniqueId();
			DFPlayer dfPlayer = this.dfManager.getEntity(uuid);
			if(!this.placeHolder.containsKey(uuid) && !this.iconBar.containsKey(uuid) && !this.skillBar.containsKey(uuid)) {
				BossBar bar = disparityList.get(uuid);
				bar.removePlayer(player);
				BossBar bar1 = Bukkit.getServer().createBossBar("", BarColor.WHITE, BarStyle.SOLID);
				bar1.setProgress(0.00);
				bar1.addPlayer(player);
				bar1.setVisible(true);
				BossBar bar2 = Bukkit.getServer().createBossBar(new CCT().colorize("\uE001&6-&f\uE002&6-&f\uE003&6-&f\uE004&6-&f\uE005&6-&f\uE006"), BarColor.WHITE, BarStyle.SOLID);
				bar2.setProgress(0.00);
				bar2.addPlayer(player);
				bar2.setVisible(true);
				ArrayList<Integer> skills = dfPlayer.getSkillList();
				String lines = new CCT().colorize("&6");
				for(int i = 0; i < skills.size(); i++) {
					String length = skills.get(i) + "";
					String s = "";
					if(length.length() == 1) {
						s += "00";
					}
					else if(length.length() == 2) {
						s += "0";
					}
					s += skills.get(i);
					lines += s;
					if(i != 5) {
						lines += "\uF803    ";
					}
				}
				BossBar bar3 = Bukkit.getServer().createBossBar(lines, BarColor.WHITE, BarStyle.SOLID);
				bar3.setProgress(0.00);
				bar3.addPlayer(player);
				bar3.setVisible(true);
				bar.addPlayer(player);
				this.placeHolder.put(uuid, bar1);
				this.iconBar.put(uuid, bar2);
				this.skillBar.put(uuid, bar3);
			}
		}
		else if(event.getStatus() == Status.FAILED_DOWNLOAD){
			UUID uuid = player.getUniqueId();
			if(this.placeHolder.containsKey(uuid) && this.iconBar.containsKey(uuid) && this.skillBar.containsKey(uuid)) {
				BossBar bar1 = this.placeHolder.get(uuid);
				BossBar bar2 = this.iconBar.get(uuid);
				BossBar bar3 = this.skillBar.get(uuid);
				bar1.removePlayer(player);
				bar2.removePlayer(player);
				bar3.removePlayer(player);
				this.placeHolder.remove(uuid);
				this.iconBar.remove(uuid);
				this.skillBar.remove(uuid);
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void disparityAttack(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) event.getDamager();
					Player victim = (Player) event.getEntity();
					if(damager.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
						if(dfManager.contains(victim) && dfManager.contains(damager)) {
							DFPlayer dfPlayerD = dfManager.getEntity(damager), dfPlayerV = dfManager.getEntity(victim);
							int levelD = dfPlayerD.getLevel(), levelV = dfPlayerV.getLevel();
							int disparity = 0;
							if(listPlayers.containsKey(victim.getUniqueId())) {
								disparity = listPlayers.get(victim.getUniqueId());
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
	@EventHandler(priority = EventPriority.LOWEST)
	public void disparityBowAttack(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Projectile) {
				if(event.getEntity() instanceof Player) {
					Projectile damager = (Projectile) event.getDamager();
					Player victim = (Player) event.getEntity();
					if(damager.getShooter() instanceof Player) {
						Player shooter = (Player) damager.getShooter();
						if(shooter.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
							if(dfManager.contains(victim) && dfManager.contains(shooter)) {
								DFPlayer dfPlayerD = dfManager.getEntity(shooter), dfPlayerV = dfManager.getEntity(victim);
								int levelD = dfPlayerD.getLevel(), levelV = dfPlayerV.getLevel();
								int disparity = 0;
								if(listPlayers.containsKey(victim.getUniqueId())) {
									disparity = listPlayers.get(victim.getUniqueId());
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
	@EventHandler(priority = EventPriority.LOWEST)
	public void disparityPotionSplash(PotionSplashEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity().getShooter() instanceof Player) {
				for (LivingEntity livingEntities : event.getAffectedEntities()) {
					if (livingEntities instanceof Player) {
						Player damager = (Player) event.getEntity().getShooter();
						Player victim = (Player) livingEntities;
						if(damager.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
							if(dfManager.contains(victim) && dfManager.contains(damager)) {
								DFPlayer dfPlayerD = dfManager.getEntity(damager), dfPlayerV = dfManager.getEntity(victim);
								int levelD = dfPlayerD.getLevel(), levelV = dfPlayerV.getLevel();
								int disparity = 0;
								if(listPlayers.containsKey(victim.getUniqueId())) {
									disparity = listPlayers.get(victim.getUniqueId());
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
		double disparity = (double)calc / 1795.00 * 100.00 + 1.0;
		return (int)disparity;
	}
}
