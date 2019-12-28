package me.WiebeHero.SeasonalEvents;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.NewAttribute;

public class ChristmasInventoryEvents implements Listener{
	private NewAttribute atr = new NewAttribute();
	@EventHandler
	public void xMasOpen(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(event.getRightClicked() != null) {
			Entity ent = event.getRightClicked();
			if(ent.getCustomName() != null) {
				if(ent.getCustomName().contains("Greg")) {
					this.XmasInventory(player);
				}
			}
		}
	}
	public void XmasInventory(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 9, (new CCT().colorize("&6Greg's Shop")));
		i.setItem(8, this.santaCookie(1));
		player.openInventory(i);
	}
	@EventHandler
	public void xMasClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(ChatColor.stripColor(title).contains("Greg")) {
			event.setCancelled(true);
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Santa_Helmet")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().remove(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Santa_Chestplate")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().remove(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Santa_Leggings")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().remove(this.santaCoal(50));	
					}
				}
				else if(item.hasKey("Santa_Boots")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().remove(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Santa_Scythe")) {
					if(player.getInventory().contains(this.santaCoal(50))) {
						player.getInventory().remove(this.santaCoal(50));
					}
				}
				else if(item.hasKey("Elf_Helmet")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().remove(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Elf_Chestplate")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().remove(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Elf_Leggings")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().remove(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Elf_Boots")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().remove(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Toy_Sword")) {
					if(player.getInventory().contains(this.santaCoal(25))) {
						player.getInventory().remove(this.santaCoal(25));
					}
				}
				else if(item.hasKey("Santa_Cookie")) {
					if(player.getInventory().contains(this.santaCoal(1))) {
						player.getInventory().remove(this.santaCoal(1));
						player.getInventory().addItem(this.santaCookie(1));
					}
				}
			}
		}
	}
	public ItemStack santaCoal(int amount) {
		ItemStack item = new ItemStack(Material.CHARCOAL, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&2L&cu&2m&cp &2o&cf &2C&co&2a&cl"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7A lump of coal? Are you kidding me?"));
		lore.add(new CCT().colorize("&7But ive been a good boy this year!"));
		lore.add(new CCT().colorize("&7You know that, i don't need that"));
		lore.add(new CCT().colorize("&7fat bald bastard to bring me presents!"));
		lore.add(new CCT().colorize("&7I could try to trade the coal with that guy"));
		lore.add(new CCT().colorize("&7nearby the X-Mas tree in spawn."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack santaCookie(int amount) {
		ItemStack item = new ItemStack(Material.COOKIE, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&2Santa's &cCookie"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Ho ho ho! Merry christmas little fella!"));
		lore.add(new CCT().colorize("&7When consumed:"));
		lore.add(new CCT().colorize("  &7Attack Damage Increase: &63.0%"));
		lore.add(new CCT().colorize("  &7Attack Speed Increase: &61.0%"));
		lore.add(new CCT().colorize("  &7Critical Chance Increase: &61.0%"));
		lore.add(new CCT().colorize("  &7Ranged Damage Increase: &64.0%"));
		lore.add(new CCT().colorize("  &7Health Increase: &610.0%"));
		lore.add(new CCT().colorize("  &7Defense Increase: &62.0%"));
		lore.add(new CCT().colorize("  &7Duration: &b17.5 Seconds"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString("Santa_Cookie", "");
		ItemStack finalItem = i.getItem();
		return atr.stripModifier(finalItem, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ATTACK_DAMAGE, Attribute.GENERIC_ATTACK_SPEED)), "mainhand");
	}
}
