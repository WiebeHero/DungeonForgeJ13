package me.WiebeHero.Skills;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;

public class ClassGreed implements Listener{
	private DFPlayerManager dfManager;
	public ClassGreed(DFPlayerManager manager) {
		this.dfManager = manager;
	}
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(dfPlayer.getPlayerClass() == Classes.GREED) {
				if(dfPlayer.getUseable() == true) {
					dfPlayer.setUseable(false);
					dfPlayer.setActivation(true);
					int level = dfPlayer.getLevel();
					long cooldown = 1700 - level * 6;
					double attackS = 20 + level * 0.30;
					long duration = 130 + level;
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Hunting Arrow!"));
					Location loc = player.getLocation();
					loc.setY(loc.getY() + 2.5);
					BlockData bd = Material.COAL_BLOCK.createBlockData();
					player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
					dfPlayer.addSpdCal(attackS, duration);
					event.setCancelled(true);
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
	public void shootGreedArrow(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity) {
				if(event.getDamager() instanceof Arrow) {
					LivingEntity p = (LivingEntity) event.getEntity();
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						Player shooter = (Player) arrow.getShooter();
						if(dfManager.contains(shooter)) {
							DFPlayer dfShooter = dfManager.getEntity(shooter);
							if(dfShooter.getPlayerClass() == Classes.GREED) {
								if(dfShooter.getActivation() == true) {
									double decrease = 50 + dfShooter.getLevel() * 0.50;
									long duration = 130 + dfShooter.getLevel();
									if(dfManager.contains(p)) {
										DFPlayer victim = dfManager.getEntity(p);
										victim.removeDfCal(decrease, duration);
										if(dfShooter.getAtkMod() > 0) {
											int cLevel = dfShooter.getAtkMod();
											double dec = cLevel * 15;
											victim.removeAtkCal(dec, duration);
										}
										if(dfShooter.getSpdMod() > 0) {
											int cLevel = dfShooter.getSpdMod();
											float inc = cLevel * 0.08F;
											dfShooter.addMove(inc, duration);
										}
										if(dfShooter.getCrtMod() > 0) {
											int cLevel = dfShooter.getCrtMod();
											float inc = cLevel * 0.08F;
											victim.removeMove(inc, duration);
											victim.returnPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int) duration, 1));
										}
										dfShooter.setActivation(false);
									}
								} 
								if(dfShooter.getRndMod() > 0) {
									long duration = 130 + dfShooter.getLevel();
									int cLevel = dfShooter.getLevel();
									double inc = cLevel * 1;
									float speed = cLevel * 0.001F;
									dfShooter.addRndCal(inc, duration);
									dfShooter.addMove(speed, duration);
								}
							}
						}
					}
				}
			}
		}
	}
}
