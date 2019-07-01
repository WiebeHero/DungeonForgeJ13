package me.WiebeHero.CustomMethods;

import java.util.UUID;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;

public class MethodRanged {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateRanged(UUID id) {
		join.getRDCalList().put(id, 100.00);
		double amount = join.getRDCalList().get(id);
		if(c.getClass(id) == Classes.LUST || c.getClass(id) == Classes.GREED || c.getClass(id) == Classes.ENVY) {
			amount = amount + 4.5 * join.getRDList().get(id);
		}
		else if(c.getClass(id) == Classes.GLUTTONY || c.getClass(id) == Classes.SLOTH) {
			amount = amount + 1.5 * join.getRDList().get(id);
		}
		else {
			amount = amount + 3.0 * join.getRDList().get(id);
		}
		join.getRDCalList().put(id, amount);
		
	}
}
