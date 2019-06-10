
package Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.attribute.Attribute;
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
import me.WiebeHero.Factions.DFFactions;

public class ClassWrath implements Listener{
	SkillJoin join = new SkillJoin();
	DFFactions fac = new DFFactions();
	MethodMovementSpeed move = new MethodMovementSpeed();
	public HashMap<UUID, Integer> activated = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> temp = new HashMap<UUID, Integer>();
	public ArrayList<UUID> wrathCooldown = new ArrayList<UUID>();
	public HashMap<UUID, Integer> wrathExtra = new HashMap<UUID, Integer>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(join.getClassList().containsKey(player.getUniqueId())) {
			if(join.getClassList().get(player.getUniqueId()).equals("Wrath")) {
				if(!wrathCooldown.contains(player.getUniqueId())) {
					String facName = "";
					for(Entry<String, ArrayList<UUID>> entry : fac.getFactionMemberList().entrySet()) {
						if(entry.getValue().contains(player.getUniqueId())) {
							facName = entry.getKey();
						}
					}
					int level = join.getLevelList().get(player.getUniqueId());
					int ad = join.getADMODList().get(player.getUniqueId());
					int as = join.getASMODList().get(player.getUniqueId());
					int cc = join.getCCMODList().get(player.getUniqueId());
					int rd = join.getRDMODList().get(player.getUniqueId());
					double damage1 = 5 + level * 0.15;
					double damage2 = 0.1 + level * 0.01;
					double range = 4 + level * 0.06;
					long cooldown = 2600 - level * 5;
					int amount = 0;
					if(rd > 0) {
						range = range + rd;
					}
					for(Entity e : player.getLocation().getNearbyEntities(range, range, range)) {
						if(e != null && e != player) {
							if(e instanceof LivingEntity) {
								amount++;
							}
						}
					}
					event.setCancelled(true);
					for(Entity e : player.getLocation().getNearbyEntities(range, range, range)) {
						if(e != null && e != player) {
							if(e instanceof LivingEntity) {
								double totalDamage = 0.00;
								LivingEntity victim = (LivingEntity) e;
								victim.getWorld().strikeLightningEffect(victim.getLocation());
								totalDamage = totalDamage + damage1;
								if(wrathExtra.containsKey(victim.getUniqueId())) {
									totalDamage = totalDamage + damage2 * wrathExtra.get(victim.getUniqueId());
									wrathExtra.remove(victim.getUniqueId());
								}
								if(ad > 0) {
									totalDamage = totalDamage + amount * (0.1 * ad);
								}
								if(as > 0) {
									long duration = 60 + (as * 20);
									victim.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(move.getSpeed(player) + 10 * amount / 1000);
									temp.put(victim.getUniqueId(), amount);
									new BukkitRunnable() {
										public void run() {
											victim.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(move.getSpeed(player) - 10 * temp.get(victim.getUniqueId()) / 1000);
											temp.remove(victim.getUniqueId());
										}
									}.runTaskLater(CustomEnchantments.getInstance(), duration);
								}
								if(cc > 0) {
									float i = ThreadLocalRandom.current().nextFloat() * 100;
									if(i < 10 * cc) {
										totalDamage = totalDamage * 2.00;
									}
								}
								if(!wrathCooldown.contains(player.getUniqueId())) {
									wrathCooldown.add(player.getUniqueId());
									new BukkitRunnable() {
										public void run() {
											wrathCooldown.remove(player.getUniqueId());
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Hatred of the Wrath &aagain!"));
										}
									}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
								}
								victim.damage(totalDamage);
							}
						}
					}
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
	public void extraDamageRegister(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			if(join.getClassList().containsKey(victim.getUniqueId())) {
				if(join.getClassList().get(victim.getUniqueId()).equals("Wrath")) {
					if(event.getDamager() instanceof LivingEntity) {
						LivingEntity attacker = (LivingEntity) event.getDamager();
						if(wrathExtra.containsKey(attacker.getUniqueId())) {
							wrathExtra.put(attacker.getUniqueId(), wrathExtra.get(attacker.getUniqueId()) + 1);
							if(!activated.containsKey(attacker.getUniqueId())) {
								activated.put(attacker.getUniqueId(), 5);
								new BukkitRunnable() {
									public void run() {
										int duration = activated.get(attacker.getUniqueId());
										duration--;
										if(duration == 0) {
											activated.remove(attacker.getUniqueId());
											wrathExtra.remove(attacker.getUniqueId());
										}
									}
								}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
							}
							else {
								activated.put(attacker.getUniqueId(), 5);
							}
						}
						else {
							wrathExtra.put(attacker.getUniqueId(), 1);
							if(!activated.containsKey(attacker.getUniqueId())) {
								activated.put(attacker.getUniqueId(), 5);
								new BukkitRunnable() {
									public void run() {
										int duration = activated.get(attacker.getUniqueId());
										duration--;
										if(duration == 0) {
											activated.remove(attacker.getUniqueId());
											wrathExtra.remove(attacker.getUniqueId());
										}
									}
								}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
							}
							else {
								activated.put(attacker.getUniqueId(), 5);
							}
						}
					}
				}
			}
		}
	}
}
