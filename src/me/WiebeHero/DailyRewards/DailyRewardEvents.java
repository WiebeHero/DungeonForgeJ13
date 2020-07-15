package me.WiebeHero.DailyRewards;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class DailyRewardEvents implements Listener{
	
	private DailyRewardManager dManager;
	private DailyRewardMenu menu;
	private RankedManager rManager;
	private DailyRewardRoll dailyRoll;
	
	public DailyRewardEvents(DailyRewardManager dManager, DailyRewardMenu menu, RankedManager rManager, DailyRewardRoll dailyRoll) {
		this.dManager = dManager;
		this.menu = menu;
		this.rManager = rManager;
		this.dailyRoll = dailyRoll;
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!this.dManager.containsDailyReward(player.getUniqueId())) {
			this.dManager.addDailyReward(new DailyReward(player.getUniqueId()));
		}
	}
	
	@EventHandler
	public void xpTraderOpen(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof HumanEntity) {
				Player player = event.getPlayer();
				HumanEntity shop = (HumanEntity) event.getRightClicked();
				if(shop.getCustomName() != null) {
					if(shop.getCustomName().contains(ChatColor.stripColor("Daily Rewards"))) {
						this.menu.DailyMenu(player);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void dailyInventory(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		InventoryView view = player.getOpenInventory();
		Inventory inv = event.getInventory();
		ItemStack stack = event.getCurrentItem();
		if(view != null && inv != null) {
			if(view.getTitle().contains("Daily Rewards")) {
				event.setCancelled(true);
				if(stack != null) {
					NBTItem item = new NBTItem(stack);
					if(item.hasKey("Daily")) {
						if(this.dManager.containsDailyReward(player.getUniqueId())) {
							DailyReward reward = this.dManager.getDailyReward(player.getUniqueId());
							if(stack.getType() == Material.CHEST_MINECART) {
								Calendar cal = Calendar.getInstance();
								reward.setLastDaily(cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR));
								this.dailyRoll.RewardDaily(player);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aDaily reward claimed!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have already claimed your daily reward today!"));
							}
						}
					}
					else if(item.hasKey("Weekly")) {
						if(this.dManager.containsDailyReward(player.getUniqueId())) {
							DailyReward reward = this.dManager.getDailyReward(player.getUniqueId());
							if(stack.getType() == Material.CHEST_MINECART) {
								reward.setLastWeekly(System.currentTimeMillis() + 604800000L);
								this.dailyRoll.RewardWeekly(player);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aClaiming weekly reward..."));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have already claimed your daily reward today!"));
							}
						}
					}
					else if(item.hasKey("Monthly")) {
						if(this.dManager.containsDailyReward(player.getUniqueId()) && this.rManager.contains(player.getUniqueId())) {
							DailyReward rewards = this.dManager.getDailyReward(player.getUniqueId());
							RankedPlayer rPlayer = this.rManager.getRankedPlayer(player.getUniqueId());
							if(stack.getType() == Material.CHEST_MINECART) {
								if(System.currentTimeMillis() >= rewards.getLastMonthly()) {
									rewards.setLastMonthly(System.currentTimeMillis() + 2592000000L);
								}
								rewards.addClaimedSlot(event.getSlot());
								this.dailyRoll.RewardMonthly(player, rPlayer.getPaidRank());
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aMonthly reward claimed!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't claim your monthly reward yet!"));
							}
						}
					}
				}
			}
		}
	}
}
