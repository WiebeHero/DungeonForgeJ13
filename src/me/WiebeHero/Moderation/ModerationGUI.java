package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ModerationGUI implements Listener{
	public HashMap<UUID, Integer> offenseList = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> reasonList = new HashMap<UUID, Integer>();
	public void InventoryTeleport(Player player, int currentpage) {
		int size = Bukkit.getOnlinePlayers().size();
		int currentPage = currentpage;
		int maxPage = size / 35;
		if(maxPage == 0) {
			maxPage = 1;
		}
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new ColorCodeTranslator().colorize("&6Teleport: " + currentPage + " / " + maxPage)));
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
		m.setDisplayName(new ColorCodeTranslator().colorize("&4&lClose"));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack nextPage() {
		ItemStack i = new ItemStack(Material.PAPER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new ColorCodeTranslator().colorize("&6&lNext Page"));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack previousPage() {
		ItemStack i = new ItemStack(Material.PAPER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new ColorCodeTranslator().colorize("&6&lPrevious Page"));
		i.setItemMeta(m);
		return i;
	}
	@EventHandler
	public void clickInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Teleport")) {
				event.setCancelled(true);
				if(item != null) {
					String name = item.getItemMeta().getDisplayName();
					title = ChatColor.stripColor(title);
					String split[] = title.split("/");
					String currentPageS = split[0].replaceAll("[^\\d.]", "");
					String maxPageS = split[1].replaceAll("[^\\d.]", "");
					int currentPage = Integer.parseInt(currentPageS);
					int maxPage = Integer.parseInt(maxPageS);
					if(item.getType() == Material.PLAYER_HEAD) {
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						if(meta.getOwningPlayer().getPlayer().isOnline()) {
							player.teleport(meta.getOwningPlayer().getPlayer());
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have teleported to &6" + meta.getOwningPlayer().getPlayer().getName()));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player isn't online anymore!"));
						}
					}
					else if(ChatColor.stripColor(name).equals("Next Page")) {
						if(currentPage < maxPage) {
							player.closeInventory();
							ModerationGUI gui = new ModerationGUI();
							gui.InventoryTeleport(player, currentPage + 1);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThere are no more pages then this."));
						}
					}
					else if(ChatColor.stripColor(name).equals("Previous Page")) {
						if(currentPage > maxPage) {
							player.closeInventory();
							ModerationGUI gui = new ModerationGUI();
							gui.InventoryTeleport(player, currentPage - 1);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThere are no more pages then this."));
						}
					}
					else if(ChatColor.stripColor(name).equals("Close")) {
						
					}
				}
			}
		}
	}
	@EventHandler
	public void clickMute(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Mute Offender")) {
				event.setCancelled(true);
				if(item != null) {
					
				}
			}
		}
	}
	@EventHandler
	public void onPlayerJoinRegister(PlayerJoinEvent event) {
		
	}
	public void loadBanList() {
		
	}
}
