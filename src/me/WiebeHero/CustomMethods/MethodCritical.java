package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;

public class MethodCritical {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateCriticalChance(Player p) {
		if(p != null) {
			join.getCCCalList().put(p.getUniqueId(), 0.00);
			double amount = join.getCCCalList().get(p.getUniqueId());
			if(c.getClass(p) == Classes.WRATH || c.getClass(p) == Classes.SLOTH) {
				amount = amount + 0.75 * join.getCCList().get(p.getUniqueId());
			}
			else if(c.getClass(p) == Classes.PRIDE || c.getClass(p) == Classes.ENVY) {
				amount = amount + 0.25 * join.getCCList().get(p.getUniqueId());
			}
			else {
				amount = amount + 0.50 * join.getCCList().get(p.getUniqueId());
			}
			join.getCCCalList().put(p.getUniqueId(), amount);
		}
	}
}
