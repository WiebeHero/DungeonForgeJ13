package Skills;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import Skills.Enums.Classes;
import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFactions;

public class ClassLust implements Listener{
	PlayerClass pc = new PlayerClass();
	SkillJoin join = new SkillJoin();
	DFFactions fac = new DFFactions();
	ArrayList<UUID> cooldownLust = new ArrayList<UUID>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(pc.getClass(player.getUniqueId()) == Classes.LUST) {
			if(!cooldownLust.contains(player.getUniqueId())) {
				String facName = "";
				for(Entry<String, ArrayList<UUID>> entry : fac.getFactionMemberList().entrySet()) {
					if(entry.getValue().contains(player.getUniqueId())) {
						facName = entry.getKey();
					}
				}
				int cc = pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER);
				int rd = pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER);
				int hh = pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER);
				int df = pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER);
				int level = pc.getSkill(player.getUniqueId(), Skills.LEVEL);
				double heal = 10 + level * 0.15;
				if(hh > 0) {
					heal = heal + hh * 5;
				}
				double damage = 1 + level * 0.04;
				if(cc > 0) {
					damage = damage + cc;
				}
				double range = 7 + level * 0.07;
				if(rd > 0) {
					range = range + rd * 2;
				}
				long cooldown = 2000 - 6 * level;
				double totalHeal = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (heal / 100);
				event.setCancelled(true);
				for(Entity e : player.getNearbyEntities(range, range, range)) {
					if(e != null && e != player) {
						if(e instanceof LivingEntity) {
							if(!fac.isTeammate(player.getUniqueId(), e.getUniqueId())) {
								LivingEntity victim = (LivingEntity) e;
								double dealtDamage = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (damage / 100);
								victim.damage(dealtDamage);
								Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
								victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
								victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
								totalHeal = totalHeal + dealtDamage;
								if(!cooldownLust.contains(player.getUniqueId())) {
									cooldownLust.add(player.getUniqueId());
									new BukkitRunnable() {
										public void run() {
											cooldownLust.remove(player.getUniqueId());
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Drain Blood &aagain!"));
										}
									}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
								}
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
				
				if(df > 0) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 600, df * 2), true);
				}
				double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				if(player.getHealth() + totalHeal < maxHealth) {
					player.setHealth(player.getHealth() + totalHeal);
				}
				else {
					player.setHealth(maxHealth);
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
			}
		}
	}
}
