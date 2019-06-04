package NeededStuff;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ServerTeleporterInv {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public void ServerSelector (Player player) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Server Teleporter")));
		//-----------------------------------------------------------------------------------------------------------------------------------------
		//GUI
		//-----------------------------------------------------------------------------------------------------------------------------------------
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
		i.setItem(10, df1(player));
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
		player.openInventory(i);
	}
	public ItemStack emptyVoid(Player player) {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(" ");
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack df1(Player player) {
		ItemStack item = new ItemStack(Material.BOOK, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&6DF-1"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new ColorCodeTranslator().colorize("&aClick me to teleport to DF-1!"));
		itemmeta.setLore(lore);
		item.setItemMeta(itemmeta);
		return item;
	}
}
