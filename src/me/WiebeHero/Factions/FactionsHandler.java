package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class FactionsHandler implements Listener{
	private DFFactions f = new DFFactions();
	private ArrayList<Material> blockAcces = new ArrayList<Material>(Arrays.asList(Material.CHEST, Material.FURNACE, Material.MINECART, Material.CHEST_MINECART, Material.HOPPER_MINECART, Material.FURNACE_MINECART, Material.CRAFTING_TABLE, Material.ENDER_CHEST, Material.DISPENSER, Material.DROPPER, Material.HOPPER, Material.SHULKER_BOX, Material.JUKEBOX, Material.BREWING_STAND));
	private ArrayList<Material> plates = new ArrayList<Material>(Arrays.asList(Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE));
	private ArrayList<Material> beds = new ArrayList<Material>(Arrays.asList(Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED, Material.GRAY_BED, Material.GREEN_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED, Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED, Material.PINK_BED, Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED));
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
					Block b = event.getClickedBlock();
					if(!blockAcces.contains(b.getType()) && player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.CREEPER_SPAWN_EGG) {
						event.setCancelled(true);
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
					}
				}
				else if(event.getAction() == Action.PHYSICAL) {
					if(plates.contains(event.getClickedBlock().getType())) {
						event.setCancelled(true);
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
					}
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
	public void territoryDestroy(BlockPlaceEvent event) {
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
	@EventHandler
	public void damagePlayersInSpawn(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Player && event.getEntity() instanceof Player) {
					Player damager = (Player) event.getDamager();
					Player victim = (Player) event.getEntity();
					RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
					RegionManager regions = container.get((com.sk89q.worldedit.world.World) damager.getWorld());
					if(damager.getWorld().getName().equals(Bukkit.getWorld("DFWarzone-1").getName())) {
						if(regions.hasRegion("spawn") && regions.hasRegion("warzone")) {
							if(regions.getRegion("spawn").contains(victim.getLocation().getBlockX(), victim.getLocation().getBlockY(), victim.getLocation().getBlockZ())) {
								if(regions.getRegion("warzone").contains(damager.getLocation().getBlockX(), damager.getLocation().getBlockY(), damager.getLocation().getBlockZ())) {
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
			if(event.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) event.getDamager();
				if(projectile.getShooter() != null && projectile.getShooter() instanceof Player && event.getEntity() instanceof Player) {
					Player damager = (Player) projectile.getShooter();
					Player victim = (Player) event.getEntity();
					RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
					RegionManager regions = container.get((com.sk89q.worldedit.world.World) damager.getWorld());
					if(damager.getWorld().getName().equals(Bukkit.getWorld("DFWarzone-1").getName())) {
						if(regions.hasRegion("spawn") && regions.hasRegion("warzone")) {
							if(regions.getRegion("spawn").contains(victim.getLocation().getBlockX(), victim.getLocation().getBlockY(), victim.getLocation().getBlockZ())) {
								if(regions.getRegion("warzone").contains(damager.getLocation().getBlockX(), damager.getLocation().getBlockY(), damager.getLocation().getBlockZ())) {
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void disableBeds(PlayerInteractEvent event) {
		Block b = event.getClickedBlock();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(beds.contains(b.getType())) {
				event.setCancelled(true);
			}
		}
	}
}
