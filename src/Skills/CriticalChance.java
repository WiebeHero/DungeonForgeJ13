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
			int level = join.getCCList().get(player.getUniqueId());
			float i = ThreadLocalRandom.current().nextFloat() * 100;
			if(join.getClassList().get(player.getUniqueId()).equals("Wrath") || join.getClassList().get(player.getUniqueId()).equals("Sloth")) {
				if(i <= level / 100 * (100 + level * 0.75)) {
					event.setDamage(event.getFinalDamage() * 2);
				}
			}
			else if(join.getClassList().get(player.getUniqueId()).equals("Pride") || join.getClassList().get(player.getUniqueId()).equals("Envy")) {
				if(i <= level / 100 * (100 + level * 0.25)) {
					event.setDamage(event.getFinalDamage() * 2);
				}
			}
			else {
				if(i <= level / 100 * (100 + level * 0.50)) {
					event.setDamage(event.getFinalDamage() * 2);
				}
			}
		}
	}
}
