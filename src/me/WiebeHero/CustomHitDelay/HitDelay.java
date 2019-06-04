package me.WiebeHero.CustomHitDelay;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class HitDelay implements Listener{
	@EventHandler
	public void hitDelay(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity victim = (LivingEntity) event.getEntity();
				new BukkitRunnable() {
					public void run() {
						victim.setNoDamageTicks(16);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 0L);
			}
		}
	}
}
