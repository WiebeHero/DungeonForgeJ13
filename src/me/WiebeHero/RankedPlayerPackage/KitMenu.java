package me.WiebeHero.RankedPlayerPackage;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;

public class KitMenu {
	private ItemStackBuilder builder;
	private RankedManager rManager;
	private RankEnum rEnum;
	public KitMenu(ItemStackBuilder builder, RankedManager rManager, RankEnum rEnum) {
		this.builder = builder;
		this.rManager = rManager;
		this.rEnum = rEnum;
	}
	public void Kits(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, 18, new CCT().colorize("&6Kits"));
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		int occupied = 0;
		for(Rank rank : rPlayer.getRanks()) {
			if(System.currentTimeMillis() >= rPlayer.getKitCooldown(rEnum.getIfPresentKit(rank.toString()))) {
				i.setItem(occupied, this.builder.constructItem(
					Material.CHEST_MINECART,
					rank.getDisplay() + " Kit",
					new String[] {
						"&7Click this to obtain the " + rank.getDisplay() + " &7kit!"
					},
					new Pair<String, Kit>("Kit", Kit.valueOf(rank.toString()))
				));
				player.openInventory(i);
			}
			else {
				long timeLeft = rPlayer.getKitCooldown(Kit.valueOf(rank.toString())) - System.currentTimeMillis();
				long diffSeconds = timeLeft / 1000 % 60;
		        long diffMinutes = timeLeft / (60 * 1000) % 60;
		        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
		        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
				i.setItem(occupied, this.builder.constructItem(
					Material.MINECART,
					rank.getDisplay() + " Kit",
					new String[] {
						"&7You cannot obtain this kit for:",
						"&6" + diffDays + " &7Days and &6" + diffHours + ":" + diffMinutes + ":" + diffSeconds
					}
				));
			}
			occupied++;
		}
		for(Entry<Kit, Boolean> entry : rPlayer.getKitUnlockList().entrySet()) {
			if(entry.getValue() == true) {
				if(System.currentTimeMillis() >= rPlayer.getKitCooldown(entry.getKey())) {
					i.setItem(occupied, this.builder.constructItem(
						Material.CHEST_MINECART,
						entry.getKey().getDisplay() + " Kit",
						new String[] {
							"&7Click this to obtain the " + entry.getKey().getDisplay() + " &7kit!"
						},
						new Pair<String, Kit>("Kit", entry.getKey())
					));
					player.openInventory(i);
				}
				else {
					long timeLeft = rPlayer.getKitCooldown(entry.getKey()) - System.currentTimeMillis();
					long diffSeconds = timeLeft / 1000 % 60;
			        long diffMinutes = timeLeft / (60 * 1000) % 60;
			        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
			        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
					i.setItem(occupied, this.builder.constructItem(
						Material.MINECART,
						entry.getKey().getDisplay() + " Kit",
						new String[] {
							"&7You cannot obtain this kit for:",
							"&6" + diffDays + " &7Days and &6" + diffHours + ":" + diffMinutes + ":" + diffSeconds
						}
					));
				}
				occupied++;
			}
			else {
				i.setItem(occupied, this.builder.constructItem(
					Material.MINECART,
					entry.getKey().getDisplay() + " Kit",
					new String[] {
						"&7You do not have this kit unlocked!"
					}
				));
				occupied++;
			}
		}
		player.openInventory(i);
	}
}
