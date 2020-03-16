package me.WiebeHero.EnchantmentAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;

public class EnchantmentGuideInventory {
	private Inventory mainGuide;
	private ArrayList<Inventory> meleeGuide;
	private ArrayList<Inventory> bowGuide;
	private ArrayList<Inventory> armorGuide;
	private ArrayList<Inventory> shieldGuide;
	private Enchantment enchantment;
	private ItemStackBuilder builder;
	public EnchantmentGuideInventory(Enchantment enchantment, ItemStackBuilder builder) {
		this.enchantment = enchantment;
		this.builder = builder;
		this.meleeGuide = new ArrayList<Inventory>();
		this.bowGuide = new ArrayList<Inventory>();
		this.armorGuide = new ArrayList<Inventory>();
		this.shieldGuide = new ArrayList<Inventory>();
		ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
		ArrayList<ItemStack> meleeStacks = new ArrayList<ItemStack>();
		ArrayList<ItemStack> bowStacks = new ArrayList<ItemStack>();
		ArrayList<ItemStack> armorStacks = new ArrayList<ItemStack>();
		ArrayList<ItemStack> shieldStacks = new ArrayList<ItemStack>();
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchantment.getMeleeEnchantments().entrySet()) {
			if(entry.getValue().getValue().getStack() != null) {
				meleeStacks.add(entry.getValue().getValue().getStack());
			}
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchantment.getBowEnchantments().entrySet()) {
			if(entry.getValue().getValue().getStack() != null) {
				bowStacks.add(entry.getValue().getValue().getStack());
			}
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchantment.getArmorEnchantments().entrySet()) {
			if(entry.getValue().getValue().getStack() != null) {
				armorStacks.add(entry.getValue().getValue().getStack());
			}
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchantment.getShieldEnchantments().entrySet()) {
			if(entry.getValue().getValue().getStack() != null) {
				shieldStacks.add(entry.getValue().getValue().getStack());
			}
		}
		Inventory inv = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Enchantments")));
		inv.setItem(10, builder.constructItem(
			Material.DIAMOND_SWORD,
			"&6Melee Enchantments",
			new ArrayList<String>(Arrays.asList(
				"&7Click this to go to the melee",
				"&7enchantments page."
			)),
			"Melee"
		));
		inv.setItem(12, builder.constructItem(
			Material.BOW,
			"&6Bow Enchantments",
			new ArrayList<String>(Arrays.asList(
				"&7Click this to go to the bow",
				"&7enchantments page."
			)),
			"Bow"
		));
		inv.setItem(14, builder.constructItem(
			Material.DIAMOND_CHESTPLATE,
			"&6Armor Enchantments",
			new ArrayList<String>(Arrays.asList(
				"&7Click this to go to the armor",
				"&7enchantments page."
			)),
			"Armor"
		));
		inv.setItem(16, builder.constructItem(
			Material.SHIELD,
			"&6Shield Enchantments",
			new ArrayList<String>(Arrays.asList(
				"&7Click this to go to the shield",
				"&7enchantments page."
			)),
			"Shield"
		));
		inv.setItem(0, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(1, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(2, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(3, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(4, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(5, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(6, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(7, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(8, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(9, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		
		inv.setItem(17, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(18, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(19, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(20, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(21, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(22, builder.constructItem(
			Material.BARRIER,
			"&cExit",
			new ArrayList<String>(),
			"Exit"
		));
		inv.setItem(23, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(24, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(25, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(26, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		
		this.mainGuide = inv;
		int pages = (int) Math.ceil((double)meleeStacks.size() / slots.size());
		int current = 0;
		if(!meleeStacks.isEmpty()) {
			for(int x = 1; x <= pages; x++) {
				Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Melee Enchantments: " + x + " / " + pages)));
				for(int k = 0; k < slots.size(); k++) {
					if(meleeStacks.size() != current) {
						i.setItem(slots.get(k), meleeStacks.get(current));
						current += 1;
					}
					else {
						break;
					}
				}
				i.setItem(0, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(1, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(2, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(3, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(4, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(5, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(6, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(7, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(8, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(9, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(17, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(18, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(26, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(27, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(35, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(36, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(44, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(45, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(46, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(47, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(48, builder.constructItem(
					Material.PAPER,
					"&aPrevious Page",
					new ArrayList<String>(),
					"Previous",
					x - 1
				));
				i.setItem(49, builder.constructItem(
					Material.BARRIER,
					"&cBack",
					new ArrayList<String>(),
					"Back"
				));
				i.setItem(50, builder.constructItem(
					Material.PAPER,
					"&aNext Page",
					new ArrayList<String>(),
					"Next",
					x + 1
				));
				i.setItem(51, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(52, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(53, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				this.meleeGuide.add(i);
			}
		}
		current = 0;
		pages = (int) Math.ceil((double)bowStacks.size() / slots.size());
		if(!bowStacks.isEmpty()) {
			for(int x = 1; x <= pages; x++) {
				Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Bow Enchantments: " + x + " / " + pages)));
				for(int k = 0; k < slots.size(); k++) {
					if(bowStacks.size() != current) {
						i.setItem(slots.get(k), bowStacks.get(current));
						current += 1;
					}
					else {
						break;
					}
				}
				i.setItem(0, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(1, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(2, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(3, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(4, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(5, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(6, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(7, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(8, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(9, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(17, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(18, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(26, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(27, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(35, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(36, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(44, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(45, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(46, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(47, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(48, builder.constructItem(
					Material.PAPER,
					"&aPrevious Page",
					new ArrayList<String>(),
					"Previous",
					x - 1
				));
				i.setItem(49, builder.constructItem(
					Material.BARRIER,
					"&cBack",
					new ArrayList<String>(),
					"Back"
				));
				i.setItem(50, builder.constructItem(
					Material.PAPER,
					"&aNext Page",
					new ArrayList<String>(),
					"Next",
					x + 1
				));
				i.setItem(51, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(52, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(53, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				this.bowGuide.add(i);
			}
		}
		current = 0;
		pages = (int) Math.ceil((double)armorStacks.size() / slots.size());
		Bukkit.getConsoleSender().sendMessage(Math.ceil(armorStacks.size() / slots.size()) + "");
		if(!armorStacks.isEmpty()) {
			Bukkit.getConsoleSender().sendMessage("Not Empty");
			for(int x = 1; x <= pages; x++) {
				Bukkit.getConsoleSender().sendMessage("Looping..");
				Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Armor Enchantments: " + x + " / " + pages)));
				for(int k = 0; k < slots.size(); k++) {
					if(armorStacks.size() != current) {
						i.setItem(slots.get(k), armorStacks.get(current));
						current += 1;
					}
					else {
						break;
					}
				}
				i.setItem(0, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(1, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(2, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(3, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(4, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(5, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(6, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(7, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(8, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(9, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(17, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(18, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(26, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(27, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(35, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(36, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(44, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(45, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(46, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(47, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(48, builder.constructItem(
					Material.PAPER,
					"&aPrevious Page",
					new ArrayList<String>(),
					"Previous",
					x - 1
				));
				i.setItem(49, builder.constructItem(
					Material.BARRIER,
					"&cBack",
					new ArrayList<String>(),
					"Back"
				));
				i.setItem(50, builder.constructItem(
					Material.PAPER,
					"&aNext Page",
					new ArrayList<String>(),
					"Next",
					x + 1
				));
				i.setItem(51, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(52, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(53, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				this.armorGuide.add(i);
			}
		}
		current = 0;
		pages = (int) Math.ceil((double)shieldStacks.size() / slots.size());
		if(!shieldStacks.isEmpty()) {
			for(int x = 1; x <= pages; x++) {
				Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Shield Enchantments: " + x + " / " + pages)));
				for(int k = 0; k < slots.size(); k++) {
					if(shieldStacks.size() != current) {
						i.setItem(slots.get(k), shieldStacks.get(current));
						current += 1;
					}
					else {
						break;
					}
				}
				i.setItem(0, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(1, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(2, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(3, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(4, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(5, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(6, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(7, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(8, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(9, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(17, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(18, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(26, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(27, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(35, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(36, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(44, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(45, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(46, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(47, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(48, builder.constructItem(
					Material.PAPER,
					"&aPrevious Page",
					new ArrayList<String>(),
					"Previous",
					x - 1
				));
				i.setItem(49, builder.constructItem(
					Material.BARRIER,
					"&cBack",
					new ArrayList<String>(),
					"Back"
				));
				i.setItem(50, builder.constructItem(
					Material.PAPER,
					"&aNext Page",
					new ArrayList<String>(),
					"Next",
					x + 1
				));
				i.setItem(51, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(52, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				i.setItem(53, builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					""
				));
				this.shieldGuide.add(i);
			}
		}
	}
	public int getMeleeSize(){
		return this.meleeGuide.size();
	}
	public int getBowSize(){
		return this.bowGuide.size();
	}
	public int getArmorSize(){
		return this.armorGuide.size();
	}
	public int getShieldSize(){
		return this.shieldGuide.size();
	}
	public void openMainInventory(Player p) {
		p.openInventory(this.mainGuide);
	}
	public void openMeleeInventory(Player p, int page) {
		p.openInventory(this.meleeGuide.get(page - 1));
	}
	public void openBowInventory(Player p, int page) {
		p.openInventory(this.bowGuide.get(page - 1));
	}
	public void openArmorInventory(Player p, int page) {
		p.openInventory(this.armorGuide.get(page - 1));
	}
	public void openShieldInventory(Player p, int page) {
		p.openInventory(this.shieldGuide.get(page - 1));
	}
}
