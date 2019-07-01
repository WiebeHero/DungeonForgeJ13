package Skills;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import Skills.Enums.Classes;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ClassEnvy implements Listener{
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	ArrayList<UUID> envyCooldown = new ArrayList<UUID>();
	ArrayList<UUID> envyDamage = new ArrayList<UUID>();
	ArrayList<UUID> envyExtra = new ArrayList<UUID>();
	ArrayList<UUID> envyBlock = new ArrayList<UUID>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(c.getClass(player.getUniqueId()) == Classes.ENVY) {
			if(!envyCooldown.contains(player.getUniqueId())) {
				int level = join.getLevelList().get(player.getUniqueId());
				int df = join.getDFMODList().get(player.getUniqueId());
				double duration = 5 + level * 0.05;
				double cooldown = 65 - level * 0.25;
				envyDamage.add(player.getUniqueId());
				Location loc = player.getLocation();
				loc.setY(loc.getY() + 2.5);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 2.0F, 1.0F);
				BlockData bd = Material.REDSTONE_BLOCK.createBlockData();
				player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have used &6Special Attack!"));
				event.setCancelled(true);
				new BukkitRunnable() {
					public void run() {
						if(envyDamage.contains(player.getUniqueId())) {
							envyDamage.remove(player.getUniqueId());
							envyBlock.add(player.getUniqueId());
						}
					}
				}.runTaskLater(CustomEnchantments.getInstance(), (long)(duration * 20));
				envyCooldown.add(player.getUniqueId());
				new BukkitRunnable() {
					public void run() {
						envyCooldown.remove(player.getUniqueId());
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Special Attack &aagain!"));
					}
				}.runTaskLater(CustomEnchantments.getInstance(), (long)(cooldown * 20));
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
			}
		}
	}
	@EventHandler
	public void extraDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player player = (Player) event.getDamager();
				if(envyDamage.contains(player.getUniqueId())) {
					int level = join.getLevelList().get(player.getUniqueId());
					double damage = 50 + level * 0.50;
					double heal = 10 + level * 0.4;
					envyDamage.remove(player.getUniqueId());
					event.setDamage(event.getFinalDamage() / 100 * (100 + damage));
					double totalHeal = event.getFinalDamage() / 100 * heal;
					double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					if(player.getHealth() + totalHeal <= maxHealth) {
						player.setHealth(player.getHealth() + totalHeal);
					}
					else {
						player.setHealth(maxHealth);
					}
					int ad = join.getADMODList().get(player.getUniqueId());
					if(ad > 0) {
						envyExtra.add(player.getUniqueId());
					}
					int rd = join.getRDMODList().get(player.getUniqueId());
					if(rd > 0) {
						double speed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
						player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed / 100 * (100 + rd * 5));
						new BukkitRunnable() {
							public void run() {
								player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 300L);
					}
					int hh = join.getHHMODList().get(player.getUniqueId());
					if(hh > 0) {
						new BukkitRunnable() {
							int duration = 8 - hh;
							public void run() {
								if(duration != 0) {
									if(player.getHealth() + 1 + (0.75 * hh) <= maxHealth) {
										player.setHealth(player.getHealth() + totalHeal);
									}
									else {
										player.setHealth(maxHealth);
									}
									duration--;
								}
								else {
									if(player.getHealth() + 1 + (0.75 * hh) <= maxHealth) {
										player.setHealth(player.getHealth() + totalHeal);
									}
									else {
										player.setHealth(maxHealth);
									}
									cancel();
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					}
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_HURT, 2.0F, 1.0F);
				}
			}
		}
		else if(event.getDamager() instanceof Arrow) {
			if(event.getEntity() instanceof LivingEntity) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					if(envyDamage.contains(player.getUniqueId())) {
						int level = join.getLevelList().get(player.getUniqueId());
						double damage = 50 + level * 0.50;
						double heal = 10 + level * 0.4;
						envyDamage.remove(player.getUniqueId());
						event.setDamage(event.getFinalDamage() / 100 * (100 + damage));
						double totalHeal = event.getFinalDamage() / 100 * heal;
						double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						if(player.getHealth() + totalHeal <= maxHealth) {
							player.setHealth(player.getHealth() + totalHeal);
						}
						else {
							player.setHealth(maxHealth);
						}
						int ad = join.getADMODList().get(player.getUniqueId());
						if(ad > 0) {
							envyExtra.add(player.getUniqueId());
						}
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_HURT, 2.0F, 1.0F);
					}
				}
			}
		}
	}
	@EventHandler
	public void extraEDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player player = (Player) event.getDamager();
				if(envyExtra.contains(player.getUniqueId())) {
					envyExtra.remove(player.getUniqueId());
					int ad = join.getADMODList().get(player.getUniqueId());
					event.setDamage(event.getFinalDamage() + ad);
				}
			}
		}
	}
	@EventHandler
	public void blockDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				if(envyBlock.contains(player.getUniqueId())) {
					envyBlock.remove(player.getUniqueId());
					int df = join.getADMODList().get(player.getUniqueId());
					event.setDamage(event.getFinalDamage() / 100 * (100 - df * 5));
				}
			}
		}
	}
}
