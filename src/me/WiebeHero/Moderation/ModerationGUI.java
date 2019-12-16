package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.DFPlayer;

public class ModerationGUI implements Listener{
	DFPlayer method = new DFPlayer();
	public HashMap<UUID, Integer> offenseList = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> reasonList = new HashMap<UUID, Integer>();
	public void PunishMenu(Player reporter, Player offender) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish: &c" + offender.getName())));
		i.setItem(0, nothing());
		i.setItem(1, this.createDisplayItem(
				Material.BOOK, 
				"&cBan", 
				"&7--------------------"
				+ "&7Click this to go to the ban menu."
				+ "&7--------------------" 
		));
		i.setItem(2, this.createDisplayItem(
				Material.BOOKSHELF, 
				"&bHistory", 
				"&7--------------------"
				+ "&7Click this to go to the history menu."
				+ "&7This shows a menu of previous punishments."
				+ "&7--------------------" 
		));
		i.setItem(3, this.createDisplayItem(
				Material.PAPER, 
				"&bMute", 
				"&7--------------------"
				+ "&7Click this to go to the mute menu."
				+ "&7--------------------" 
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void BanChoice(Player reporter, Player offender) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish: &c" + offender.getName())));
		i.setItem(0, nothing());
		i.setItem(1, this.createDisplayItem(
				Material.ENCHANTED_BOOK, 
				"&cBan", 
				"&7--------------------"
				+ "&7Click this to ban &6" + offender.getName()
				+ "&7--------------------" 
		));
		i.setItem(2, nothing());
		i.setItem(3, this.createDisplayItem(
				Material.BOOK, 
				"&cUnban", 
				"&7--------------------"
				+ "&7Click this to unban &6" + offender.getName()
				+ "&7--------------------" 
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void BanReason(Player reporter, Player offender) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 36, (new CCT().colorize("&6Punish Reason: &c" + offender.getName())));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.createDisplayItem(
				Material.DIAMOND_ORE, 
				"&cXray"
		));
		i.setItem(11, this.createDisplayItem(
				Material.RABBIT_FOOT, 
				"&cBHop"
		));
		i.setItem(12, this.createDisplayItem(
				Material.FEATHER, 
				"&cFly"
		));
		i.setItem(13, this.createDisplayItem(
				Material.REDSTONE_BLOCK, 
				"&cKill Aura"
		));
		i.setItem(14, this.createDisplayItem(
				Material.REDSTONE, 
				"&cAuto Clicker"
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(26, nothing());
		i.setItem(27, nothing());
		i.setItem(28, nothing());
		i.setItem(29, nothing());
		i.setItem(30, nothing());
		i.setItem(31, nothing());
		i.setItem(32, nothing());
		i.setItem(33, nothing());
		i.setItem(34, nothing());
		i.setItem(35, nothing());
		reporter.openInventory(i);
	}
	public void MuteChoice(Player reporter, Player offender) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish: &c" + offender.getName())));
		i.setItem(0, nothing());
		i.setItem(1, this.createDisplayItem(
				Material.ENCHANTED_BOOK, 
				"&cMute", 
				"&7--------------------"
				+ "&7Click this to mute &6" + offender.getName()
				+ "&7--------------------" 
		));
		i.setItem(2, nothing());
		i.setItem(3, this.createDisplayItem(
				Material.BOOK, 
				"&cUnmute", 
				"&7--------------------"
				+ "&7Click this to unmute &6" + offender.getName()
				+ "&7--------------------" 
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void MuteReason(Player reporter, Player offender) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 36, (new CCT().colorize("&6Punish Reason: &c" + offender.getName())));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.createDisplayItem(
				Material.BARRIER, 
				"&cCursing"
		));
		i.setItem(11, this.createDisplayItem(
				Material.BROWN_WOOL, 
				"&cRacism"
		));
		i.setItem(12, this.createDisplayItem(
				Material.PINK_WOOL, 
				"&cSexism"
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(26, nothing());
		i.setItem(27, nothing());
		i.setItem(28, nothing());
		i.setItem(29, nothing());
		i.setItem(30, nothing());
		i.setItem(31, nothing());
		i.setItem(32, nothing());
		i.setItem(33, nothing());
		i.setItem(34, nothing());
		i.setItem(35, nothing());
		reporter.openInventory(i);
	}
	public void SpawnerCreate(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Spawner Create")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, nothing());
		i.setItem(11, nothing());
		if(player.getInventory().getItem(3) == null) {
			i.setItem(12, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					"",
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					"",
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					"",
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					"",
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					"",
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(16, player.getInventory().getItem(7));
		}
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void SpawnerEntity(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Spawner Type")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.createDisplayItem(
				Material.ZOMBIE_SPAWN_EGG, 
				"&fZombie",
				"",
				"EntityType",
				EntityType.ZOMBIE
		));
		i.setItem(11, this.createDisplayItem(
				Material.SKELETON_SPAWN_EGG, 
				"&fSkeleton",
				"",
				"EntityType",
				EntityType.SKELETON
		));
		i.setItem(12, this.createDisplayItem(
				Material.ZOMBIE_PIGMAN_SPAWN_EGG, 
				"&fZombie Pigmen",
				"",
				"EntityType",
				EntityType.PIG_ZOMBIE
		));
		i.setItem(13, this.createDisplayItem(
				Material.SPIDER_SPAWN_EGG, 
				"&fSpider",
				"",
				"EntityType",
				EntityType.SPIDER
		));
		i.setItem(14, this.createDisplayItem(
				Material.HUSK_SPAWN_EGG, 
				"&fHusk",
				"",
				"EntityType",
				EntityType.HUSK
		));
		i.setItem(15, this.createDisplayItem(
				Material.STRAY_SPAWN_EGG, 
				"&fStray",
				"",
				"EntityType",
				EntityType.STRAY
		));
		i.setItem(16, this.createDisplayItem(
				Material.BLAZE_SPAWN_EGG, 
				"&fBlaze",
				"",
				"EntityType",
				EntityType.BLAZE
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void SpawnerTier(Player reporter) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Spawner Tier")));
		i.setItem(0, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 1",
				"",
				"Tier",
				1
		));
		i.setItem(1, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 2",
				"",
				"Tier",
				2
		));
		i.setItem(2, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 3",
				"",
				"Tier",
				3
		));
		i.setItem(3, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 4",
				"",
				"Tier",
				4
		));
		i.setItem(4, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 5",
				"",
				"Tier",
				5
		));
		reporter.openInventory(i);
	}
	public void LootCreate(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Loot Create")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, nothing());
		i.setItem(11, nothing());
		if(player.getInventory().getItem(3) == null) {
			i.setItem(12, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					"",
					"LootSlot"
			));
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					"",
					"LootSlot"
			));
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					"",
					"LootSlot"
			));
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					"",
					"LootSlot"
			));
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					"",
					"LootSlot"
			));
		}
		else {
			i.setItem(16, player.getInventory().getItem(7));
		}
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void LootRadius(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Loot Radius")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 1",
				"",
				"Radius",
				1
		));
		i.setItem(11, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 2",
				"",
				"Radius",
				2
		));
		i.setItem(12, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 3",
				"",
				"Radius",
				3
		));
		i.setItem(13, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 4",
				"",
				"Radius",
				4
		));
		i.setItem(14, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 5",
				"",
				"Radius",
				5
		));
		i.setItem(15, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 8",
				"",
				"Radius",
				8
		));
		i.setItem(16, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 10",
				"",
				"Radius",
				10
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void LootTier(Player reporter) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Loot Tier")));
		i.setItem(0, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 1",
				"",
				"Tier",
				1
		));
		i.setItem(1, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 2",
				"",
				"Tier",
				2
		));
		i.setItem(2, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 3",
				"",
				"Tier",
				3
		));
		i.setItem(3, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 4",
				"",
				"Tier",
				4
		));
		i.setItem(4, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 5",
				"",
				"Tier",
				5
		));
		reporter.openInventory(i);
	}
	public void InventoryTeleport(Player player, int currentpage) {
		int size = Bukkit.getOnlinePlayers().size();
		int currentPage = currentpage;
		int maxPage = size / 35;
		if(maxPage == 0) {
			maxPage = 1;
		}
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Teleport: " + currentPage + " / " + maxPage)));
		//-----------------------------------------------------------------------------------------------------------------------------------------
		//GUI
		//-----------------------------------------------------------------------------------------------------------------------------------------
		i.setItem(36, nothing());
		i.setItem(37, nothing());
		i.setItem(38, nothing());
		i.setItem(39, nothing());
		i.setItem(40, nothing());
		i.setItem(41, nothing());
		i.setItem(42, nothing());
		i.setItem(43, nothing());
		i.setItem(44, nothing());
		i.setItem(48, previousPage());
		i.setItem(49, close());
		i.setItem(50, nextPage());
		int count = 0 + 35 * currentPage;
		int invSlot = 0;
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(count <= 35 * currentPage && count >= 35 * (currentPage - 1)) {
				if(p != player) {
					ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
					item.setItemMeta(meta);
					i.setItem(invSlot, item);
					invSlot++;
					count++;
				}
			}
			else {
				invSlot = 0;
				count = 0;
				break;
			}
		}
		player.openInventory(i);
	}
	public void InfoMenu(Player clicker, Player clicked) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Info")));
		//-----------------------------------------------------------------------------------------------------------------------------------------
		//GUI
		//-----------------------------------------------------------------------------------------------------------------------------------------
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(13, this.dfPlayerItem(clicked));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		
	}
	public ItemStack dfPlayerItem(Player p) {
		DFPlayer dfPlayer = method.getPlayer(p);
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7--------------------"));
		lore.add(new CCT().colorize("&7Class: &6" + dfPlayer.getPlayerClass()));
		lore.add(new CCT().colorize("&7Level: &b" + dfPlayer.getLevel()));
		lore.add(new CCT().colorize("&7XP to levelup: &b" + (dfPlayer.getExperience() - dfPlayer.getMaxExperience())));
		lore.add(new CCT().colorize("&7--------------------"));
		lore.add(new CCT().colorize("&7Attack Damage: &6"));
		lore.add(new CCT().colorize("&7Attack Speed: &6"));
		lore.add(new CCT().colorize("&7Critical Chance: &6"));
		lore.add(new CCT().colorize("&7Ranged Damage: &6"));
		lore.add(new CCT().colorize("&7Health: &6"));
		lore.add(new CCT().colorize("&7Defense: &6"));
		lore.add(new CCT().colorize("&7--------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public void ExamineMenu(Player clicker, Player clicked) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&6Examine")));
		PlayerInventory inv = clicked.getInventory();
		ItemStack stack[] = inv.getArmorContents();
		for(int n = 0; n <= 3; n++) {
			if(stack[n] != null || stack[n].getType() != Material.AIR) {
				i.setItem(n, stack[n]);
			}
			else {
				i.setItem(n, nothing());
			}
		}
		if(inv.getItemInOffHand() != null) {
			i.setItem(4, inv.getItemInOffHand());
		}
		else {
			i.setItem(4, nothing());
		}
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		ItemStack content[] = inv.getContents();
		for(int n = 9; n <= 53; n++) {
			i.setItem(n, content[n - 9]);
		}
		clicker.openInventory(i);
	}
	public ItemStack createDisplayItem(Material mat, String name, String loreS) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		ArrayList<String> lore = new ArrayList<String>();
		String loreL[] = loreS.split("//");
		for(int i = 0; i < loreL.length; i++) {
			lore.add(new CCT().colorize(loreL[i]));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name, String loreS, String tag) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		ArrayList<String> lore = new ArrayList<String>();
		String loreL[] = loreS.split("//");
		for(int i = 0; i < loreL.length; i++) {
			lore.add(new CCT().colorize(loreL[i]));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString(tag, "");
		item = tempItem.getItem();
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name, String loreS, String tag, String tagValue) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		ArrayList<String> lore = new ArrayList<String>();
		String loreL[] = loreS.split("//");
		for(int i = 0; i < loreL.length; i++) {
			lore.add(new CCT().colorize(loreL[i]));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString(tag, tagValue);
		item = tempItem.getItem();
		return item;
	}

	public ItemStack createDisplayItem(Material mat, String name, String loreS, String tag, int tagValue) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		ArrayList<String> lore = new ArrayList<String>();
		String loreL[] = loreS.split("//");
		for(int i = 0; i < loreL.length; i++) {
			lore.add(new CCT().colorize(loreL[i]));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setInteger(tag, tagValue);
		item = tempItem.getItem();
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name, String loreS, String tag, Object tagValue) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		ArrayList<String> lore = new ArrayList<String>();
		String loreL[] = loreS.split("//");
		for(int i = 0; i < loreL.length; i++) {
			lore.add(new CCT().colorize(loreL[i]));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setObject(tag, tagValue);
		item = tempItem.getItem();
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack nothing() {
		ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName("");
		i.setItemMeta(m);
		return i;
	}
	public ItemStack close() {
		ItemStack i = new ItemStack(Material.BARRIER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&4&lClose"));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack nextPage() {
		ItemStack i = new ItemStack(Material.PAPER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&6&lNext Page"));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack previousPage() {
		ItemStack i = new ItemStack(Material.PAPER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&6&lPrevious Page"));
		i.setItemMeta(m);
		return i;
	}
}
