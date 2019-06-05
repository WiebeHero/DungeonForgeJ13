package Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
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
import me.WiebeHero.CustomMethods.MethodMovementSpeed;

public class ClassPride implements Listener{
	SkillJoin join = new SkillJoin();
	MethodMovementSpeed move = new MethodMovementSpeed();
	public ArrayList<UUID> prideCooldown = new ArrayList<UUID>();
	public HashMap<UUID, Double> prideAbsorb = new HashMap<UUID, Double>();
	public HashMap<UUID, Double> temp = new HashMap<UUID, Double>();
	public static ArrayList<UUID> prideExtraAS = new ArrayList<UUID>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(join.getClassList().containsKey(player.getUniqueId())) {
			if(join.getClassList().get(player.getUniqueId()).equals("Pride")) {
				if(!prideCooldown.contains(player.getUniqueId())) {
					prideCooldown.add(player.getUniqueId());
					prideExtraAS.add(player.getUniqueId());
					int level = join.getLevelList().get(player.getUniqueId());
					int rd = join.getRDMODList().get(player.getUniqueId());
					long duration = 100 + level * 2;
					long cooldown = 2000 - level * 6;
					if(rd > 0) {
						cooldown = cooldown - 20 * rd;
					}
					double speed = 20.00 + level * 0.30;
					double attackS = 20 + level * 0.30;
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_DEATH, 2.0F, 2.0F);
					Location loc = player.getLocation();
					loc.setY(loc.getY() - 1.5);
					BlockData bd = Material.COBWEB.createBlockData();
					player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have used &6Quick Attack!"));
					move.setSpeed(player, move.getSpeed(player) / 100 * (100 + speed));
					AttackSpeed aSpeed = new AttackSpeed();
					prideExtraAS.add(player.getUniqueId());
					aSpeed.attackSpeedRun(player, duration, (1.00 + attackS / 100));
					event.setCancelled(true);
					new BukkitRunnable() {
						public void run() {
							prideExtraAS.remove(player.getUniqueId());
							prideAbsorb.remove(player.getUniqueId());
							aSpeed.attackSpeedRun(player, duration, speed / 100);
							move.setSpeed(player, move.getSpeed(player) - speed / 1000 - temp.get(player.getUniqueId()));
							temp.remove(player.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), duration);
					new BukkitRunnable() {
						public void run() {
							prideCooldown.remove(player.getUniqueId());
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Quick Attack &aagain!"));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
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
	@EventHandler
	public void decreaseDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(prideExtraAS.contains(player.getUniqueId())) {
				int df = join.getDFMODList().get(player.getUniqueId());
				if(df > 0) {
					double damage = event.getDamage() / 100 * (df * 5);
					prideAbsorb.put(player.getUniqueId(), damage);
				}
				int level = join.getLevelList().get(player.getUniqueId());
				double decrease = 10 + level * 0.15;
				event.setDamage(event.getFinalDamage() / 100.00 * (100.00 - decrease));
			}
		}
		else if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			LivingEntity victim = (LivingEntity) event.getEntity();
			if(prideExtraAS.contains(player.getUniqueId())) {
				int as = join.getASMODList().get(player.getUniqueId());
				if(as > 0) {
					if(!temp.containsKey(player.getUniqueId())) {
						temp.put(player.getUniqueId(), 0.00);
					}
					temp.put(player.getUniqueId(), temp.get(player.getUniqueId()) + 0.001 * as);
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(move.getSpeed(player) + 0.001 * as);
				}
				int ad = join.getADMODList().get(player.getUniqueId());
				if(ad > 0) {
					int amp = 0;
					int durationAdd = 20 * ad;
					PotionEffectType type = PotionEffectType.WEAKNESS;
					if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
						int durationNow = victim.getPotionEffect(type).getDuration();
						victim.removePotionEffect(type);
						victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
					}
					else {
						victim.removePotionEffect(type);
						victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
					}
				}
				if(prideAbsorb.containsKey(player.getUniqueId())) {
					event.setDamage(event.getFinalDamage() + prideAbsorb.get(player.getUniqueId()));
					prideAbsorb.put(player.getUniqueId(), 0.00);
				}
			}
		}
	}
	public ArrayList<UUID> getPrideList(){
		return ClassPride.prideExtraAS;
	}
}
