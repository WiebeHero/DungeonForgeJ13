package Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import Skills.Enums.Classes;
import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ClassGreed implements Listener{
	PlayerClass pc = new PlayerClass();
	EffectSkills sk = new EffectSkills();
	public ArrayList<UUID> greedCooldown = new ArrayList<UUID>();
	public ArrayList<UUID> greedArrow = new ArrayList<UUID>();
	public ArrayList<UUID> arrowHit = new ArrayList<UUID>();
	public HashMap<UUID, UUID> extraDamage = new HashMap<UUID, UUID>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(pc.getClass(player.getUniqueId()) == Classes.GREED) {
			if(!greedCooldown.contains(player.getUniqueId())) {
				if(!greedArrow.contains(player.getUniqueId())) {
					int level = pc.getSkill(player.getUniqueId(), Skills.LEVEL);
					long cooldown = 1700 - level * 6;
					double attackS = 20 + level * 0.30;
					long duration = 130 + level;
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Hunting Arrow!"));
					Location loc = player.getLocation();
					loc.setY(loc.getY() + 2.5);
					BlockData bd = Material.COAL_BLOCK.createBlockData();
					player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
					greedCooldown.add(player.getUniqueId());
					pc.setCalculation(player.getUniqueId(), Skills.ATTACK_SPEED_EXTRA, pc.getCalculation(player.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA) + attackS);
					sk.attackSpeed(player);
					event.setCancelled(true);
					new BukkitRunnable() {
						public void run() {
							greedCooldown.remove(player.getUniqueId());
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Hunting Arrow &aagain!"));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
					new BukkitRunnable() {
						public void run() {
							pc.setCalculation(player.getUniqueId(), Skills.ATTACK_SPEED_EXTRA, pc.getCalculation(player.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA) - attackS);
							sk.attackSpeed(player);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), duration);
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
			}
		}
	}
	@EventHandler
	public void shootGreedArrow(EntityShootBowEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof Player) {
				if(event.getProjectile() instanceof Arrow) {
					Player player = (Player) event.getEntity();
					Arrow arrow = (Arrow) event.getProjectile();
					if(greedArrow.contains(player.getUniqueId())) {
						greedArrow.remove(player.getUniqueId());
						arrowHit.add(arrow.getUniqueId());
					}
				}
			}
		}
	}
	@EventHandler
	public void arrowGreedHit(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Arrow) {
				if(event.getEntity() instanceof LivingEntity) {
					Arrow arrow = (Arrow) event.getDamager();
					LivingEntity ent = (LivingEntity) event.getEntity();
					if(arrowHit.contains(arrow.getUniqueId())) {
						Player attack = (Player) arrow.getShooter();
						int cc = pc.getSkill(attack.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER);
						if(cc > 0) {
							int amp = 0;
							int durationAdd = 250 + 100 * cc;
							PotionEffectType type = PotionEffectType.WITHER;
							if(ent.hasPotionEffect(type) && ent.getPotionEffect(type).getAmplifier() == amp) {
								int durationNow = ent.getPotionEffect(type).getDuration();
								ent.removePotionEffect(type);
								ent.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
							}
							else {
								ent.removePotionEffect(type);
								ent.addPotionEffect(new PotionEffect(type, durationAdd, amp));
							}
						}
						int as = pc.getSkill(attack.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER);
						double move = attack.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
						if(as > 0) {
							attack.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(move / 100 * (100 + as * 10));
						}
						int level = pc.getSkill(attack.getUniqueId(), Skills.LEVEL);
						long duration = 130 + level;
						int rd = pc.getSkill(attack.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER);
						if(rd > 0) {
							duration = duration + 10 * rd;
						}
						arrowHit.remove(arrow.getUniqueId());
						extraDamage.put(ent.getUniqueId(), attack.getUniqueId());
						new BukkitRunnable() {
							public void run() {
								extraDamage.remove(ent.getUniqueId());
								attack.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(move);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), duration);
					}
				}
			}
		}
	}
	@EventHandler
	public void extraDamageEnt(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity ent = (LivingEntity) event.getEntity();
				if(extraDamage.containsKey(ent.getUniqueId())) {
					int level = pc.getSkill(extraDamage.get(ent.getUniqueId()), Skills.LEVEL);
					double damage = 50 + level * 0.50;
					event.setDamage(event.getFinalDamage() / 100 * (100 + damage));
				}
			}
		}
	}
	@EventHandler
	public void extraDamagePas(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity && event.getDamager() instanceof Player) {
				LivingEntity ent = (LivingEntity) event.getEntity();
				Player player = (Player) event.getDamager();
				if(extraDamage.containsKey(ent.getUniqueId())) {
					if(extraDamage.get(ent.getUniqueId()).equals(player.getUniqueId())) {
						int ad = pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER);
						event.setDamage(event.getFinalDamage() + ad * 2);
					}
				}
			}
		}
	}
}
