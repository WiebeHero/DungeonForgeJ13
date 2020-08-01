package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;
import net.md_5.bungee.api.ChatColor;

public class KitRoll {
	
	private RankedManager rManager;
	private ItemStackBuilder builder;
	
	public KitRoll(RankedManager rManager, ItemStackBuilder builder) {
		this.rManager = rManager;
		this.builder = builder;
	}
	
	public void KitRollMenu(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Decrypting Random Kit...")));
		player.openInventory(i);
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		ArrayList<ItemStack> lootList = new ArrayList<ItemStack>();
		for(int i1 = 0; i1 <= 39; i1++) {
			Kit kit = rPlayer.getRandomNotUnlockedKit();
			String s = kit.toString();
			s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
			lootList.add(this.builder.constructItem(
					kit.getMaterialDisplay(),
					1,
					"&6" + s + " Kit",
					new ArrayList<String>(Arrays.asList(
						"&7Get this kit if the roll ends",
						"&7On this item!"
					)),
					new Pair<String, Kit>("NewKit", kit)
			));
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
																giveItem(player, finalReward);
																cancel();
															}
														}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 16L);
													}
												}
												else {
													giveItem(player, finalReward);
													cancel();
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 12L);
									}
								}
								else {
									giveItem(player, finalReward);
									cancel();
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6L);
					}				
				}
				else {
					giveItem(player, finalReward);
					cancel();
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 3L);
	}
	public void giveItem(Player player, ItemStack item) {
		NBTItem i = new NBTItem(item);
		if(i.hasKey("NewKit")) {
			RankedPlayer rPlayer = this.rManager.getRankedPlayer(player.getUniqueId());
			Kit kit = i.getObject("NewKit", Kit.class);
			String kitS = kit.toString();
			rPlayer.setKitUnlock(kit, true);
			player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have recieved the &6" + kitS.substring(0, 1).toUpperCase() + kitS.substring(1).toLowerCase() + " &a kit!"));
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
}
