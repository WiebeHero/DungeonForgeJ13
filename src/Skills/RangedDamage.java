package Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class RangedDamage implements Listener{
	SkillJoin join = new SkillJoin();
	public HashMap<UUID, Float> arrows = new HashMap<UUID, Float>();
	@EventHandler
	public void rangedShot(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player) {
			arrows.put(event.getProjectile().getUniqueId(), event.getForce());
		}
	}
	@EventHandler
	public void rangedDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();
				if(arrows.containsKey(arrow.getUniqueId())) {
					event.setDamage(event.getFinalDamage() / 100 * (join.getRDCalList().get(player.getUniqueId()) + join.getRDExtraList().get(player.getUniqueId())) * arrows.get(arrow.getUniqueId()));
				}
			}
		}
	}
}
