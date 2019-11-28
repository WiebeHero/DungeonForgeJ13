
package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodMovementSpeed;
import me.WiebeHero.Skills.Enums.Classes;

public class ClassWrath implements Listener{
	DFPlayer method = new DFPlayer();
	MethodMovementSpeed move = new MethodMovementSpeed();
	public HashMap<UUID, Integer> activated = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> temp = new HashMap<UUID, Integer>();
	public ArrayList<UUID> wrathCooldown = new ArrayList<UUID>();
	public HashMap<UUID, Integer> wrathExtra = new HashMap<UUID, Integer>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
		if(method.containsPlayer(player)) {
			if(dfPlayer.getPlayerClass() == Classes.WRATH) {
				if(dfPlayer.getUseable()) {
					dfPlayer.setUseable(false);
					int level = dfPlayer.getLevel();
					double damage1 = 5 + level * 0.15;
					double range = 4 + level * 0.06;
					long cooldown = 2600 - level * 5;
					event.setCancelled(true);
					ArrayList<Entity> ents = new ArrayList<Entity>(player.getNearbyEntities(range, range, range));
					ents.remove(player);
					if(!ents.isEmpty()) {
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
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
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
					if(method.containsPlayer(player)) {
						DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
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
