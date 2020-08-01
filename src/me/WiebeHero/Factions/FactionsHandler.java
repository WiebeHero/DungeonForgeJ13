package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import javafx.util.Pair;
import me.WiebeHero.CapturePoints.CapturePoint;
import me.WiebeHero.CapturePoints.CapturePointManager;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFFactionLevelUpEvent;
import me.WiebeHero.CustomEvents.DFFactonXpGainEvent;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.Factions.DFFactionGroups.FactionGroup;
import me.WiebeHero.Factions.DFFactionGroups.FactionPermission;
import net.md_5.bungee.api.ChatColor;

public class FactionsHandler implements Listener{
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	private FactionInventory facInventory;
	private CapturePointManager cpManager;
	private ItemStackBuilder builder;
	private DFFactionGroups facGroups;
	public FactionsHandler(DFFactionManager facManager, DFFactionPlayerManager facPlayerManager, FactionInventory facInventory, CapturePointManager cpManager, ItemStackBuilder builder, DFFactionGroups facGroups) {
		this.cpManager = cpManager;
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
		this.facInventory = facInventory;
		this.builder = builder;
		this.facGroups = facGroups;
		this.containerAcces = new ArrayList<Material>(Arrays.asList(Material.CHEST, Material.FURNACE, Material.MINECART, Material.CHEST_MINECART, Material.HOPPER_MINECART, Material.FURNACE_MINECART, Material.CRAFTING_TABLE, Material.ENDER_CHEST, Material.DISPENSER, Material.DROPPER, Material.HOPPER, Material.SHULKER_BOX, Material.JUKEBOX, Material.BREWING_STAND));	
	}
	private ArrayList<Material> containerAcces;
	@EventHandler
	public void territoryInteract(PlayerInteractEvent event) {
		if(event.getPlayer().getWorld().getName().equals(Bukkit.getWorld("FactionWorld-1").getName())) {
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
			Action action = event.getAction();
			if(facPlayer != null) {
				if(facPlayer.getFactionId() != null) {
					DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
					if(block != null) {
						if(faction.isInChunk(block.getLocation())) {
							if(action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK || action == Action.PHYSICAL) {
								if(block.getType().toString().contains("DOOR")) {
									FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
									boolean state = faction.getPermission(group, FactionPermission.OPEN_DOORS);
									if(!state) {
										event.setCancelled(true);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permissions to interact with doors in your territory!"));
									}
								}
								else if(block.getType().toString().contains("BUTTON")) {
									FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
									boolean state = faction.getPermission(group, FactionPermission.PRESS_BUTTONS);
									if(!state) {
										event.setCancelled(true);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permissions to interact with buttons in your territory!"));
									}
								}
								else if(block.getType() == Material.LEVER) {
									FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
									boolean state = faction.getPermission(group, FactionPermission.FLICK_LEVERS);
									if(!state) {
										event.setCancelled(true);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permissions to interact with levers in your territory!"));
									}
								}
								else if(block.getType().toString().contains("PLATE")) {
									FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
									boolean state = faction.getPermission(group, FactionPermission.ACTIVATE_PRESSURE_PLATE);
									if(!state) {
										event.setCancelled(true);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permissions to interact with pressure plates in your territory!"));
									}
								}
								else if(block.getType() == Material.STRING) {
									FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
									boolean state = faction.getPermission(group, FactionPermission.ACTIVATE_TRIPWIRE_HOOKS);
									if(!state) {
										event.setCancelled(true);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permissions to interact with tripwire hooks in your territory!"));
									}
								}
							}
						}
						else if(this.facManager.isInAChunk(block.getLocation())) {
							if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
								Block b = event.getClickedBlock();
								if(!this.containerAcces.contains(b.getType()) && player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.CREEPER_SPAWN_EGG) {
									event.setCancelled(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
								}
							}
						}
					}
				}
				else if(this.facManager.isInAChunk(block.getLocation())) {
					if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
						Block b = event.getClickedBlock();
						if(!this.containerAcces.contains(b.getType()) && player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.CREEPER_SPAWN_EGG) {
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
			DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
			if(facPlayer != null) {
				if(facPlayer.getFactionId() != null) {
					DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
					if(faction.isInChunk(block.getLocation())) {
						FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
						boolean state = faction.getPermission(group, FactionPermission.BREAK_BLOCKS);
						if(!state) {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permission to break blocks in your territory!"));
						}
					}
					else if(this.facManager.isInAChunk(block.getLocation())) {
						event.setCancelled(true);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
					}
				}
				else if(this.facManager.isInAChunk(block.getLocation())) {
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
			DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
			if(facPlayer != null) {
				if(facPlayer.getFactionId() != null) {
					DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
					if(faction.isInChunk(block.getLocation())) {
						FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
						boolean state = faction.getPermission(group, FactionPermission.PLACE_BLOCKS);
						if(!state) {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the permission to place blocks in your territory!"));
						}
					}
					else if(this.facManager.isInAChunk(block.getLocation())) {
						event.setCancelled(true);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't interact with enemy territory!"));
					}
				}
				else if(this.facManager.isInAChunk(block.getLocation())) {
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
				DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(damager);
				DFFactionPlayer oPlayer = this.facPlayerManager.getFactionPlayer(victim);
				if(facPlayer != null && oPlayer != null) {
					if(facPlayer.getFactionId() != null && oPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
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
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(damager);
					DFFactionPlayer oPlayer = this.facPlayerManager.getFactionPlayer(victim);
					if(facPlayer != null && oPlayer != null) {
						if(facPlayer.getFactionId() != null && oPlayer.getFactionId() != null) {
							DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
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
			if(b.getType().toString().contains("BED")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void newFacPlayer(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!this.facPlayerManager.contains(player.getUniqueId())) {
			this.facPlayerManager.add(player.getUniqueId());
		}
	}
	@EventHandler
	public void losePower(PlayerDeathEvent event) {
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(event.getEntity());
		DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
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
						DFFactonXpGainEvent ev = new DFFactonXpGainEvent(player, facPlayer.getFactionId(), finalXP, this.facManager, this.facPlayerManager);
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
	@EventHandler
	public void pvSizeIncrease(DFFactionLevelUpEvent event) {
		Player player = event.getPlayer();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
		if(facPlayer != null) {
			if(facPlayer.getFactionId() != null) {
				DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
				if(faction != null) {
					for(int level : event.getLevelsPassed()) {
						if(level % 20 == 0) {
							ItemStack stacks[] = faction.getFactionVault().getContents();
							faction.setFactionVault(CustomEnchantments.getInstance().getServer().createInventory(null, faction.getVaultSize(), new CCT().colorize("&a" + faction.getName() + "'s Vault")));
							faction.getFactionVault().setContents(stacks);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void factionMenuClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			InventoryView view = player.getOpenInventory();
			ItemStack stack = event.getCurrentItem();
			ClickType click = event.getClick();
			if(stack != null && stack.getType() != Material.AIR) {
				NBTItem item = new NBTItem(stack);
				if(view.getTitle().contains("Faction Overview")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("Leveling")) {
							this.facInventory.FactionLevelProgress(player, faction, 1);
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.0F);
						}
						else if(item.hasKey("Members")) {
							this.facInventory.FactionMemberManagement(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 2.0F, 1.5F);
						}
						else if(item.hasKey("ChunksAndHomes")) {
							this.facInventory.FactionChunkHomes(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_GRASS_PLACE, 2.0F, 1.5F);
						}
						else if(item.hasKey("Vault")) {
							FactionGroup group = this.facGroups.getRankFactionGroup(facPlayer.getRank());
							boolean state = faction.getPermission(group, FactionPermission.FACTION_VAULT_ACCES);
							if(state) {
								player.openInventory(faction.getFactionVault());
								player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 2.0F, 1.5F);
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use the faction vault!"));
							}
						}
						else if(item.hasKey("Settings")) {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aFaction settings are coming soon!"));
						}
						else {
							Inventory inv = event.getClickedInventory();
							if(inv.getType() == InventoryType.PLAYER) {
								if(click == ClickType.LEFT) {
									if(stack.getType().toString().contains("BANNER")) {
										stack.setAmount(1);
										faction.setBanner(stack);
										this.facInventory.MainFactionInventory(player, faction);
										for(CapturePoint cp : this.cpManager.getCapturePointList()) {
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
										player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 1.0F);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aFaction banner has been set!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe item that needs to replaced needs to be a banner!"));
									}
								}
							}
							else if(inv.getType() == InventoryType.CHEST) {
								if(click == ClickType.RIGHT) {
									if(item.hasKey("BannerRemove")) {
										faction.setBanner(new ItemStack(Material.BLACK_BANNER, 1));
										this.facInventory.MainFactionInventory(player, faction);
										for(CapturePoint cp : this.cpManager.getCapturePointList()) {
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
										player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 1.0F);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aFaction banner has been reset!"));
									}
								}
							}
						}
					}
				}
				else if(view.getTitle().contains("Faction Leveling")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("Next")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							int newPage = item.getInteger("Next");
							int maxPage = (int) Math.ceil((double)faction.getMaxLevel() / 28.00);
							if(newPage <= maxPage && maxPage > 1) {
								this.facInventory.FactionLevelProgress(player, faction, newPage);
							}
							else if(newPage > maxPage && maxPage > 1) {
								this.facInventory.FactionLevelProgress(player, faction, 1);
							}
						}
						else if(item.hasKey("Previous")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							int newPage = item.getInteger("Previous");
							int maxPage = (int) Math.ceil((double)faction.getMaxLevel() / 28.00);
							if(newPage >= 1 && maxPage > 1) {
								this.facInventory.FactionLevelProgress(player, faction, newPage);
							}
							else if(newPage < 1 && maxPage > 1) {
								this.facInventory.FactionLevelProgress(player, faction, maxPage);
							}
						}
						else if(item.hasKey("Back")) {
							this.facInventory.MainFactionInventory(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Member Management")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("View")) {
							this.facInventory.FactionMemberMenu(player, faction);
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F);
						}
						else if(item.hasKey("Permission")) {
							this.facInventory.FactionGroupSelection(player, faction);
							player.playSound(player.getLocation(), Sound.ENTITY_VINDICATOR_AMBIENT, 2.0F, 0.5F);
						}
						else if(item.hasKey("Back")){
							this.facInventory.MainFactionInventory(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Members")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("Player")) {
							int rankMe = facPlayer.getRank();
							FactionGroup group = this.facGroups.getRankFactionGroup(rankMe);
							UUID uuidOther = item.getObject("Player", UUID.class);
							OfflinePlayer op = Bukkit.getOfflinePlayer(uuidOther);
							DFFactionPlayer oFacPlayer = this.facPlayerManager.getFactionPlayer(uuidOther);
							int rankOther = oFacPlayer.getRank();
							if(click == ClickType.LEFT) {
								if(faction.getPermission(group, FactionPermission.PROMOTE_FACTION_MEMBERS) && rankMe > rankOther && rankOther < 4) {
									oFacPlayer.setRank(rankOther + 1);
									FactionGroup otherGroup = this.facGroups.getRankFactionGroup(oFacPlayer.getRank());
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + op.getName() + " &ato &6" + otherGroup.getDisplay() + "!"));
									if(Bukkit.getPlayer(uuidOther) != null) {
										Player oPlayer = Bukkit.getPlayer(uuidOther);
										oPlayer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &apromoted you to to &6" + otherGroup.getDisplay() + "!"));
									}
									player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.5F);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote this player to a higher faction rank!"));
								}
							}
							else if(click == ClickType.RIGHT) {
								if(faction.getPermission(group, FactionPermission.DEMOTE_FACTION_MEMBERS) && rankMe > rankOther && rankOther > 1) {
									oFacPlayer.setRank(rankOther - 1);
									FactionGroup otherGroup = this.facGroups.getRankFactionGroup(oFacPlayer.getRank());
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have demoted &6" + op.getName() + " &ato &6" + otherGroup.getDisplay() + "!"));
									if(Bukkit.getPlayer(uuidOther) != null) {
										Player oPlayer = Bukkit.getPlayer(uuidOther);
										oPlayer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cdemoted you to to &6" + otherGroup.getDisplay() + "!"));
									}
									player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 0.5F);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't demote this player to a lower faction rank!"));
								}
							}
							else if(click == ClickType.MIDDLE) {
								if(faction.getPermission(group, FactionPermission.DEMOTE_FACTION_MEMBERS) && rankMe > rankOther) {
									oFacPlayer.setRank(1);
									oFacPlayer.setFactionId(null);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + op.getName() + " &afrom your faction!"));
									if(Bukkit.getPlayer(uuidOther) != null) {
										Player oPlayer = Bukkit.getPlayer(uuidOther);
										oPlayer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ckicked you from your faction!"));
									}
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 0.5F);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't kick this player from your faction!"));
								}
							}
						}
						else if(item.hasKey("Back")){
							this.facInventory.FactionMemberManagement(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Groups")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("FactionGroup")) {
							FactionGroup group = item.getObject("FactionGroup", FactionGroup.class);
							this.facInventory.FactionPermissionMenu(player, faction, group);
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2.0F, 1.0F);
						}
						else if(item.hasKey("Back")){
							this.facInventory.FactionMemberManagement(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Permissions")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						String itemName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("FactionGroup") && item.hasKey(itemName)) {
							FactionGroup group = item.getObject("FactionGroup", FactionGroup.class);
							FactionPermission perm = item.getObject(itemName, FactionPermission.class);
							boolean state = faction.getPermission(group, perm);
							if(state == true) {
								state = false;
								faction.setPermission(group, perm, state);
							}
							else if(state == false) {
								state = true;
								faction.setPermission(group, perm, state);
							}
							Inventory inv = view.getTopInventory();
							inv.setItem(event.getSlot(), this.builder.constructItem(
									Material.PAPER,
									1,
									"&a" + perm.getDisplay(),
									new String[] {
											state ? "&7" + perm.getDisplay() + ": &aOn" : "&7" + perm.getDisplay() + ": &cOff"
									},
									new Pair<String, FactionPermission>(perm.getDisplay(), perm),
									new Pair<String, FactionGroup>("FactionGroup", group)
							));
							player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 1.5F);
						}
						else if(item.hasKey("Back")){
							this.facInventory.FactionGroupSelection(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Homes and Chunks")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("Homes")) {
							this.facInventory.FactionHomeManagement(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 2.0F, 0.5F);
						}
						else if(item.hasKey("Chunks")) {
							this.facInventory.FactionChunkManagement(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_GRASS_PLACE, 2.0F, 0.5F);
						}
						else if(item.hasKey("Back")){
							this.facInventory.MainFactionInventory(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Homes")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("FactionHome")) {
							if(click == ClickType.RIGHT) {
								String name = item.getString("FactionHome");
								if(faction.hasFactionHome(name)) {
									faction.removeFactionHome(name);
									this.facInventory.FactionHomeManagement(player, faction);
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
								}
								else {
									this.facInventory.FactionHomeManagement(player, faction);
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
								}
							}
						}
						else if(item.hasKey("Back")) {
							this.facInventory.FactionChunkHomes(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
				else if(view.getTitle().contains("Faction Chunks")) {
					event.setCancelled(true);
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer.getFactionId() != null) {
						DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
						if(item.hasKey("Chunk")) {
							if(click == ClickType.RIGHT) {
								Long chunk = item.getLong("Chunk");
								if(faction.hasChunk(chunk)) {
									faction.removeChunk(chunk);
									this.facInventory.FactionChunkManagement(player, faction);
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
								}
								else {
									this.facInventory.FactionChunkManagement(player, faction);
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
								}
							}
						}
						else if(item.hasKey("Back")){
							this.facInventory.FactionChunkHomes(player, faction);
							player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.0F);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void pvClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
		if(facPlayer != null && facPlayer.getFactionId() != null) {
			DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
			InventoryView view = player.getOpenInventory();
			Inventory inv = event.getInventory();
			if(view.getTitle().contains(faction.getName() + "'s Vault")) {
				faction.setFactionVault(inv);
				faction.setStackList(inv.getContents());
				player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 1.5F);
			}
		}
	}
	@EventHandler
	public void pvClose(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
		if(facPlayer != null && facPlayer.getFactionId() != null) {
			DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
			InventoryView view = player.getOpenInventory();
			Inventory inv = event.getInventory();
			if(view.getTitle().contains(faction.getName() + "'s Vault")) {
				new BukkitRunnable() {
					public void run() {
						faction.setFactionVault(inv);
						faction.setStackList(inv.getContents());
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 1L);
			}
		}
	}
}
