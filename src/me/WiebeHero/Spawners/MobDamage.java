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
				LivingEntity attacker = (LivingEntity) event.getDamager();
				if(SpawnerList.getMobList().containsKey(attacker.getUniqueId())) {
					event.setDamage(event.getFinalDamage() + (1.0 * SpawnerList.getMobList().get(attacker.getUniqueId())) * (1.0 + 0.02 * SpawnerList.getLevelMobList().get(attacker.getUniqueId())));
				}
			}
		}
		else {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Monster) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					if(SpawnerList.getMobList().containsKey(victim.getUniqueId())) {
						event.setDamage(event.getFinalDamage() * (1.00 - (0.02 * SpawnerList.getMobList().get(victim.getUniqueId())) - (0.0015 * SpawnerList.getLevelMobList().get(victim.getUniqueId()))));
					}
				}
			}
		}
	}
}
