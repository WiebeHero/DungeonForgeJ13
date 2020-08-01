package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;

public class ModerationGUI implements Listener{
	private DFPlayerManager dfManager;
	private ItemStackBuilder builder;
	private PunishManager pManager;
	public ModerationGUI(DFPlayerManager dfManager, ItemStackBuilder builder, PunishManager pManager) {
		this.dfManager = dfManager;
		this.builder = builder;
		this.pManager = pManager;
	}
	public HashMap<UUID, Integer> offenseList = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> reasonList = new HashMap<UUID, Integer>();
	public void PunishMenu(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish Menu: &c" + name)));
		i.setItem(0, nothing());
		i.setItem(1, this.builder.constructItem(
				Material.BOOK, 
				"&cBan",
				new String[] {
						"&7--------------------",
						"&7Click this to go to the ban menu.",
						"&7--------------------" 
				},
				new Pair<String, String>("Ban Option", "")
		));
		i.setItem(2, this.builder.constructItem(
				Material.BOOKSHELF, 
				"&bHistory", 
				new String[] {
						"&7--------------------",
						"&7Click this to go to the history menu.",
						"&7This shows a menu of previous punishments.",
						"&7--------------------" 
				},
				new Pair<String, String>("History Option", "")
		));
		i.setItem(3, this.builder.constructItem(
				Material.PAPER, 
				"&bMute", 
				new String[] {
						"&7--------------------",
						"&7Click this to go to the mute menu.",
						"&7--------------------" 
				},
				new Pair<String, String>("Mute Option", "")
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void BanChoice(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish, Ban Choice: &c" + name)));
		i.setItem(0, nothing());
		i.setItem(1, this.builder.constructItem(
				Material.ENCHANTED_BOOK, 
				"&cBan", 
				new String[] {
						"&7--------------------",
						"&7Click this to ban &6" + name,
						"&7--------------------" 
				},
				new Pair<String, String>("Ban", "")
		));
		i.setItem(2, nothing());
		i.setItem(3, this.builder.constructItem(
				Material.BOOK, 
				"&cUnban", 
				new String[] {
						"&7--------------------",
						"&7Click this to unban &6" + name,
						"&7--------------------" 
				},
				new Pair<String, String>("Unban", "")
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void BanReason(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 36, (new CCT().colorize("&6Punish, Ban Reason: &c" + name)));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.builder.constructItem(
				Material.DIAMOND_SWORD, 
				"&cCombat Modifications",
				new String[] {
					"&cAimbot, Bowaimbot, Forcefield",
					"&cor other combat related modifications."
				},
				new Pair<String, String>("Combat Modifications", "")
		));
		i.setItem(11, this.builder.constructItem(
				Material.REDSTONE, 
				"&cAuto Modifications",
				new String[] {
					"&cAutoarmor, Autosword, Automine, Autoeat, Autofish, Autoswim, Autosoup, Autosteal",
					"&cor other auto related modifications."
				},
				new Pair<String, String>("Auto Modifications", "")
		));
		i.setItem(12, this.builder.constructItem(
				Material.FEATHER, 
				"&cMovement Modifications",
				new String[] {
					"&cTeleport, Fly, Bunny Hop, Blink, Safewalk, Speed, Anit Knockback, Sneak, Freecam",
					"&cor other movement related modifications."
				},
				new Pair<String, String>("Movement Modifications", "")
		));
		i.setItem(13, this.builder.constructItem(
				Material.COMPASS, 
				"&cESP Modifications",
				new String[] {
					"&cPlayer ESP, Player Tracer, Item ESP, Mob ESP, Chest ESP, Block ESP, Minimap",
					"&cor other ESP related modifications."
				},
				new Pair<String, String>("ESP Modifications", "")
		));
		i.setItem(14, this.builder.constructItem(
				Material.APPLE, 
				"&cHealth Modifications",
				new String[] {
					"&cGodmode, Antipotion, Zoot, Health Hacks",
					"&cor other health related modifications."
				},
				new Pair<String, String>("Health Modifications", "")
		));
		i.setItem(15, this.builder.constructItem(
				Material.GLASS, 
				"&cBuilding Modifications",
				new String[] {
					"&cNuker, Fastbuild, Fastbreak, Scaffold",
					"&cor other building related modifications."
				},
				new Pair<String, String>("Building Modifications", "")
		));
		i.setItem(16, this.builder.constructItem(
				Material.OBSIDIAN, 
				"&cWallhack Modifications",
				new String[] {
					"&cXray, Wallhack",
					"&cor other wallhack related modifications."
				},
				new Pair<String, String>("Wallhack Modifications", "")
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, this.builder.constructItem(
				Material.JUKEBOX, 
				"&cParty Modifications",
				new String[] {
					"&cFullbright, Twerk, Derp",
					"&cor other party related modifications."
				},
				new Pair<String, String>("Party Modifications", "")
		));
		i.setItem(20, this.builder.constructItem(
				Material.TNT, 
				"&cIllegal Raiding",
				new String[] {
					"&cBugraiding/Trickraiding",
					"&cor other illegal raid options."
				},
				new Pair<String, String>("Illegal Raiding", "")
		));
		i.setItem(21, this.builder.constructItem(
				Material.GRASS, 
				"&cMap Glitching",
				new String[] {
					"&cGlitching out of any world/Abusing Map glitches",
					"&cor other Map glitches."
				},
				new Pair<String, String>("Map Glitching", "")
		));	
		i.setItem(22, this.builder.constructItem(
				Material.GOLDEN_PICKAXE, 
				"&cDuplicating Items/Glitching Items",
				new String[] {
					"&cDuplicating Items/Glitching Items",
					"&cor other Dupe/Glitch bugs."
				},
				new Pair<String, String>("Item Duplication/Glitching", "")
		));
		i.setItem(23, this.builder.constructItem(
				Material.BARRIER, 
				"&cAbusing selfmade/other plugins",
				new String[] {
					"&cAbusing selfmade/other plugins to gain an advantage"
				},
				new Pair<String, String>("Abusing selfmade/other plugins", "")
		));
		i.setItem(24, this.builder.constructItem(
				Material.JUKEBOX, 
				"&cKeep/Using forbidden items",
				new String[] {
					"&cKeep/Using forbidden items"
				},
				new Pair<String, String>("Forbidden Items", "")
		));
		i.setItem(26, nothing());
		i.setItem(27, nothing());
		i.setItem(28, nothing());
		i.setItem(29, nothing());
		i.setItem(30, nothing());
		i.setItem(31, nothing());
		i.setItem(32, nothing());
		i.setItem(33, nothing());
		i.setItem(34, nothing());
		i.setItem(35, nothing());
		reporter.openInventory(i);
	}
	public void MuteChoice(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish, Mute Choice: &c" + name)));
		i.setItem(0, nothing());
		i.setItem(1, this.builder.constructItem(
				Material.ENCHANTED_BOOK, 
				"&cMute", 
				new String[] {
						"&7--------------------",
						"&7Click this to mute &6" + name,
						"&7--------------------" 
				},
				new Pair<String, String>("Mute", "")
		));
		i.setItem(2, nothing());
		i.setItem(3, this.builder.constructItem(
				Material.BOOK, 
				"&cUnmute", 
				new String[] {
						"&7--------------------",
						"&7Click this to unmute &6" + name,
						"&7--------------------" 
				},
				new Pair<String, String>("Unmute", "")
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void MuteReason(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Punish, Mute Reason: &c" + name)));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.builder.constructItem(
				Material.BARRIER, 
				"&cSpamming",
				new String[] {
						"&cSpamming, Bypassing chat filters/caps typing."
				},
				new Pair<String, String>("Spamming Related", "")
		));
		i.setItem(11, this.builder.constructItem(
				Material.BROWN_WOOL, 
				"&cInsulting",
				new String[] {
						"&cInsulting staff or a member (example; FUCK YOU CALLEDRISINGSUN)"
				},
				new Pair<String, String>("Insult Related", "")
		));
		i.setItem(12, this.builder.constructItem(
				Material.PINK_WOOL, 
				"&cSlurs",
				new String[] {
						"&cSexual Slurs/Racist Slurs/Pedophilic Slurs."
				},
				new Pair<String, String>("Slur Related", "")
		));
		i.setItem(13, this.builder.constructItem(
				Material.RED_WOOL, 
				"&cThreats",
				new String[] {
						"&cDeath Threats/General Threats",
						"&c(for example; I'M GOING TO FIND YOUR ADRESS AND I'LL KILL YOUR FAMILY)"
				},
				new Pair<String, String>("Threat Related", "")
		));
		i.setItem(14, this.builder.constructItem(
				Material.REDSTONE, 
				"&cChat Modifications",
				new String[] {
						"&cFancy Chat/Chat Modifications/Autotext Spammer"
				},
				new Pair<String, String>("Chat Related", "")
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		reporter.openInventory(i);
	}
	public void PunishHistory(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&6Punish History: &c" + name)));
		for(int x = 0; x < 45; x++) {
			i.setItem(x, nothing());
		}
		OfflinePlayer p = Bukkit.getOfflinePlayer(name);
		Punish punish = pManager.get(p.getUniqueId());
		ArrayList<String> replacedLore = new ArrayList<String>();
		if(punish.isBanned()) {
			replacedLore.add("&7Currently Banned: Yes");
			if(punish.getBanTime() > System.currentTimeMillis()) {
				Long timeLeft = punish.getBanTime() - System.currentTimeMillis();
				long diffSeconds = timeLeft / 1000 % 60;
		        long diffMinutes = timeLeft / (60 * 1000) % 60;
		        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
		        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
		        String time = diffDays + " Days " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
				replacedLore.add("&7Can come back in: &b" + time);
			}
			else if(punish.getBanPerm()){
				replacedLore.add("&7Can come back in: &bNever");
			}
		}
		else {
			replacedLore.add("&7Currently Banned: No");
		}
		if(punish.isMuted()) {
			replacedLore.add("&7Currently Muted: Yes");
			if(punish.getMuteTime() > System.currentTimeMillis()) {
				Long timeLeft = punish.getMuteTime() - System.currentTimeMillis();
				long diffSeconds = timeLeft / 1000 % 60;
		        long diffMinutes = timeLeft / (60 * 1000) % 60;
		        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
		        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
		        String time = diffDays + " Days " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
				replacedLore.add("&7Can chat again in: &b" + time);
			}
			else if(punish.getBanPerm()){
				replacedLore.add("&7Can chat again in: &bNever");
			}
		}
		else {
			replacedLore.add("&7Currently Muted: No");
		}
		ItemStack head = this.builder.constructItem(
				Material.PLAYER_HEAD,
				1,
				"&6" + name,
				replacedLore
		);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(p);
		head.setItemMeta(meta);
		i.setItem(15, head);
		ArrayList<String> banReasons = punish.getBanReasonsList();
		ArrayList<String> bannedBy = punish.getBannedByList();
		ArrayList<String> banDates = punish.getBanDateList();
		ArrayList<String> total = new ArrayList<String>();
		total.add("&7----------------------------");
		if(!banReasons.isEmpty()) {
			for(int x = 0; x < banReasons.size(); x++) {
				total.add("&7Banned on: " + banDates.get(x));
				total.add("&7Banned by: " + bannedBy.get(x));
				total.add("&7Banned reason: " + banReasons.get(x));
				total.add("&7----------------------------");
			}
		}
		else {
			total.add("&7This player has not been banned yet!");
			total.add("&7----------------------------");
		}
		i.setItem(24, this.builder.constructItem(
				Material.BOOK,
				"&cBan History",
				total
		));
		ArrayList<String> muteReasons = punish.getMuteReasonsList();
		ArrayList<String> mutedBy = punish.getMutedByList();
		ArrayList<String> muteDates = punish.getMuteDateList();
		total = new ArrayList<String>();
		total.add("&7----------------------------");
		if(!muteReasons.isEmpty()) {
			for(int x = 0; x < muteReasons.size(); x++) {
				total.add("&7Muted on: " + muteDates.get(x));
				total.add("&7Muted by: " + mutedBy.get(x));
				total.add("&7Muted reason: " + muteReasons.get(x));
				total.add("&7----------------------------");
			}
		}
		else {
			total.add("&7This player has not been muted yet!");
			total.add("&7----------------------------");
		}
		i.setItem(33, this.builder.constructItem(
				Material.BOOK,
				"&cMute History",
				total
		));
		
		i.setItem(11, this.builder.constructItem(
				Material.PAPER,
				"&cRemove first ban punishment.",
				new String[] {
					"&7Remove the first ban punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				},
				new Pair<String, String>("Remove First Ban", "")
		));
		
		i.setItem(12, this.builder.constructItem(
				Material.PAPER,
				"&cRemove first mute punishment.",
				new String[] {
					"&7Remove the first mute punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				},
				new Pair<String, String>("Remove First Mute", "")
		));
		
		i.setItem(20, this.builder.constructItem(
				Material.PAPER,
				"&cRemove last ban punishment.",
				new String[] {
					"&7Remove the last ban punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				},
				new Pair<String, String>("Remove Last Ban", "")
		));
		
		i.setItem(21, this.builder.constructItem(
				Material.PAPER,
				"&cRemove last mute punishment.",
				new String[] {
					"&7Remove the last mute punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				},
				new Pair<String, String>("Remove Last Mute", "")
		));
		
		i.setItem(29, this.builder.constructItem(
				Material.PAPER,
				"&c&lClear ban punishments.",
				new String[] {
					"&7Clear all ban punishments"
				},
				new Pair<String, String>("Clear Bans", "")
		));
		
		i.setItem(30, this.builder.constructItem(
				Material.PAPER,
				"&c&lClear mute punishments.",
				new String[] {
					"&7Clear all mute punishments"
				},
				new Pair<String, String>("Clear Mutes", "")
		));
		reporter.openInventory(i);
	}
	public void SpawnerCreate(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Spawner Create")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, nothing());
		i.setItem(11, nothing());
		if(player.getInventory().getItem(3) == null) {
			i.setItem(12, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					},
					new Pair<String, String>("SpawnerSlot", "")
			));
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					},
					new Pair<String, String>("SpawnerSlot", "")
			));
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					},
					new Pair<String, String>("SpawnerSlot", "")
			));
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					},
					new Pair<String, String>("SpawnerSlot", "")
			));
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					},
					new Pair<String, String>("SpawnerSlot", "")
			));
		}
		else {
			i.setItem(16, player.getInventory().getItem(7));
		}
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void SpawnerEntity(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Spawner Type")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.builder.constructItem(
				Material.ZOMBIE_SPAWN_EGG, 
				"&fZombie",
				new String[] {
					"&7--------------------",
					"&7Click this to select the mob type",
					"&7to be a zombie!",
					"&7--------------------"
				},
				new Pair<String, EntityType>("EntityType", EntityType.ZOMBIE)
		));
		i.setItem(11, this.builder.constructItem(
				Material.SKELETON_SPAWN_EGG, 
				"&fSkeleton",
				new String[] {
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a zombie!",
						"&7--------------------"
					},
				new Pair<String, EntityType>("EntityType", EntityType.SKELETON)
		));
		i.setItem(12, this.builder.constructItem(
				Material.ZOMBIE_PIGMAN_SPAWN_EGG, 
				"&fZombie Pigmen",
				new String[] {
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a zombie pigmen!",
						"&7--------------------"
					},
				new Pair<String, EntityType>("EntityType", EntityType.PIG_ZOMBIE)
		));
		i.setItem(13, this.builder.constructItem(
				Material.SPIDER_SPAWN_EGG, 
				"&fSpider",
				new String[] {
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a spider!",
						"&7--------------------"
					},
				new Pair<String, EntityType>("EntityType", EntityType.SPIDER)
		));
		i.setItem(14, this.builder.constructItem(
				Material.HUSK_SPAWN_EGG, 
				"&fHusk",
				new String[] {
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a husk!",
						"&7--------------------"
					},
				new Pair<String, EntityType>("EntityType", EntityType.HUSK)
		));
		i.setItem(15, this.builder.constructItem(
				Material.STRAY_SPAWN_EGG, 
				"&fStray",
				new String[] {
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a stray!",
						"&7--------------------"
					},
				new Pair<String, EntityType>("EntityType", EntityType.STRAY)
		));
		i.setItem(16, this.builder.constructItem(
				Material.BLAZE_SPAWN_EGG, 
				"&fBlaze",
				new String[] {
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a blaze!",
						"&7--------------------"
					},
				new Pair<String, EntityType>("EntityType", EntityType.BLAZE)
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void SpawnerTier(Player reporter) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Spawner Tier")));
		i.setItem(0, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 1",
				new String[] {
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 1!",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 1)
		));
		i.setItem(1, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 2",
				new String[] {
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 2!",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 2)
		));
		i.setItem(2, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 3",
				new String[] {
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 3!",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 3)
		));
		i.setItem(3, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 4",
				new String[] {
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 4!",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 4)
		));
		i.setItem(4, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 5",
				new String[] {
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 5!",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 5)
		));
		reporter.openInventory(i);
	}
	public void LootCreate(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Loot Create")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, nothing());
		i.setItem(11, nothing());
		if(player.getInventory().getItem(3) == null) {
			i.setItem(12, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						},
					new Pair<String, String>("LootSlot", "")
			));
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						},
					new Pair<String, String>("LootSlot", "")
			));
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						},
					new Pair<String, String>("LootSlot", "")
			));
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						},
					new Pair<String, String>("LootSlot", "")
			));
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, this.builder.constructItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new String[] {
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						},
					new Pair<String, String>("LootSlot", "")
			));
		}
		else {
			i.setItem(16, player.getInventory().getItem(7));
		}
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void LootRadius(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Loot Radius")));
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(10, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 1",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 1.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 1)
		));
		i.setItem(11, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 2",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 2.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 2)
		));
		i.setItem(12, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 3",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 3.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 3)
		));
		i.setItem(13, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 4",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 4.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 4)
		));
		i.setItem(14, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 5",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 5.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 5)
		));
		i.setItem(15, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 8",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 8.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 8)
		));
		i.setItem(16, this.builder.constructItem(
				Material.PAPER, 
				"&fRadius of spawning: 10",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 10.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Radius", 10)
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		player.openInventory(i);
	}
	public void LootTier(Player reporter) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Loot Tier")));
		i.setItem(0, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 1",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 1.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 1)
		));
		i.setItem(1, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 2",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 2.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 2)
		));
		i.setItem(2, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 3",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 3.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 3)
		));
		i.setItem(3, this.builder.constructItem(
				Material.PAPER, 
				"&fTier 4",
				new String[] {
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 4.",
						"&7--------------------"
					},
				new Pair<String, Integer>("Tier", 4)
		));
		reporter.openInventory(i);
	}
	public void CapturePointMenu(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Capture Point Create")));
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing());
		}
		ItemStack stack = this.builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE, 
				"&fNew Capture Point",
				new String[] {
						"&7--------------------",
						"&7Click this to create a new spawner.",
						"&7--------------------" 
				},
				new Pair<String, Integer>("CapturePointSlot", 1)
		);
		if(player.getInventory().getItem(3) == null) {
			i.setItem(12, stack);
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, stack);
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, stack);
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, stack);
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, stack);
		}
		else {
			i.setItem(16, player.getInventory().getItem(7));
		}
		player.openInventory(i);
	}
	public void CapturePointMultiplier(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Capture Point Multiplier")));
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing());
		}
		i.setItem(10, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 10%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 10%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 10.0)
		));
		i.setItem(11, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 15%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 15%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 15.0)
		));
		i.setItem(12, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 20%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 20%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 20.0)
		));
		i.setItem(13, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 25%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 25%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 25.0)
		));
		i.setItem(14, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 30%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 30%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 30.0)
		));
		i.setItem(15, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 35%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 35%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 35.0)
		));
		i.setItem(16, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 40%",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 40%",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointMultiplier", 40.0)
		));
		player.openInventory(i);
	}
	public void CapturePointRadius(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Capture Point Radius")));
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing());
		}
		i.setItem(10, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 5 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&75 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 5.0)
		));
		i.setItem(11, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 7.5 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&77.5 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 7.5)
		));
		i.setItem(12, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 10 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&710 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 10.0)
		));
		i.setItem(13, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 12.5 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&712.5 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 12.5)
		));
		i.setItem(14, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 15 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&715 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 15.0)
		));
		i.setItem(15, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 17.5 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&717.5 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 17.5)
		));
		i.setItem(16, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 20 Blocks",
				new String[] {
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&720 block radius",
					"&7--------------------"
				},
				new Pair<String, Double>("CapturePointRadius", 20.0)
		));
		player.openInventory(i);
	}
	public void InventoryTeleport(Player player, int currentpage) {
		int size = Bukkit.getOnlinePlayers().size();
		int currentPage = currentpage;
		int maxPage = size / 35;
		if(maxPage == 0) {
			maxPage = 1;
		}
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Teleport: " + currentPage + " / " + maxPage)));
		//-----------------------------------------------------------------------------------------------------------------------------------------
		//GUI
		//-----------------------------------------------------------------------------------------------------------------------------------------
		i.setItem(36, nothing());
		i.setItem(37, nothing());
		i.setItem(38, nothing());
		i.setItem(39, nothing());
		i.setItem(40, nothing());
		i.setItem(41, nothing());
		i.setItem(42, nothing());
		i.setItem(43, nothing());
		i.setItem(44, nothing());
		i.setItem(48, previousPage());
		i.setItem(49, close());
		i.setItem(50, nextPage());
		int count = 0 + 35 * currentPage;
		int invSlot = 0;
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(count <= 35 * currentPage && count >= 35 * (currentPage - 1)) {
				if(p != player) {
					ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
					item.setItemMeta(meta);
					i.setItem(invSlot, item);
					invSlot++;
					count++;
				}
			}
			else {
				invSlot = 0;
				count = 0;
				break;
			}
		}
		player.openInventory(i);
	}
	public void InfoMenu(Player clicker, Player clicked) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Info")));
		//-----------------------------------------------------------------------------------------------------------------------------------------
		//GUI
		//-----------------------------------------------------------------------------------------------------------------------------------------
		i.setItem(0, nothing());
		i.setItem(1, nothing());
		i.setItem(2, nothing());
		i.setItem(3, nothing());
		i.setItem(4, nothing());
		i.setItem(5, nothing());
		i.setItem(6, nothing());
		i.setItem(7, nothing());
		i.setItem(8, nothing());
		i.setItem(9, nothing());
		i.setItem(13, this.dfPlayerItem(clicked));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, nothing());
		i.setItem(20, nothing());
		i.setItem(21, nothing());
		i.setItem(22, nothing());
		i.setItem(23, nothing());
		i.setItem(24, nothing());
		i.setItem(25, nothing());
		i.setItem(26, nothing());
		clicker.openInventory(i);
	}
	public ItemStack dfPlayerItem(Player p) {
		DFPlayer dfPlayer = dfManager.getEntity(p);
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7--------------------"));
		lore.add(new CCT().colorize("&7Class: &6" + dfPlayer.getPlayerClass()));
		lore.add(new CCT().colorize("&7Level: &b" + dfPlayer.getLevel()));
		lore.add(new CCT().colorize("&7XP to levelup: &b" + (dfPlayer.getMaxExperience() - dfPlayer.getExperience())));
		lore.add(new CCT().colorize("&7--------------------"));
		lore.add(new CCT().colorize("&7Attack Damage: &6" + dfPlayer.getAtk()));
		lore.add(new CCT().colorize("&7Attack Speed: &6" + dfPlayer.getSpd()));
		lore.add(new CCT().colorize("&7Critical Chance: &6" + dfPlayer.getCrt()));
		lore.add(new CCT().colorize("&7Ranged Damage: &6" + dfPlayer.getRnd()));
		lore.add(new CCT().colorize("&7Health: &6" + dfPlayer.getHp()));
		lore.add(new CCT().colorize("&7Defense: &6" + dfPlayer.getDf()));
		lore.add(new CCT().colorize("&7--------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public void ExamineMenu(Player clicker, Player clicked) {
//		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 45, (new CCT().colorize("&6Examine")));
//		PlayerInventory inv = clicked.getInventory();
//		ItemStack stack[] = inv.getArmorContents();
//		for(int n = 0; n <= 3; n++) {
//			if(stack[n] != null || stack[n].getType() != Material.AIR) {
//				i.setItem(n, stack[n]);
//			}
//			else {
//				i.setItem(n, nothing());
//			}
//		}
//		if(inv.getItemInOffHand() != null) {
//			i.setItem(4, inv.getItemInOffHand());
//		}
//		else {
//			i.setItem(4, nothing());
//		}
//		i.setItem(5, nothing());
//		i.setItem(6, nothing());
//		i.setItem(7, nothing());
//		i.setItem(8, nothing());
//		ItemStack content[] = inv.getContents();
//		for(int n = 9; n <= 53; n++) {
//			i.setItem(n, content[n - 9]);
//		}
//		clicker.openInventory(i);
	}
	public ItemStack nothing() {
		ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(" ");
		i.setItemMeta(m);
		return i;
	}
	public ItemStack close() {
		ItemStack i = new ItemStack(Material.BARRIER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&4&lClose"));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack nextPage() {
		ItemStack i = new ItemStack(Material.PAPER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&6&lNext Page"));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack previousPage() {
		ItemStack i = new ItemStack(Material.PAPER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&6&lPrevious Page"));
		i.setItemMeta(m);
		return i;
	}
}
