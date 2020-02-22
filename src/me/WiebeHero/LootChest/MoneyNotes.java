package me.WiebeHero.LootChest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class MoneyNotes implements Listener{
	private DFPlayerManager dfManager;
	private DFScoreboard score;
	public MoneyNotes(DFPlayerManager dfManager, DFScoreboard score) {
		this.dfManager = dfManager;
		this.score = score;
	}
	@EventHandler
	public void moneyGain(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.PAPER) {
					if(player.getInventory().getItemInMainHand().hasItemMeta()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
							if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Money Note:")) {
								String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
								name = name.replaceAll("[^\\d.]", "");
								int money = Integer.parseInt(name);
								DFPlayer dfPlayer = dfManager.getEntity(player);
								dfPlayer.addMoney(money);
								score.updateScoreboard(player);
								player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							}
						}
					}
				}
			}
		}
	}
}
