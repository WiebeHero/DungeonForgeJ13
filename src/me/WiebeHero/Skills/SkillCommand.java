package me.WiebeHero.Skills;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class SkillCommand implements CommandExecutor{
	private SkillMenu menu;
	public String skill = "skill";
	public String skills = "skills";
	public String level = "level";
	public SkillCommand(SkillMenu menu) {
		this.menu = menu;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(skill) || cmd.getName().equalsIgnoreCase(skills)) {
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
