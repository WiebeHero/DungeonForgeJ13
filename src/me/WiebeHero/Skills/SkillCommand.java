package me.WiebeHero.Skills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;

public class SkillCommand implements CommandExecutor{
	private SkillMenu menu;
	public String skill = "skill";
	public SkillCommand(SkillMenu menu) {
		this.menu = menu;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(skill)) {
				if(args.length == 0) {
					menu.SkillMenuInv(player);
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /skill or /skills"));
				}
			}
		}
		return false;
	}
}
