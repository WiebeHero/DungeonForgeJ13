package me.WiebeHero.LootChest;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;

public class LootRewards {
	public static ArrayList<ItemStack> rewards1 = new ArrayList<>();
	public static ArrayList<ItemStack> rewards2 = new ArrayList<>();
	public static ArrayList<ItemStack> rewards3 = new ArrayList<>();
	public static ArrayList<ItemStack> rewards4 = new ArrayList<>();
	public void loadRewards() {
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 1
		//--------------------------------------------------------------------------------------------------------------------
		rewards1.add(steak8());
		rewards1.add(steak8());
		rewards1.add(steak8());
		rewards1.add(steak8());
		rewards1.add(steak8());
		rewards1.add(steak12());
		rewards1.add(steak12());
		rewards1.add(steak12());
		rewards1.add(steak12());
		rewards1.add(steak12());
		rewards1.add(potato16());
		rewards1.add(potato16());
		rewards1.add(potato16());
		rewards1.add(potato16());
		rewards1.add(potato16());
		rewards1.add(potato32());
		rewards1.add(potato32());
		rewards1.add(potato32());
		rewards1.add(potato32());
		rewards1.add(potato32());
		rewards1.add(potato64());
		rewards1.add(potato64());
		rewards1.add(potato64());
		rewards1.add(potato64());
		rewards1.add(potato64());
		rewards1.add(fish12());
		rewards1.add(fish12());
		rewards1.add(fish12());
		rewards1.add(fish12());
		rewards1.add(fish12());
		rewards1.add(fish18());
		rewards1.add(fish18());
		rewards1.add(fish18());
		rewards1.add(fish18());
		rewards1.add(fish18());
		rewards1.add(fish24());
		rewards1.add(fish24());
		rewards1.add(fish24());
		rewards1.add(fish24());
		rewards1.add(fish24());
		rewards1.add(chicken10());
		rewards1.add(chicken10());
		rewards1.add(chicken10());
		rewards1.add(chicken10());
		rewards1.add(chicken10());
		rewards1.add(chicken14());
		rewards1.add(chicken14());
		rewards1.add(chicken14());
		rewards1.add(chicken14());
		rewards1.add(chicken14());
		rewards1.add(chicken18());
		rewards1.add(chicken18());
		rewards1.add(chicken18());
		rewards1.add(chicken18());
		rewards1.add(chicken18());
		rewards1.add(obby4());
		rewards1.add(obby4());
		rewards1.add(obby4());
		rewards1.add(obby4());
		rewards1.add(obby4());
		rewards1.add(obby8());
		rewards1.add(obby8());
		rewards1.add(obby8());
		rewards1.add(obby8());
		rewards1.add(obby8());
		rewards1.add(obby16());
		rewards1.add(obby16());
		rewards1.add(obby16());
		rewards1.add(obby16());
		rewards1.add(obby16());
		rewards1.add(tnt2());
		rewards1.add(tnt2());
		rewards1.add(tnt2());
		rewards1.add(tnt2());
		rewards1.add(tnt2());
		rewards1.add(tnt4());
		rewards1.add(tnt4());
		rewards1.add(tnt4());
		rewards1.add(tnt4());
		rewards1.add(tnt4());
		rewards1.add(tnt8());
		rewards1.add(tnt8());
		rewards1.add(tnt8());
		rewards1.add(tnt8());
		rewards1.add(tnt8());
		rewards1.add(strPot1());
		rewards1.add(strPot1());
		rewards1.add(strPot1());
		rewards1.add(strPot1());
		rewards1.add(strPot1());
		rewards1.add(spdPot1());
		rewards1.add(spdPot1());
		rewards1.add(spdPot1());
		rewards1.add(spdPot1());
		rewards1.add(spdPot1());
		rewards1.add(eggWolf1());
		rewards1.add(eggWolf1());
		rewards1.add(eggWolf1());
		rewards1.add(eggWolf1());
		rewards1.add(eggWolf1());
		rewards1.add(eggOcelot1());
		rewards1.add(eggOcelot1());
		rewards1.add(eggOcelot1());
		rewards1.add(eggOcelot1());
		rewards1.add(eggOcelot1());
		rewards1.add(log12());
		rewards1.add(log12());
		rewards1.add(log12());
		rewards1.add(log12());
		rewards1.add(log12());
		rewards1.add(log16());
		rewards1.add(log16());
		rewards1.add(log16());
		rewards1.add(log16());
		rewards1.add(log16());
		rewards1.add(log20());
		rewards1.add(log20());
		rewards1.add(log20());
		rewards1.add(log20());
		rewards1.add(log20());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl16());
		rewards1.add(enderPearl16());
		rewards1.add(enderPearl16());
		rewards1.add(enderPearl16());
		rewards1.add(enderPearl16());
		rewards1.add(commonCrystal());
		rewards1.add(rareCrystal());
		rewards1.add(playerXPBottle(75));
		rewards1.add(playerXPBottle(150));
		rewards1.add(playerXPBottle(300));
		rewards1.add(moneyNote(250));
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 2
		//--------------------------------------------------------------------------------------------------------------------
		rewards2.add(steak8());
		rewards2.add(steak8());
		rewards2.add(steak8());
		rewards2.add(steak8());
		rewards2.add(steak8());
		rewards2.add(steak12());
		rewards2.add(steak12());
		rewards2.add(steak12());
		rewards2.add(steak12());
		rewards2.add(steak12());
		rewards2.add(potato16());
		rewards2.add(potato16());
		rewards2.add(potato16());
		rewards2.add(potato16());
		rewards2.add(potato16());
		rewards2.add(potato32());
		rewards2.add(potato32());
		rewards2.add(potato32());
		rewards2.add(potato32());
		rewards2.add(potato32());
		rewards2.add(potato64());
		rewards2.add(potato64());
		rewards2.add(potato64());
		rewards2.add(potato64());
		rewards2.add(potato64());
		rewards2.add(fish12());
		rewards2.add(fish12());
		rewards2.add(fish12());
		rewards2.add(fish12());
		rewards2.add(fish12());
		rewards2.add(fish18());
		rewards2.add(fish18());
		rewards2.add(fish18());
		rewards2.add(fish18());
		rewards2.add(fish18());
		rewards2.add(fish24());
		rewards2.add(fish24());
		rewards2.add(fish24());
		rewards2.add(fish24());
		rewards2.add(fish24());
		rewards2.add(chicken10());
		rewards2.add(chicken10());
		rewards2.add(chicken10());
		rewards2.add(chicken10());
		rewards2.add(chicken10());
		rewards2.add(chicken14());
		rewards2.add(chicken14());
		rewards2.add(chicken14());
		rewards2.add(chicken14());
		rewards2.add(chicken14());
		rewards2.add(chicken18());
		rewards2.add(chicken18());
		rewards2.add(chicken18());
		rewards2.add(chicken18());
		rewards2.add(chicken18());
		rewards2.add(obby4());
		rewards2.add(obby4());
		rewards2.add(obby4());
		rewards2.add(obby4());
		rewards2.add(obby4());
		rewards2.add(obby8());
		rewards2.add(obby8());
		rewards2.add(obby8());
		rewards2.add(obby8());
		rewards2.add(obby8());
		rewards2.add(obby16());
		rewards2.add(obby16());
		rewards2.add(obby16());
		rewards2.add(obby16());
		rewards2.add(obby16());
		rewards2.add(tnt2());
		rewards2.add(tnt2());
		rewards2.add(tnt2());
		rewards2.add(tnt2());
		rewards2.add(tnt2());
		rewards2.add(tnt4());
		rewards2.add(tnt4());
		rewards2.add(tnt4());
		rewards2.add(tnt4());
		rewards2.add(tnt4());
		rewards2.add(tnt8());
		rewards2.add(tnt8());
		rewards2.add(tnt8());
		rewards2.add(tnt8());
		rewards2.add(tnt8());
		rewards2.add(strPot1());
		rewards2.add(strPot1());
		rewards2.add(strPot1());
		rewards2.add(strPot1());
		rewards2.add(strPot1());
		rewards2.add(spdPot1());
		rewards2.add(spdPot1());
		rewards2.add(spdPot1());
		rewards2.add(spdPot1());
		rewards2.add(spdPot1());
		rewards2.add(eggWolf1());
		rewards2.add(eggWolf1());
		rewards2.add(eggWolf1());
		rewards2.add(eggWolf1());
		rewards2.add(eggWolf1());
		rewards2.add(eggOcelot1());
		rewards2.add(eggOcelot1());
		rewards2.add(eggOcelot1());
		rewards2.add(eggOcelot1());
		rewards2.add(eggOcelot1());
		rewards2.add(log12());
		rewards2.add(log12());
		rewards2.add(log12());
		rewards2.add(log12());
		rewards2.add(log12());
		rewards2.add(log16());
		rewards2.add(log16());
		rewards2.add(log16());
		rewards2.add(log16());
		rewards2.add(log16());
		rewards2.add(log20());
		rewards2.add(log20());
		rewards2.add(log20());
		rewards2.add(log20());
		rewards2.add(log20());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl16());
		rewards2.add(enderPearl16());
		rewards2.add(enderPearl16());
		rewards2.add(enderPearl16());
		rewards2.add(enderPearl16());
		rewards2.add(commonCrystal());
		rewards2.add(commonCrystal());
		rewards2.add(rareCrystal());
		rewards2.add(rareCrystal());
		rewards2.add(epicCrystal());
		rewards2.add(playerXPBottle(75));
		rewards2.add(playerXPBottle(150));
		rewards2.add(playerXPBottle(300));
		rewards2.add(playerXPBottle(600));
		rewards2.add(moneyNote(250));
		rewards2.add(moneyNote(500));
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 3
		//--------------------------------------------------------------------------------------------------------------------
		rewards3.add(steak8());
		rewards3.add(steak8());
		rewards3.add(steak8());
		rewards3.add(steak8());
		rewards3.add(steak8());
		rewards3.add(steak12());
		rewards3.add(steak12());
		rewards3.add(steak12());
		rewards3.add(steak12());
		rewards3.add(steak12());
		rewards3.add(potato16());
		rewards3.add(potato16());
		rewards3.add(potato16());
		rewards3.add(potato16());
		rewards3.add(potato16());
		rewards3.add(potato32());
		rewards3.add(potato32());
		rewards3.add(potato32());
		rewards3.add(potato32());
		rewards3.add(potato32());
		rewards3.add(potato64());
		rewards3.add(potato64());
		rewards3.add(potato64());
		rewards3.add(potato64());
		rewards3.add(potato64());
		rewards3.add(fish12());
		rewards3.add(fish12());
		rewards3.add(fish12());
		rewards3.add(fish12());
		rewards3.add(fish12());
		rewards3.add(fish18());
		rewards3.add(fish18());
		rewards3.add(fish18());
		rewards3.add(fish18());
		rewards3.add(fish18());
		rewards3.add(fish24());
		rewards3.add(fish24());
		rewards3.add(fish24());
		rewards3.add(fish24());
		rewards3.add(fish24());
		rewards3.add(chicken10());
		rewards3.add(chicken10());
		rewards3.add(chicken10());
		rewards3.add(chicken10());
		rewards3.add(chicken10());
		rewards3.add(chicken14());
		rewards3.add(chicken14());
		rewards3.add(chicken14());
		rewards3.add(chicken14());
		rewards3.add(chicken14());
		rewards3.add(chicken18());
		rewards3.add(chicken18());
		rewards3.add(chicken18());
		rewards3.add(chicken18());
		rewards3.add(chicken18());
		rewards3.add(obby4());
		rewards3.add(obby4());
		rewards3.add(obby4());
		rewards3.add(obby4());
		rewards3.add(obby4());
		rewards3.add(obby8());
		rewards3.add(obby8());
		rewards3.add(obby8());
		rewards3.add(obby8());
		rewards3.add(obby8());
		rewards3.add(obby16());
		rewards3.add(obby16());
		rewards3.add(obby16());
		rewards3.add(obby16());
		rewards3.add(obby16());
		rewards3.add(tnt2());
		rewards3.add(tnt2());
		rewards3.add(tnt2());
		rewards3.add(tnt2());
		rewards3.add(tnt2());
		rewards3.add(tnt4());
		rewards3.add(tnt4());
		rewards3.add(tnt4());
		rewards3.add(tnt4());
		rewards3.add(tnt4());
		rewards3.add(tnt8());
		rewards3.add(tnt8());
		rewards3.add(tnt8());
		rewards3.add(tnt8());
		rewards3.add(tnt8());
		rewards3.add(strPot1());
		rewards3.add(strPot1());
		rewards3.add(strPot1());
		rewards3.add(strPot1());
		rewards3.add(strPot1());
		rewards3.add(spdPot1());
		rewards3.add(spdPot1());
		rewards3.add(spdPot1());
		rewards3.add(spdPot1());
		rewards3.add(spdPot1());
		rewards3.add(eggWolf1());
		rewards3.add(eggWolf1());
		rewards3.add(eggWolf1());
		rewards3.add(eggWolf1());
		rewards3.add(eggWolf1());
		rewards3.add(eggOcelot1());
		rewards3.add(eggOcelot1());
		rewards3.add(eggOcelot1());
		rewards3.add(eggOcelot1());
		rewards3.add(eggOcelot1());
		rewards3.add(log12());
		rewards3.add(log12());
		rewards3.add(log12());
		rewards3.add(log12());
		rewards3.add(log12());
		rewards3.add(log16());
		rewards3.add(log16());
		rewards3.add(log16());
		rewards3.add(log16());
		rewards3.add(log16());
		rewards3.add(log20());
		rewards3.add(log20());
		rewards3.add(log20());
		rewards3.add(log20());
		rewards3.add(log20());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl16());
		rewards3.add(enderPearl16());
		rewards3.add(enderPearl16());
		rewards3.add(enderPearl16());
		rewards3.add(enderPearl16());
		rewards3.add(commonCrystal());
		rewards3.add(commonCrystal());
		rewards3.add(commonCrystal());
		rewards3.add(rareCrystal());
		rewards3.add(rareCrystal());
		rewards3.add(rareCrystal());
		rewards3.add(epicCrystal());
		rewards3.add(epicCrystal());
		rewards3.add(legendaryCrystal());
		rewards3.add(playerXPBottle(75));
		rewards3.add(playerXPBottle(150));
		rewards3.add(playerXPBottle(300));
		rewards3.add(playerXPBottle(600));
		rewards3.add(playerXPBottle(1000));
		rewards3.add(moneyNote(250));
		rewards3.add(moneyNote(500));
		rewards3.add(moneyNote(1000));
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 4
		//--------------------------------------------------------------------------------------------------------------------
		rewards4.add(steak8());
		rewards4.add(steak8());
		rewards4.add(steak8());
		rewards4.add(steak8());
		rewards4.add(steak8());
		rewards4.add(steak12());
		rewards4.add(steak12());
		rewards4.add(steak12());
		rewards4.add(steak12());
		rewards4.add(steak12());
		rewards4.add(potato16());
		rewards4.add(potato16());
		rewards4.add(potato16());
		rewards4.add(potato16());
		rewards4.add(potato16());
		rewards4.add(potato32());
		rewards4.add(potato32());
		rewards4.add(potato32());
		rewards4.add(potato32());
		rewards4.add(potato32());
		rewards4.add(potato64());
		rewards4.add(potato64());
		rewards4.add(potato64());
		rewards4.add(potato64());
		rewards4.add(potato64());
		rewards4.add(fish12());
		rewards4.add(fish12());
		rewards4.add(fish12());
		rewards4.add(fish12());
		rewards4.add(fish12());
		rewards4.add(fish18());
		rewards4.add(fish18());
		rewards4.add(fish18());
		rewards4.add(fish18());
		rewards4.add(fish18());
		rewards4.add(fish24());
		rewards4.add(fish24());
		rewards4.add(fish24());
		rewards4.add(fish24());
		rewards4.add(fish24());
		rewards4.add(chicken10());
		rewards4.add(chicken10());
		rewards4.add(chicken10());
		rewards4.add(chicken10());
		rewards4.add(chicken10());
		rewards4.add(chicken14());
		rewards4.add(chicken14());
		rewards4.add(chicken14());
		rewards4.add(chicken14());
		rewards4.add(chicken14());
		rewards4.add(chicken18());
		rewards4.add(chicken18());
		rewards4.add(chicken18());
		rewards4.add(chicken18());
		rewards4.add(chicken18());
		rewards4.add(obby4());
		rewards4.add(obby4());
		rewards4.add(obby4());
		rewards4.add(obby4());
		rewards4.add(obby4());
		rewards4.add(obby8());
		rewards4.add(obby8());
		rewards4.add(obby8());
		rewards4.add(obby8());
		rewards4.add(obby8());
		rewards4.add(obby16());
		rewards4.add(obby16());
		rewards4.add(obby16());
		rewards4.add(obby16());
		rewards4.add(obby16());
		rewards4.add(tnt2());
		rewards4.add(tnt2());
		rewards4.add(tnt2());
		rewards4.add(tnt2());
		rewards4.add(tnt2());
		rewards4.add(tnt4());
		rewards4.add(tnt4());
		rewards4.add(tnt4());
		rewards4.add(tnt4());
		rewards4.add(tnt4());
		rewards4.add(tnt8());
		rewards4.add(tnt8());
		rewards4.add(tnt8());
		rewards4.add(tnt8());
		rewards4.add(tnt8());
		rewards4.add(strPot1());
		rewards4.add(strPot1());
		rewards4.add(strPot1());
		rewards4.add(strPot1());
		rewards4.add(strPot1());
		rewards4.add(spdPot1());
		rewards4.add(spdPot1());
		rewards4.add(spdPot1());
		rewards4.add(spdPot1());
		rewards4.add(spdPot1());
		rewards4.add(eggWolf1());
		rewards4.add(eggWolf1());
		rewards4.add(eggWolf1());
		rewards4.add(eggWolf1());
		rewards4.add(eggWolf1());
		rewards4.add(eggOcelot1());
		rewards4.add(eggOcelot1());
		rewards4.add(eggOcelot1());
		rewards4.add(eggOcelot1());
		rewards4.add(eggOcelot1());
		rewards4.add(log12());
		rewards4.add(log12());
		rewards4.add(log12());
		rewards4.add(log12());
		rewards4.add(log12());
		rewards4.add(log16());
		rewards4.add(log16());
		rewards4.add(log16());
		rewards4.add(log16());
		rewards4.add(log16());
		rewards4.add(log20());
		rewards4.add(log20());
		rewards4.add(log20());
		rewards4.add(log20());
		rewards4.add(log20());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl16());
		rewards4.add(enderPearl16());
		rewards4.add(enderPearl16());
		rewards4.add(enderPearl16());
		rewards4.add(enderPearl16());
		rewards4.add(commonCrystal());
		rewards4.add(commonCrystal());
		rewards4.add(commonCrystal());
		rewards4.add(commonCrystal());
		rewards4.add(rareCrystal());
		rewards4.add(rareCrystal());
		rewards4.add(rareCrystal());
		rewards4.add(rareCrystal());
		rewards4.add(epicCrystal());
		rewards4.add(epicCrystal());
		rewards4.add(epicCrystal());
		rewards4.add(legendaryCrystal());
		rewards4.add(legendaryCrystal());
		rewards4.add(mythicCrystal());
		rewards4.add(playerXPBottle(75));
		rewards4.add(playerXPBottle(150));
		rewards4.add(playerXPBottle(300));
		rewards4.add(playerXPBottle(600));
		rewards4.add(playerXPBottle(1000));
		rewards4.add(playerXPBottle(1500));
		rewards4.add(moneyNote(250));
		rewards4.add(moneyNote(500));
		rewards4.add(moneyNote(1000));
		rewards4.add(moneyNote(2500));
	}
	public ItemStack steak8() {
		ItemStack item1 = new ItemStack(Material.COOKED_BEEF, 8);
		return item1;
	}
	public ItemStack steak12() {
		ItemStack item1 = new ItemStack(Material.COOKED_BEEF, 12);
		return item1;
	}
	public ItemStack steak16() {
		ItemStack item1 = new ItemStack(Material.COOKED_BEEF, 16);
		return item1;
	}
	public ItemStack potato16() {
		ItemStack item1 = new ItemStack(Material.POTATO, 16);
		return item1;
	}
	public ItemStack potato32() {
		ItemStack item1 = new ItemStack(Material.POTATO, 32);
		return item1;
	}
	public ItemStack potato64() {
		ItemStack item1 = new ItemStack(Material.POTATO, 64);
		return item1;
	}
	public ItemStack fish12() {
		ItemStack item1 = new ItemStack(Material.COOKED_COD, 12);
		return item1;
	}
	public ItemStack fish18() {
		ItemStack item1 = new ItemStack(Material.COOKED_COD, 18);
		return item1;
	}
	public ItemStack fish24() {
		ItemStack item1 = new ItemStack(Material.COOKED_COD, 24);
		return item1;
	}
	public ItemStack chicken10() {
		ItemStack item1 = new ItemStack(Material.COOKED_CHICKEN, 10);
		return item1;
	}
	public ItemStack chicken14() {
		ItemStack item1 = new ItemStack(Material.COOKED_CHICKEN, 14);
		return item1;
	}
	public ItemStack chicken18() {
		ItemStack item1 = new ItemStack(Material.COOKED_CHICKEN, 18);
		return item1;
	}
	public ItemStack enderPearl4() {
		ItemStack item1 = new ItemStack(Material.ENDER_PEARL, 4);
		return item1;
	}
	public ItemStack enderPearl8() {
		ItemStack item1 = new ItemStack(Material.ENDER_PEARL, 8);
		return item1;
	}
	public ItemStack enderPearl16() {
		ItemStack item1 = new ItemStack(Material.ENDER_PEARL, 16);
		return item1;
	}
	public ItemStack strPot1() {
		ItemStack item1 = new ItemStack(Material.POTION, 1);
		PotionMeta meta = (PotionMeta) item1.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&fPotion of Strength"));
		meta.setColor(Color.fromRGB(150, 0, 0));
		meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true);
		item1.setItemMeta(meta);
		return item1;
	}
	public ItemStack spdPot1() {
		ItemStack item1 = new ItemStack(Material.POTION, 1);
		PotionMeta meta = (PotionMeta) item1.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&fPotion of Speed"));
		meta.setColor(Color.fromRGB(200, 200, 200));
		meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0), true);
		item1.setItemMeta(meta);
		return item1;
	}
	public ItemStack obby4() {
		ItemStack item1 = new ItemStack(Material.OBSIDIAN, 4);
		return item1;
	}
	public ItemStack obby8() {
		ItemStack item1 = new ItemStack(Material.OBSIDIAN, 8);
		return item1;
	}
	public ItemStack obby16() {
		ItemStack item1 = new ItemStack(Material.OBSIDIAN, 16);
		return item1;
	}
	public ItemStack tnt2() {
		ItemStack item1 = new ItemStack(Material.TNT, 2);
		return item1;
	}
	public ItemStack tnt4() {
		ItemStack item1 = new ItemStack(Material.TNT, 4);
		return item1;
	}
	public ItemStack tnt8() {
		ItemStack item1 = new ItemStack(Material.TNT, 8);
		return item1;
	}
	public ItemStack log12() {
		ItemStack item1 = new ItemStack(Material.OAK_LOG, 12);
		return item1;
	}
	public ItemStack log16() {
		ItemStack item1 = new ItemStack(Material.OAK_LOG, 16);
		return item1;
	}
	public ItemStack log20() {
		ItemStack item1 = new ItemStack(Material.OAK_LOG, 20);
		return item1;
	}
	public ItemStack eggZombie1() {
		ItemStack item1 = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
		return item1;
	}
	public ItemStack eggSkeleton1() {
		ItemStack item1 = new ItemStack(Material.SKELETON_SPAWN_EGG, 1);
		return item1;
	}
	public ItemStack eggEndermen1() {
		ItemStack item1 = new ItemStack(Material.ENDERMAN_SPAWN_EGG, 1);
		return item1;
	}
	public ItemStack eggBlaze1() {
		ItemStack item1 = new ItemStack(Material.BLAZE_SPAWN_EGG, 1);
		return item1;
	}
	public ItemStack eggWolf1() {
		ItemStack item1 = new ItemStack(Material.WOLF_SPAWN_EGG, 1);
		return item1;
	}
	public ItemStack eggOcelot1() {
		ItemStack item1 = new ItemStack(Material.OCELOT_SPAWN_EGG, 1);
		return item1;
	}
	public ItemStack moneyNote(int money) {
		ItemStack item1 = new ItemStack(Material.PAPER, 1);
		NBTItem i = new NBTItem(item1);
		i.setInteger("Money", money);
		item1 = i.getItem();
		ItemMeta meta1 = item1.getItemMeta();
		meta1.setDisplayName(new CCT().colorize("&aMoney Note: $" + i.getInteger("Money")));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Right click this note to recieve"));
		lore1.add(new CCT().colorize("&7the amount of money that is on"));
		lore1.add(new CCT().colorize("&7the note."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack commonCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&7Common Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		NBTItem item = new NBTItem(item1);
		item.setString("CrystalObject", "");
		item.setString("Rarity", "&7Common");
		item1 = item.getItem();
		return item1;
	}
	public ItemStack rareCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&aRare Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: &aRare"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		NBTItem item = new NBTItem(item1);
		item.setString("CrystalObject", "");
		item.setString("Rarity", "&aRare");
		item1 = item.getItem();
		return item1;
	}
	public ItemStack epicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&bEpic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: &bEpic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		NBTItem item = new NBTItem(item1);
		item.setString("CrystalObject", "");
		item.setString("Rarity", "&bEpic");
		item1 = item.getItem();
		return item1;
	}
	public ItemStack legendaryCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&cLegendary Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: &cLegendary"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		NBTItem item = new NBTItem(item1);
		item.setString("CrystalObject", "");
		item.setString("Rarity", "&cLegendary");
		item1 = item.getItem();
		return item1;
	}
	public ItemStack mythicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&5Mythic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: &5Mythic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		NBTItem item = new NBTItem(item1);
		item.setString("CrystalObject", "");
		item.setString("Rarity", "&5Mythic");
		item1 = item.getItem();
		return item1;
	}
	public ItemStack heroicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&eHeroic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: &eHeroic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		NBTItem item = new NBTItem(item1);
		item.setString("CrystalObject", "");
		item.setString("Rarity", "&eHeroic");
		item1 = item.getItem();
		return item1;
	}
	public ItemStack playerXPBottle(int xp) {
		ItemStack item1 = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item1.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lXP Bottle"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7When you throw this bottle, you"));
		lore1.add(new CCT().colorize("&7will gain xp appropiate to it's amount"));
		lore1.add(new CCT().colorize("&7XP Amount: &6" + xp));
		itemmeta.setLore(lore1);
		item1.setItemMeta(itemmeta);
		NBTItem item = new NBTItem(item1);
		item.setInteger("XPBottle", xp);
		item1 = item.getItem();
		return item1;
	}
	public static ArrayList<ItemStack> getTier1List(){
		return LootRewards.rewards1;
	}
	public static ArrayList<ItemStack> getTier2List(){
		return LootRewards.rewards2;
	}
	public static ArrayList<ItemStack> getTier3List(){
		return LootRewards.rewards3;
	}
	public static ArrayList<ItemStack> getTier4List(){
		return LootRewards.rewards4;
	}
}
