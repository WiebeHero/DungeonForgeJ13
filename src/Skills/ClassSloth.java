package Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
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
import org.bukkit.util.Vector;

import Skills.Enums.Classes;
import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ClassSloth implements Listener{
	SkillJoin join = new SkillJoin();
	PlayerClass pc = new PlayerClass();
	public ArrayList<UUID> slothCooldown = new ArrayList<UUID>();
	public HashMap<UUID, ArrayList<UUID>> arrowList = new HashMap<UUID, ArrayList<UUID>>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(pc.getClass(player.getUniqueId()) == Classes.SLOTH) {
			if(!slothCooldown.contains(player.getUniqueId())) {
				int cc = pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER);
				Location loc = player.getEyeLocation();
				Vector vec = loc.getDirection();
				vec.setY(0.00);
				loc.add(vec.getX() - (vec.getX()* 2.0),0,vec.getZ() - (vec.getZ() * 2.0));
				int level = pc.getSkill(player.getUniqueId(), Skills.LEVEL);
				double arrowA = 10 + level * 0.25;
				double cooldown = 75 - level * 0.2;
				event.setCancelled(true);
				for(int i = 0; i < (int)arrowA; i++) {
					Arrow arrow = player.getWorld().spawnArrow(loc, rotateVectorAroundY(vec, 180), (float)(1.50 + cc * 0.20), 26);
					arrow.setShooter(player);
					arrow.setCritical(true);
					if(!arrowList.containsKey(player.getUniqueId())) {
						arrowList.put(player.getUniqueId(), new ArrayList<UUID>());
					}
					arrowList.get(player.getUniqueId()).add(arrow.getUniqueId());
				}
				slothCooldown.add(player.getUniqueId());
				new BukkitRunnable() {
					public void run() {
						slothCooldown.remove(player.getUniqueId());
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Thorn of the Hedge &aagain!"));
					}
				}.runTaskLater(CustomEnchantments.getInstance(), (long)(cooldown * 20));
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
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
	@EventHandler
	public void attackSloth(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Arrow) {
				if(event.getEntity() instanceof LivingEntity) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						Player attacker = (Player) arrow.getShooter();
						if(arrowList.containsKey(attacker.getUniqueId())) {
							if(arrowList.get(attacker.getUniqueId()).contains(arrow.getUniqueId())) {
								int level = pc.getSkill(attacker.getUniqueId(), Skills.LEVEL);
								int amp = 1;
								int durationAdd = 40 + level * 4;
								PotionEffectType type = PotionEffectType.POISON;
								if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
									int durationNow = victim.getPotionEffect(type).getDuration();
									victim.removePotionEffect(type);
									victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
								}
								else {
									victim.removePotionEffect(type);
									victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
								}
								int ad = pc.getSkill(attacker.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER);
								double damage = 4 + level * 0.11;
								if(ad > 0) {
									damage = damage + ad;
								}
								event.setDamage(damage);
								int hh = pc.getSkill(attacker.getUniqueId(), Skills.MAX_HEALTH_MODIFIER);
								if(hh > 0) {
									double maxHealth = attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
									double healMe = maxHealth * (hh / 100);
									if(attacker.getHealth() + healMe <= maxHealth) {
										attacker.setHealth(attacker.getHealth() + healMe);
									}
									else {
										attacker.setHealth(maxHealth);
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
	public void heTouchme(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player) {
				Player victim = (Player) event.getEntity();
				if(pc.hasClass(victim.getUniqueId())) {
					if(pc.getClass(victim.getUniqueId()) == Classes.SLOTH) {
						int df = pc.getSkill(victim.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER);
						if(df > 0) {
							float i1 = ThreadLocalRandom.current().nextFloat() * 100;
							if(i1 < 0.5 * df) {
								int cc = pc.getSkill(victim.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER);
								Location loc = victim.getEyeLocation();
								Vector vec = loc.getDirection();
								vec.setY(0.00);
								loc.add(vec.getX() - (vec.getX()* 2.0),0,vec.getZ() - (vec.getZ() * 2.0));
								int level = pc.getSkill(victim.getUniqueId(), Skills.LEVEL);
								double arrowA = (10 + level * 0.25) / 100 * (10 * df);
								event.setCancelled(true);
								for(int i = 0; i < (int)(arrowA); i++) {
									Arrow arrow = victim.getWorld().spawnArrow(loc, rotateVectorAroundY(vec, 180), (float)(1.50 + cc * 0.20), 26);
									arrow.setShooter(victim);
									arrow.setCritical(true);
									if(!arrowList.containsKey(victim.getUniqueId())) {
										arrowList.put(victim.getUniqueId(), new ArrayList<UUID>());
									}
									arrowList.get(victim.getUniqueId()).add(arrow.getUniqueId());
								}
							}
						}
					}
				}
			}
		}
	}
}
