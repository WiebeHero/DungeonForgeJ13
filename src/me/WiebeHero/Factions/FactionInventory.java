package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class FactionInventory {
	
	private ItemStackBuilder builder;
	int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
	
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
		ItemStack banner = faction.getBanner().clone();
		NBTItem item = new NBTItem(banner);
		item.setString("BannerRemove", "");
		item.setString("BannerSet", "");
		banner = item.getItem();
		ItemMeta meta = banner.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&6" + faction.getName() + "'s banner"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7Left click a banner in your inventory"));
		lore.add(new CCT().colorize("&7to set your faction banner."));
		lore.add(new CCT().colorize("&7Right click this banner to reset your"));
		lore.add(new CCT().colorize("&7faction banner."));
		meta.setLore(lore);
		banner.setItemMeta(meta);
		i.setItem(13, banner);
		player.openInventory(i);
	}
	
	public void MainFactionInventory(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&aFaction Overview: &6" + faction.getName())));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing);
		}
		
		ItemStack overview = this.builder.constructItem(
				Material.PAPER,
				"&aFaction: &6" + faction.getName(),
				new ArrayList<String>(Arrays.asList(
						"&7Faction Name: &6" + faction.getName(),
						"&7Faction Leader: &6" + faction.getLeader().getName(),
						"&7Faction Level: &6" + faction.getLevel(),
						"&7Click this to view your faction leveling process!"
				)),
				new Pair<String, String>("Leveling", "")
		);
		i.setItem(10, overview);
		
		ItemStack members = this.builder.constructItem(
				Material.BOOK,
				"&cFaction Members (Coming Soon...)",
				new ArrayList<String>(Arrays.asList(
						"&7Your faction has a total of &6" + faction.getMembers().size() + " &7members",
						"&7in your faction. Click here to view members and",
						"&7manage them!"
				)),
				new Pair<String, String>("Members", "")
		);
		i.setItem(11, members);
		
		ItemStack chunks = this.builder.constructItem(
				Material.GRASS_BLOCK,
				"&cChunks and Homes (Coming Soon...)",
				new ArrayList<String>(Arrays.asList(
						"&7You have a total of &6" + faction.getChunkList().size() + " &7chunks claimed.",
						"&7You have a total of &6" + faction.getFactionHomesAmount() + " &7factions homes set.",
						"&7Click here to view your chunks locations and",
						"&7faction homes and manage them!"
				)),
				new Pair<String, String>("ChunksAndHomes", "")
		);
		i.setItem(12, chunks);
		
		ItemStack energy = this.builder.constructItem(
				Material.END_CRYSTAL,
				"&aEnergy",
				new ArrayList<String>(Arrays.asList(
						"&7Your faction has &6" + String.format("%.2f", faction.getEnergy()) + " &7/ &6" + faction.getMaxEnergy() + " &7energy.",
						"&7Click here to view your chunks locations and",
						"&7manage them!"
				))
		);
		i.setItem(13, energy);
		
		ItemStack vault = this.builder.constructItem(
				Material.CHEST,
				"&cVault (&cComing Soon...)",
				new ArrayList<String>(Arrays.asList(
						"&7This is your faction vault! Click this",
						"&7to open your faction vault!"
				)),
				new Pair<String, String>("Vault", "")
		);
		i.setItem(14, vault);
		
		ItemStack settings = this.builder.constructItem(
				Material.COMPARATOR,
				"&cFaction Settings (Coming Soon...)",
				new ArrayList<String>(Arrays.asList(
						"&7Click this to edit your faction settings!"
				)),
				new Pair<String, String>("Settings", "")
		);
		i.setItem(15, settings);
		
		ItemStack banner = faction.getBanner().clone();
		banner = this.builder.constructItem(
				banner,
				"&6" + faction.getName() + "'s banner",
				new ArrayList<String>(Arrays.asList(
						"&7Left click a banner in your inventory",
						"&7to set your faction banner.",
						"&7Right click this banner to reset your",
						"&7faction banner."
				)),
				new Pair<String, String>("BannerRemove", ""),
				new Pair<String, String>("BannerSet", "")
		);
		i.setItem(16, banner);
		
		player.openInventory(i);
	}
	
	public void FactionLevelProgress(Player player, DFFaction faction, int page) {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		int level = faction.getLevel();
		int maxPage = (int) Math.ceil((double)faction.getMaxLevel() / 28.00);
		for(int i = 1; i <= maxPage * 28.00; i++) {
			if(i <= faction.getMaxLevel()) {
				stacks.add(this.builder.constructItem(
						level >= i ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE,
						1,
						level >= i ? "&aFaction Level: &6" + i : "&cFaction Level: &6" + i,
						new ArrayList<String>(Arrays.asList(
								"&7+ Increase player xp gain.",
								"&7+ Increase item xp gain. (From mobs)",
								i % 10 == 0 ? "&7+ Increase max faction member size." : null,
								i % 20 == 0 ? "&7+ Increase faction vault size." : null,
								i % 25 == 0 ? "&7+ Increase max faction homes." : null
						))
				));
			}
			else {
				stacks.add(this.builder.constructItem(
						Material.RED_STAINED_GLASS_PANE,
						1,
						"&cUnknown",
						new ArrayList<String>(Arrays.asList(
								"&7Coming soon..."
						))
				));
			}
		}
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&aFaction Leveling: &6" + page + " / " + maxPage)));
		int array[] = new int[] {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing);
		}
		
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], stacks.get(x + 28 * (page - 1)));
		}
		i.setItem(8, this.builder.constructItem(
				Material.ENCHANTING_TABLE,
				1,
				"&6Progression Table",
				new ArrayList<String>(Arrays.asList(
						"&7Level: &b" + faction.getLevel(),
						"&7Next Level: &7[&b" + faction.getExperience() + " &6/ &b" + faction.getMaxExperience() + "&7]"
				))
		));
		i.setItem(48, this.builder.constructItem(
				Material.PAPER,
				1,
				"&aPrevious Page",
				new Pair<String, Integer>("Previous", page - 1)
		));
		i.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		i.setItem(50, this.builder.constructItem(
				Material.PAPER,
				1,
				"&aNext Page",
				new Pair<String, Integer>("Next", page + 1)
		));
		player.openInventory(i);
	}
	
	public void FactionMemberManagement(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&aFaction Members: &6" + faction.getName())));
	}
	
	public void FactionChunkHomes(Player player, DFFaction faction) {
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 44};
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Homes and Chunks")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		i.setItem(20, this.builder.constructItem(
				Material.OAK_DOOR,
				1,
				"&aFaction Homes",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to manage your",
						"&7faction homes!"
				)),
				new Pair<String, String>("Homes", "")
		));
		i.setItem(24, this.builder.constructItem(
				Material.GRASS_BLOCK,
				1,
				"&aChunks",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to manage your",
						"&7chunks!"
				)),
				new Pair<String, String>("Chunks", "")
		));
		i.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
	}
	
	public void FactionChunkManagement(Player player, DFFaction faction) {
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 44};
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Homes and Chunks")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		i.setItem(20, this.builder.constructItem(
				Material.OAK_DOOR,
				1,
				"&aFaction Homes",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to manage your",
						"&7faction homes!"
				)),
				new Pair<String, String>("Homes", "")
		));
		i.setItem(24, this.builder.constructItem(
				Material.GRASS_BLOCK,
				1,
				"&aChunks",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to manage your",
						"&7chunks!"
				)),
				new Pair<String, String>("Chunks", "")
		));
		i.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
	}
	
	public void FactionHomeManagement(Player player, DFFaction faction) {
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 44};
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Homes and Chunks")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		i.setItem(20, this.builder.constructItem(
				Material.OAK_DOOR,
				1,
				"&aFaction Homes",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to manage your",
						"&7faction homes!"
				)),
				new Pair<String, String>("Homes", "")
		));
		i.setItem(24, this.builder.constructItem(
				Material.GRASS_BLOCK,
				1,
				"&aChunks",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to manage your",
						"&7chunks!"
				)),
				new Pair<String, String>("Chunks", "")
		));
		i.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
	}
	
	public void FactionVault(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&aFaction Vault: &6" + faction.getName())));
	}
	
	public void FactionSettings(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&aFaction Settings: &6" + faction.getName())));
	}
}
