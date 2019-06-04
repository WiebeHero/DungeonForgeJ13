package me.WiebeHero.LootChest;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class LootRewards {
	public static ArrayList<ItemStack> rewards1 = new ArrayList<>();
	public static ArrayList<ItemStack> rewards2 = new ArrayList<>();
	public static ArrayList<ItemStack> rewards3 = new ArrayList<>();
	public static ArrayList<ItemStack> rewards4 = new ArrayList<>();
	public LootRewards() {
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 1
		//--------------------------------------------------------------------------------------------------------------------
		rewards1.add(trickyFish());
		rewards1.add(seasonedCarrot());
		rewards1.add(bunnyPotion());
		rewards1.add(chocolateChippedCookie());
		rewards1.add(faceSteak());
		rewards1.add(skyFish());
		rewards1.add(steak8());
		rewards1.add(steak8());
		rewards1.add(steak8());
		rewards1.add(steak12());
		rewards1.add(steak12());
		rewards1.add(steak12());
		rewards1.add(potato16());
		rewards1.add(potato16());
		rewards1.add(potato16());
		rewards1.add(potato32());
		rewards1.add(potato32());
		rewards1.add(potato32());
		rewards1.add(potato64());
		rewards1.add(potato64());
		rewards1.add(potato64());
		rewards1.add(fish12());
		rewards1.add(fish12());
		rewards1.add(fish12());
		rewards1.add(fish18());
		rewards1.add(fish18());
		rewards1.add(fish18());
		rewards1.add(fish24());
		rewards1.add(fish24());
		rewards1.add(fish24());
		rewards1.add(chicken10());
		rewards1.add(chicken10());
		rewards1.add(chicken10());
		rewards1.add(chicken14());
		rewards1.add(chicken14());
		rewards1.add(chicken14());
		rewards1.add(chicken18());
		rewards1.add(chicken18());
		rewards1.add(chicken18());
		rewards1.add(obby4());
		rewards1.add(obby4());
		rewards1.add(obby4());
		rewards1.add(obby8());
		rewards1.add(obby8());
		rewards1.add(obby8());
		rewards1.add(obby16());
		rewards1.add(obby16());
		rewards1.add(obby16());
		rewards1.add(tnt2());
		rewards1.add(tnt2());
		rewards1.add(tnt2());
		rewards1.add(tnt4());
		rewards1.add(tnt4());
		rewards1.add(tnt4());
		rewards1.add(tnt8());
		rewards1.add(tnt8());
		rewards1.add(tnt8());
		rewards1.add(strPot1());
		rewards1.add(strPot1());
		rewards1.add(strPot1());
		rewards1.add(spdPot1());
		rewards1.add(spdPot1());
		rewards1.add(spdPot1());
		rewards1.add(eggWolf1());
		rewards1.add(eggWolf1());
		rewards1.add(eggWolf1());
		rewards1.add(eggOcelot1());
		rewards1.add(eggOcelot1());
		rewards1.add(eggOcelot1());
		rewards1.add(log12());
		rewards1.add(log12());
		rewards1.add(log12());
		rewards1.add(log16());
		rewards1.add(log16());
		rewards1.add(log16());
		rewards1.add(log20());
		rewards1.add(log20());
		rewards1.add(log20());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl4());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl8());
		rewards1.add(enderPearl16());
		rewards1.add(enderPearl16());
		rewards1.add(enderPearl16());
		rewards1.add(commonCrystal());
		rewards1.add(rareCrystal());
		rewards1.add(epicCrystal());
		rewards1.add(playerXPBottle25());
		rewards1.add(playerXPBottle50());
		rewards1.add(playerXPBottle100());
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 2
		//--------------------------------------------------------------------------------------------------------------------
		rewards2.add(trickyFish());
		rewards2.add(seasonedCarrot());
		rewards2.add(bunnyPotion());
		rewards2.add(chocolateChippedCookie());
		rewards2.add(faceSteak());
		rewards2.add(skyFish());
		rewards2.add(legendaryHero());
		rewards2.add(steak8());
		rewards2.add(steak8());
		rewards2.add(steak8());
		rewards2.add(steak12());
		rewards2.add(steak12());
		rewards2.add(steak12());
		rewards2.add(potato16());
		rewards2.add(potato16());
		rewards2.add(potato16());
		rewards2.add(potato32());
		rewards2.add(potato32());
		rewards2.add(potato32());
		rewards2.add(potato64());
		rewards2.add(potato64());
		rewards2.add(potato64());
		rewards2.add(fish12());
		rewards2.add(fish12());
		rewards2.add(fish12());
		rewards2.add(fish18());
		rewards2.add(fish18());
		rewards2.add(fish18());
		rewards2.add(fish24());
		rewards2.add(fish24());
		rewards2.add(fish24());
		rewards2.add(chicken10());
		rewards2.add(chicken10());
		rewards2.add(chicken10());
		rewards2.add(chicken14());
		rewards2.add(chicken14());
		rewards2.add(chicken14());
		rewards2.add(chicken18());
		rewards2.add(chicken18());
		rewards2.add(chicken18());
		rewards2.add(obby4());
		rewards2.add(obby4());
		rewards2.add(obby4());
		rewards2.add(obby8());
		rewards2.add(obby8());
		rewards2.add(obby8());
		rewards2.add(obby16());
		rewards2.add(obby16());
		rewards2.add(obby16());
		rewards2.add(tnt2());
		rewards2.add(tnt2());
		rewards2.add(tnt2());
		rewards2.add(tnt4());
		rewards2.add(tnt4());
		rewards2.add(tnt4());
		rewards2.add(tnt8());
		rewards2.add(tnt8());
		rewards2.add(tnt8());
		rewards2.add(strPot1());
		rewards2.add(strPot1());
		rewards2.add(strPot1());
		rewards2.add(spdPot1());
		rewards2.add(spdPot1());
		rewards2.add(spdPot1());
		rewards2.add(eggWolf1());
		rewards2.add(eggWolf1());
		rewards2.add(eggWolf1());
		rewards2.add(eggOcelot1());
		rewards2.add(eggOcelot1());
		rewards2.add(eggOcelot1());
		rewards2.add(log12());
		rewards2.add(log12());
		rewards2.add(log12());
		rewards2.add(log16());
		rewards2.add(log16());
		rewards2.add(log16());
		rewards2.add(log20());
		rewards2.add(log20());
		rewards2.add(log20());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl4());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl8());
		rewards2.add(enderPearl16());
		rewards2.add(enderPearl16());
		rewards2.add(enderPearl16());
		rewards2.add(commonCrystal());
		rewards2.add(rareCrystal());
		rewards2.add(epicCrystal());
		rewards2.add(legendaryCrystal());
		rewards2.add(playerXPBottle25());
		rewards2.add(playerXPBottle50());
		rewards2.add(playerXPBottle100());
		rewards2.add(playerXPBottle250());
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 3
		//--------------------------------------------------------------------------------------------------------------------
		rewards3.add(trickyFish());
		rewards3.add(seasonedCarrot());
		rewards3.add(bunnyPotion());
		rewards3.add(chocolateChippedCookie());
		rewards3.add(faceSteak());
		rewards3.add(skyFish());
		rewards3.add(purifiedSpiderEye());
		rewards3.add(menJerky());
		rewards3.add(steak8());
		rewards3.add(steak8());
		rewards3.add(steak8());
		rewards3.add(steak12());
		rewards3.add(steak12());
		rewards3.add(steak12());
		rewards3.add(potato16());
		rewards3.add(potato16());
		rewards3.add(potato16());
		rewards3.add(potato32());
		rewards3.add(potato32());
		rewards3.add(potato32());
		rewards3.add(potato64());
		rewards3.add(potato64());
		rewards3.add(potato64());
		rewards3.add(fish12());
		rewards3.add(fish12());
		rewards3.add(fish12());
		rewards3.add(fish18());
		rewards3.add(fish18());
		rewards3.add(fish18());
		rewards3.add(fish24());
		rewards3.add(fish24());
		rewards3.add(fish24());
		rewards3.add(chicken10());
		rewards3.add(chicken10());
		rewards3.add(chicken10());
		rewards3.add(chicken14());
		rewards3.add(chicken14());
		rewards3.add(chicken14());
		rewards3.add(chicken18());
		rewards3.add(chicken18());
		rewards3.add(chicken18());
		rewards3.add(obby4());
		rewards3.add(obby4());
		rewards3.add(obby4());
		rewards3.add(obby8());
		rewards3.add(obby8());
		rewards3.add(obby8());
		rewards3.add(obby16());
		rewards3.add(obby16());
		rewards3.add(obby16());
		rewards3.add(tnt2());
		rewards3.add(tnt2());
		rewards3.add(tnt2());
		rewards3.add(tnt4());
		rewards3.add(tnt4());
		rewards3.add(tnt4());
		rewards3.add(tnt8());
		rewards3.add(tnt8());
		rewards3.add(tnt8());
		rewards3.add(strPot1());
		rewards3.add(strPot1());
		rewards3.add(strPot1());
		rewards3.add(spdPot1());
		rewards3.add(spdPot1());
		rewards3.add(spdPot1());
		rewards3.add(eggWolf1());
		rewards3.add(eggWolf1());
		rewards3.add(eggWolf1());
		rewards3.add(eggOcelot1());
		rewards3.add(eggOcelot1());
		rewards3.add(eggOcelot1());
		rewards3.add(log12());
		rewards3.add(log12());
		rewards3.add(log12());
		rewards3.add(log16());
		rewards3.add(log16());
		rewards3.add(log16());
		rewards3.add(log20());
		rewards3.add(log20());
		rewards3.add(log20());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl4());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl8());
		rewards3.add(enderPearl16());
		rewards3.add(enderPearl16());
		rewards3.add(enderPearl16());
		rewards3.add(commonCrystal());
		rewards3.add(rareCrystal());
		rewards3.add(epicCrystal());
		rewards3.add(legendaryCrystal());
		rewards3.add(mythicCrystal());
		rewards3.add(playerXPBottle25());
		rewards3.add(playerXPBottle50());
		rewards3.add(playerXPBottle100());
		rewards3.add(playerXPBottle250());
		rewards3.add(playerXPBottle500());
		//--------------------------------------------------------------------------------------------------------------------
		//Tier 4
		//--------------------------------------------------------------------------------------------------------------------
		rewards4.add(trickyFish());
		rewards4.add(seasonedCarrot());
		rewards4.add(bunnyPotion());
		rewards4.add(chocolateChippedCookie());
		rewards4.add(faceSteak());
		rewards4.add(skyFish());
		rewards4.add(purifiedSpiderEye());
		rewards4.add(menJerky());
		rewards4.add(butterScotchPie());
		rewards4.add(steak8());
		rewards4.add(steak8());
		rewards4.add(steak8());
		rewards4.add(steak12());
		rewards4.add(steak12());
		rewards4.add(steak12());
		rewards4.add(potato16());
		rewards4.add(potato16());
		rewards4.add(potato16());
		rewards4.add(potato32());
		rewards4.add(potato32());
		rewards4.add(potato32());
		rewards4.add(potato64());
		rewards4.add(potato64());
		rewards4.add(potato64());
		rewards4.add(fish12());
		rewards4.add(fish12());
		rewards4.add(fish12());
		rewards4.add(fish18());
		rewards4.add(fish18());
		rewards4.add(fish18());
		rewards4.add(fish24());
		rewards4.add(fish24());
		rewards4.add(fish24());
		rewards4.add(chicken10());
		rewards4.add(chicken10());
		rewards4.add(chicken10());
		rewards4.add(chicken14());
		rewards4.add(chicken14());
		rewards4.add(chicken14());
		rewards4.add(chicken18());
		rewards4.add(chicken18());
		rewards4.add(chicken18());
		rewards4.add(obby4());
		rewards4.add(obby4());
		rewards4.add(obby4());
		rewards4.add(obby8());
		rewards4.add(obby8());
		rewards4.add(obby8());
		rewards4.add(obby16());
		rewards4.add(obby16());
		rewards4.add(obby16());
		rewards4.add(tnt2());
		rewards4.add(tnt2());
		rewards4.add(tnt2());
		rewards4.add(tnt4());
		rewards4.add(tnt4());
		rewards4.add(tnt4());
		rewards4.add(tnt8());
		rewards4.add(tnt8());
		rewards4.add(tnt8());
		rewards4.add(strPot1());
		rewards4.add(strPot1());
		rewards4.add(strPot1());
		rewards4.add(spdPot1());
		rewards4.add(spdPot1());
		rewards4.add(spdPot1());
		rewards4.add(eggWolf1());
		rewards4.add(eggWolf1());
		rewards4.add(eggWolf1());
		rewards4.add(eggOcelot1());
		rewards4.add(eggOcelot1());
		rewards4.add(eggOcelot1());
		rewards4.add(log12());
		rewards4.add(log12());
		rewards4.add(log12());
		rewards4.add(log16());
		rewards4.add(log16());
		rewards4.add(log16());
		rewards4.add(log20());
		rewards4.add(log20());
		rewards4.add(log20());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl4());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl8());
		rewards4.add(enderPearl16());
		rewards4.add(enderPearl16());
		rewards4.add(enderPearl16());
		rewards4.add(commonCrystal());
		rewards4.add(rareCrystal());
		rewards4.add(epicCrystal());
		rewards4.add(legendaryCrystal());
		rewards4.add(mythicCrystal());
		rewards4.add(heroicCrystal());
		rewards4.add(playerXPBottle25());
		rewards4.add(playerXPBottle50());
		rewards4.add(playerXPBottle100());
		rewards4.add(playerXPBottle250());
		rewards4.add(playerXPBottle500());
		rewards4.add(playerXPBottle1000());
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
		ItemStack item1 = new ItemStack(Material.POTION, 1, (byte)8201);
		return item1;
	}
	public ItemStack spdPot1() {
		ItemStack item1 = new ItemStack(Material.POTION, 1, (byte)8194);
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
	public ItemStack commonCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Common Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack rareCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&aRare Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &aRare"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack epicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&bEpic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &bEpic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack legendaryCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&cLegendary Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &cLegendary"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack mythicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&5Mythic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &5Mythic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack heroicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&eHeroic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &eHeroic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack trickyFish() {
		ItemStack item1 = new ItemStack(Material.COD, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Tricky Fish"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 15 seconds of Invisibility I."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack seasonedCarrot() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_CARROT, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Seasoned Carrot"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 5 seconds of Resistance I."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack chocolateChippedCookie() {
		ItemStack item1 = new ItemStack(Material.COOKIE, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&aChocolate Chipped Cookie"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 4 seconds of Speed II."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack bunnyPotion() {
		ItemStack item1 = new ItemStack(Material.POTION, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&aBunny Potion"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you drink this, you will gain 15 seconds of Speed I and Jump II."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack faceSteak() {
		ItemStack item1 = new ItemStack(Material.COOKED_BEEF, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&bFace Steak"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 15 seconds of Fire Resistance I."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack skyFish() {
		ItemStack item1 = new ItemStack(Material.COD, 1, (byte)2);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&bSky Fish"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 12.5 seconds of Levitation I."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack legendaryHero() {
		ItemStack item1 = new ItemStack(Material.BREAD, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&cLegendary Hero"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you regen 5% of your max HP"));
		lore1.add(new ColorCodeTranslator().colorize("&7And you will deal 10% more damage for 10 seconds."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack menJerky() {
		ItemStack item1 = new ItemStack(Material.BEEF, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&5Men Jerky"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you regen 2 HP"));
		lore1.add(new ColorCodeTranslator().colorize("&7And you will gain 5 second of Strength I"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack purifiedSpiderEye() {
		ItemStack item1 = new ItemStack(Material.SPIDER_EYE, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&5Purified Spider Eye"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you regen 2% of you're MAX HP"));
		lore1.add(new ColorCodeTranslator().colorize("&7And it will cleanse you from any poison effect."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack fusgel() {
		ItemStack item1 = new ItemStack(Material.YELLOW_WOOL);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&6Fusgel"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 15 seconds of Regeneration II."));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack holyPotato() {
		ItemStack item1 = new ItemStack(Material.POTATO, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&6Holy Potato"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you will gain 10 seconds of Regeneration II."));
		lore1.add(new ColorCodeTranslator().colorize("&7You will also gain 20 seconds of Absorption IV"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack butterScotchPie() {
		ItemStack item1 = new ItemStack(Material.PUMPKIN_PIE, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&6Butterscotch Pie"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you eat this, you heal 20% of you're MAX HP"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack playerXPBottle25() {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: 25"));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack playerXPBottle50() {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: 50"));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack playerXPBottle100() {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: 100"));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack playerXPBottle250() {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: 250"));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack playerXPBottle500() {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: 500"));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack playerXPBottle1000() {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: 1000"));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
}
