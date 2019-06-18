package me.WiebeHero.CustomMethods;

import org.bukkit.entity.Player;

import Skills.SkillJoin;

public class MethodAttack {
	SkillJoin join = new SkillJoin();
	public void updateAttack(Player p, double extra) {
		if(extra != 0.00) {
			if(p != null) {
				join.getADCalList().put(p.getUniqueId(), join.getADCalList().get(p.getUniqueId()) + extra);
			}
		}
	}
}
