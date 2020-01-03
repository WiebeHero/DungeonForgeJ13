package me.WiebeHero.Scoreboard;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFShops.MoneyCreate;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Skills.DFPlayer;

public class DFScoreboard implements Listener{
	MoneyCreate money = new MoneyCreate();
	DFFaction method = new DFFaction();
	DFPlayer def = new DFPlayer();
	Methods m = new Methods();
	public HashMap<UUID, Chunk> delay = new HashMap<UUID, Chunk>();
	public HashMap<UUID, Scoreboard> scoreboards = new HashMap<UUID, Scoreboard>();
	public HashMap<UUID, Team> teams = new HashMap<UUID, Team>();
	public HashMap<UUID, String> ranks = new HashMap<UUID, String>();
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		this.registerRank(player);
		this.generateScoreboard(player);
	}
	@EventHandler
	public void moveIt(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(delay.containsKey(player.getUniqueId())) {
			if(!delay.get(player.getUniqueId()).equals(player.getChunk())) {
				delay.put(player.getUniqueId(), player.getChunk());
				this.generateScoreboard(player);
			}
		}
		else {
			delay.put(player.getUniqueId(), player.getChunk());
		}
	}
	public void generateScoreboard(Player player) {
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
		DFFaction faction = method.getFaction(player.getUniqueId());
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
		String territory = "";
		if(faction != null) {
			facN = "&6" + faction.getName();
			if(faction.isInChunk(player)) {
				territory = "&a&l" + faction.getName();
			}
		}
		else if(method.isInAChunk(player)) {
			for(DFFaction fac : CustomEnchantments.getInstance().factionList) {
				if(fac.getChunkList().contains(player.getLocation().getChunk())) {
					territory = "&c&l" + fac.getName();
				}
			}
		}
		else {
			territory = "&7Wilderness";
		}
		Score facLine = score.getScore(new CCT().colorize("&7Faction: " + facN));
		Score facTeritory = score.getScore(new CCT().colorize("&7Territory: " + territory));
		Score rank = score.getScore(new CCT().colorize("&7Rank: " + ranks.get(player.getUniqueId())));
		Score level = score.getScore(new CCT().colorize("&7Level: &b&l" + def.getPlayer(player).getLevel()));
		Score adress = score.getScore(new CCT().colorize("    &2&lplay.dungeonforge.net"));
		Score cash = score.getScore(new CCT().colorize("&7Money: &a$" + String.format("%.2f", money.getMoneyList().get(player.getUniqueId()))));
		//--------------------------------------------------------------------------------------
		//Prefix/Suffix setting
		//--------------------------------------------------------------------------------------
		DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
		Team t = scoreboard.registerNewTeam(player.getName());
		teams.put(player.getUniqueId(), t);
		t.setPrefix(new CCT().colorize("&6[&b" + dfPlayer.getLevel() + "&6]&7 "));
		String stringClass = dfPlayer.getPlayerClass().toString().toLowerCase();
		String now = stringClass.substring(0, 1).toUpperCase() + stringClass.substring(1);
		t.setSuffix(new CCT().colorize(" &6" + now));
		player.setPlayerListName(new CCT().colorize(t.getPrefix() + player.getName() + " " + ranks.get(player.getUniqueId())));
		t.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
		t.addEntry(player.getName());
		//--------------------------------------------------------------------------------------
		//Adding players to my scoreboard
		//--------------------------------------------------------------------------------------
		for(UUID uuid : scoreboards.keySet()) {
			Player p = Bukkit.getPlayer(uuid);
			if(p != null) {
				if(!scoreboard.getTeam(player.getName()).hasEntry(p.getName())) {
					t.addEntry(p.getName());
				}
			}
		}
		//--------------------------------------------------------------------------------------
		//Adding me to others scoreboard
		//--------------------------------------------------------------------------------------
		for(UUID uuid : scoreboards.keySet()) {
			Player p = Bukkit.getPlayer(uuid);
			if(p != null) {
				Scoreboard other = p.getScoreboard();
				if(other != null) {
					if(other.getTeam(p.getName()) != null) {
						if(!other.getTeam(p.getName()).hasEntry(player.getName())) {
							other.getTeam(p.getName()).addEntry(player.getName());
						}
					}
				}
			}
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
		if(player.hasPermission("owner")) {
			ranks.put(player.getUniqueId(), "&2Owner");
		}
		else if(player.hasPermission("manager")) {
			ranks.put(player.getUniqueId(), "&5Manager");
		}
		else if(player.hasPermission("headadmin")) {
			ranks.put(player.getUniqueId(), "&4Head Admin");
		}
		else if(player.hasPermission("admin")) {
			ranks.put(player.getUniqueId(), "&cAdmin");
		}
		else if(player.hasPermission("headmod")) {
			ranks.put(player.getUniqueId(), "&1Head Mod");
		}
		else if(player.hasPermission("moderator")) {
			ranks.put(player.getUniqueId(), "&9Mod");
		}
		else if(player.hasPermission("helper+")) {
			ranks.put(player.getUniqueId(), "&aHelper+");
		}
		else if(player.hasPermission("helper")) {
			ranks.put(player.getUniqueId(), "&aHelper");
		}
		else if(player.hasPermission("qaadmin")) {
			ranks.put(player.getUniqueId(), "&cQA Admin");
		}
		else if(player.hasPermission("qa")) {
			ranks.put(player.getUniqueId(), "&bQA");
		}
		else if(player.hasPermission("youtuber")) {
			ranks.put(player.getUniqueId(), "&dYoutuber");
		}
		else if(player.hasPermission("bronze")) {
			ranks.put(player.getUniqueId(), "&6Bronze");
		}
		else if(player.hasPermission("silver")) {
			ranks.put(player.getUniqueId(), "&7Silver");
		}
		else if(player.hasPermission("gold")) {
			ranks.put(player.getUniqueId(), "&eGold");
		}
		else if(player.hasPermission("platinum")) {
			ranks.put(player.getUniqueId(), "&3Platinum");
		}
		else if(player.hasPermission("diamond")) {
			ranks.put(player.getUniqueId(), "&bDiamond");
		}
		else if(player.hasPermission("emerald")) {
			ranks.put(player.getUniqueId(), "&aEmerald");
		}
		else {
			ranks.put(player.getUniqueId(), "&7User");
		}
	}
}
