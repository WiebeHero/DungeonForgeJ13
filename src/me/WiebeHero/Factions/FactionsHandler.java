package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CapturePoints.CapturePoint;
import me.WiebeHero.CapturePoints.CapturePointManager;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEvents.DFFactonXpGainEvent;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;

public class FactionsHandler implements Listener{
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	private FactionInventory facInventory;
	private CapturePointManager cpManager;
	public FactionsHandler(DFFactionManager facManager, DFFactionPlayerManager facPlayerManager, FactionInventory facInventory, CapturePointManager cpManager) {
		this.cpManager = cpManager;
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
		this.facInventory = facInventory;
	}
	private ArrayList<Material> blockAcces = new ArrayList<Material>(Arrays.asList(Material.CHEST, Material.FURNACE, Material.MINECART, Material.CHEST_MINECART, Material.HOPPER_MINECART, Material.FURNACE_MINECART, Material.CRAFTING_TABLE, Material.ENDER_CHEST, Material.DISPENSER, Material.DROPPER, Material.HOPPER, Material.SHULKER_BOX, Material.JUKEBOX, Material.BREWING_STAND));
	private ArrayList<Material> plates = new ArrayList<Material>(Arrays.asList(Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE));
	private ArrayList<Material> beds = new ArrayList<Material>(Arrays.asList(Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED, Material.GRAY_BED, Material.GREEN_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED, Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED, Material.PINK_BED, Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED));
	@EventHandler
	public void territoryInteract(PlayerInteractEvent event) {
		if(event.getPlayer().getWorld().getName().equals(Bukkit.getWorld("FactionWorld-1").getName())) {
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
			if(facPlayer != null) {
				if(facPlayer.getFactionId() != null) {
					DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
					if(block != null) {
						if(faction.isInChunk(block.getLocation())) {
							if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
								int rank = facPlayer.getRank();
								if(rank < 2) {
									event.setCancelled(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be atleast member to interact in your claimed territory!"));
								}
							}
							
						}
						else if(this.facManager.isInAChunk(block.getLocation())) {
							if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
								Block b = event.getClickedBlock();
								if(!this.blockAcces.contains(b.getType()) && player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.CREEPER_SPAWN_EGG) {
									event.setCancelled(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
								}
							}
							else if(event.getAction() == Action.PHYSICAL) {
								if(this.plates.contains(event.getClickedBlock().getType())) {
									event.setCancelled(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
								}
							}
						}
					}
				}
				else if(this.facManager.isInAChunk(block.getLocation())) {
					if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
						Block b = event.getClickedBlock();
						if(!this.blockAcces.contains(b.getType()) && player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.CREEPER_SPAWN_EGG) {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
						}
					}
					else if(event.getAction() == Action.PHYSICAL) {
						if(this.plates.contains(event.getClickedBlock().getType())) {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
						}
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
			DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
			if(facPlayer != null) {
				if(facPlayer.getFactionId() != null) {
					DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
					if(faction.isInChunk(block.getLocation())) {
						int rank = facPlayer.getRank();
						if(rank < 2) {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be atleast member to break blocks in your claimed territory!"));
						}
					}
					else if(facManager.isInAChunk(block.getLocation())) {
						event.setCancelled(true);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
					}
				}
				else if(facManager.isInAChunk(block.getLocation())) {
					event.setCancelled(true);
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
				}
			}
		}
	}
	@EventHandler
	public void territoryDestroy(BlockPlaceEvent event) {
		if(event.getPlayer().getWorld().getName().equals(Bukkit.getWorld("FactionWorld-1").getName())) {
			Player player = event.getPlayer();
			Block block = event.getBlock();
			DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
			if(facPlayer != null) {
				if(facPlayer.getFactionId() != null) {
					DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
					if(faction.isInChunk(block.getLocation())) {
						int rank = facPlayer.getRank();
						if(rank < 2) {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be atleast member to break blocks in your claimed territory!"));
						}
					}
					else if(facManager.isInAChunk(block.getLocation())) {
						event.setCancelled(true);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
					}
				}
				else if(facManager.isInAChunk(block.getLocation())) {
					event.setCancelled(true);
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
				}
			}
		}
	}
	@EventHandler
	public void damageFactionMember(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Player) {
				Player damager = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(damager);
				DFFactionPlayer oPlayer = facPlayerManager.getFactionPlayer(victim);
				if(facPlayer != null && oPlayer != null) {
					if(facPlayer.getFactionId() != null && oPlayer.getFactionId() != null) {
						DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
						if(faction.isMember(victim.getUniqueId())) {
							event.setCancelled(true);
							damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't harm your own faction members!"));
						}
						else {
							if(oPlayer != null) {
								if(oPlayer.getFactionId() != null) {
									if(faction.isAlly(oPlayer.getFactionId())) {
										event.setCancelled(true);
										damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't harm your faction allies!"));
									}
								}
							}
						}
					}
				}
			}
		}
		if(event.getDamager() instanceof Projectile) {
			Projectile arrow = (Projectile) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) arrow.getShooter();
					Player victim = (Player) event.getEntity();
					DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(damager);
					DFFactionPlayer oPlayer = facPlayerManager.getFactionPlayer(victim);
					if(facPlayer != null && oPlayer != null) {
						if(facPlayer.getFactionId() != null && oPlayer.getFactionId() != null) {
							DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
							if(faction.isMember(victim.getUniqueId())) {
								event.setCancelled(true);
								damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't harm your own faction members!"));
							}
							else {
								if(oPlayer != null) {
									if(oPlayer.getFactionId() != null) {
										if(faction.isAlly(oPlayer.getFactionId())) {
											event.setCancelled(true);
											damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't harm your faction allies!"));
										}
									}
								}
							}
						}
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
					RegionManager regions = container.get(BukkitAdapter.adapt(damager.getWorld()));
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
					RegionManager regions = container.get(BukkitAdapter.adapt(damager.getWorld()));
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

	@EventHandler(priority = EventPriority.LOWEST)
	public void newFacPlayer(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!facPlayerManager.contains(player.getUniqueId())) {
			facPlayerManager.add(player.getUniqueId());
		}
	}
	@EventHandler
	public void losePower(PlayerDeathEvent event) {
		DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(event.getEntity());
		DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
		if(faction != null) {
			faction.removeEnergy(2.0);
			event.getEntity().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have died and your faction has lost 2 power!"));
		}
	}
	
	@EventHandler
	public void dragInventory(InventoryDragEvent event) {
		Player player = (Player)event.getWhoClicked();
		InventoryView view = player.getOpenInventory();
		if(view.getTitle().contains("Faction Banner")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void clickInventory(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
		DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
		ItemStack stack = event.getCurrentItem();
		Inventory inv = event.getClickedInventory();
		InventoryView view = player.getOpenInventory();
		ClickType click = event.getClick();
		if(view.getTitle().contains("Faction Banner")) {
			event.setCancelled(true);
			if(inv.getType() == InventoryType.CHEST) {
				if(click == ClickType.RIGHT) {
					if(stack != null) {
						NBTItem item = new NBTItem(stack);
						if(item.hasKey("BannerRemove")) {
							faction.setBanner(new ItemStack(Material.BLACK_BANNER, 1));
							facInventory.FactionBannerInventory(player, faction);
							for(CapturePoint cp : cpManager.getCapturePointList().values()) {
								if(cp.getCapturedId() != null) {
									if(cp.getCapturedId().equals(faction.getFactionId())) {
										Location loc = cp.getCaptureLocation();
										loc.getBlock().setType(stack.getType());
										Banner banner = (Banner)loc.getBlock().getState();
										banner.setPatterns(null);
										banner.update();
									}
								}
							}
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aFaction banner has been reset!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou must have nothing in your cursor while resetting the banner!"));
					}
				}
			}
			else if(inv.getType() == InventoryType.PLAYER) {
				if(click == ClickType.LEFT) {
					if(stack != null) {
						if(stack.getType().toString().contains("BANNER")) {
							stack.setAmount(1);
							faction.setBanner(stack);
							facInventory.FactionBannerInventory(player, faction);
							for(CapturePoint cp : cpManager.getCapturePointList().values()) {
								if(cp.getCapturedId() != null) {
									if(cp.getCapturedId().equals(faction.getFactionId())) {
										Location loc = cp.getCaptureLocation();
										BannerMeta m = (BannerMeta)stack.getItemMeta();
										loc.getBlock().setType(stack.getType());
										Banner banner = (Banner)loc.getBlock().getState();
										banner.setPatterns(m.getPatterns());
										banner.update();
									}
								}
							}
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aFaction banner has been set!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe item that needs to replaced needs to be a banner!"));
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void xpEarnMobs10(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player player = event.getEntity().getKiller();
				DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
				if(facPlayer != null) {
					if(facPlayer.getFactionId() != null) {
						LivingEntity victim = event.getEntity();
						int finalXP = 0;
						if(!(victim instanceof Player)) {
							Entity ent = NBTInjector.patchEntity(victim);
							NBTCompound comp = NBTInjector.getNbtData(ent);
							if(comp.hasKey("SpawnerUUID")) {
								int tier = comp.getInteger("Tier");
								int levelMob = comp.getInteger("Level");
								//-----------------------------------------------------------------------------------------------------------------------------------------
								//XP Adding
								//-----------------------------------------------------------------------------------------------------------------------------------------
								int i1 = 0;
								if(tier == 1) {
									i1 = new Random().nextInt(1) + 1 + (int)(0.1 * (double)levelMob);
								}
								else if(tier == 2) {
									i1 = new Random().nextInt(2) + 2 + (int)(0.15 * (double)levelMob);
								}
								else if(tier == 3) {
									i1 = new Random().nextInt(3) + 3 + (int)(0.2 * (double)levelMob);
								}
								else if(tier == 4) {
									i1 = new Random().nextInt(4) + 4 + (int)(0.25 * (double)levelMob);
								}
								else if(tier == 5) {
									i1 = new Random().nextInt(5) + 5 + (int)(0.3 * (double)levelMob);
								}
								finalXP = i1;
							}
						}
						DFFactonXpGainEvent ev = new DFFactonXpGainEvent(facPlayer.getFactionId(), finalXP, this.facManager, this.facPlayerManager);
						Bukkit.getServer().getPluginManager().callEvent(ev);
						ev.proceed();
					}
				}
			}
		}
	}
	@EventHandler
	public void xpGainPlayer(DFPlayerXpGainEvent event) {
		Player player = event.getPlayer();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
		if(facPlayer != null) {
			if(facPlayer.getFactionId() != null) {
				DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
				event.setXPMultiplier(event.getXPMultiplier() + faction.getExperienceGainMultiplier());
			}
		}
	}
	@EventHandler
	public void xpGainItem(DFItemXpGainEvent event) {
		Player player = event.getPlayer();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
		if(facPlayer != null) {
			if(facPlayer.getFactionId() != null) {
				if(event.getEquipmentSlot() != null) {
					DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
					event.setXPMultiplier(event.getXPMultiplier() + faction.getExperienceGainMultiplier());
				}
			}
		}
	}
}
