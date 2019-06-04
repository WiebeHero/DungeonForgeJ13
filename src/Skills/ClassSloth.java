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

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ClassSloth implements Listener{
	SkillJoin join = new SkillJoin();
	public ArrayList<UUID> slothCooldown = new ArrayList<UUID>();
	public HashMap<UUID, ArrayList<UUID>> arrowList = new HashMap<UUID, ArrayList<UUID>>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(join.getClassList().containsKey(player.getUniqueId())) {
			if(join.getClassList().get(player.getUniqueId()).equals("Sloth")) {
				if(!slothCooldown.contains(player.getUniqueId())) {
					int cc = join.getASMODList().get(player.getUniqueId());
					Location loc = player.getEyeLocation();
					Vector vec = loc.getDirection();
					vec.setY(0.00);
					loc.add(vec.getX() - (vec.getX()* 2.0),0,vec.getZ() - (vec.getZ() * 2.0));
					int level = join.getLevelList().get(player.getUniqueId());
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
		else {
			player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have not chosen a class!"));
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
		if(event.getDamager() instanceof Arrow) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity victim = (LivingEntity) event.getEntity();
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player attacker = (Player) arrow.getShooter();
					if(arrowList.get(attacker.getUniqueId()).contains(arrow.getUniqueId())) {
						int level = join.getLevelList().get(attacker.getUniqueId());
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
						int ad = join.getADMODList().get(attacker.getUniqueId());
						double damage = 4 + level * 0.11;
						if(ad > 0) {
							damage = damage + ad;
						}
						event.setDamage(damage);
						int hh = join.getHHMODList().get(attacker.getUniqueId());
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
	@EventHandler
	public void heTouchme(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			if(join.getClassList().containsKey(victim.getUniqueId())) {
				if(join.getClassList().get(victim.getUniqueId()).equals("Sloth")) {
					int df = join.getDFMODList().get(victim.getUniqueId());
					if(df > 0) {
						float i1 = ThreadLocalRandom.current().nextFloat() * 100;
						if(i1 < 0.5 * df) {
							int cc = join.getASMODList().get(victim.getUniqueId());
							Location loc = victim.getEyeLocation();
							Vector vec = loc.getDirection();
							vec.setY(0.00);
							loc.add(vec.getX() - (vec.getX()* 2.0),0,vec.getZ() - (vec.getZ() * 2.0));
							int level = join.getLevelList().get(victim.getUniqueId());
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
