package Skills;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class HealthH implements Listener{
	SkillJoin join = new SkillJoin();
	public void changeHealth(Player p) {
		int level = 0;
		if(join.getHHList().get(p.getUniqueId()) != null) {
			level = level + join.getHHList().get(p.getUniqueId());
		}
		if(join.getClassList().get(p.getUniqueId()) != null) {
			if(join.getClassList().get(p.getUniqueId()).equals("Lust") || join.getClassList().get(p.getUniqueId()).equals("Gluttony")) {
				double health = Math.round(join.getHHCalList().get(p.getUniqueId()) / 100.00 * (100.00 + level * 7.5));
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
			}
			else if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Pride")) {
				double health = Math.round(join.getHHCalList().get(p.getUniqueId()) / 100.00 * (100.00 + level * 2.5));
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
			}
			else {
				double health = Math.round(join.getHHCalList().get(p.getUniqueId()) / 100.00 * (100.00 + level * 5.0));
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
			}
		}
		else {
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.00);
		}
	}
}
