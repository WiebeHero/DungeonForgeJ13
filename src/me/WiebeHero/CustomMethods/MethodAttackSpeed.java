package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.SkillJoin;

public class MethodAttackSpeed {
	SkillJoin join = new SkillJoin();
	public void updateAttackSpeed(Player p) {
		if(p != null) {
			join.getASCalList().put(p.getUniqueId(), 100.00);
			double amount = join.getASCalList().get(p.getUniqueId());
			if(join.getClassList().get(p.getUniqueId()).equals("Pride") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
				amount = amount + join.getASCalList().get(p.getUniqueId()) + 0.60 * join.getASList().get(p.getUniqueId());
			}
			else if(join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
				amount = amount + join.getASCalList().get(p.getUniqueId()) + 0.20 * join.getASList().get(p.getUniqueId());
			}
			else {
				amount = amount + join.getASCalList().get(p.getUniqueId()) + 0.40 * join.getASList().get(p.getUniqueId());
			}
			join.getASCalList().put(p.getUniqueId(), amount);
		}
	}
}
