package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.ClassC;
import Skills.SkillJoin;
import Skills.Enums.Classes;

public class MethodDefense {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateDefense(Player p) {
		if(p != null) {
			join.getDFCalList().put(p.getUniqueId(), 100.00);
			double amount = join.getDFCalList().get(p.getUniqueId());
			if(c.getClass(p) == Classes.GLUTTONY || c.getClass(p) == Classes.SLOTH || c.getClass(p) == Classes.PRIDE) {
				amount = amount + 5.0 * join.getDFList().get(p.getUniqueId());
			}
			else if(c.getClass(p) == Classes.WRATH || c.getClass(p) == Classes.GREED) {
				amount = amount + 1.66 * join.getDFList().get(p.getUniqueId());
			}
			else {
				amount = amount + 3.33 * join.getDFList().get(p.getUniqueId());
			}
			join.getDFCalList().put(p.getUniqueId(), amount);
		}
	}
}
