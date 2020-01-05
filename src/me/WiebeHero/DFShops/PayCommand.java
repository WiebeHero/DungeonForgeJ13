package me.WiebeHero.DFShops;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.Skills.DFPlayer;

public class PayCommand implements Listener,CommandExecutor{
	public String command = "pay";
	public String balance = "balance";
	public String money = "money";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(command)) {
				if(args.length > 0) {
					if(args.length > 1) {
						Player newPlayer = null;
						UUID uuid1 = player.getUniqueId();
						UUID uuid2 = null;
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(!p.getName().equals(player.getName())) {
								if(p.getName().equals(args[0])) {
									uuid2 = p.getUniqueId();
									newPlayer = p;
								}
							}
						}
						if(uuid2 != null) {
							DFPlayer dfPlayerMe = new DFPlayer().getPlayer(uuid1);
							DFPlayer dfPlayerThem = new DFPlayer().getPlayer(uuid2);
							double moneyMe = dfPlayerMe.getMoney();
							double moneyThem = dfPlayerThem.getMoney();
							String sAmount = args[1];
							double amount = Double.parseDouble(sAmount);
							if(amount > 0 && amount <= Double.MAX_VALUE) {
								if(amount <= moneyMe) {
									double newMoneyMe = moneyMe - amount;
									double newMoneyThem = moneyThem + amount;
									dfPlayerMe.setMoney(newMoneyMe);
									dfPlayerThem.setMoney(newMoneyThem);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have paid &6" + amount + "$ &a&lto " + newPlayer.getName() + "!"));
									newPlayer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &a&l" + player.getName() + " &ahas paid you &6" + amount + "$!"));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNot a valid amount!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cPlayer not found, or its yourself silly ;)"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to type the amount you want to pay the player."));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to type the players name after 'pay'"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(balance) || cmd.getName().equalsIgnoreCase(money)) {
				if(args.length == 0) {
					UUID uuid1 = player.getUniqueId();
					DFPlayer dfPlayer = new DFPlayer().getPlayer(uuid1);
					double moneyMe = dfPlayer.getMoney();
					player.sendMessage(new CCT().colorize("&aYou're balance is: &6" + moneyMe + "$"));
				}
				else if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						UUID uuid1 = target.getUniqueId();
						DFPlayer dfPlayer = new DFPlayer().getPlayer(uuid1);
						double moneyMe = dfPlayer.getMoney();
						player.sendMessage(new CCT().colorize("&a" + target.getName() + " balance is: &6" + moneyMe + "$"));
					}
					else {
						player.sendMessage(new CCT().colorize("&cThis player has not logged online, or is offline!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&cThere are to many arguments! Use: /bal (Player Name)"));
				}
			}
		}
		return true;
	}
}
