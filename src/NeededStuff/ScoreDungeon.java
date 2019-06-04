package NeededStuff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFactions;

public class ScoreDungeon implements Listener{
	public ArrayList<String> list = new ArrayList<String>();
	DFFactions fac = new DFFactions();
	@EventHandler
    public void playerJoin(PlayerMoveEvent event) { 
		Player player = event.getPlayer();
		if(!list.contains(player.getName())) {
			list.add(player.getName());
			File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			int level = yml.getInt("Skills.Players." + player.getUniqueId() + ".Level");
			Scoreboard board = CustomEnchantments.scores.get(player.getUniqueId());
			File f1 =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
			YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f1);
			try{
				yml1.load(f1);
	        }
	        catch(IOException e){
	            e.printStackTrace();
	        } 
			catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			File f3 =  new File("plugins/CustomEnchantments/moneyConfig.yml");
			YamlConfiguration yml3 = YamlConfiguration.loadConfiguration(f3);
			try{
				yml3.load(f3);
	        }
	        catch(IOException e){
	            e.printStackTrace();
	        } 
			catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			org.bukkit.scoreboard.Scoreboard b = board;
			Objective o = player.getScoreboard().getObjective(player.getName());
			if(o == null) {
				o = b.registerNewObjective(player.getName(), "Scoreboard", "myScoreboard");
			}
			else {
				o = b.getObjective(player.getName());
			}
			o.setDisplayName(new ColorCodeTranslator().colorize("&2&lDungeonForge"));
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			Team t = b.getTeam(player.getName() + "1");
			if(player.hasPermission("owner")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &2Owner"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("manager")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &5Manager"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("headadmin")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &4Head Admin"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("admin")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &cAdmin"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("headmod")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &1Head Mod"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("mod")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &bMod"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("helper+")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &aHelper+"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("helper")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &aHelper"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("bronze")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &6Bronze"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("silver")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &7Silver"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("gold")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &eGold"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("platinum")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &1Platinum"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("diamond")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &bDiamond"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName() + "1").getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else if(player.hasPermission("emerald")) {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				t.setSuffix(new ColorCodeTranslator().colorize(" &aEmerald"));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + board.getTeam(player.getName()).getSuffix()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			else {
				t.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 "));
				player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName()));
				t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
			//Money
			int moneyMe = yml3.getInt("List." + player.getUniqueId().toString() + ".Money");
			//Level
			int levelMe = yml1.getInt("Skills.Players." + player.getUniqueId().toString() + ".Level");
			//Faction Info
			Score blank1 = o.getScore("");
			Score blank2 = o.getScore(" ");
			Score blank3 = o.getScore("  ");
			Score facName = null;
			Score facTeritory = null;
			String facN = "";
			for(Entry<String, ArrayList<UUID>> entry : fac.getFactionMemberList().entrySet()) {
				if(entry.getValue().contains(player.getUniqueId())) {
					facN = entry.getKey();
				}
			}
			if(!facN.equals("")) {
				facName = o.getScore(new ColorCodeTranslator().colorize("&7Faction: &6" + facN));
			}
			else {
				facName = o.getScore(new ColorCodeTranslator().colorize("&7Faction: &6None"));
			}
			if(player.getWorld().getName() == Bukkit.getWorld("DFWarzone-1").getName()) {
				facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&lWarzone"));
			}
			else if(!facN.equals("")) {
				boolean check = false;
				for(Entry<String, ArrayList<Chunk>> entry : fac.getChunkList().entrySet()) {
					if(!entry.getKey().equals(facN)) {
						if(entry.getValue().contains(player.getLocation().getChunk())) {
							check = true;
						}
					}
				}
				if(fac.getChunkList().get(facN).contains(player.getLocation().getChunk())) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &a&lFriendly"));
				}
				else if(check == true) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6&cEnemy"));
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
				}
			}
			else {
				facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: Unknown"));
			}
			Score money = o.getScore(new ColorCodeTranslator().colorize("&7Money: &a" + moneyMe));
			Score level1 = o.getScore(new ColorCodeTranslator().colorize("&7Level: &b&l" + levelMe));
			int xp = yml1.getInt("Skills.Players." + player.getUniqueId() + ".XP");
			int maxxp = yml1.getInt("Skills.Players." + player.getUniqueId() + ".MAXXP");
			int needed = maxxp - xp;
			Score xpToLevelUp = o.getScore(new ColorCodeTranslator().colorize("&7XP to level up: &b&l" + needed));
			Score adress = o.getScore(new ColorCodeTranslator().colorize("    &2&lplay.dungeonforge.net"));
			Set<String> entries;
	        entries = b.getEntries();
	        for(String entry : entries){
	            b.resetScores(entry);
	        }
	        blank1.setScore(9);
			facName.setScore(8);
			facTeritory.setScore(7);
			money.setScore(6);
			blank2.setScore(5);
			level1.setScore(4);
			xpToLevelUp.setScore(3);
			blank3.setScore(2);
			adress.setScore(1);
			player.setScoreboard(b);
			player.setScoreboard(board);
			new BukkitRunnable() {
				@Override
				public void run() {
					list.remove(player.getName());
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 30L);
		}
	}
}
