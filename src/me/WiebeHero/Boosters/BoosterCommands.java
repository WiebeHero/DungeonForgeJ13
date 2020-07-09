package me.WiebeHero.Boosters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.MoreStuff.CombatTag;

public class BoosterCommands implements CommandExecutor{
	public String boosters = "boosters";
	private BoosterInventory bInv;
	public BoosterCommands(BoosterInventory bInv) {
		this.bInv = bInv;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				if(cmd.getName().equalsIgnoreCase(boosters)) {
					if(args.length == 0) {
						bInv.ActiveBoosters(player);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /boosters"));
					}
				}
			}
			else {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command while in combat!"));
			}
		}
		return false;
	}
}
