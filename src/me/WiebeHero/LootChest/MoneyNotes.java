package me.WiebeHero.LootChest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.tr7zw.nbtapi.NBTItem;
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
				NBTItem i = new NBTItem(player.getInventory().getItemInMainHand());
				if(i.hasKey("Money")) {
					int money = i.getInteger("Money");
					DFPlayer dfPlayer = dfManager.getEntity(player);
					dfPlayer.addMoney(money);
					score.updateScoreboard(player);
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
				}
			}
		}
	}
}
