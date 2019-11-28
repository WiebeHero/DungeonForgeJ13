package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.Enums.Classes;

public class ClassSloth implements Listener{
	public ArrayList<UUID> arrowList = new ArrayList<UUID>();
	public EffectSkills sk = new EffectSkills();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		DFPlayer method = new DFPlayer();
		if(method.containsPlayer(player)) {
			DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
			if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
				if(dfPlayer.getUseable()) {
					dfPlayer.setUseable(false);
					dfPlayer.setActivation(true);
					Location loc = player.getEyeLocation();
					Vector vec = loc.getDirection();
					vec.setY(0.00);
					loc.add(vec.getX() - (vec.getX()* 2.0),0,vec.getZ() - (vec.getZ() * 2.0));
					int level = dfPlayer.getLevel();
					double arrowA = 10 + level * 0.25;
					long duration = 100 + level * 1;
					long cooldown = 1500 - level * 4;
					double damage = 3 + level * 0.05;
					double defense = 30 + level * 0.20;
					dfPlayer.addDfCal(defense, duration);
					if(dfPlayer.getAtkMod() > 0) {
						int cLevel = dfPlayer.getLevel();
						double inc = cLevel * 0.4;
						dfPlayer.addAtkCal(inc, duration);
					}
					event.setCancelled(true);
					for(int i = 0; i <= (int)arrowA; i++) {
						Arrow arrow = player.getWorld().spawnArrow(loc, rotateVectorAroundY(vec, 180), (float)(1.50 * 0.20), 26);
						arrow.setShooter(player);
						arrow.setCritical(true);
						EffectSkills.arrowList.put(arrow.getUniqueId(), 1.0F);
						EffectSkills.arrowDamage.put(arrow.getUniqueId(), damage);
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
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
				}
			}
		}
	}
	@EventHandler
	public void arrowHit(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					DFPlayer method = new DFPlayer();
					if(method.containsPlayer(player)) {
						if(arrowList.contains(arrow.getUniqueId())) {
							DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
							if(dfPlayer.getHp() > 0) {
								int cLevel = dfPlayer.getHp();
								double inc = cLevel * 0.5;
								if(dfPlayer.getHP() + inc <= dfPlayer.getMaxHp()) {
									dfPlayer.returnPlayer().setHealth(dfPlayer.getHP() + inc);
								}
								else {
									dfPlayer.returnPlayer().setHealth(dfPlayer.getMaxHp());
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void turnTable(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				DFPlayer method = new DFPlayer();
				if(method.containsPlayer(player)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
					if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
						if(dfPlayer.getCrtMod() > 0) {
							if(dfPlayer.getHP() >= dfPlayer.getMaxHp() * 0.50) {
								dfPlayer.addCrtCal(25, 1);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void fullIt(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				DFPlayer method = new DFPlayer();
				if(method.containsPlayer(player)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
					if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
						if(dfPlayer.getCrtMod() > 0) {
							if(dfPlayer.getHP() <= dfPlayer.getMaxHp() * 0.50) {
								dfPlayer.addDfCal(25, 1);
							}
						}
					}
				}
			}
		}
	}
	public static Vector rotateVectorAroundY(Vector vector, double degrees) {
	    double rad = Math.toRadians(degrees);
	   
	    double currentX = vector.getX();
	    double currentZ = vector.getZ();
	   
	    double cosine = Math.cos(rad);
	    double sine = Math.sin(rad);
	   
	    return new Vector((cosine * currentX - sine * currentZ), vector.getY(), (sine * currentX + cosine * currentZ));
	}
}
