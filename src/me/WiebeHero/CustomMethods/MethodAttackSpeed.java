package me.WiebeHero.CustomMethods;

import java.util.UUID;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;

public class MethodAttackSpeed {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateAttackSpeed(UUID id) {
		join.getASCalList().put(id, 100.00);
		double amount = join.getASCalList().get(id);
		if(c.getClass(id) == Classes.PRIDE || c.getClass(id) == Classes.GREED) {
			amount = amount + 0.60 * join.getASList().get(id);
		}
		else if(c.getClass(id) == Classes.SLOTH || c.getClass(id) == Classes.ENVY) {
			amount = amount + 0.20 * join.getASList().get(id);
		}
		else {
			amount = amount + 0.40 * join.getASList().get(id);
		}
		join.getASCalList().put(id, amount);
	}
}
