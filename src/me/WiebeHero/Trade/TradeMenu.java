package me.WiebeHero.Trade;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class TradeMenu {
	
	private ItemStackBuilder builder;
	private ArrayList<Integer> fill;
	private ArrayList<Integer> itemsYou;
	private ArrayList<Integer> itemsThem;
	
	public TradeMenu(ItemStackBuilder builder) {
		this.fill = new ArrayList<Integer>(Arrays.asList(4, 13, 22, 31, 36, 37, 38, 39, 41, 42, 43, 44, 45, 47, 48, 50, 51, 53));
		this.itemsYou = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 28, 29, 30));
		this.itemsThem = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34));
		this.builder = builder;
	}
	
	public void TradeOverview(Player tradeMe, Player tradingWith) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6" + tradeMe.getName() + " trading with " + tradingWith.getName())));
		
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" ",
				new String[] {}
		);
		
		for(int x = 0; x < this.fill.size(); x++){
			i.setItem(this.fill.get(x), nothing);
		}
		
		i.setItem(40, this.builder.constructItem(
				Material.LIME_DYE,
				1,
				"&aAccept Trade",
				new String[] {},
				new Pair<String, String>("Trade Start", "")
		));
		ItemStack readyStatus = this.builder.constructItem(
				Material.GRAY_DYE,
				1,
				"&7Status: &cNot ready",
				new String[] {},
				new Pair<String, String>("Status", "Not Ready")
		);
		i.setItem(46, readyStatus);
		i.setItem(52, readyStatus);
		ItemStack moneyStatus = this.builder.constructItem(
				Material.BARRIER,
				1,
				"&aMoney: 0.00$",
				new String[] {},
				new Pair<String, Double>("Money", 0.00)
		);
		i.setItem(27, moneyStatus);
		i.setItem(35, moneyStatus);
		
		ItemStack moneySign = this.builder.constructItem(
				Material.SIGN,
				1,
				"&aPut money in trade",
				new String[] {
						"&7Click this to deposit money",
						"&7into the trade!"
				},
				new Pair<String, Double>("Money", 0.00)
		);
		i.setItem(49, moneySign);
		
		tradeMe.openInventory(i);
		tradingWith.openInventory(i);
	}
	
	public ArrayList<Integer> getFillerSlots(){
		return this.fill;
	}
	
	public ArrayList<Integer> getYourSlots(){
		return this.itemsYou;
	}
	
	public ArrayList<Integer> getTheirSlots(){
		return this.itemsThem;
	}
}
