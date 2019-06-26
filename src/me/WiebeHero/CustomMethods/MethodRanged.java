package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.ClassC;
import Skills.SkillJoin;
import Skills.Enums.Classes;

public class MethodRanged {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateRanged(Player p) {
		if(p != null) {
			join.getRDCalList().put(p.getUniqueId(), 100.00);
			double amount = join.getRDCalList().get(p.getUniqueId());
			if(c.getClass(p) == Classes.LUST || c.getClass(p) == Classes.GREED || c.getClass(p) == Classes.ENVY) {
				amount = amount + 4.5 * join.getRDList().get(p.getUniqueId());
			}
			else if(c.getClass(p) == Classes.GLUTTONY || c.getClass(p) == Classes.SLOTH) {
				amount = amount + 1.5 * join.getRDList().get(p.getUniqueId());
			}
			else {
				amount = amount + 3.0 * join.getRDList().get(p.getUniqueId());
			}
			join.getRDCalList().put(p.getUniqueId(), amount);
		}
	}
}
