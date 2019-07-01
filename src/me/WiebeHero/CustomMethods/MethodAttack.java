package me.WiebeHero.CustomMethods;

import java.util.UUID;

import org.bukkit.Bukkit;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;
import net.md_5.bungee.api.ChatColor;

public class MethodAttack {
	SkillJoin join = new SkillJoin();
	ClassC c = new ClassC();
	public void updateAttack(UUID id) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Yes1");
		if(id != null) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Yes2");
			join.getADCalList().put(id, 100.0);
			double attackAmount = join.getADCalList().get(id);
			if(c.getClass(id) == Classes.WRATH || c.getClass(id) == Classes.ENVY) {
				attackAmount = attackAmount + join.getADList().get(id) * 3.75;
			}
			else if(c.getClass(id) == Classes.LUST || c.getClass(id) == Classes.GLUTTONY) {
				attackAmount = attackAmount + join.getADList().get(id) * 1.25;
			}
			else {
				attackAmount = attackAmount + join.getADList().get(id) * 2.5;
			}
			join.getADCalList().put(id, attackAmount);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "" + join.getADList().get(id));
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "" + join.getADCalList().get(id));
		}
	}
}
