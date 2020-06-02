package me.WiebeHero.CapturePoints;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;

public class CapturePointCommand implements CommandExecutor{
	
	private CapturePointMenu cpMenu;
	public String capturepoints = "capturepoints";
	public CapturePointCommand(CapturePointMenu cpMenu) {
		this.cpMenu = cpMenu;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(capturepoints)) {
				if(args.length == 0) {
					cpMenu.CapturePointOverview(player);
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /capturepoints or /cp"));
				}
			}
		}
		return false;
	}
}
