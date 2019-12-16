package me.WiebeHero.MoreStuff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.DFPlayer;

public class Disparitys implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public static HashMap<String, BossBar> disparityList = new HashMap<String, BossBar>();
	public static HashMap<String, Integer> listPlayers = new HashMap<String, Integer>();
	public static ArrayList<String> names = new ArrayList<String>();
	@EventHandler
	public void onWalk(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		BossBar bar = disparityList.get(player.getName());
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
			if(player.getLocation().getZ() <= 772 && player.getLocation().getZ() >= -1022) {
				if(!names.contains(player.getName())) {				
					names.add(player.getName());
					if(bar != null) {
						if(!bar.getPlayers().contains(player)) {
							bar.addPlayer(player);
						}
					}
					new BukkitRunnable() {
						@Override
						public void run() {
							double zPlayer = player.getLocation().getZ();
							int disparity = 0;
							for(int i = 1; i <= 100; i++) {
								if(zPlayer > 772 - i * 18) {
									disparity = i;
									break;
								}
							}
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
							names.remove(player.getName());
						}
					}.runTaskLater(plugin, 20L);
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
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Player) {
				File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
				try{
					yml.load(f);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        } 
				catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
				Player damager = (Player) event.getDamager();
				if(damager.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
					Player victim = (Player) event.getEntity();
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
						damager.sendMessage(new CCT().colorize("&cYou can't damage this person here, they are too low level."));
						event.setCancelled(true);
					}
					else if(higherL > disparity) {
						damager.sendMessage(new CCT().colorize("&cYou can't damage this person here, they are too low level."));
						event.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler
	public void disparityBowAttack(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Projectile) {
			if(event.getEntity() instanceof Player) {
				Projectile damager = (Projectile) event.getDamager();
				Player victim = (Player) event.getEntity();
				if(damager.getShooter() instanceof Player) {
					Player shooter = (Player) damager.getShooter();
					File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
					YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
					try{
						yml.load(f);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        } 
					catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
					if(shooter.getWorld().getName().equalsIgnoreCase("dfwn-1")) {
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
							shooter.sendMessage(new CCT().colorize("&cYou can't damage this person here, they are too low level."));
							event.setCancelled(true);
						}
						else if(higherL > disparity) {
							shooter.sendMessage(new CCT().colorize("&cYou can't damage this person here, they are too low level."));
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void disparityPotionSplash(PotionSplashEvent event) {
		if(event.getEntity().getShooter() instanceof Player) {
			for (LivingEntity livingEntities : event.getAffectedEntities()) {
				if (livingEntities instanceof Player) {
					Player damager = (Player) event.getEntity().getShooter();
					Player victim = (Player) livingEntities;
					File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
					YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
					try{
						yml.load(f);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        } 
					catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
					if(damager.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
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
							damager.sendMessage(new CCT().colorize("&cYou can't damage this person here, they are too low level."));
							event.setCancelled(true);
						}
						else if(higherL > disparity) {
							damager.sendMessage(new CCT().colorize("&cYou can't damage this person here, they are too low level."));
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
