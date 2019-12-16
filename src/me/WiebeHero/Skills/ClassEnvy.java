package me.WiebeHero.Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.Enums.Classes;

public class ClassEnvy implements Listener{
	DFPlayer method = new DFPlayer();
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(method.containsPlayer(player)) {
			DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
			if(dfPlayer.getPlayerClass() == Classes.ENVY) {
				if(dfPlayer.getUseable() == true) {
					dfPlayer.setActivation(true);
					dfPlayer.setUseable(false);
					int level = dfPlayer.getLevel();
					double duration = 5 + level * 0.05;
					double cooldown = 75 - level * 0.25;
					double damage = 50 + level * 0.50;
					dfPlayer.addAtkCal(damage, 0);
					Location loc = player.getLocation();
					loc.setY(loc.getY() + 2.5);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 2.0F, 1.0F);
					player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 80, 0.15, 0.15, 0.15, 0); 
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used &6Special Attack!"));
					event.setCancelled(true);
					new BukkitRunnable() {
						public void run() {
							if(dfPlayer.getActivation() == true) {
								dfPlayer.setActivation(false);
								dfPlayer.removeAtkCal(damage, 0);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have failed to use &6Special Attack!"));
								new BukkitRunnable() {
									public void run() {
										dfPlayer.setUseable(true);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can use &6Special Attack &aagain!"));
									}
								}.runTaskLater(CustomEnchantments.getInstance(), (long)(cooldown * 20));
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), (long)(duration * 20));
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
				}
			}
		}
	}
	HashMap<UUID, Double> healthReduction = new HashMap<UUID, Double>();
	@EventHandler
	public void extraDamage(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) {
				if(event.getEntity() instanceof LivingEntity) {
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
						if(method.containsPlayer(player)) {
							DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
							if(dfPlayer.getPlayerClass() == Classes.ENVY) {
								if(dfPlayer.getActivation() == true) {
									dfPlayer.setActivation(false);
									int level = dfPlayer.getLevel();
									double cooldown = 75 - level * 0.25;
									double damage = 50 + level * 0.50;
									final Player pp = player;
									new BukkitRunnable() {
										public void run() {
											dfPlayer.removeAtkCal(damage, 0);
											new BukkitRunnable() {
												public void run() {
													dfPlayer.setUseable(true);
													pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can use &6Special Attack &aagain!"));
												}
											}.runTaskLater(CustomEnchantments.getInstance(), (long)(cooldown * 20));
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 1L);
									LivingEntity p = (LivingEntity) event.getEntity();
									DFPlayer dfPlay = new DFPlayer().getPlayer(p);
									if(dfPlayer.getAtkMod() > 0) {
										int cLevel = dfPlayer.getAtkMod();
										double weak = cLevel * 5;
										double dur = cLevel * 3;
										dfPlay.removeAtkCal(weak, (long)dur * 20);
									}
									if(dfPlayer.getRndMod() > 0) {
										int cLevel = dfPlayer.getRndMod();
										double weak = cLevel * 2.5;
										double dur = cLevel * 3;
										dfPlay.removeCrtCal(weak, (long)dur * 20);
										dfPlay.removeRndCal(weak, (long)dur * 20);
									}
									if(dfPlayer.getDfMod() > 0) {
										int cLevel = dfPlayer.getDfMod();
										double break1 = cLevel * 10;
										double dur = cLevel * 3;
										dfPlay.removeDfCal(break1, (long)dur * 20);
									}
									if(dfPlayer.getHpMod() > 0) {
										int cLevel = dfPlayer.getHpMod();
										double dis = cLevel * 7;
										double dur = cLevel * 3;
										healthReduction.put(p.getUniqueId(), dur);
										new BukkitRunnable() {
											public void run() {
												healthReduction.remove(p.getUniqueId(), dis);
											}
										}.runTaskLater(CustomEnchantments.getInstance(), (long)(dur * 20));
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
	public void recover(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) event.getEntity();
			if(healthReduction.containsKey(ent.getUniqueId())) {
				event.setAmount(event.getAmount() / 100 * (100 - healthReduction.get(ent.getUniqueId())));
			}
		}
	}
}
