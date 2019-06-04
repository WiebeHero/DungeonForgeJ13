package me.WiebeHero.CustomEnchantments;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class Items implements Listener{
		private Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
				//Common Items 
				//§
				public void customTrickyFish() {
					ItemStack item = new ItemStack(Material.COD, 1 );
					ItemMeta meta = item.getItemMeta();	
					meta.setDisplayName(ChatColor.GRAY + "Tricky Fish");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This is the " + ChatColor.GRAY + "Tricky Fish " + ChatColor.GRAY + "When you");
					lore.add(ChatColor.GRAY + "Consume this item, it will give you " + ChatColor.RED + "Invisibility and 1 heart of health " );
					meta.setLore(lore);
					item.setItemMeta(meta);
					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					@SuppressWarnings("deprecation")
					ShapedRecipe tr =new ShapedRecipe(item);
					tr.shape("$%$","%#%","$%$");
					tr.setIngredient('#', Material.COD);
					tr.setIngredient('%', Material.POTION);
					tr.setIngredient('$', Material.FEATHER);
					plugin.getServer().addRecipe(tr);
				//Rare Items
				}
				public void customBunnyPotion() {
					ItemStack item = new ItemStack(Material.POTION, 1 );
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.GREEN + "Bunny Potion");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This is the " + ChatColor.GREEN + "Bunny Potion " + ChatColor.GRAY + "When you");
					lore.add(ChatColor.GRAY + "Consume this item, it will give you " + ChatColor.RED + "Invisibility and 1 heart of health " );
					meta.setLore(lore);
					item.setItemMeta(meta);
					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					@SuppressWarnings("deprecation")
					ShapedRecipe bp =new ShapedRecipe(item);
					bp.shape("$%$","%#%","$%$");
					bp.setIngredient('#', Material.RABBIT_FOOT);
					bp.setIngredient('%', Material.POTION);
					bp.setIngredient('$', Material.RABBIT_HIDE);
					plugin.getServer().addRecipe(bp);
				//Legendary Items
				}
				public void customLegendaryHero() {
					ItemStack item = new ItemStack(Material.BREAD,1 );
					ItemMeta meta = item.getItemMeta();	
					meta.setDisplayName(ChatColor.RED + "Legendary Hero");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This is the " + ChatColor.RED + "Legendary Hero " + ChatColor.GRAY + "When you");
					lore.add(ChatColor.GRAY + "Consume this item, it will give you " + ChatColor.RED + "+30% " + ChatColor.GRAY + "attack damage." );
					meta.setLore(lore);
					item.setItemMeta(meta);
					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					@SuppressWarnings("deprecation")
					ShapedRecipe LG =new ShapedRecipe(item);
					LG.shape("@#@","$%$","@#@");
					LG.setIngredient('@', Material.BLAZE_POWDER);
					LG.setIngredient('$', Material.GOLDEN_APPLE);
					LG.setIngredient('#', Material.GLOWSTONE_DUST);
					LG.setIngredient('%', Material.BREAD);
					plugin.getServer().addRecipe(LG);
	}
}
	

