package me.WiebeHero.RankedPlayerPackage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.RankedPlayerPackage.RankEnum.ModRank;
import me.WiebeHero.RankedPlayerPackage.RankEnum.PayRank;
import net.luckperms.api.model.user.User;

public class RankedPlayerListener implements Listener{
	private MethodLuck luck = new MethodLuck();
	private RankedManager rManager = CustomEnchantments.getInstance().rankedManager;
	@EventHandler(priority = EventPriority.LOWEST)
	public void joinRank(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		User user = luck.loadUser(player.getUniqueId());
		boolean mod = false;
		boolean pay = false;
		if(luck.containsParrent(user, "owner")) {
			RankedPlayer r = new RankedPlayer(ModRank.OWNER, "&2Owner", 999, 999);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "manager")) {
			RankedPlayer r = new RankedPlayer(ModRank.MANAGER, "&5Manager", 999, 999);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "headadmin")) {
			RankedPlayer r = new RankedPlayer(ModRank.HEAD_ADMIN, "&4Head Admin", 999, 999);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "admin")) {
			RankedPlayer r = new RankedPlayer(ModRank.ADMIN, "&4Admin", 999, 999);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "headmod")) {
			RankedPlayer r = new RankedPlayer(ModRank.HEAD_MOD, "&1Head Mod", 1, 1);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "moderator")) {
			RankedPlayer r = new RankedPlayer(ModRank.MOD, "&9Mod", 1, 1);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "helper+")) {
			RankedPlayer r = new RankedPlayer(ModRank.HELPER_PLUS, "&aHelper+", 1, 1);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "helper")) {
			RankedPlayer r = new RankedPlayer(ModRank.HELPER, "&aHelper", 1, 1);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "qualityassuranceadmin")) {
			RankedPlayer r = new RankedPlayer(ModRank.QA_ADMIN, "&cQA Admin", 1, 1);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "qualityassurance")) {
			RankedPlayer r = new RankedPlayer(ModRank.QA, "&bQA", 1, 1);
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "youtuber")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.YOUTUBER);
				r.setAHCount(7);
				r.setHomeCount(7);
			}
			else {
				r = new RankedPlayer(PayRank.YOUTUBER, "&cY&fo&cu&ft&cu&fb&ce&fr", 7, 7);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "emerald")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.EMERALD);
				r.setAHCount(7);
				r.setHomeCount(7);
			}
			else {
				r = new RankedPlayer(PayRank.EMERALD, "&aEmerald", 7, 7);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "diamond")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.DIAMOND);
				r.setAHCount(6);
				r.setHomeCount(6);
			}
			else {
				r = new RankedPlayer(PayRank.DIAMOND, "&bDiamond", 6, 6);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "platinum")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.PLATINUM);
				r.setAHCount(5);
				r.setHomeCount(5);
			}
			else {
				r = new RankedPlayer(PayRank.PLATINUM, "&3Platinum", 5, 5);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "gold")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.GOLD);
				r.setAHCount(4);
				r.setHomeCount(4);
			}
			else {
				r = new RankedPlayer(PayRank.GOLD, "&eGold", 4, 4);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "silver")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.SILVER);
			}
			else {
				r = new RankedPlayer(PayRank.SILVER, "&7Silver", 3, 3);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "bronze")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.setPayRank(PayRank.BRONZE);
			}
			else {
				r = new RankedPlayer(PayRank.BRONZE, "&6Bronze", 2, 2);
			}
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(mod == false && pay == false) {
			RankedPlayer r = new RankedPlayer(ModRank.USER, PayRank.USER, "&7User", 1, 1);
			rManager.add(player.getUniqueId(), r);
		}
		else if(mod == false && pay == true) {
			RankedPlayer r = rManager.getRankedPlayer(player.getUniqueId());
			r.setModRank(ModRank.USER);
		}
		else if(mod == true && pay == false) {
			RankedPlayer r = rManager.getRankedPlayer(player.getUniqueId());
			r.setPayRank(PayRank.USER);
		}
	}
}
