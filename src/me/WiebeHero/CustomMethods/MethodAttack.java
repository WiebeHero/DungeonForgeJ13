package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.SkillJoin;

public class MethodAttack {
	SkillJoin join = new SkillJoin();
	public void updateAttack(Player p) {
		if(p != null) {
			join.getADCalList().put(p.getUniqueId(), 100.0);
			double attackAmount = join.getADCalList().get(p.getUniqueId());
			if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
				attackAmount = attackAmount + join.getADList().get(p.getUniqueId()) * 3.75;
			}
			else if(join.getClassList().get(p.getUniqueId()).equals("Lust") || join.getClassList().get(p.getUniqueId()).equals("Gluttony")) {
				attackAmount = attackAmount + join.getADList().get(p.getUniqueId()) * 1.25;
			}
			else {
				attackAmount = attackAmount + join.getADList().get(p.getUniqueId()) * 2.5;
			}
			join.getADCalList().put(p.getUniqueId(), attackAmount);
		}
	}
}
