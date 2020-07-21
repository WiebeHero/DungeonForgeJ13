package me.WiebeHero.LootChest;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
			ItemStack stack = player.getInventory().getItemInMainHand();
			if(stack != null && stack.getType() != Material.AIR) {
				NBTItem i = new NBTItem(player.getInventory().getItemInMainHand());
				if(i.hasKey("Money")) {
					int money1 = i.getInteger("Money");
					double money2 = i.getDouble("Money");
					DFPlayer dfPlayer = dfManager.getEntity(player);
					if(money1 > 0) {
						dfPlayer.addMoney(money1);
					}
					else if(money2 > 0.00) {
						dfPlayer.addMoney(money2);
					}
					score.updateScoreboard(player);
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
				}
				else if(i.hasKey("RandomMoney")) {
					int min = i.getInteger("Min");
					int max = i.getInteger("Max");
					int money = new Random().nextInt(max) + min;
					DFPlayer dfPlayer = dfManager.getEntity(player);
					dfPlayer.addMoney(money);
					score.updateScoreboard(player);
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
				}
			}
		}
	}
}
