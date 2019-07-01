package me.WiebeHero.CustomMethods;

import java.util.UUID;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;

public class MethodDefense {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateDefense(UUID id) {
		join.getDFCalList().put(id, 100.00);
		double amount = join.getDFCalList().get(id);
		if(c.getClass(id) == Classes.GLUTTONY || c.getClass(id) == Classes.SLOTH || c.getClass(id) == Classes.PRIDE) {
			amount = amount + 5.0 * join.getDFList().get(id);
		}
		else if(c.getClass(id) == Classes.WRATH || c.getClass(id) == Classes.GREED) {
			amount = amount + 1.66 * join.getDFList().get(id);
		}
		else {
			amount = amount + 3.33 * join.getDFList().get(id);
		}
		join.getDFCalList().put(id, amount);
	}
}
