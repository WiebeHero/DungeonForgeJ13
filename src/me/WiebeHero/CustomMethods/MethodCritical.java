package me.WiebeHero.CustomMethods;

import java.util.UUID;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;

public class MethodCritical {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateCriticalChance(UUID id) {
		join.getCCCalList().put(id, 0.00);
		double amount = join.getCCCalList().get(id);
		if(c.getClass(id) == Classes.WRATH || c.getClass(id) == Classes.SLOTH) {
			amount = amount + 0.75 * join.getCCList().get(id);
		}
		else if(c.getClass(id) == Classes.PRIDE || c.getClass(id) == Classes.ENVY) {
			amount = amount + 0.25 * join.getCCList().get(id);
		}
		else {
			amount = amount + 0.50 * join.getCCList().get(id);
		}
		join.getCCCalList().put(id, amount);
		
	}
}
