package me.WiebeHero.CustomMethods;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;

public class ItemStackBuilder {
	public ItemStack constructItem(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		return item;
	}
	public ItemStack constructItem(Material mat, String display) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, String key) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString(key, "");
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, String key, String value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString(key, value);
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, String key, Kit value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject(key, value);
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, ArrayList<String> key, ArrayList<String> value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(int i1 = 0; i1 < key.size(); i1++) {
			i.setString(key.get(i1), value.get(i1));
		}
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore, ArrayList<String> key, ArrayList<String> value) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(int i1 = 0; i1 < key.size(); i1++) {
			i.setString(key.get(i1), value.get(i1));
		}
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, String key, int value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		meta.setLore(lore);
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setInteger(key, value);
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, String key, Object value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject(key, value);
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, int amount) {
		ItemStack item = new ItemStack(mat, amount);
		return item;
	}
	public ItemStack constructItem(Material mat, int amount, String display) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, int amount, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore, String key) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString(key, "");
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore, String key, String value) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString(key, value);
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore, String key, int value) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setInteger(key, value);
		return i.getItem();
	}
	public ItemStack constructItem(Material mat,int amount, String display, ArrayList<String> lore, String key, Object value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject(key, value);
		return i.getItem();
	}
}
