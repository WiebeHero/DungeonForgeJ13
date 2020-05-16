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
		i.setItem(1, this.createDisplayItem(
				Material.BOOK, 
				"&cBan",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to go to the ban menu.",
						"&7--------------------" 
				)),
				"Ban Option"
		));
		i.setItem(2, this.createDisplayItem(
				Material.BOOKSHELF, 
				"&bHistory", 
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to go to the history menu.",
						"&7This shows a menu of previous punishments.",
						"&7--------------------" 
				)),
				"History Option"
		));
		i.setItem(3, this.createDisplayItem(
				Material.PAPER, 
				"&bMute", 
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to go to the mute menu.",
						"&7--------------------" 
				)),
				"Mute Option"
		));
		i.setItem(4, nothing());
		reporter.openInventory(i);
	}
	public void BanChoice(Player reporter, String name) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Punish, Ban Choice: &c" + name)));
		i.setItem(0, nothing());
		i.setItem(1, this.createDisplayItem(
				Material.ENCHANTED_BOOK, 
				"&cBan", 
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to ban &6" + name,
						"&7--------------------" 
				)),
				"Ban"
		));
		i.setItem(2, nothing());
		i.setItem(3, this.createDisplayItem(
				Material.BOOK, 
				"&cUnban", 
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to unban &6" + name,
						"&7--------------------" 
				)),
				"Unban"
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
		i.setItem(10, this.createDisplayItem(
				Material.DIAMOND_SWORD, 
				"&cCombat Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cAimbot, Bowaimbot, Forcefield",
					"&cor other combat related modifications."
				)),
				"Combat Modifications"
		));
		i.setItem(11, this.createDisplayItem(
				Material.REDSTONE, 
				"&cAuto Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cAutoarmor, Autosword, Automine, Autoeat, Autofish, Autoswim, Autosoup, Autosteal",
					"&cor other auto related modifications."
				)),
				"Auto Modifications"
		));
		i.setItem(12, this.createDisplayItem(
				Material.FEATHER, 
				"&cMovement Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cTeleport, Fly, Bunny Hop, Blink, Safewalk, Speed, Anit Knockback, Sneak, Freecam",
					"&cor other movement related modifications."
				)),
				"Movement Modifications"
		));
		i.setItem(13, this.createDisplayItem(
				Material.COMPASS, 
				"&cESP Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cPlayer ESP, Player Tracer, Item ESP, Mob ESP, Chest ESP, Block ESP, Minimap",
					"&cor other ESP related modifications."
				)),
				"ESP Modifications"
		));
		i.setItem(14, this.createDisplayItem(
				Material.APPLE, 
				"&cHealth Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cGodmode, Antipotion, Zoot, Health Hacks",
					"&cor other health related modifications."
				)),
				"Health Modifications"
		));
		i.setItem(15, this.createDisplayItem(
				Material.GLASS, 
				"&cBuilding Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cNuker, Fastbuild, Fastbreak, Scaffold",
					"&cor other building related modifications."
				)),
				"Building Modifications"
		));
		i.setItem(16, this.createDisplayItem(
				Material.OBSIDIAN, 
				"&cWallhack Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cXray, Wallhack",
					"&cor other wallhack related modifications."
				)),
				"Wallhack Modifications"
		));
		i.setItem(17, nothing());
		i.setItem(18, nothing());
		i.setItem(19, this.createDisplayItem(
				Material.JUKEBOX, 
				"&cParty Modifications",
				new ArrayList<String>(Arrays.asList(
					"&cFullbright, Twerk, Derp",
					"&cor other party related modifications."
				)),
				"Party Modifications"
		));
		i.setItem(20, this.createDisplayItem(
				Material.TNT, 
				"&cIllegal Raiding",
				new ArrayList<String>(Arrays.asList(
					"&cBugraiding/Trickraiding",
					"&cor other illegal raid options."
				)),
				"Illegal Raiding"
		));
		i.setItem(21, this.createDisplayItem(
				Material.GRASS, 
				"&cMap Glitching",
				new ArrayList<String>(Arrays.asList(
					"&cGlitching out of any world/Abusing Map glitches",
					"&cor other Map glitches."
				)),
				"Map Glitching"
		));	
		i.setItem(22, this.createDisplayItem(
				Material.GOLDEN_PICKAXE, 
				"&cDuplicating Items/Glitching Items",
				new ArrayList<String>(Arrays.asList(
					"&cDuplicating Items/Glitching Items",
					"&cor other Dupe/Glitch bugs."
				)),
				"Item Duplication/Glitching"
		));
		i.setItem(23, this.createDisplayItem(
				Material.BARRIER, 
				"&cAbusing selfmade/other plugins",
				new ArrayList<String>(Arrays.asList(
					"&cAbusing selfmade/other plugins to gain an advantage"
				)),
				"Abusing selfmade/other plugins"
		));
		i.setItem(24, this.createDisplayItem(
				Material.JUKEBOX, 
				"&cKeep/Using forbidden items",
				new ArrayList<String>(Arrays.asList(
					"&cKeep/Using forbidden items"
				)),
				"Forbidden Items"
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
		i.setItem(1, this.createDisplayItem(
				Material.ENCHANTED_BOOK, 
				"&cMute", 
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to mute &6" + name,
						"&7--------------------" 
				)),
				"Mute"
		));
		i.setItem(2, nothing());
		i.setItem(3, this.createDisplayItem(
				Material.BOOK, 
				"&cUnmute", 
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to unmute &6" + name,
						"&7--------------------" 
				)),
				"Unmute"
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
		i.setItem(10, this.createDisplayItem(
				Material.BARRIER, 
				"&cSpamming",
				new ArrayList<String>(Arrays.asList(
						"&cSpamming, Bypassing chat filters/caps typing."
				)),
				"Spamming Related"
		));
		i.setItem(11, this.createDisplayItem(
				Material.BROWN_WOOL, 
				"&cInsulting",
				new ArrayList<String>(Arrays.asList(
						"&cInsulting staff or a member (example; FUCK YOU CALLEDRISINGSUN)"
				)),
				"Insult Related"
		));
		i.setItem(12, this.createDisplayItem(
				Material.PINK_WOOL, 
				"&cSlurs",
				new ArrayList<String>(Arrays.asList(
						"&cSexual Slurs/Racist Slurs/Pedophilic Slurs."
				)),
				"Slur Related"
		));
		i.setItem(13, this.createDisplayItem(
				Material.RED_WOOL, 
				"&cThreats",
				new ArrayList<String>(Arrays.asList(
						"&cDeath Threats/General Threats",
						"&c(for example; I'M GOING TO FIND YOUR ADRESS AND I'LL KILL YOUR FAMILY)"
				)),
				"Threat Related"
		));
		i.setItem(14, this.createDisplayItem(
				Material.REDSTONE, 
				"&cChat Modifications",
				new ArrayList<String>(Arrays.asList(
						"&cFancy Chat/Chat Modifications/Autotext Spammer"
				)),
				"Chat Related"
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
				new ArrayList<String>(Arrays.asList(
					"&7Remove the first ban punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				)),
				"Remove First Ban"
		));
		
		i.setItem(12, this.builder.constructItem(
				Material.PAPER,
				"&cRemove first mute punishment.",
				new ArrayList<String>(Arrays.asList(
					"&7Remove the first mute punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				)),
				"Remove First Mute"
		));
		
		i.setItem(20, this.builder.constructItem(
				Material.PAPER,
				"&cRemove last ban punishment.",
				new ArrayList<String>(Arrays.asList(
					"&7Remove the last ban punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				)),
				"Remove Last Ban"
		));
		
		i.setItem(21, this.builder.constructItem(
				Material.PAPER,
				"&cRemove last mute punishment.",
				new ArrayList<String>(Arrays.asList(
					"&7Remove the last mute punishment",
					"&7that is currently present in the",
					"&7list. Other punishments will move",
					"&7appriopiatly."
				)),
				"Remove Last Mute"
		));
		
		i.setItem(29, this.builder.constructItem(
				Material.PAPER,
				"&c&lClear ban punishments.",
				new ArrayList<String>(Arrays.asList(
					"&7Clear all ban punishments"
				)),
				"Clear Bans"
		));
		
		i.setItem(30, this.builder.constructItem(
				Material.PAPER,
				"&c&lClear mute punishments.",
				new ArrayList<String>(Arrays.asList(
					"&7Clear all mute punishments"
				)),
				"Clear Mutes"
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
			i.setItem(12, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					)),
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					)),
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					)),
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					)),
					"SpawnerSlot"
			));
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Spawner",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new spawner.",
							"&7--------------------" 
					)),
					"SpawnerSlot"
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
		i.setItem(10, this.createDisplayItem(
				Material.ZOMBIE_SPAWN_EGG, 
				"&fZombie",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to select the mob type",
					"&7to be a zombie!",
					"&7--------------------"
				)),
				"EntityType",
				EntityType.ZOMBIE
		));
		i.setItem(11, this.createDisplayItem(
				Material.SKELETON_SPAWN_EGG, 
				"&fSkeleton",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a zombie!",
						"&7--------------------"
					)),
				"EntityType",
				EntityType.SKELETON
		));
		i.setItem(12, this.createDisplayItem(
				Material.ZOMBIE_PIGMAN_SPAWN_EGG, 
				"&fZombie Pigmen",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a zombie pigmen!",
						"&7--------------------"
					)),
				"EntityType",
				EntityType.PIG_ZOMBIE
		));
		i.setItem(13, this.createDisplayItem(
				Material.SPIDER_SPAWN_EGG, 
				"&fSpider",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a spider!",
						"&7--------------------"
					)),
				"EntityType",
				EntityType.SPIDER
		));
		i.setItem(14, this.createDisplayItem(
				Material.HUSK_SPAWN_EGG, 
				"&fHusk",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a husk!",
						"&7--------------------"
					)),
				"EntityType",
				EntityType.HUSK
		));
		i.setItem(15, this.createDisplayItem(
				Material.STRAY_SPAWN_EGG, 
				"&fStray",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a stray!",
						"&7--------------------"
					)),
				"EntityType",
				EntityType.STRAY
		));
		i.setItem(16, this.createDisplayItem(
				Material.BLAZE_SPAWN_EGG, 
				"&fBlaze",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the mob type",
						"&7to be a blaze!",
						"&7--------------------"
					)),
				"EntityType",
				EntityType.BLAZE
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
		i.setItem(0, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 1",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 1!",
						"&7--------------------"
					)),
				"Tier",
				1
		));
		i.setItem(1, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 2",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 2!",
						"&7--------------------"
					)),
				"Tier",
				2
		));
		i.setItem(2, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 3",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 3!",
						"&7--------------------"
					)),
				"Tier",
				3
		));
		i.setItem(3, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 4",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 4!",
						"&7--------------------"
					)),
				"Tier",
				4
		));
		i.setItem(4, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 5",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to select the tier of the",
						"&7spawner to be 5!",
						"&7--------------------"
					)),
				"Tier",
				5
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
			i.setItem(12, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						)),
					"LootSlot"
			));
		}
		else {
			i.setItem(12, player.getInventory().getItem(3));
		}
		if(player.getInventory().getItem(4) == null) {
			i.setItem(13, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						)),
					"LootSlot"
			));
		}
		else {
			i.setItem(13, player.getInventory().getItem(4));
		}
		if(player.getInventory().getItem(5) == null) {
			i.setItem(14, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						)),
					"LootSlot"
			));
		}
		else {
			i.setItem(14, player.getInventory().getItem(5));
		}
		if(player.getInventory().getItem(6) == null) {
			i.setItem(15, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						)),
					"LootSlot"
			));
		}
		else {
			i.setItem(15, player.getInventory().getItem(6));
		}
		if(player.getInventory().getItem(7) == null) {
			i.setItem(16, this.createDisplayItem(
					Material.WHITE_STAINED_GLASS_PANE, 
					"&fNew Loot Chest",
					new ArrayList<String>(Arrays.asList(
							"&7--------------------",
							"&7Click this to create a new loot chest.",
							"&7--------------------"
						)),
					"LootSlot"
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
		i.setItem(10, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 1",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 1.",
						"&7--------------------"
					)),
				"Radius",
				1
		));
		i.setItem(11, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 2",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 2.",
						"&7--------------------"
					)),
				"Radius",
				2
		));
		i.setItem(12, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 3",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 3.",
						"&7--------------------"
					)),
				"Radius",
				3
		));
		i.setItem(13, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 4",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 4.",
						"&7--------------------"
					)),
				"Radius",
				4
		));
		i.setItem(14, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 5",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 5.",
						"&7--------------------"
					)),
				"Radius",
				5
		));
		i.setItem(15, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 8",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 8.",
						"&7--------------------"
					)),
				"Radius",
				8
		));
		i.setItem(16, this.createDisplayItem(
				Material.PAPER, 
				"&fRadius of spawning: 10",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7spawning radius of 10.",
						"&7--------------------"
					)),
				"Radius",
				10
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
		i.setItem(0, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 1",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 1.",
						"&7--------------------"
					)),
				"Tier",
				1
		));
		i.setItem(1, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 2",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 2.",
						"&7--------------------"
					)),
				"Tier",
				2
		));
		i.setItem(2, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 3",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 3.",
						"&7--------------------"
					)),
				"Tier",
				3
		));
		i.setItem(3, this.createDisplayItem(
				Material.PAPER, 
				"&fTier 4",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this for the loot chest to have a",
						"&7tier radius of 4.",
						"&7--------------------"
					)),
				"Tier",
				4
		));
		reporter.openInventory(i);
	}
	public void CapturePointMenu(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Capture Point Create")));
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing());
		}
		ItemStack stack = this.createDisplayItem(
				Material.WHITE_STAINED_GLASS_PANE, 
				"&fNew Capture Point",
				new ArrayList<String>(Arrays.asList(
						"&7--------------------",
						"&7Click this to create a new spawner.",
						"&7--------------------" 
				)),
				"CapturePointSlot"
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
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 10%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				10
		));
		i.setItem(11, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 15%",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 15%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				15
		));
		i.setItem(12, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 20%",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 20%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				20
		));
		i.setItem(13, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 25%",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 25%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				25
		));
		i.setItem(14, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 30%",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 30%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				30
		));
		i.setItem(15, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 35%",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 35%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				35
		));
		i.setItem(16, this.builder.constructItem(
				Material.PAPER,
				"&aXP Increase: 40%",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will increase XP gain by 40%",
					"&7--------------------"
				)),
				"CapturePointMultiplier",
				40
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
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&75 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				5
		));
		i.setItem(11, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 7.5 Blocks",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&77.5 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				7.5
		));
		i.setItem(12, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 10 Blocks",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&710 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				10
		));
		i.setItem(13, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 12.5 Blocks",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&712.5 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				12.5
		));
		i.setItem(14, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 15 Blocks",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&715 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				15
		));
		i.setItem(15, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 17.5 Blocks",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&717.5 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				17.5
		));
		i.setItem(16, this.builder.constructItem(
				Material.PAPER,
				"&aCapture Point Radius: 20 Blocks",
				new ArrayList<String>(Arrays.asList(
					"&7--------------------",
					"&7Click this to make it so that this",
					"&7capture point will activate in a",
					"&720 block radius",
					"&7--------------------"
				)),
				"CapturePointRadius",
				20
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
	public ItemStack createDisplayItem(Material mat, String name, String tag) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString(tag, "");
		return i.getItem();
	}
	public ItemStack createDisplayItem(Material mat, String name, String tag, String value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setString(tag, value);
		return i.getItem();
	}
	public ItemStack createDisplayItem(Material mat, String name, String tag, int value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setInteger(tag, value);
		return i.getItem();
	}
	public ItemStack createDisplayItem(Material mat, String name, String tag, Object value) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject(tag, value);
		return i.getItem();
	}
	public ItemStack createDisplayItem(Material mat, String name, ArrayList<String> lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name, ArrayList<String> lore, String tag) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString(tag, "");
		item = tempItem.getItem();
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name, ArrayList<String> lore, String tag, String tagValue) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString(tag, tagValue);
		item = tempItem.getItem();
		return item;
	}

	public ItemStack createDisplayItem(Material mat, String name, ArrayList<String> lore, String tag, int tagValue) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setInteger(tag, tagValue);
		item = tempItem.getItem();
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name, ArrayList<String> lore, String tag, Object tagValue) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		for(int i = 0; i < lore.size(); i++) {
			lore.set(i, new CCT().colorize(lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setObject(tag, tagValue);
		item = tempItem.getItem();
		return item;
	}
	public ItemStack createDisplayItem(Material mat, String name) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		item.setItemMeta(meta);
		return item;
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
