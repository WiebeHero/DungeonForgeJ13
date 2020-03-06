package me.WiebeHero.EnchantmentAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;

public class EnchantmentGuideInventory {
	private Inventory mainGuide;
	private ArrayList<Inventory> meleeGuide;
	private Enchantment enchantment;
	private ItemStackBuilder builder;
	public EnchantmentGuideInventory(Enchantment enchantment, ItemStackBuilder builder) {
		this.enchantment = enchantment;
		this.builder = builder;
		ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
		int available = 0;
		for(Entry<String, Pair<Condition, CommandFile>> entry : enchantment.getMeleeEnchantments().entrySet()) {
			if(entry.getValue().getValue().getStack() != null) {
				available++;
			}
		}
		Inventory inv = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Enchantment Guide")));
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
		inv.setItem(26, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(27, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(35, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(36, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(44, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(45, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(46, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(47, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(48, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(49, builder.constructItem(
			Material.BARRIER,
			"&cBack",
			new ArrayList<String>(),
			"Back"
		));
		inv.setItem(50, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(51, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(52, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		inv.setItem(53, builder.constructItem(
			Material.GRAY_STAINED_GLASS_PANE,
			""
		));
		this.mainGuide = inv;
		int pages = (int) Math.ceil(available / slots.size() + 1);
		for(int x = 1; x <= pages; x++) {
			Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Enchantment Guide")));
			for(int k = 1; k <= pages; k++) {
				
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
				"Previous"
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
				"Next"
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
		}
	}
	public void openMainInventory() {
		
	}
	public void openMeleeInventory() {
		
	}
}
