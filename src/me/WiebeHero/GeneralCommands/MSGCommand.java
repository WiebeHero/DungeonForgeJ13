package me.WiebeHero.GeneralCommands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.MoreStuff.CombatTag;

public class MSGCommand implements CommandExecutor{
	private MSGManager msgManager;
	private Methods method;
	public MSGCommand(MSGManager msgManager, Methods method) {
		this.msgManager = msgManager;
		this.method = method;
	}
	public String msg = "msg";
	public String ignore = "ignore";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(msg)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length >= 2) {
						Player p = Bukkit.getPlayer(args[0]);
						if(p != null) {
							if(p.isOnline()) {
								MSG msg = msgManager.get(p.getUniqueId());
								if(msg != null) {
									if(!msg.isInIgnore(p.getUniqueId())) {
										String message = "";
										for(int i = 1; i < args.length; i++) {
											message = message + " " + args[i];
										}
										p.sendMessage("§6" + player.getName() + " whispers to §6" + p.getName() + ": §7" + message);
										player.sendMessage("§6" + player.getName() + " whispers to §6" + p.getName() + ": §7" + message);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't send an MSG to &6" + p.getName() + " &cbecause they have you in their ignore list."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAn error has occured, please consult higher ups."));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cis not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid command usage: /msg (Player Name) (Message)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command because you are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(ignore)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 2) {
						if(args[0].equalsIgnoreCase("add")) {
							Player p = Bukkit.getPlayer(args[1]);
							if(p != null) {
								MSG msg = msgManager.get(player.getUniqueId());
								if(msg != null) {
									if(!msg.isInIgnore(p.getUniqueId())) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ahas been added to your ignore list."));
										msg.addIgnore(p.getUniqueId());
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis already in you ignore list!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAn error has occured, please consult higher ups."));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
							}
						}
						else if(args[0].equals("remove")) {
							Player temp = method.convertOfflinePlayer(args[1]);
							if(temp.getName() != null) {
								MSG msg = msgManager.get(player.getUniqueId());
								if(msg != null) {
									if(msg.isInIgnore(temp.getUniqueId())) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + temp.getName() + " &ahas been removed from your ignore list."));
										msg.removeIgnore(temp.getUniqueId());
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + temp.getName() + " &cis not in you ignore list!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAn error has occured, please consult higher ups."));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
							}
						}
						else if(args[0].equals("clear")) {
							MSG msg = msgManager.get(player.getUniqueId());
							if(msg != null) {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour ignore list has been cleared!"));
								msg.setIgnoreList(new ArrayList<UUID>());
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAn error has occured, please consult higher ups."));
							}
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid command usage: /ignore add/remove/clear (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command because you are in combat!"));
				}
			}
		}
		return true;
	}
}
