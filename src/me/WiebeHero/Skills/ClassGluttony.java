package me.WiebeHero.Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.Enums.Classes;

public class ClassGluttony implements Listener{
	DFPlayer method = new DFPlayer();
	public HashMap<UUID, Double> recover = new HashMap<UUID, Double>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(method.containsPlayer(player)) {
			DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
			if(dfPlayer.getPlayerClass() == Classes.GLUTTONY) {
				if(dfPlayer.getUseable() == true) {
					dfPlayer.setUseable(false);
					dfPlayer.setActivation(true);
					int level = dfPlayer.getLevel();
					double heal = 10 + level * 0.25;
					double defense = 50 + level * 0.50;
					long duration = 200 + level * 2;
					long cooldown = 2300 - level * 4;
					dfPlayer.addDfCal(defense, duration);
					if(dfPlayer.getSpdMod() > 0) {
						int cLevel = dfPlayer.getSpdMod();
						double hp = cLevel * 4;
						double de = cLevel * 1;
						recover.put(dfPlayer.getUUID(), hp);
						cooldown = cooldown - (long)(de * 20);
					}
					Location locCF = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1.5D, player.getLocation().getZ() + 0D);
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have used &6Stand!"));
					player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 2, (float) 0.5);
					double totalHeal = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (heal / 100);
					double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					event.setCancelled(true);
					dfPlayer.addDfCal(defense, duration);
					if(player.getHealth() + totalHeal <= maxHealth) {
						player.setHealth(player.getHealth() + totalHeal);
					}
					else {
						player.setHealth(maxHealth);
					}
					final long finalCool = cooldown;
					new BukkitRunnable() {
						public void run() {
							dfPlayer.setActivation(false);
							new BukkitRunnable() {
								public void run() {
									dfPlayer.setUseable(true);
									recover.remove(dfPlayer.getUUID());
								}
							}.runTaskLater(CustomEnchantments.getInstance(), finalCool);
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
	public void hitBySmth(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
			Player attacker = (Player) event.getDamager();
			LivingEntity player = (LivingEntity) event.getEntity();
			if(method.containsPlayer(attacker) && method.containsPlayer(player)) {
				DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
				DFPlayer aDFPlayer = new DFPlayer().getPlayer(attacker);
				if(dfPlayer.getPlayerClass() == Classes.GLUTTONY) {
					if(dfPlayer.getActivation() == true) {
						if(dfPlayer.getCrtMod() > 0) {
							int cLevel = dfPlayer.getCrtMod();
							double dec = cLevel * 1.5;
							double dur = cLevel * 3;
							aDFPlayer.removeCrtCal(dec, (long)dur * 20);
						}
					}
				}
			}
		}
	}
	public HashMap<UUID, Boolean> ironWall = new HashMap<UUID, Boolean>();
	@EventHandler
	public void healUp(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(ironWall.containsKey(player.getUniqueId())) {
				double maxHP = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				double health = player.getHealth();
				if(health > maxHP * 0.40) {
					ironWall.put(player.getUniqueId(), false);
				}
			}
			if(recover.containsKey(player.getUniqueId())) {
				event.setAmount(event.getAmount() / 100 * (100 + recover.get(player.getUniqueId())));
			}
		}
	}
	@EventHandler
	public void ironWall(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(method.containsPlayer(player)) {
				DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
				if(!ironWall.containsKey(dfPlayer.getUUID())) {
					ironWall.put(dfPlayer.getUUID(), false);
				}
				if(dfPlayer.getPlayerClass() == Classes.GLUTTONY) {
					if(dfPlayer.getDfMod() > 0) {
						int cLevel = dfPlayer.getDfMod();
						double inc = cLevel * 20;
						double dur = cLevel * 3;
						if(dfPlayer.getHP() < dfPlayer.getMaxHp() * 0.40) {
							ironWall.put(dfPlayer.getUUID(), true);
							dfPlayer.addDfCal(inc, (long)dur * 20);
						}
					}
				}
			}
		}
	}
}
