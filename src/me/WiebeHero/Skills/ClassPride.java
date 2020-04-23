package me.WiebeHero.Skills;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;

public class ClassPride implements Listener{
	private DFPlayerManager dfManager;
	public ClassPride(DFPlayerManager manager) {
		this.dfManager = manager;
	}
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(dfPlayer.getPlayerClass() == Classes.PRIDE) {
				event.setCancelled(true);
				if(dfPlayer.getUseable()) {
					dfPlayer.setUseable(false);
					dfPlayer.setActivation(true);
					int level = dfPlayer.getLevel();
					long duration = 200 + level * 1;
					long cooldown = 1800 - level * 6;
					double attackS = 20 + level * 0.30;
					double defense = 30 + level * 0.50;
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_DEATH, 2.0F, 2.0F);
					Location loc = player.getLocation();
					loc.setY(loc.getY() - 1.5);
					BlockData bd = Material.COBWEB.createBlockData();
					player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used &6Quick Attack!"));
					if(dfPlayer.getSpdMod() > 0) {
						int cLevel = dfPlayer.getSpdMod();
						double inc = cLevel * 10;
						dfPlayer.addSpdCal(attackS + inc, duration);
					}
					else {
						dfPlayer.addSpdCal(attackS, duration);
					}
					if(dfPlayer.getDfMod() > 0) {
						int cLevel = dfPlayer.getDfMod();
						double inc = cLevel * 10;
						dfPlayer.addDfCal(defense + inc, duration);
					}
					else {
						dfPlayer.addDfCal(defense, duration);
					}
					new BukkitRunnable() {
						public void run() {
							dfPlayer.setActivation(false);
							new BukkitRunnable() {
								public void run() {
									dfPlayer.setUseable(true);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), duration);
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
				}
			}
		}
	}
	@EventHandler
	public void terminator(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity) {
				if(event.getDamager() instanceof Arrow || event.getDamager() instanceof Player) {
					Player player = null;
					if(event.getDamager() instanceof Player) {
						player = (Player)event.getDamager();
					}
					else if(event.getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getDamager();
						if(arrow.getShooter() instanceof Player) {
							player = (Player) arrow.getShooter();
						}
					}
					if(player != null) {
						if(dfManager.contains(player)) {
							DFPlayer dfPlayer = dfManager.getEntity(player);
							if(dfPlayer != null) {
								if(dfPlayer.getPlayerClass() == Classes.PRIDE) {
									int level = dfPlayer.getLevel();
									long duration = 200 + level * 1;
									if(dfPlayer.getAtkMod() > 0) {
										int cLevel = dfPlayer.getAtkMod();
										double inc = cLevel * 1.5;
										dfPlayer.addAtkCal(inc, duration);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void deflection(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity) {
				if(event.getDamager() instanceof Arrow) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						Player player = (Player) arrow.getShooter();
						if(dfManager.contains(victim) && dfManager.contains(player)) {
							DFPlayer dfPlayer = dfManager.getEntity(player), dfVictim = dfManager.getEntity(victim);
							if(dfVictim.getPlayerClass() != null) {
								if(dfVictim.getPlayerClass() == Classes.PRIDE) {
									if(dfVictim.getRndMod() > 0) {
										int cLevel = dfVictim.getRndMod();
										double inc = cLevel * 10;
										dfPlayer.removeRndCal(inc, 1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
