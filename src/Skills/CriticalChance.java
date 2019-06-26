package Skills;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CriticalChance implements Listener{
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void criticalChance(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			float i = ThreadLocalRandom.current().nextFloat() * 100;
			if(i <= join.getCCCalList().get(player.getUniqueId()) + join.getCCCalList().get(player.getUniqueId())) {
				event.setDamage(event.getFinalDamage() * 2);
			}
		}
	}
}
