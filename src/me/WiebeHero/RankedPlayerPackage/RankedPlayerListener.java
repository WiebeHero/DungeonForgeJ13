package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.Scoreboard.WGMethods;
import net.luckperms.api.model.user.User;

public class RankedPlayerListener implements Listener{
	private MethodLuck luck;
	private RankedManager rManager;
	private WGMethods wg;
	private ArrayList<UUID> cooldown;
	public RankedPlayerListener(RankedManager rManager, MethodLuck luck, WGMethods wg) {
		this.luck = luck;
		this.rManager = rManager;
		this.wg = wg;
		this.cooldown = new ArrayList<UUID>();
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void joinRank(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		User user = luck.loadUser(player.getUniqueId());
		boolean mod = false;
		boolean pay = false;
		if(luck.containsParrent(user, "owner")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.OWNER);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.OWNER, "&2Owner", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "manager")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.MANAGER);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.MANAGER, "&5Manager", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "headadmin")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HEAD_ADMIN);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.HEAD_ADMIN, "&4Head Admin", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "admin")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.ADMIN);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.ADMIN, "&4Admin", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "headmod")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HEAD_MOD);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.HEAD_MOD, "&1Head Mod", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "moderator")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.MOD);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.MOD, "&9Mod", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "helper_plus")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HELPER_PLUS);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.HELPER_PLUS, "&aHelper+", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "helper")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HELPER);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.HELPER, "&aHelper", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "qualityassuranceadmin")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.QA_ADMIN);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.QA_ADMIN, "&cQA Admin", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "qualityassurance")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.QA);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.QA, "&bQA", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		if(luck.containsParrent(user, "youtuber")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.YOUTUBER);
				r.setAHCount(7);
				r.setHomeCount(7);
			}
			else {
				r = new RankedPlayer(Rank.YOUTUBER, "&cY&fo&cu&ft&cu&fb&ce&fr", 7, 7);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "emerald")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.EMERALD);
				r.setAHCount(7);
				r.setHomeCount(7);
			}
			else {
				r = new RankedPlayer(Rank.EMERALD, "&aEmerald", 7, 7);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "diamond")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.DIAMOND);
				r.setAHCount(6);
				r.setHomeCount(6);
			}
			else {
				r = new RankedPlayer(Rank.DIAMOND, "&bDiamond", 6, 6);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 45, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "platinum")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.PLATINUM);
				r.setAHCount(5);
				r.setHomeCount(5);
			}
			else {
				r = new RankedPlayer(Rank.PLATINUM, "&3Platinum", 5, 5);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 36, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "gold")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.GOLD);
				r.setAHCount(4);
				r.setHomeCount(4);
			}
			else {
				r = new RankedPlayer(Rank.GOLD, "&eGold", 4, 4);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 27, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "silver")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.SILVER);
				r.setAHCount(3);
				r.setHomeCount(3);
			}
			else {
				r = new RankedPlayer(Rank.SILVER, "&7Silver", 3, 3);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 18, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "bronze")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.BRONZE);
				r.setAHCount(2);
				r.setHomeCount(2);
			}
			else {
				r = new RankedPlayer(Rank.BRONZE, "&6Bronze", 2, 2);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 9, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(mod == false && pay == false) {
			RankedPlayer r = new RankedPlayer(Rank.USER, "&7User", 1, 1);
			r.loadKits();
			rManager.add(player.getUniqueId(), r);
		}
		else if(mod == false && pay == true) {
			RankedPlayer r = rManager.getRankedPlayer(player.getUniqueId());
			r.addRank(Rank.USER);
			r.loadKits();
		}
		else if(mod == true && pay == false) {
			RankedPlayer r = rManager.getRankedPlayer(player.getUniqueId());
			r.addRank(Rank.USER);
			r.loadKits();
		}
	}
	public void loadRankedPlayers(OfflinePlayer player) {
		User user = luck.loadUser(player.getUniqueId());
		boolean mod = false;
		boolean pay = false;
		if(luck.containsParrent(user, "owner")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.OWNER);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.OWNER, "&2Owner", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "manager")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.MANAGER);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.MANAGER, "&5Manager", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "headadmin")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HEAD_ADMIN);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.HEAD_ADMIN, "&4Head Admin", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "admin")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.ADMIN);
				r.setAHCount(999);
				r.setHomeCount(999);
			}
			else {
				r = new RankedPlayer(Rank.ADMIN, "&4Admin", 999, 999);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			r.getPV().setContents(r.getStackList());
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "headmod")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HEAD_MOD);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.HEAD_MOD, "&1Head Mod", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "moderator")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.MOD);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.MOD, "&9Mod", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "helper_plus")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HELPER_PLUS);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.HELPER_PLUS, "&aHelper+", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "helper")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.HELPER);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.HELPER, "&aHelper", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "qualityassuranceadmin")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.QA_ADMIN);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.QA_ADMIN, "&cQA Admin", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		else if(luck.containsParrent(user, "qualityassurance")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.QA);
				r.setAHCount(1);
				r.setHomeCount(1);
			}
			else {
				r = new RankedPlayer(Rank.QA, "&bQA", 1, 1);
			}
			rManager.add(player.getUniqueId(), r);
			mod = true;
		}
		if(luck.containsParrent(user, "youtuber")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.YOUTUBER);
				r.setAHCount(7);
				r.setHomeCount(7);
			}
			else {
				r = new RankedPlayer(Rank.YOUTUBER, "&cY&fo&cu&ft&cu&fb&ce&fr", 7, 7);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "emerald")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.EMERALD);
				r.setAHCount(7);
				r.setHomeCount(7);
			}
			else {
				r = new RankedPlayer(Rank.EMERALD, "&aEmerald", 7, 7);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 54, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "diamond")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.DIAMOND);
				r.setAHCount(6);
				r.setHomeCount(6);
			}
			else {
				r = new RankedPlayer(Rank.DIAMOND, "&bDiamond", 6, 6);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 45, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "platinum")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.PLATINUM);
				r.setAHCount(5);
				r.setHomeCount(5);
			}
			else {
				r = new RankedPlayer(Rank.PLATINUM, "&3Platinum", 5, 5);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 36, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "gold")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.GOLD);
				r.setAHCount(4);
				r.setHomeCount(4);
			}
			else {
				r = new RankedPlayer(Rank.GOLD, "&eGold", 4, 4);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 27, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "silver")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.SILVER);
				r.setAHCount(3);
				r.setHomeCount(3);
			}
			else {
				r = new RankedPlayer(Rank.SILVER, "&7Silver", 3, 3);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 18, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(luck.containsParrent(user, "bronze")) {
			RankedPlayer r = null;
			if(rManager.contains(player.getUniqueId())) {
				r = rManager.getRankedPlayer(player.getUniqueId());
				r.addRank(Rank.BRONZE);
				r.setAHCount(2);
				r.setHomeCount(2);
			}
			else {
				r = new RankedPlayer(Rank.BRONZE, "&6Bronze", 2, 2);
			}
			r.setPV(CustomEnchantments.getInstance().getServer().createInventory(null, 9, new CCT().colorize("&7" + player.getName() + "'s Vault")));
			rManager.add(player.getUniqueId(), r);
			pay = true;
		}
		if(mod == false && pay == false) {
			RankedPlayer r = new RankedPlayer(Rank.USER, "&7User", 1, 1);
			r.loadKits();
			rManager.add(player.getUniqueId(), r);
		}
		else if(mod == false && pay == true) {
			RankedPlayer r = rManager.getRankedPlayer(player.getUniqueId());
			r.addRank(Rank.USER);
			r.loadKits();
		}
		else if(mod == true && pay == false) {
			RankedPlayer r = rManager.getRankedPlayer(player.getUniqueId());
			r.addRank(Rank.USER);
			r.loadKits();
		}
	}
	@EventHandler
	public void pvClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		InventoryView view = player.getOpenInventory();
		Inventory inv = event.getInventory();
		if(view.getTitle().contains(player.getName() + "'s Vault")) {
			RankedPlayer rPlayer = this.rManager.getRankedPlayer(player.getUniqueId());
			rPlayer.setPV(inv);
			rPlayer.setStackList(inv.getContents());
		}
	}
	@EventHandler
	public void disablePlayerFly(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		RankedPlayer rPlayer = this.rManager.getRankedPlayer(player.getUniqueId());
		if(rPlayer != null) {
			if(!wg.isInZone(player, "spawn")) {
				if(rPlayer.getFlight()) {
					if(!this.cooldown.contains(player.getUniqueId())) {
						this.cooldown.add(player.getUniqueId());
						new BukkitRunnable() {
							public void run() {
								if(cooldown.contains(player.getUniqueId())) {
									cooldown.remove(player.getUniqueId());
								}
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1L);
						rPlayer.setFlight(false);
						player.setAllowFlight(false);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use player fly outside of spawn and has been turned off!"));
					}
				}
			}
		}
	}
}
