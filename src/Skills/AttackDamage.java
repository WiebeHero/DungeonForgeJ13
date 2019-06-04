package Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class AttackDamage implements Listener{
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void onDamageEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(event.getCause() == DamageCause.ENTITY_ATTACK) {
				int level = join.getADList().get(player.getUniqueId());
				double calc = event.getFinalDamage() / 100 * (100 + level * 2.5);
				if(join.getClassList().get(player.getUniqueId()).equals("Wrath") || join.getClassList().get(player.getUniqueId()).equals("Envy")) {
					calc = event.getFinalDamage() / 100 * (100 + level * 3.75);
				}
				else if(join.getClassList().get(player.getUniqueId()).equals("Lust") || join.getClassList().get(player.getUniqueId()).equals("Gluttony")) {
					calc = event.getFinalDamage() / 100 * (100 + level * 1.25);
				}
				event.setDamage(calc);
			}
		}
	}
}
