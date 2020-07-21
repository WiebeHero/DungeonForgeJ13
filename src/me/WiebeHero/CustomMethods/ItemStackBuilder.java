package me.WiebeHero.CustomMethods;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
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
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, String display, Pair... pair) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair<Object, Object> p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
			
		}
		return i.getItem();
	}
	public ItemStack constructItem(Material mat, String display, ArrayList<String> lore, Pair... pair) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair<Object, Object> p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
			
		}
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
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack constructItem(Material mat, int amount, String display, Pair... pair) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
		}
		return i.getItem();
	}
	
	public ItemStack constructItem(Material mat, int amount, String display, ArrayList<String> lore, Pair... pair) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
		}
		return i.getItem();
	}
	
	public ItemStack constructItem(ItemStack stack) {
		ItemStack item = new ItemStack(stack);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, String display) {
		ItemStack item = new ItemStack(stack);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, ArrayList<String> lore) {
		ItemStack item = new ItemStack(stack);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(stack);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, String display, Pair... pair) {
		ItemStack item = new ItemStack(stack);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair<Object, Object> p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
		}
		return i.getItem();
	}
	public ItemStack constructItem(ItemStack stack, int amount, String display, Pair... pair) {
		ItemStack item = new ItemStack(stack);
		stack.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair<Object, Object> p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
		}
		return i.getItem();
	}
	public ItemStack constructItem(ItemStack stack, String display, ArrayList<String> lore, Pair... pair) {
		ItemStack item = new ItemStack(stack);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair<Object, Object> p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
		}
		return i.getItem();
	}
	public ItemStack constructItem(ItemStack stack, int amount, String display, ArrayList<String> lore, Pair... pair) {
		ItemStack item = new ItemStack(stack);
		stack.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		for(Pair<Object, Object> p : pair) {
			if(p.getKey() instanceof String && p.getValue() instanceof String) {
				Pair<String, String> newP = new Pair<String, String>((String)p.getKey(), (String)p.getValue());
				i.setString(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Boolean) {
				Pair<String, Boolean> newP = new Pair<String, Boolean>((String)p.getKey(), (Boolean)p.getValue());
				i.setBoolean(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof Byte && p.getValue() instanceof Byte) {
				Pair<String, Byte> newP = new Pair<String, Byte>((String)p.getKey(), (Byte)p.getValue());
				i.setByte(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Integer) {
				Pair<String, Integer> newP = new Pair<String, Integer>((String)p.getKey(), (Integer)p.getValue());
				i.setInteger(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Float) {
				Pair<String, Float> newP = new Pair<String, Float>((String)p.getKey(), (Float)p.getValue());
				i.setFloat(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Double) {
				Pair<String, Double> newP = new Pair<String, Double>((String)p.getKey(), (Double)p.getValue());
				i.setDouble(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Long) {
				Pair<String, Long> newP = new Pair<String, Long>((String)p.getKey(), (Long)p.getValue());
				i.setLong(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Short) {
				Pair<String, Short> newP = new Pair<String, Short>((String)p.getKey(), (Short)p.getValue());
				i.setShort(newP.getKey(), newP.getValue());
			}
			else if(p.getKey() instanceof String && p.getValue() instanceof Object) {
				Pair<String, Object> newP = new Pair<String, Object>((String)p.getKey(), (Object)p.getValue());
				i.setObject(newP.getKey(), newP.getValue());
			}
		}
		return i.getItem();
	}
	
	public ItemStack constructItem(ItemStack stack, int amount) {
		ItemStack item = new ItemStack(stack);
		item.setAmount(amount);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, int amount, String display) {
		ItemStack item = new ItemStack(stack);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, int amount, ArrayList<String> lore) {
		ItemStack item = new ItemStack(stack);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack constructItem(ItemStack stack, int amount, String display, ArrayList<String> lore) {
		ItemStack item = new ItemStack(stack);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(display));
		ArrayList<String> newLore = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			if(lore.get(i) != null) {
				newLore.add(new CCT().colorize(lore.get(i)));
			}
		}
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
}
