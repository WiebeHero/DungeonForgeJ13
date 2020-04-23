package me.WiebeHero.CustomHitDelay;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;

public class HitDelay extends SwordSwingProgress implements Listener{
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void hitDelay(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
				LivingEntity victim = (LivingEntity) event.getEntity();
				Player player = (Player) event.getDamager();
				double attackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();
				int temp = (int)(1 / attackSpeed * 20 / 65 * (100 - getAttackStrength(player) * 100));
				final int cooldown = temp > 40 ? 40 : temp;
				new BukkitRunnable() {
					public void run() {
						victim.setNoDamageTicks(cooldown);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 0L);
			}
			else if(event.getDamager() instanceof Arrow && event.getEntity() instanceof LivingEntity) {
				LivingEntity victim = (LivingEntity) event.getEntity();
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					double attackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();
					int temp = (int)(1 / attackSpeed * 20 / 65 * (100 - getAttackStrength(player) * 100));
					final int cooldown = temp > 40 ? 40 : temp;
					new BukkitRunnable() {
						public void run() {
							victim.setNoDamageTicks(cooldown);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 0L);
				}
			}
		}
	}
}
