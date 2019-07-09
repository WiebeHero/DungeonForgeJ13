package me.WiebeHero.Spawners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobDamage implements Listener{
	@EventHandler
	public void damageFromMobs(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Monster) {
			if(event.getEntity() instanceof Player) {
				LivingEntity uuidOfAttacker = (LivingEntity) event.getDamager();
				if(SpawnerList.getMobList().containsKey(uuidOfAttacker.getUniqueId())) {
					event.setDamage(4.5 + 3 * SpawnerList.getMobList().get(uuidOfAttacker.getUniqueId()));
				}
			}
		}
		else {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Monster) {
					LivingEntity uuidOfVictim = (LivingEntity) event.getEntity();
					if(SpawnerList.getMobList().containsKey(uuidOfVictim.getUniqueId())) {
						event.setDamage(event.getFinalDamage() * (1.00 - 0.10 * SpawnerList.getMobList().get(uuidOfVictim.getUniqueId())));
					}
				}
			}
		}
	}
}
