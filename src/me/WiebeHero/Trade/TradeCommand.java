package me.WiebeHero.Trade;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class TradeCommand implements CommandExecutor{
	private TradeMenu menu;
	private TradeEvents events;
	private RankedManager rManager;
	public String trade = "trade";
	private HashMap<UUID, UUID> tradeList = new HashMap<UUID, UUID>();
	public TradeCommand(TradeMenu menu, TradeEvents events, RankedManager rManager) {
		this.menu = menu;
		this.events = events;
		this.rManager = rManager;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(trade)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
					if(args.length == 1) {
						if(Bukkit.getPlayer(args[0]) != null) {
							Player other = Bukkit.getPlayer(args[0]);
							if(player != other) { 
								if(player.getLocation().distance(other.getLocation()) <= 15.00) {
									if(tradeList.containsKey(other.getUniqueId())) {
										if(tradeList.get(other.getUniqueId()).equals(player.getUniqueId())) {
											menu.TradeOverview(other, player);
											events.addTrading(other.getUniqueId(), player.getUniqueId());
											tradeList.remove(other.getUniqueId());
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /trade (Player Name)"));
										}
									}
									else {
										tradeList.put(player.getUniqueId(), other.getUniqueId());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTrade request sent to &6" + other.getName() + "!"));
										other.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas requested to trade with you! Type: /trade &6" + player.getName() + " &ato start trading!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou must be closer to the player you want to trade with!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't trade with yourself!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /trade (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command while in combat!"));
				}
			}
		}
		return false;
	}
}
