package Skills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SkillCommand implements CommandExecutor{
	SkillMenu menu = new SkillMenu();
	SkillJoin join = new SkillJoin();
	public String skill = "skill";
	public String skills = "skills";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(skill) || cmd.getName().equalsIgnoreCase(skills)) {
				if(args.length == 0) {
					menu.SkillMenuInv(player);
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /skill or /skills"));
				}
			}
		}
		return false;
	}
}
