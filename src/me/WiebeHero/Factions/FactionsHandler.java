package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class FactionsHandler implements Listener{
	private DFFactions f = new DFFactions();
	@EventHandler
	public void territoryInteract(PlayerInteractEvent event) {
		if(event.getPlayer().getWorld().getName().equals(Bukkit.getWorld("FactionWorld-1").getName())) {
			Player player = event.getPlayer();
			String fName = "";
			for(Entry<String, ArrayList<UUID>> entry : f.getFactionMemberList().entrySet()) {
				if(entry.getValue().contains(player.getUniqueId())) {
					fName = entry.getKey();
				}
			}
			ArrayList<Chunk> chunkListYou = new ArrayList<Chunk>();
			ArrayList<Chunk> chunkListThem = new ArrayList<Chunk>();
			for(Entry<String, ArrayList<Chunk>> entry : f.getChunkList().entrySet()) {
				if(entry.getKey().equals(fName)) {
					chunkListYou.addAll(entry.getValue());
				}
				else {
					chunkListThem.addAll(entry.getValue());
				}
			}
			if(chunkListYou.contains(player.getLocation().getChunk())) {
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
					int rank = f.getRankedList().get(player.getUniqueId());
					if(rank < 2) {
						event.setCancelled(true);
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou need to be atleast member to interact in your claimed territory!"));
					}
				}
			}
			else if(chunkListThem.contains(player.getLocation().getChunk())) {
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
					event.setCancelled(true);
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
				}
			}
		}
	}
	@EventHandler
	public void territoryDestroy(BlockBreakEvent event) {
		if(event.getPlayer().getWorld().getName().equals(Bukkit.getWorld("FactionWorld-1").getName())) {
			Player player = event.getPlayer();
			Block block = event.getBlock();
			String fName = "";
			for(Entry<String, ArrayList<UUID>> entry : f.getFactionMemberList().entrySet()) {
				if(entry.getValue().contains(player.getUniqueId())) {
					fName = entry.getKey();
				}
			}
			ArrayList<Chunk> chunkListYou = new ArrayList<Chunk>();
			ArrayList<Chunk> chunkListThem = new ArrayList<Chunk>();
			for(Entry<String, ArrayList<Chunk>> entry : f.getChunkList().entrySet()) {
				if(entry.getKey().equals(fName)) {
					chunkListYou.addAll(entry.getValue());
				}
				else {
					chunkListThem.addAll(entry.getValue());
				}
			}
			if(chunkListYou.contains(block.getLocation().getChunk())) {
				int rank = f.getRankedList().get(player.getUniqueId());
				if(rank < 2) {
					event.setCancelled(true);
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou need to be atleast member to break blocks in your claimed territory!"));
				}
			}
			else if(chunkListThem.contains(block.getLocation().getChunk())) {
				event.setCancelled(true);
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
			}
		}
	}
	@EventHandler
	public void damageFactionMember(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Player) {
				Player damager = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				String fName = "";
				for(Entry<String, ArrayList<UUID>> entry : f.getFactionMemberList().entrySet()) {
					if(entry.getValue().contains(damager.getUniqueId())) {
						fName = entry.getKey();
					}
				}
				if(f.getFactionMemberList().get(fName).contains(victim.getUniqueId())) {
					event.setCancelled(true);
					damager.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't harm your own faction members!"));
				}
			}
		}
		if(event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) arrow.getShooter();
					Player victim = (Player) event.getEntity();
					String fName = "";
					for(Entry<String, ArrayList<UUID>> entry : f.getFactionMemberList().entrySet()) {
						if(entry.getValue().contains(damager.getUniqueId())) {
							fName = entry.getKey();
						}
					}
					if(f.getFactionMemberList().get(fName).contains(victim.getUniqueId())) {
						event.setCancelled(true);
						damager.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't harm your own faction members!"));
					}
				}
			}
		}
	}
}
