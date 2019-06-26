package Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import Skills.Enums.Classes;

public class AttackDamage implements Listener{
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	@EventHandler
	public void onDamageEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(event.getCause() == DamageCause.ENTITY_ATTACK) {
				double calc = 0.00;
				if(c.getClass(player) == Classes.WRATH || c.getClass(player) == Classes.ENVY) {
					calc = event.getFinalDamage() / 100 * (join.getADCalList().get(player.getUniqueId()) + join.getADExtraList().get(player.getUniqueId()));
				}
				else if(c.getClass(player) == Classes.LUST || c.getClass(player) == Classes.GLUTTONY) {
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
