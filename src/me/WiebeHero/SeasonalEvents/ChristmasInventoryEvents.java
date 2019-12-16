package me.WiebeHero.SeasonalEvents;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;

public class ChristmasInventoryEvents implements Listener{
	@EventHandler
	public void xMasClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(ChatColor.stripColor(title).contains("Greg")) {
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Santa_Helmet")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().removeItem(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Santa_Chestplate")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().removeItem(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Santa_Leggings")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().removeItem(this.santaCoal(50));	
					}
				}
				else if(item.hasKey("Santa_Boots")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().removeItem(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Santa_Scythe")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().removeItem(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Elf_Helmet")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().removeItem(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Elf_Chestplate")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().removeItem(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Elf_Leggings")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().removeItem(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Elf_Boots")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().removeItem(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Toy_Sword")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().removeItem(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Santa_Cookie")) {
					if(player.getInventory().contains(this.santaCoal(1))) {
						player.getInventory().removeItem(this.santaCoal(1));
					}
				}
			}
		}
	}
	public ItemStack santaCoal(int amount) {
		ItemStack item = new ItemStack(Material.CHARCOAL, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&2L&cu&2m&cp &2o&cf &2C&co&2a&2l"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7A lump of coal? Are you kidding me? But ive been a good boy this year!"));
		lore.add(new CCT().colorize("&7You know that, i don't need that fat bald bastard to bring me presents!"));
		lore.add(new CCT().colorize("&7Maybe that guy by the X-Mas tree in spawn is collecting coal for..."));
		lore.add(new CCT().colorize("&7Well something, maybe i can get something from him using this."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
