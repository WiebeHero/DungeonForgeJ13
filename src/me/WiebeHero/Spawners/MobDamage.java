package me.WiebeHero.Spawners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobDamage implements Listener{
	private SpawnerList s = new SpawnerList();
	@EventHandler
	public void damageFromMobs(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Monster) {
			if(event.getEntity() instanceof Player) {
				LivingEntity uuidOfAttacker = (LivingEntity) event.getDamager();
				if(s.getMobList().containsKey(uuidOfAttacker)) {
					event.setDamage(4.5 + 3 * s.getMobList().get(uuidOfAttacker));
				}
			}
		}
		else {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Monster) {
					LivingEntity uuidOfVictim = (LivingEntity) event.getEntity();
					if(s.getMobList().containsKey(uuidOfVictim)) {
						event.setDamage(event.getFinalDamage() * (1.00 - 0.10 * s.getMobList().get(uuidOfVictim)));
					}
				}
			}
		}
	}
}
