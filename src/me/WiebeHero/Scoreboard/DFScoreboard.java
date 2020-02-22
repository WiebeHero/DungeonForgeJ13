package me.WiebeHero.Scoreboard;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import net.luckperms.api.model.user.User;

public class DFScoreboard implements Listener{
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	private DFPlayerManager dfManager;
	private MethodLuck luck;
	private WGMethods wg;
	public DFScoreboard(DFPlayerManager dfManager, DFFactionManager facManager, DFFactionPlayerManager facPlayerManager, MethodLuck luck, WGMethods wg) {
		this.dfManager = dfManager;
		this.facManager = facManager;
		this.luck = luck;
		this.wg = wg;
		this.facPlayerManager = facPlayerManager;
	}
	public HashMap<UUID, Chunk> delay = new HashMap<UUID, Chunk>();
	public HashMap<UUID, Scoreboard> scoreboards = new HashMap<UUID, Scoreboard>();
	public HashMap<UUID, Team> teams = new HashMap<UUID, Team>();
	public HashMap<UUID, String> ranks = new HashMap<UUID, String>();
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		this.registerRank(player);
		for(Player p : Bukkit.getOnlinePlayers()) {
			this.generateScoreboard(p);
		}
	}
	@EventHandler
	public void moveIt(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(delay.containsKey(player.getUniqueId())) {
			if(!delay.get(player.getUniqueId()).equals(player.getChunk())) {
				delay.put(player.getUniqueId(), player.getChunk());
				this.updateScoreboard(player);
			}
		}
		else {
			delay.put(player.getUniqueId(), player.getChunk());
		}
	}
	public void updateScoreboard(Player player) {
		if(player.getScoreboard() != null) {
			DFPlayer df = dfManager.getEntity(player);
			Scoreboard scoreboard = player.getScoreboard();
			Objective score = scoreboard.getObjective("score");
			//--------------------------------------------------------------------------------------
			//Set normal scoreboard
			//--------------------------------------------------------------------------------------
			score.setDisplayName(new CCT().colorize("&2&lDungeonForge"));
			score.setDisplaySlot(DisplaySlot.SIDEBAR);
			DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
			DFFaction faction = facPlayer.getFaction();
			//--------------------------------------------------------------------------------------
			//Blank Scores
			//--------------------------------------------------------------------------------------
			Score blank1 = score.getScore("");
			Score blank2 = score.getScore(" ");
			Score blank3 = score.getScore("  ");
			//--------------------------------------------------------------------------------------
			//Faction setting
			//--------------------------------------------------------------------------------------
			String facN = "&7None";
			String territory = "&7Wilderness";
			if(faction != null) {
				facN = "&6" + faction.getName();
				if(faction.isInChunk(player)) {
					territory = "&a&l" + faction.getName();
				}
			}
			else if(facManager.isInAChunk(player)) {
				for(DFFaction fac : facManager.getFactionMap().values()) {
					if(fac.getChunkList().contains(player.getLocation().getChunk())) {
						territory = "&c&l" + fac.getName();
					}
				}
			}
			if(wg.isInZone(player, "warzone")) {
				territory = "&c&lWarzone";
			}
			else if(wg.isInZone(player, "spawn")) {
				territory = "&a&lSpawn";
			}
			Score facLine = score.getScore(new CCT().colorize("&7Faction: " + facN));
			Score facTeritory = score.getScore(new CCT().colorize("&7Territory: " + territory));
			Score rank = score.getScore(new CCT().colorize("&7Rank: " + ranks.get(player.getUniqueId())));
			Score level = score.getScore(new CCT().colorize("&7Level: &b&l" + dfManager.getEntity(player).getLevel()));
			Score adress = score.getScore(new CCT().colorize("    &2&lplay.dungeonforge.eu"));
			Score cash = score.getScore(new CCT().colorize("&7Money: &a$" + String.format("%.2f", df.getMoney())));
			for(Player p : Bukkit.getOnlinePlayers()) {
				DFPlayer dfPlayer = dfManager.getEntity(player);
				Team t = null;
				if(scoreboard.getTeam(p.getName()) == null) {
					t = scoreboard.registerNewTeam(p.getName());
				}
				else {
					t = scoreboard.getTeam(p.getName());
				}
				t.setPrefix(new CCT().colorize("&6[&b" + dfPlayer.getLevel() + "&6]&7 "));
				String stringClass = dfPlayer.getPlayerClass().toString().toLowerCase();
				String now = stringClass.substring(0, 1).toUpperCase() + stringClass.substring(1);
				t.setSuffix(new CCT().colorize(" &6" + now));
				p.setPlayerListName(new CCT().colorize(t.getPrefix() + p.getName() + " " + ranks.get(p.getUniqueId())));
				t.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
				t.addEntry(p.getName());
			}
			Set<String> entries;
	        entries = scoreboard.getEntries();
	        for(String entry : entries){
	        	scoreboard.resetScores(entry);
	        }
			blank1.setScore(9);
			facLine.setScore(8);
			facTeritory.setScore(7);
			cash.setScore(6);
			blank2.setScore(5);
			rank.setScore(4);
			level.setScore(3);
			blank3.setScore(2);
			adress.setScore(1);
			scoreboards.put(player.getUniqueId(), scoreboard);
			player.setScoreboard(scoreboard);
		}
		else {
			this.generateScoreboard(player);
		}
	}
	public void generateScoreboard(Player player) {
		DFPlayer df = dfManager.getEntity(player);
		scoreboards.put(player.getUniqueId(), null);
		Scoreboard scoreboard = CustomEnchantments.getInstance().getServer().getScoreboardManager().getNewScoreboard();
		//--------------------------------------------------------------------------------------
		//Set health scoreboard
		//--------------------------------------------------------------------------------------
		Objective o = scoreboard.registerNewObjective("health", "health", "display");
		o.setDisplayName(new CCT().colorize("&c❤"));
		o.setDisplaySlot(DisplaySlot.BELOW_NAME);
		//--------------------------------------------------------------------------------------
		//Set normal scoreboard
		//--------------------------------------------------------------------------------------
		Objective score = scoreboard.registerNewObjective("score", "", "");
		score.setDisplayName(new CCT().colorize("&2&lDungeonForge"));
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
		DFFaction faction = facPlayer.getFaction();
		//--------------------------------------------------------------------------------------
		//Blank Scores
		//--------------------------------------------------------------------------------------
		Score blank1 = score.getScore("");
		Score blank2 = score.getScore(" ");
		Score blank3 = score.getScore("  ");
		//--------------------------------------------------------------------------------------
		//Faction setting
		//--------------------------------------------------------------------------------------
		String facN = "&7None";
		String territory = "&7Wilderness";
		if(faction != null) {
			facN = "&6" + faction.getName();
			Bukkit.broadcastMessage(faction.isInChunk(player.getLocation()) + "");
			Bukkit.broadcastMessage(faction.isInChunk(player) + "");
			if(faction.isInChunk(player)) {
				territory = "&a&l" + faction.getName();
			}
		}
		else if(facManager.isInAChunk(player)) {
			for(DFFaction fac : facManager.getFactionMap().values()) {
				if(fac.getChunkList().contains(player.getLocation().getChunk())) {
					territory = "&c&l" + fac.getName();
				}
			}
		}
		if(wg.isInZone(player, "warzone")) {
			territory = "&c&lWarzone";
		}
		else if(wg.isInZone(player, "spawn")) {
			territory = "&a&lSpawn";
		}
		Score facLine = score.getScore(new CCT().colorize("&7Faction: " + facN));
		Score facTeritory = score.getScore(new CCT().colorize("&7Territory: " + territory));
		Score rank = score.getScore(new CCT().colorize("&7Rank: " + ranks.get(player.getUniqueId())));
		Score level = score.getScore(new CCT().colorize("&7Level: &b&l" + dfManager.getEntity(player).getLevel()));
		Score adress = score.getScore(new CCT().colorize("    &2&lplay.dungeonforge.eu"));
		Score cash = score.getScore(new CCT().colorize("&7Money: &a$" + String.format("%.2f", df.getMoney())));
		for(Player p : Bukkit.getOnlinePlayers()) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			Team t = null;
			if(scoreboard.getTeam(p.getName()) == null) {
				t = scoreboard.registerNewTeam(p.getName());
			}
			else {
				t = scoreboard.getTeam(p.getName());
			}
			t.setPrefix(new CCT().colorize("&6[&b" + dfPlayer.getLevel() + "&6]&7 "));
			String stringClass = dfPlayer.getPlayerClass().toString().toLowerCase();
			String now = stringClass.substring(0, 1).toUpperCase() + stringClass.substring(1);
			t.setSuffix(new CCT().colorize(" &6" + now));
			p.setPlayerListName(new CCT().colorize(t.getPrefix() + p.getName() + " " + ranks.get(p.getUniqueId())));
			t.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
			t.addEntry(p.getName());
		}
		if(scoreboard.getTeam("GRAY") == null) {
			scoreboard.registerNewTeam("GRAY");
			scoreboard.getTeam("GRAY").setPrefix(ChatColor.GRAY + "");
			scoreboard.getTeam("GRAY").setColor(ChatColor.GRAY);
			scoreboard.registerNewTeam("GREEN");
			scoreboard.getTeam("GREEN").setPrefix(ChatColor.GREEN + "");
			scoreboard.getTeam("GREEN").setColor(ChatColor.GREEN);
			scoreboard.registerNewTeam("AQUA");
			scoreboard.getTeam("AQUA").setPrefix(ChatColor.AQUA + "");
			scoreboard.getTeam("AQUA").setColor(ChatColor.AQUA);
			scoreboard.registerNewTeam("RED");
			scoreboard.getTeam("RED").setPrefix(ChatColor.RED + "");
			scoreboard.getTeam("RED").setColor(ChatColor.RED);
			scoreboard.registerNewTeam("PURPLE");
			scoreboard.getTeam("PURPLE").setPrefix(ChatColor.DARK_PURPLE + "");
			scoreboard.getTeam("PURPLE").setColor(ChatColor.DARK_PURPLE);
			scoreboard.registerNewTeam("YELLOW");
			scoreboard.getTeam("YELLOW").setPrefix(ChatColor.YELLOW + "");
			scoreboard.getTeam("YELLOW").setColor(ChatColor.YELLOW);
		}
		Set<String> entries;
        entries = scoreboard.getEntries();
        for(String entry : entries){
        	scoreboard.resetScores(entry);
        }
        blank1.setScore(9);
		facLine.setScore(8);
		facTeritory.setScore(7);
		cash.setScore(6);
		blank2.setScore(5);
		rank.setScore(4);
		level.setScore(3);
		blank3.setScore(2);
		adress.setScore(1);
		scoreboards.put(player.getUniqueId(), scoreboard);
		player.setScoreboard(scoreboard);
	}
	public void registerRank(Player player) {
		User user = luck.loadUser(player.getUniqueId());
		if(luck.containsParrent(user, "owner")) {
			ranks.put(player.getUniqueId(), "&2Owner");
		}
		else if(luck.containsParrent(user, "manager")) {
			ranks.put(player.getUniqueId(), "&5Manager");
		}
		else if(luck.containsParrent(user, "headadmin")) {
			ranks.put(player.getUniqueId(), "&4Head Admin");
		}
		else if(luck.containsParrent(user, "admin")) {
			ranks.put(player.getUniqueId(), "&cAdmin");
		}
		else if(luck.containsParrent(user, "headmod")) {
			ranks.put(player.getUniqueId(), "&1Head Mod");
		}
		else if(luck.containsParrent(user, "moderator")) {
			ranks.put(player.getUniqueId(), "&9Mod");
		}
		else if(luck.containsParrent(user, "helper+")) {
			ranks.put(player.getUniqueId(), "&aHelper+");
		}
		else if(luck.containsParrent(user, "helper")) {
			ranks.put(player.getUniqueId(), "&aHelper");
		}
		else if(luck.containsParrent(user, "qualityassuranceadmin")) {
			ranks.put(player.getUniqueId(), "&cQA Admin");
		}
		else if(luck.containsParrent(user, "qualityassurance")) {
			ranks.put(player.getUniqueId(), "&bQA");
		}
		else if(luck.containsParrent(user, "youtuber")) {
			ranks.put(player.getUniqueId(), "&dYoutuber");
		}
		else if(luck.containsParrent(user, "bronze")) {
			ranks.put(player.getUniqueId(), "&6Bronze");
		}
		else if(luck.containsParrent(user, "silver")) {
			ranks.put(player.getUniqueId(), "&7Silver");
		}
		else if(luck.containsParrent(user, "gold")) {
			ranks.put(player.getUniqueId(), "&eGold");
		}
		else if(luck.containsParrent(user, "platinum")) {
			ranks.put(player.getUniqueId(), "&3Platinum");
		}
		else if(luck.containsParrent(user, "diamond")) {
			ranks.put(player.getUniqueId(), "&bDiamond");
		}
		else if(luck.containsParrent(user, "emerald")) {
			ranks.put(player.getUniqueId(), "&aEmerald");
		}
		else {
			ranks.put(player.getUniqueId(), "&7User");
		}
	}
}
