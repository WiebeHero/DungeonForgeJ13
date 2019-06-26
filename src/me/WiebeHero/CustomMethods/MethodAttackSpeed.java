package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.ClassC;
import Skills.SkillJoin;
import Skills.Enums.Classes;

public class MethodAttackSpeed {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateAttackSpeed(Player p) {
		if(p != null) {
			join.getASCalList().put(p.getUniqueId(), 100.00);
			double amount = join.getASCalList().get(p.getUniqueId());
			if(c.getClass(p) == Classes.PRIDE || c.getClass(p) == Classes.GREED) {
				amount = amount + 0.60 * join.getASList().get(p.getUniqueId());
			}
			else if(c.getClass(p) == Classes.SLOTH || c.getClass(p) == Classes.ENVY) {
				amount = amount + 0.20 * join.getASList().get(p.getUniqueId());
			}
			else {
				amount = amount + 0.40 * join.getASList().get(p.getUniqueId());
			}
			join.getASCalList().put(p.getUniqueId(), amount);
		}
	}
}
