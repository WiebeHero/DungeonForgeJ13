
package me.WiebeHero.Skills;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
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
import me.WiebeHero.DFPlayerPackage.EffectSkills;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class ClassWrath implements Listener{
	private DFPlayerManager dfManager;
	private DFFactionPlayerManager facPlayerManager;
	private DFFactionManager facManager;
	public ClassWrath(DFPlayerManager manager, DFFactionManager facManager, DFFactionPlayerManager facPlayerManager) {
		this.dfManager = manager;
		this.facPlayerManager = facPlayerManager;
		this.facManager = facManager;
	}
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(dfPlayer.getPlayerClass() == Classes.WRATH) {
				if(dfPlayer.getUseable()) {
					DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
					DFFaction fac = facManager.getFaction(facPlayer.getFactionId());
					int level = dfPlayer.getLevel();
					double damage1 = 5 + level * 0.15;
					double range = 4 + level * 0.06;
					long cooldown = 2600 - level * 5;
					event.setCancelled(true);
					ArrayList<Entity> ents = new ArrayList<Entity>(player.getNearbyEntities(range, range, range));
					ents.remove(player);
					if(!ents.isEmpty()) {
						for(Entity ent : ents) {
							if(ent instanceof LivingEntity) {
								if(fac != null) {
									if(fac.isMember(ent.getUniqueId())) {
										ents.remove(ent);
									}
								}
							}
						}
					}
					if(!ents.isEmpty()) {
						dfPlayer.setUseable(false);
						for(Entity e : ents) {
							if(e != null) {
								if(e instanceof LivingEntity) {
									LivingEntity victim = (LivingEntity) e;
									victim.getWorld().strikeLightningEffect(victim.getLocation());
									victim.damage(damage1);
								}
							}
						}
						if(dfPlayer.getAtkMod() > 0) {
							int cLevel = dfPlayer.getAtkMod();
							double inc = cLevel * 1.5;
							inc = inc * ents.size();
							long dur = (long) (cLevel * 2.5 * 20);
							dfPlayer.addAtkCal(inc, dur);
						}
						if(dfPlayer.getCrtMod() > 0) {
							int cLevel = dfPlayer.getCrtMod();
							double inc = cLevel * 0.75;
							inc = inc * ents.size();
							long dur = cLevel * 3;
							dfPlayer.addCrtCal(inc, dur);
						}
						if(dfPlayer.getRndMod() > 0) {
							int cLevel = dfPlayer.getRndMod();
							long dur = cLevel * 60;
							for(Entity e : ents) {
								if(e instanceof Player) {
									EffectSkills.disableBow.add(e.getUniqueId());
									new BukkitRunnable() {
										public void run() {
											EffectSkills.disableBow.remove(e.getUniqueId());
										}
									}.runTaskLater(CustomEnchantments.getInstance(), dur);
								}
							}
						}
						new BukkitRunnable() {
							public void run() {
								dfPlayer.setUseable(true);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
				}
			}
		}
	}
	@EventHandler
	public void chainStrikes(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof LivingEntity) {
					Player player = (Player) event.getDamager();
					if(dfManager.contains(player)) {
						DFPlayer dfPlayer = dfManager.getEntity(player);
						if(dfPlayer.getPlayerClass() == Classes.WRATH) {
							if(dfPlayer.getSpdMod() > 0) {
								int cLevel = dfPlayer.getSpdMod();
								double inc = cLevel * 0.75;
								long dur = cLevel * 40;
								dfPlayer.addAtkCal(inc, dur);
								dfPlayer.addCrtCal(inc, dur);
							}
						}
					}
				}
			}
		}
	}
}
