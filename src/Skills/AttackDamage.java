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
				double calc = 0.00;
				if(join.getClassList().get(player.getUniqueId()).equals("Wrath") || join.getClassList().get(player.getUniqueId()).equals("Envy")) {
					calc = event.getFinalDamage() / 100 * (join.getADCalList().get(player.getUniqueId()) + join.getADExtraList().get(player.getUniqueId()));
				}
				else if(join.getClassList().get(player.getUniqueId()).equals("Lust") || join.getClassList().get(player.getUniqueId()).equals("Gluttony")) {
					calc = event.getFinalDamage() / 100 * (join.getADCalList().get(player.getUniqueId()) + join.getADExtraList().get(player.getUniqueId()));
				}
				else {
					calc = event.getFinalDamage() / 100 * (join.getADCalList().get(player.getUniqueId()) + join.getADExtraList().get(player.getUniqueId()));
				}
				event.setDamage(calc);
			}
		}
	}
}
