package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class FactionInventory {
	
	private ItemStackBuilder builder;
	
	public FactionInventory(ItemStackBuilder builder) {
		this.builder = builder;
	}
	
	public void FactionBannerInventory(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Faction Banner")));
		ArrayList<Integer> exceptions = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 13, 14, 15, 16));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < i.getSize(); x++) {
			if(!exceptions.contains(x)) {
				i.setItem(x, nothing);
			}
		}
		ItemStack banner = faction.getBanner();
		NBTItem item = new NBTItem(banner);
		item.setString("BannerRemove", "");
		item.setString("BannerSet", "");
		banner = item.getItem();
		ItemMeta meta = banner.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&6" + faction.getName() + "'s banner"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Drag and drop to set a new banner."));
		lore.add(new CCT().colorize("&7Right click the banner to reset it."));
		meta.setLore(lore);
		banner.setItemMeta(meta);
		i.setItem(13, banner);
		player.openInventory(i);
	}
}
