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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;

public class ClassSloth implements Listener{
	private DFPlayerManager dfManager;
	private ArrayList<UUID> arrowList = new ArrayList<UUID>();
	public ClassSloth(DFPlayerManager manager) {
		this.dfManager = manager;
	}
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
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
						arrow.setMetadata("AttackStrength", new FixedMetadataValue(CustomEnchantments.getInstance(), 1.0F));
						arrow.setDamage(damage);
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
	public void arrowHit(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					if(dfManager.contains(player)) {
						if(arrowList.contains(arrow.getUniqueId())) {
							DFPlayer dfPlayer = dfManager.getEntity(player);
							if(dfPlayer.getHp() > 0) {
								int cLevel = dfPlayer.getHp();
								double inc = cLevel * 0.5;
								if(dfPlayer.getHealth() + inc <= dfPlayer.getMaxHealth()) {
									dfPlayer.returnPlayer().setHealth(dfPlayer.getHealth() + inc);
								}
								else {
									dfPlayer.returnPlayer().setHealth(dfPlayer.getMaxHealth());
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
				if(dfManager.contains(player)) {
					DFPlayer dfPlayer = dfManager.getEntity(player);
					if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
						if(dfPlayer.getCrtMod() > 0) {
							if(dfPlayer.getHealth() >= dfPlayer.getMaxHealth() * 0.50) {
								dfPlayer.addCrtCal(dfPlayer.getCrtCal() * 5, 1);
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
				if(dfManager.contains(player)) {
					DFPlayer dfPlayer = dfManager.getEntity(player);
					if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
						if(dfPlayer.getCrtMod() > 0) {
							if(dfPlayer.getHealth() <= dfPlayer.getMaxHealth() * 0.50) {
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
