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
					if(join.getClassList().get(player.getUniqueId()).equals("Lust") || join.getClassList().get(player.getUniqueId()).equals("Greed") || join.getClassList().get(player.getUniqueId()).equals("Envy")) {
						event.setDamage(event.getFinalDamage() / 100 * (100 + join.getRDList().get(player.getUniqueId()) * 4.5) * arrows.get(arrow.getUniqueId()));
					}
					else if(join.getClassList().get(player.getUniqueId()).equals("Gluttony") || join.getClassList().get(player.getUniqueId()).equals("Sloth")) {
						event.setDamage(event.getFinalDamage() / 100 * (100 + join.getRDList().get(player.getUniqueId()) * 1.5) * arrows.get(arrow.getUniqueId()));
					}
					else {
						event.setDamage(event.getFinalDamage() / 100 * (100 + join.getRDList().get(player.getUniqueId()) * 3) * arrows.get(arrow.getUniqueId()));
					}
					
				}
			}
		}
	}
}
