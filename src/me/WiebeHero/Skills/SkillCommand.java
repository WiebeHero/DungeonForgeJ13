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
	private DFPlayerManager dfManager;
	private RankedManager rManager;
	public String skill = "skill";
	public String skills = "skills";
	public String level = "level";
	public SkillCommand(SkillMenu menu, DFPlayerManager dfManager, RankedManager rManager) {
		this.menu = menu;
		this.dfManager = dfManager;
		this.rManager = rManager;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(cmd.getName().equalsIgnoreCase(skill) || cmd.getName().equalsIgnoreCase(skills)) {
				if(args.length == 0) {
					menu.SkillMenuInv(player);
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /skill or /skills"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(level)) {
				if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
					if(args.length == 3) {
						if(args[0].equalsIgnoreCase("set")) {
							Player p = Bukkit.getPlayer(args[1]);
							if(p != null) {
								try {
									int level = Integer.parseInt(args[2]);
									if(level > 0 && level <= 100) {
										dfManager.softResetEntity(p);
										DFPlayer dfPlayer = dfManager.getEntity(p);
										for(int i = 1; i < level; i++) {
									        if(dfPlayer.getLevel() < 100) {
												dfPlayer.addLevel(1);
												dfPlayer.setExperience(0);
												dfPlayer.setMaxExperience((int)(double)(dfPlayer.getMaxExperience() / 100.00 * 120.00));
												dfPlayer.addSkillPoints(3);
											}
								        }
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level can only be between 1 and 100!"));
									}
								}
								catch(NumberFormatException ex) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level you gave is not a valid number!"));
									ex.printStackTrace();
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
							}
						}
					}
				}
			}
		}
		return false;
	}
}
