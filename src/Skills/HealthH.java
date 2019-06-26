package Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomMethods.MethodHealth;

public class HealthH implements Listener{
	SkillJoin join = new SkillJoin();
	MethodHealth hh = new MethodHealth();
	public void updateHealth(Player p) {
		hh.setMaxHealth(p, 20.00 / 100.00 * join.getHHCalList().get(p.getUniqueId()) + join.getHHExtraList().get(p.getUniqueId()));
	}
}
