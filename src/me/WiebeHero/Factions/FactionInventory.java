package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.Factions.DFFactionGroups.FactionGroup;
import me.WiebeHero.Factions.DFFactionGroups.FactionPermission;

public class FactionInventory {
	
	private ItemStackBuilder builder;
	private DFFactionGroups group;
	private int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
	
	public FactionInventory(ItemStackBuilder builder, DFFactionGroups group) {
		this.builder = builder;
		this.group = group;
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
				new String[] {
						"&7Faction Name: &6" + faction.getName(),
						"&7Faction Leader: &6" + faction.getLeader().getName(),
						"&7Faction Level: &6" + faction.getLevel(),
						"&7Click this to view your faction leveling process!"
				},
				new Pair<String, String>("Leveling", "")
		);
		i.setItem(10, overview);
		
		ItemStack members = this.builder.constructItem(
				Material.BOOK,
				"&aFaction Members",
				new String[] {
						"&7Your faction has a total of &6" + faction.getMembers().size() + " &7members",
						"&7in your faction. Click here to view members and",
						"&7manage them!"
				},
				new Pair<String, String>("Members", "")
		);
		i.setItem(11, members);
		
		ItemStack chunks = this.builder.constructItem(
				Material.GRASS_BLOCK,
				"&aChunks and Homes",
				new String[] {
						"&7You have a total of &6" + faction.getChunkList().size() + " &7chunks claimed.",
						"&7You have a total of &6" + faction.getFactionHomesAmount() + " &7factions homes set.",
						"&7Click here to view your chunks locations and",
						"&7faction homes and manage them!"
				},
				new Pair<String, String>("ChunksAndHomes", "")
		);
		i.setItem(12, chunks);
		
		ItemStack energy = this.builder.constructItem(
				Material.END_CRYSTAL,
				"&aEnergy",
				new String[] {
						"&7Your faction has &6" + String.format("%.2f", faction.getEnergy()) + " &7/ &6" + faction.getMaxEnergy() + " &7energy."
				}
		);
		i.setItem(13, energy);
		
		ItemStack vault = this.builder.constructItem(
				Material.CHEST,
				"&aFaction Vault",
				new String[] {
						"&7This is your faction vault! Click this",
						"&7to open your faction vault!"
				},
				new Pair<String, String>("Vault", "")
		);
		i.setItem(14, vault);
		
		ItemStack settings = this.builder.constructItem(
				Material.COMPARATOR,
				"&cFaction Settings (Coming Soon...)",
				new String[] {
						"&7Click this to edit your faction settings!"
				},
				new Pair<String, String>("Settings", "")
		);
		i.setItem(15, settings);
		
		ItemStack banner = faction.getBanner().clone();
		banner = this.builder.constructItem(
				banner,
				"&6" + faction.getName() + "'s banner",
				new String[] {
						"&7Left click a banner in your inventory",
						"&7to set your faction banner.",
						"&7Right click this banner to reset your",
						"&7faction banner."
				},
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
						new String[] {
								"&7+ Increase player xp gain.",
								"&7+ Increase item xp gain. (From mobs)",
								i % 10 == 0 ? "&7+ Increase max faction member size." : null,
								i % 20 == 0 ? "&7+ Increase faction vault size." : null,
								i % 25 == 0 ? "&7+ Increase max faction homes." : null
						}
				));
			}
			else {
				stacks.add(this.builder.constructItem(
						Material.RED_STAINED_GLASS_PANE,
						1,
						"&cUnknown",
						new String[] {
								"&7Coming soon..."
						}
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
				new String[] {
						"&7Level: &b" + faction.getLevel(),
						"&7Next Level: &7[&b" + faction.getExperience() + " &6/ &b" + faction.getMaxExperience() + "&7]"
				}
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
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Member Management")));
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 41, 42, 43, 44};
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		i.setItem(20, this.builder.constructItem(
				Material.PLAYER_HEAD,
				1,
				"&a&lView members",
				new String[] {
						"&7Click this to promote, demote and",
						"&7kick faction members!"
				},
				new Pair<String, String>("View", "")
		));
		i.setItem(24, this.builder.constructItem(
				Material.BOOK,
				1,
				"&a&lPermissions",
				new String[] {
						"&7Click this to manage the permissions",
						"&7that your faction groups has!"
				},
				new Pair<String, String>("Permission", "")
		));
		i.setItem(40, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionMemberMenu(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&aFaction Members")));
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52, 53};
		int memberSlots[] = new int[] {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		ArrayList<UUID> uuids = faction.getMembers();
		HashMap<UUID, DFFactionPlayer> facPlayers = faction.getMemberMap();
		DFFactionPlayer facPlayer = facPlayers.get(player.getUniqueId());
		int rankMe = facPlayer.getRank();
		FactionGroup group = this.group.getRankFactionGroup(rankMe);
		for(int x = 0; x < uuids.size(); x++) {
			OfflinePlayer pl = Bukkit.getOfflinePlayer(uuids.get(x));
			DFFactionPlayer facP = facPlayers.get(uuids.get(x));
			int rankOther = facP.getRank();
			ItemStack head = CustomEnchantments.getInstance().createHead(pl);
			i.setItem(memberSlots[x], this.builder.constructItem(
					head,
					1,
					"&a" + pl.getName(),
					new String[] {
							"&7Rank: &6" + group.getDisplay(),
							faction.getPermission(group, FactionPermission.PROMOTE_FACTION_MEMBERS) && rankMe > rankOther ? "&7Left click to promote this player!" : null,
							faction.getPermission(group, FactionPermission.DEMOTE_FACTION_MEMBERS) && rankMe > rankOther ? "&7Right click to demote this player!" : null,
							faction.getPermission(group, FactionPermission.KICK_FACTION_MEMBERS) && rankMe > rankOther ? "&7Middle click to kick this player!" : null
					},
					new Pair<String, UUID>("Player", uuids.get(x))
			));
		}
		i.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionGroupSelection(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Groups")));
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		i.setItem(20, this.builder.constructItem(
				Material.LEATHER_HELMET,
				1,
				"&7Recruit",
				new String[] {
						"&7Click here to manage the permissions",
						"&7for the recruits!"
				},
				new Pair<String, FactionGroup>("FactionGroup", FactionGroup.RECRUIT)
		));
		i.setItem(22, this.builder.constructItem(
				Material.CHAINMAIL_HELMET,
				1,
				"&7Members",
				new String[] {
						"&7Click here to manage the permissions",
						"&7for the members!"
				},
				new Pair<String, FactionGroup>("FactionGroup", FactionGroup.MEMBER)
		));
		i.setItem(24, this.builder.constructItem(
				Material.IRON_HELMET,
				1,
				"&7Officier",
				new String[] {
						"&7Click here to manage the permissions",
						"&7for the officers!"
				},
				new Pair<String, FactionGroup>("FactionGroup", FactionGroup.OFFICER)
		));
		i.setItem(40, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionPermissionMenu(Player player, DFFaction faction, FactionGroup group) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&aFaction Permissions: " + group.getDisplay())));
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
		int slots[] = new int[] {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		ArrayList<Pair<FactionPermission, Boolean>> list = faction.getPermissions(group);
		for(int x = 0; x < list.size(); x++) {
			Pair<FactionPermission, Boolean> pair = list.get(x);
			i.setItem(slots[x], this.builder.constructItem(
					Material.PAPER,
					1,
					"&a" + pair.getKey().getDisplay(),
					new String[] {
							pair.getValue() ? "&7" + pair.getKey().getDisplay() + ": &aOn" : "&7" + pair.getKey().getDisplay() + ": &cOff"
					},
					new Pair<String, FactionPermission>(pair.getKey().getDisplay(), pair.getKey()),
					new Pair<String, FactionGroup>("FactionGroup", group)
			));
		}
		i.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionChunkHomes(Player player, DFFaction faction) {
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 41, 42, 43, 44};
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
				new String[] {
						"&7Click here to manage your",
						"&7faction homes!"
				},
				new Pair<String, String>("Homes", "")
		));
		i.setItem(24, this.builder.constructItem(
				Material.GRASS_BLOCK,
				1,
				"&aChunks",
				new String[] {
						"&7Click here to manage your",
						"&7chunks!"
				},
				new Pair<String, String>("Chunks", "")
		));
		i.setItem(40, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionChunkManagement(Player player, DFFaction faction) {
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 41, 42, 43, 44};
		int slots[] = new int[] {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Chunks")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		DFFactionPlayer facPlayer = faction.getMemberMap().get(player.getUniqueId());
		ArrayList<Long> chunkList = faction.getChunkList();
		if(!chunkList.isEmpty()) {
			for(int x = 0; x < chunkList.size(); x++) {
				Long key = chunkList.get(x);
				Chunk c = player.getWorld().getChunkAt(key);
				Location center = c.getBlock(8, 64, 8).getLocation().add(0.5, 0.0, 0.5);
				String xS = String.format("%.2f", center.getX());
				String zS = String.format("%.2f", center.getZ());
				FactionGroup group = this.group.getRankFactionGroup(facPlayer.getRank());
				boolean state = faction.getPermission(group, FactionPermission.SET_FACTION_HOMES);
				i.setItem(slots[x], this.builder.constructItem(
						Material.GRASS_BLOCK,
						1,
						"&aFaction Chunk",
						new String[] {
								"&7Chunk Location: &6X: " + xS + " Z: " + zS,
								state ? "&7Right click to unclaim this chunk!" : ""
						},
						new Pair<String, Long>("Chunk", key)
				));
			}
		}
		else {
			i.setItem(22, this.builder.constructItem(
					Material.RED_STAINED_GLASS_PANE,
					1,
					"&cNo chunks claimed yet!",
					new String[] {}
			));
		}
		i.setItem(40, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionHomeManagement(Player player, DFFaction faction) {
		int array[] = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 41, 42, 43, 44};
		int slots[] = new int[] {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&aFaction Homes")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < array.length; x++) {
			i.setItem(array[x], nothing);
		}
		DFFactionPlayer facPlayer = faction.getMemberMap().get(player.getUniqueId());
		if(!faction.getFactionHomes().isEmpty()) {
			int x = 0;
			FactionGroup group = this.group.getRankFactionGroup(facPlayer.getRank());
			boolean state = faction.getPermission(group, FactionPermission.SET_FACTION_HOMES);
			for(Entry<String, Location> entry : faction.getFactionHomes().entrySet()) {
				String name = entry.getKey();
				Location loc = entry.getValue();
				i.setItem(slots[x], this.builder.constructItem(
					Material.OAK_DOOR,
					1,
					"&aFaction Home: &6" + name,
					new String[] {
							"&7Location: &6X: " + String.format("%.2f", loc.getX()) + " Y: " + String.format("%.2f", loc.getY()) + " Z: " + String.format("%.2f", loc.getZ()),
							state ? "&7Right click to remove this faction home!" : null
					},
					new Pair<String, String>("FactionHome", name)
				));
				x++;
			}
		}
		else {
			i.setItem(22, this.builder.constructItem(
					Material.RED_STAINED_GLASS_PANE,
					1,
					"&cNo faction homes set yet!",
					new String[] {}
			));
		}
		i.setItem(40, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&cBack",
				new Pair<String, String>("Back", "")
		));
		player.openInventory(i);
	}
	
	public void FactionVault(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&aFaction Vault: &6" + faction.getName())));
	}
	
	public void FactionSettings(Player player, DFFaction faction) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&aFaction Settings: &6" + faction.getName())));
	}
}
