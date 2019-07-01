package Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomMethods.MethodHealth;

public class HealthH implements Listener{
	SkillJoin join = new SkillJoin();
	public void updateHealth(Player p) {
		MethodHealth hh = new MethodHealth();
		hh.setMaxHealth(p, 20.00 / 100.00 * (join.getHHCalList().get(p.getUniqueId()) + join.getHHExtraList().get(p.getUniqueId())));
	}
}
