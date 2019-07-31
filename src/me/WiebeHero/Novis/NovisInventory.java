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

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class NovisInventory extends NovisRewards implements Listener{
	public Set<String> check = new HashSet<String>();
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	BukkitTask bt1;
	BukkitTask bt2;
	BukkitTask bt3;
	BukkitTask bt4;
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Common
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory1(Player player, ArrayList<ItemStack> finalLootList, String rarity) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		bt1 = new BukkitRunnable() {
			int counter = 17;
			@Override
			public void run() {
				int finalRewards = new Random().nextInt(finalLootList.size());
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
					i.setItem(9, emptyVoid(player));
					i.setItem(10, finalLootList.get(randomNumber(rarity)));
					i.setItem(11, finalLootList.get(randomNumber(rarity)));
					i.setItem(12, finalLootList.get(randomNumber(rarity)));
					i.setItem(13, finalLootList.get(randomNumber(rarity)));
					i.setItem(14, finalLootList.get(randomNumber(rarity)));
					i.setItem(15, finalLootList.get(randomNumber(rarity)));
					i.setItem(16, finalLootList.get(randomNumber(rarity)));
					i.setItem(17, emptyVoid(player));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.4);
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						bt2 = new BukkitRunnable() {
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
									i.setItem(9, emptyVoid(player));
									i.setItem(10, emptyVoid(player));
									i.setItem(11, finalLootList.get(randomNumber(rarity)));
									i.setItem(12, finalLootList.get(randomNumber(rarity)));
									i.setItem(13, finalLootList.get(randomNumber(rarity)));
									i.setItem(14, finalLootList.get(randomNumber(rarity)));
									i.setItem(15, finalLootList.get(randomNumber(rarity)));
									i.setItem(16, emptyVoid(player));
									i.setItem(17, emptyVoid(player));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.6);
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										bt3 = new BukkitRunnable() {
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
													i.setItem(9, emptyVoid(player));
													i.setItem(10, emptyVoid(player));
													i.setItem(11, emptyVoid(player));
													i.setItem(12, finalLootList.get(randomNumber(rarity)));
													i.setItem(13, finalLootList.get(randomNumber(rarity)));
													i.setItem(14, finalLootList.get(randomNumber(rarity)));
													i.setItem(15, emptyVoid(player));
													i.setItem(16, emptyVoid(player));
													i.setItem(17, emptyVoid(player));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.8);
													}
													counter--;
													if(counter == 0) {
														cancel();
														bt4 = new BukkitRunnable() {
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
																i.setItem(9, emptyVoid(player));
																i.setItem(10, emptyVoid(player));
																i.setItem(11, emptyVoid(player));
																i.setItem(12, emptyVoid(player));
																i.setItem(13, finalLootList.get(randomNumber(rarity)));
																i.setItem(14, emptyVoid(player));
																i.setItem(15, emptyVoid(player));
																i.setItem(16, emptyVoid(player));
																i.setItem(17, emptyVoid(player));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																for(Player victim1 : Bukkit.getOnlinePlayers()) {
																	((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
																}
																cancel();
																player.getInventory().addItem(finalLootList.get(finalRewards));
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
																			player.closeInventory();
																		}
																	}
																}.runTaskLater(CustomEnchantments.getInstance(), 0L);
															}
														}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 16L);
													}
												}
												else {
													cancel();
													player.getInventory().addItem(finalLootList.get(finalRewards));
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 12L);
									}
								}
								else {
									cancel();
									player.getInventory().addItem(finalLootList.get(finalRewards));
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6L);
					}				
				}
				else {
					cancel();
					player.getInventory().addItem(finalLootList.get(finalRewards));
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
				ItemStack item = player.getInventory().getItemInMainHand();
				LivingEntity novis = (LivingEntity) event.getRightClicked();
				if(novis.getName().contains(ChatColor.stripColor("Novis"))) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasDisplayName()){
								if(!(check.contains(player.getName()))) {
									if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Common Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player, listSelection("Common"), "Common");
										check.add(player.getName());
										CustomEnchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 20L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Rare Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player, listSelection("Rare"), "Rare");
										check.add(player.getName());
										CustomEnchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 20L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Epic Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player, listSelection("Epic"), "Epic");
										check.add(player.getName());
										CustomEnchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 20L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Legendary Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player, listSelection("Legendary"), "Legendary");
										check.add(player.getName());
										CustomEnchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 20L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Mythic Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player, listSelection("Mythic"), "Mythic");
										check.add(player.getName());
										CustomEnchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 20L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Heroic Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player, listSelection("Heroic"), "Heroic");
										check.add(player.getName());
										CustomEnchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 20L);
									}
								}
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
		ItemStack item = event.getCurrentItem();
		InventoryView open = event.getView();
		if(open == null) {
			return;
		}
		if(open.getTitle().contains(ChatColor.stripColor("Decrypting "))) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Random Reward Selection
	//--------------------------------------------------------------------------------------------------------------------
	public int randomNumber(String s) {
		int number = 0;
		if(s.contains("Common")) {
			number = new Random().nextInt(NovisRewards.rewards1.size());
		}
		if(s.contains("Rare")) {
			number = new Random().nextInt(NovisRewards.rewards2.size());
		}
		if(s.contains("Epic")) {
			number = new Random().nextInt(NovisRewards.rewards3.size());
		}
		if(s.contains("Legendary")) {
			number = new Random().nextInt(NovisRewards.rewards4.size());
		}
		if(s.contains("Mythic")) {
			number = new Random().nextInt(NovisRewards.rewards5.size());
		}
		if(s.contains("Heroic")) {
			number = new Random().nextInt(NovisRewards.rewards6.size());
		}
		return number;
	}
	public ArrayList<ItemStack> listSelection(String s) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		if(s.contains("Common")) {
			list = NovisRewards.rewards1;
		}
		if(s.contains("Rare")) {
			list = NovisRewards.rewards2;
		}
		if(s.contains("Epic")) {
			list = NovisRewards.rewards3;
		}
		if(s.contains("Legendary")) {
			list = NovisRewards.rewards4;
		}
		if(s.contains("Mythic")) {
			list = NovisRewards.rewards5;
		}
		if(s.contains("Heroic")) {
			list = NovisRewards.rewards6;
		}
		return list;
	}
}
