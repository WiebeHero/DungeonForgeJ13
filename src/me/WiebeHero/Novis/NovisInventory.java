package me.WiebeHero.Novis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class NovisInventory implements Listener{
	private Set<String> check = new HashSet<String>();
	private NovisRewards rewards;
	public NovisInventory(NovisRewards rewards) {
		this.rewards = rewards;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Common
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory1(Player player, ArrayList<ItemStack> finalLootList) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		ArrayList<ItemStack> lootList = new ArrayList<ItemStack>();
		for(int i1 = 0; i1 <= 39; i1++) {
			lootList.add(finalLootList.get(new Random().nextInt(finalLootList.size())));
		}
		ItemStack finalReward = lootList.get(32);
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		new BukkitRunnable() {
			int counter = 17;
			int count = 0;
			@Override
			public void run() {
				if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, lootList.get(count));
					i.setItem(10, lootList.get(count + 1));
					i.setItem(11, lootList.get(count + 2));
					i.setItem(12, lootList.get(count + 3));
					i.setItem(13, lootList.get(count + 4));
					i.setItem(14, lootList.get(count + 5));
					i.setItem(15, lootList.get(count + 6));
					i.setItem(16, lootList.get(count + 7));
					i.setItem(17, lootList.get(count + 8));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.4F);
					if(!(counter <= 1)) {
						count++;
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, lootList.get(count));
									i.setItem(10, lootList.get(count + 1));
									i.setItem(11, lootList.get(count + 2));
									i.setItem(12, lootList.get(count + 3));
									i.setItem(13, lootList.get(count + 4));
									i.setItem(14, lootList.get(count + 5));
									i.setItem(15, lootList.get(count + 6));
									i.setItem(16, lootList.get(count + 7));
									i.setItem(17, lootList.get(count + 8));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(22, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.6F);
									if(!(counter <= 1)) {
										count++;
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, lootList.get(count));
													i.setItem(10, lootList.get(count + 1));
													i.setItem(11, lootList.get(count + 2));
													i.setItem(12, lootList.get(count + 3));
													i.setItem(13, lootList.get(count + 4));
													i.setItem(14, lootList.get(count + 5));
													i.setItem(15, lootList.get(count + 6));
													i.setItem(16, lootList.get(count + 7));
													i.setItem(17, lootList.get(count + 8));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.8F);
													if(!(counter <= 1)) {
														count++;
													}
													counter--;
													if(counter == 0) {
														cancel();
														new BukkitRunnable() {
															@Override
															public void run() {
																i.setItem(0, emptyVoid(player));
																i.setItem(1, emptyVoid(player));
																i.setItem(2, emptyVoid(player));
																i.setItem(3, emptyVoid(player));
																i.setItem(4, emptyVoid(player));
																i.setItem(5, emptyVoid(player));
																i.setItem(6, emptyVoid(player));
																i.setItem(7, emptyVoid(player));
																i.setItem(8, emptyVoid(player));
																i.setItem(9, lootList.get(count));
																i.setItem(10, lootList.get(count + 1));
																i.setItem(11, lootList.get(count + 2));
																i.setItem(12, lootList.get(count + 3));
																i.setItem(13, lootList.get(count + 4));
																i.setItem(14, lootList.get(count + 5));
																i.setItem(15, lootList.get(count + 6));
																i.setItem(16, lootList.get(count + 7));
																i.setItem(17, lootList.get(count + 8));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.0F);
																cancel();
																if(player.getInventory().firstEmpty() != -1) {
																	player.getInventory().setItem(player.getInventory().firstEmpty(), finalReward);
																}
																else {
																	player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere is not enough space in your inventory, so the item has been dropped on the ground."));
																}
															}
														}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 16L);
													}
												}
												else {
													cancel();
													if(player.getInventory().firstEmpty() != -1) {
														player.getInventory().setItem(player.getInventory().firstEmpty(), finalReward);
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere is not enough space in your inventory, so the item has been dropped on the ground."));
													}
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 12L);
									}
								}
								else {
									cancel();
									if(player.getInventory().firstEmpty() != -1) {
										player.getInventory().setItem(player.getInventory().firstEmpty(), finalReward);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere is not enough space in your inventory, so the item has been dropped on the ground."));
									}
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6L);
					}				
				}
				else {
					cancel();
					if(player.getInventory().firstEmpty() != -1) {
						player.getInventory().setItem(player.getInventory().firstEmpty(), finalReward);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere is not enough space in your inventory, so the item has been dropped on the ground."));
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 3L);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Activate Novis
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void novisActivate(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof MagmaCube) {
				Player player = event.getPlayer();
				ItemStack i = player.getInventory().getItemInMainHand();
				LivingEntity novis = (LivingEntity) event.getRightClicked();
				if(novis.getName().contains(ChatColor.stripColor("Novis"))) {
					if(i != null) {
						if(!check.contains(player.getName())) {
							if(player.getInventory().firstEmpty() != -1) {
								NBTItem item = new NBTItem(i);
								if(item.hasKey("CrystalObject")) {
									player.getInventory().getItemInMainHand().setAmount(i.getAmount() - 1);
									check.add(player.getName());
									String loottable = item.getString("Rarity");
									this.NewInventory1(player, listSelection(loottable));
									new BukkitRunnable() {
										public void run() {
											check.remove(player.getName());
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 20L);
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere is not enough space in your inventory."));
							}
						}
					}
				}
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Random GUI Color
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack emptyVoid(Player player) {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(" ");
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Prevent Clicking in Novis
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void novisPreventClick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		InventoryView open = event.getView();
		if(open == null) {
			return;
		}
		if(open.getTitle().contains(ChatColor.stripColor("Decrypting "))) {
			if(inv != null) {
				event.setCancelled(true);
			}
		}
	}
	public ArrayList<ItemStack> listSelection(String s) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		if(s.contains("Common")) {
			list = this.rewards.getCommonRewards();
		}
		else if(s.contains("Rare")) {
			list = this.rewards.getRareRewards();
		}
		else if(s.contains("Epic")) {
			list = this.rewards.getEpicRewards();
		}
		else if(s.contains("Legendary")) {
			list = this.rewards.getLegendaryRewards();
		}
		else if(s.contains("Mythic")) {
			list = this.rewards.getMythicRewards();
		}
		else if(s.contains("Heroic")) {
			list = this.rewards.getHeroicRewards();
		}
		return list;
	}
}
