package Skills;

import java.util.ArrayList;
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
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import Skills.Enums.Classes;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ClassGluttony implements Listener{
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	ArrayList<UUID> gluttonyCooldown = new ArrayList<UUID>();
	ArrayList<UUID> gluttonyDecrease = new ArrayList<UUID>();
	@EventHandler
	public void activateAbility(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(c.getClass(player.getUniqueId()) == Classes.GLUTTONY) {
			if(!gluttonyCooldown.contains(player.getUniqueId())) {
				int level = join.getLevelList().get(player.getUniqueId());
				int cc = join.getCCMODList().get(player.getUniqueId());
				int as = join.getASMODList().get(player.getUniqueId());
				int hh = join.getHHMODList().get(player.getUniqueId());
				double heal = 10 + level * 0.25;
				if(hh > 0) {
					heal = heal + hh * 5;
				}
				long duration = 200 + level * 2;
				if(cc > 0) {
					duration = duration + (cc * 5 * duration);
				}
				if(as > 0) {
					double speed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed / 100 * (100 + as * 5));
					new BukkitRunnable() {
						public void run() {
							player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), duration);
				}
				long cooldown = 2300 - level * 4;
				Location locCF = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1.5D, player.getLocation().getZ() + 0D);
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have used &6Stand!"));
				player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 2, (float) 0.5);
				double totalHeal = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (heal / 100);
				double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				event.setCancelled(true);
				if(!gluttonyCooldown.contains(player.getUniqueId())) {
					gluttonyDecrease.add(player.getUniqueId());
					gluttonyCooldown.add(player.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							gluttonyCooldown.remove(player.getUniqueId());
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou can use &6Stand &aagain!"));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
					new BukkitRunnable() {
						public void run() {
							gluttonyDecrease.remove(player.getUniqueId());
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6Stand&c's ran out."));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), duration);
				}
				if(player.getHealth() + totalHeal <= maxHealth) {
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
	@EventHandler
	public void damageDecrease(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity attacker = (LivingEntity) event.getDamager();
				Player victim = (Player) event.getEntity();
				if(gluttonyDecrease.contains(victim.getUniqueId())) {
					int level = join.getLevelList().get(victim.getUniqueId());
					int df = join.getDFMODList().get(victim.getUniqueId());
					if(df > 0) {
						double damageBack = df * 5;
						attacker.damage(event.getFinalDamage() / 100 * damageBack);
					}
					double decrease = 20 + level * 0.30;
					event.setDamage(event.getFinalDamage() / 100 * (100 - decrease));
				}
			}
		}
	}
}
