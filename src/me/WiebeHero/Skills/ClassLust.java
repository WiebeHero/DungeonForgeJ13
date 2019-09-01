package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFactions;
import me.WiebeHero.Skills.Enums.Classes;

public class ClassLust implements Listener{
	SkillJoin join = new SkillJoin();
	DFFactions fac = new DFFactions();
	ArrayList<UUID> cooldownLust = new ArrayList<UUID>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
		if(dfPlayer.getPlayerClass() == Classes.LUST) {
			if(dfPlayer.getUseable()) {
				int level = dfPlayer.getLevel();
				double heal = 10 + level * 0.15;
				double damage = 5 + level * 0.05;
				double range = 7 + level * 0.07;
				long cooldown = 1700 - 6 * level;
				double healMe = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (heal / 100);
				double healAllies = 0.00;
				ArrayList<Entity> ents = new ArrayList<Entity>(player.getNearbyEntities(range, range, range));
				ents.remove(player);
				if(!ents.isEmpty()) {
					event.setCancelled(true);
					for(Entity e : ents) {
						if(e != null) {
							if(e instanceof LivingEntity) {
								if(!fac.isTeammate(player.getUniqueId(), e.getUniqueId())) {
									LivingEntity victim = (LivingEntity) e;
									double dealtDamage = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (damage / 100);
									victim.damage(dealtDamage);
									Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
									victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
									victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
									healMe = healMe + dealtDamage;
									healAllies = healAllies + dealtDamage;
								}
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have used &6Drain Blood!"));
							Location locCF = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1.8D, player.getLocation().getZ() + 0D);
							player.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
						}
					}
					ArrayList<Player> others = new ArrayList<Player>();
					if(healMe + dfPlayer.getHP() <= dfPlayer.getMaxHp()) {
						dfPlayer.returnPlayer().setHealth(dfPlayer.getHp() + healMe);
					}
					else {
						if(dfPlayer.getHpMod() > 0) {
							int cLevel = dfPlayer.getHpMod();
							double remaining = dfPlayer.getMaxHp() - (healMe + dfPlayer.getHP());
							int amp = (int) Math.round(remaining / 4);
							int dur = cLevel * 5;
							dfPlayer.returnPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, amp, dur * 20));
						}
						dfPlayer.returnPlayer().setHealth(dfPlayer.getMaxHp());
						
					}
					for(Entity e : player.getNearbyEntities(range, range, range)) {
						if(e != null && e != player) {
							if(e instanceof Player) {
								if(fac.isTeammate(player.getUniqueId(), e.getUniqueId())) {
									Player p = (Player) e;
									others.add(p);
								}
							}
						}
					}
					healAllies = healAllies / others.size();
					if(!others.isEmpty()) {
						for(Player p : others) {
							if(healAllies + p.getHealth() <= p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
								dfPlayer.returnPlayer().setHealth(p.getHealth() + healAllies);
							}
							else {
								dfPlayer.returnPlayer().setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							}
						}
					}
					dfPlayer.setUseable(false);
					new BukkitRunnable() {
						public void run() {
							dfPlayer.setUseable(true);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use your Ability yet!"));
			}
		}
	}
	@EventHandler
	public void criticalDown(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof Player) {
				if(event.getDamager() instanceof Arrow || event.getDamager() instanceof Player) {
					Player player = null;
					Player victim = (Player) event.getEntity();
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
						DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
						DFPlayer dfVictim = new DFPlayer().getPlayer(victim);
						if(dfPlayer.getPlayerClass() == Classes.LUST) {
							if(dfPlayer.getCrtMod() > 0) {
								int cLevel = dfPlayer.getCrtMod();
								double inc = cLevel * 0.5;
								double dur = cLevel * 2.5;
								dfVictim.removeCrtCal(inc, (long)dur * 20);
							}
							if(dfPlayer.getRndMod() > 0) {
								int cLevel = dfPlayer.getRndMod();
								double inc = cLevel * 10;
								double test = 1 - dfPlayer.getHp() / dfPlayer.getMaxHp();
								dfPlayer.addRndCal(inc * test, 1);
							}
							if(dfPlayer.getDfMod() > 0) {
								int cLevel = dfPlayer.getDfMod();
								double inc = cLevel * 10;
								double test = 1 - dfPlayer.getHp() / dfPlayer.getMaxHp();
								dfPlayer.addDfCal(test * inc, 1);
							}
						}
					}
				}
			}
		}
	}
}
