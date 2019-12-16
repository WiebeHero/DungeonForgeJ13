package me.WiebeHero.Moderation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.LootChest.LootChest;
import me.WiebeHero.LootChest.LootChestManager;
import me.WiebeHero.Spawners.DFSpawner;
import me.WiebeHero.Spawners.DFSpawnerManager;

public class ModerationEvents implements Listener{
	private PunishManager pManager = CustomEnchantments.getInstance().punishManager;
	private StaffManager sManager = CustomEnchantments.getInstance().staffManager;
	private DFSpawnerManager spManager = CustomEnchantments.getInstance().spawnerManager;
	private LootChestManager lcManager = CustomEnchantments.getInstance().lootChestManager;
	private ModerationGUI gui = new ModerationGUI();
	private Methods m = new Methods();
	private HashMap<UUID, UUID> target = new HashMap<UUID, UUID>();
	private HashMap<UUID, String> reason = new HashMap<UUID, String>();
	private HashMap<UUID, EntityType> spawnerType = new HashMap<UUID, EntityType>();
	private HashMap<UUID, Integer> spawnerTier = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> spawnerSlot = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootTier = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootRadius = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootSlot = new HashMap<UUID, Integer>();
	@EventHandler
	public void punishJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(!pManager.contains(p.getUniqueId())) {
			new Punish(p.getUniqueId());
		}
		if(!sManager.contains(p.getUniqueId())) {
			if(p.hasPermission("owner")) {
				new Staff(p.getUniqueId(), 10);
			}
			else if(p.hasPermission("manager")) {
				new Staff(p.getUniqueId(), 9);
			}
			else if(p.hasPermission("headadmin")) {
				new Staff(p.getUniqueId(), 8);
			}
			else if(p.hasPermission("admin")) {
				new Staff(p.getUniqueId(), 7);
			}
			else if(p.hasPermission("headmod")) {
				new Staff(p.getUniqueId(), 6);
			}
			else if(p.hasPermission("moderator")) {
				new Staff(p.getUniqueId(), 5);
			}
			else if(p.hasPermission("helper+")) {
				new Staff(p.getUniqueId(), 4);
			}
			else if(p.hasPermission("helper")) {
				new Staff(p.getUniqueId(), 3);
			}
			else if(p.hasPermission("qaadmin")) {
				new Staff(p.getUniqueId(), 2);
			}
			else if(p.hasPermission("qa")) {
				new Staff(p.getUniqueId(), 1);
			}
		}
	}

	@EventHandler
	public void staffListLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				staff.switchStaffMode(false);
				player.getInventory().setContents(staff.getInv());
			}
		}
	}
	@EventHandler
	public void staffListLeave(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				staff.switchStaffMode(false);
				player.getInventory().setContents(staff.getInv());
			}
		}
	}
	@EventHandler
	public void cancelPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void cancelDestroy(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void cancelInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void foodChangeStaff(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					player.setFoodLevel(20);
				}
			}
		}
	}
	@EventHandler
	public void cancelHealthLoss(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void cancelHealthLossEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
		else if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void chatAttempt(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if(pManager.contains(p.getUniqueId())) {
			Punish punish = pManager.get(p.getUniqueId());
			if(punish.getMuteTime() > System.currentTimeMillis()) {
				event.setCancelled(true);
				Calendar calender = Calendar.getInstance();
				Long timeLeft = punish.getMuteTime() - System.currentTimeMillis();
				calender.setTimeInMillis(timeLeft);
				Date date = calender.getTime();
				SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't chat because you are muted! You can chat again on: &6" + form.format(date)));
			}
			else {
				punish.setMuteTime(0L);
			}
		}
	}
	@EventHandler
	public void joinAttempt(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(pManager.contains(p.getUniqueId())) {
			Punish punish = pManager.get(p.getUniqueId());
			if(punish.getBanTime() > System.currentTimeMillis()) {
				p.kickPlayer("");
			}
			else {
				punish.setMuteTime(0L);
			}
		}
		
	}
	@EventHandler
	public void kickBan(PlayerKickEvent event) {
		Player p = event.getPlayer();
		if(pManager.contains(p.getUniqueId())) {
			Punish punish = pManager.get(p.getUniqueId());
			if(punish.getBanTime() > System.currentTimeMillis()) {
				Calendar calender = Calendar.getInstance();
				Long timeLeft = punish.getBanTime() - System.currentTimeMillis();
				calender.setTimeInMillis(timeLeft);
				Date date = calender.getTime();
				SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				event.setReason(new CCT().colorize("&2&l[DungeonForge]: &cYou can't join because you are banned! You can join again on: &6" + form.format(date)));
			}
			else {
				punish.setBanTime(0L);
			}
		}
	}

	@EventHandler
	public void activatePerk(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				if(player.getInventory().getItemInMainHand() != null) {
					ItemStack i = player.getInventory().getItemInMainHand();
					NBTItem item = new NBTItem(i);
					if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if(item.hasKey("TeleporterItem")) {
							gui.InventoryTeleport(player, 1);
						}
						else if(item.hasKey("VanishItem")) {
							if(staff.getVanishMode() == true) {
								staff.switchVanishMode(false);
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.showPlayer(CustomEnchantments.getInstance(), player);
								}
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &c&lVanish Disabled."));
							}
							else if(staff.getVanishMode() == false) {
								staff.switchVanishMode(true);
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.hidePlayer(CustomEnchantments.getInstance(), player);
								}
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &a&lVanish Enabled."));
							}
						}
						else if(item.hasKey("SpawnerItem")) {
							staff.switchSpawnerMode(true);
							player.getInventory().clear();
							player.getInventory().setItem(0, gui.createDisplayItem(
									Material.COMPASS, 
									"&aSpawner Menu", 
									"&7Right click this item to open//"
									+ "&7up the spawner menu to create/delete spawner items.", 
									"SpawnerCreate"
							));
							player.getInventory().setItem(1, gui.createDisplayItem(
									Material.BARRIER, 
									"&cSpawner Delete", 
									"&7Right click this item to delete//"
									+ "&7any spawner. Make sure there is a block//"
									+ "&7actually placed in the spawner.", 
									"SpawnerDelete"
							));
							player.getInventory().setItem(2, gui.createDisplayItem(
									Material.YELLOW_STAINED_GLASS, 
									"&6Spawner See", 
									"&7Right click this item to see//"
									+ "&7any spawner in a 20 block radius.//"
									+ "&7These spawners will turn into yellow glass.", 
									"SpawnerSee"
							));
						}
						else if(item.hasKey("LootItem")) {
							staff.switchLootMode(true);
							player.getInventory().clear();
							player.getInventory().setItem(0, gui.createDisplayItem(
									Material.COMPASS, 
									"&aLoot Menu", 
									"&7Right click this item to open//"
									+ "&7up the loot chests menu to create/delete spawner items.", 
									"LootCreate"
							));
							player.getInventory().setItem(1, gui.createDisplayItem(
									Material.BARRIER, 
									"&cLoot Delete", 
									"&7Right click this item to delete//"
									+ "&7any loot chest. Make sure there is a block//"
									+ "&7actually placed in the position of the loot chest.//"
									+ "&7(A chest in its place is also valid)",
									"LootDelete"
							));
							player.getInventory().setItem(2, gui.createDisplayItem(
									Material.YELLOW_STAINED_GLASS, 
									"&6Loot See", 
									"&7Right click this item to see//"
									+ "&7any loot chest in a 20 block radius.//"
									+ "&7These loot chests will turn into yellow glass.", 
									"LootSee"
							));
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void activePerk(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				if(player.getInventory().getItemInMainHand() != null && event.getRightClicked() != null) {
					if(event.getRightClicked() instanceof Player) {
						Player clicked = (Player) event.getRightClicked();
						ItemStack i = player.getInventory().getItemInMainHand();
						NBTItem item = new NBTItem(i);
						if(item.hasKey("InfoItem")) {
							gui.InfoMenu(player, clicked);
						}
						else if(item.hasKey("ExamineItem")) {
							gui.ExamineMenu(player, clicked);
						}
						else if(item.hasKey("PunishItem")) {
							gui.PunishMenu(player, clicked);
							target.put(player.getUniqueId(), clicked.getUniqueId());
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to right click a player."));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo player targetted."));
				}
			}
		}
	}
	@EventHandler
	public void teleportInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Teleport")) {
				if(item != null) {
					String name = item.getItemMeta().getDisplayName();
					title = ChatColor.stripColor(title);
					String split[] = title.split("/");
					String currentPageS = split[0].replaceAll("[^\\d.]", "");
					String maxPageS = split[1].replaceAll("[^\\d.]", "");
					int currentPage = Integer.parseInt(currentPageS);
					int maxPage = Integer.parseInt(maxPageS);
					if(item.getType() == Material.PLAYER_HEAD) {
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						if(meta.getOwningPlayer().getPlayer().isOnline()) {
							player.teleport(meta.getOwningPlayer().getPlayer());
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have teleported to &6" + meta.getOwningPlayer().getPlayer().getName()));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player isn't online anymore!"));
						}
					}
					else if(ChatColor.stripColor(name).equals("Next Page")) {
						if(currentPage < maxPage) {
							player.closeInventory();
							gui.InventoryTeleport(player, currentPage + 1);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere are no more pages then this."));
						}
					}
					else if(ChatColor.stripColor(name).equals("Previous Page")) {
						if(currentPage > maxPage) {
							player.closeInventory();
							gui.InventoryTeleport(player, currentPage - 1);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere are no more pages then this."));
						}
					}
					else if(ChatColor.stripColor(name).equals("Close")) {
						player.closeInventory();
					}
				}
			}
		}
	}
	@EventHandler
	public void punishMenu(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							String name = item.getItemMeta().getDisplayName();
							if(name.contains("Ban")) {
								if(target.containsKey(player.getUniqueId())) {
									gui.BanChoice(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									player.closeInventory();
								}
							}
							else if(name.contains("Mute")) {
								if(target.containsKey(player.getUniqueId())) {
									gui.MuteChoice(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									player.closeInventory();
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void banChoice(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							Punish pun = pManager.get(target.get(player.getUniqueId()));
							String name = item.getItemMeta().getDisplayName();
							if(name.contains("Ban")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(!pun.isBanned()) {
										gui.BanReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
									}
								}
							}
							else if(name.contains("Unban")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(pun.isBanned()) {
										pun.setBanTime(0L);
										pun.removeBannedBy(pun.getBanOffense() - 1);
										pun.removeBanReason(pun.getBanOffense() - 1);
										Player offender = Bukkit.getPlayer(target.get(player.getUniqueId()));
										if(offender != null && offender.isOnline()) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unbanned &6" + offender.getName()));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not banned!"));
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
	public void banReason(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							String name = item.getItemMeta().getDisplayName();
							if(target.containsKey(player.getUniqueId())) {
								Punish pun = pManager.get(target.get(player.getUniqueId()));
								if(name.contains("XRay")) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("XRay");
									if(pun.getBanOffense() == 0) {
										pun.setBanTime(System.currentTimeMillis());
									}
									if(pun.getBanOffense() == 1) {
										
									}
									if(pun.getBanOffense() == 2) {
	
									}
									player.closeInventory();
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void muteChoice(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							Punish pun = pManager.get(target.get(player.getUniqueId()));
							String name = item.getItemMeta().getDisplayName();
							if(name.contains("Mute")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(!pun.isMuted()) {
										gui.MuteReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
									}
								}
							}
							else if(name.contains("Unmute")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(pun.isMuted()) {
										pun.setMuteTime(0L);
										pun.removeMutedBy(pun.getMuteOffense() - 1);
										pun.removeMuteReason(pun.getMuteOffense() - 1);
										Player offender = Bukkit.getPlayer(target.get(player.getUniqueId()));
										if(offender != null && offender.isOnline()) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unmuted &6" + offender.getName()));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not muted!"));
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
	public void muteReason(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							String name = item.getItemMeta().getDisplayName();
							if(target.containsKey(player.getUniqueId())) {
								Punish pun = pManager.get(target.get(player.getUniqueId()));
								if(name.contains("XRay")) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("XRay");
									if(pun.getBanOffense() == 0) {
										pun.setBanTime(System.currentTimeMillis());
									}
									if(pun.getBanOffense() == 1) {
										
									}
									if(pun.getBanOffense() == 2) {
	
									}
									player.closeInventory();
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void cancelPunish(InventoryCloseEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if(event.getReason() == Reason.PLAYER) {
				if(event.getView().getTitle().contains("Punish")) {
					target.remove(player.getUniqueId());
					reason.remove(player.getUniqueId());
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cReport cancelled."));
				}
			}
		}
	}
	@EventHandler
	public void spawnerMenu(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(i != null) {
			NBTItem item = new NBTItem(i);
			if(item.hasKey("SpawnerCreate")) {
				gui.SpawnerCreate(player);
			}
			else if(item.hasKey("SpawnerDelete")){
				boolean message = false;
				Location loc = event.getClickedBlock().getLocation();
				for(DFSpawner spawner : spManager.getSpawnerList().values()) {
					if(spawner != null) {
						if(spawner.getLocation().distance(loc) <= 2.5) {
							spManager.remove(spawner.getUUID());
							message = true;
						}
					}
				}
				if(message == true) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner Removed!"));
				}
				else if(message == false) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo spawner found."));
				}
			}
			else if(item.hasKey("SpawnerSee")) {
				int count = 0;
				for(DFSpawner spawner : spManager.getSpawnerList().values()) {
					if(spawner != null) {
						if(player.getLocation().distance(spawner.getLocation()) <= 40.0) {
							player.sendBlockChange(spawner.getLocation(), Material.YELLOW_STAINED_GLASS.createBlockData());
							count++;
							new BukkitRunnable() {
								public void run() {
									Location loc = spawner.getLocation();
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner Location: " + String.format("%.2f", loc.getX()) + " " + String.format("%.2f", loc.getY()) + " " + String.format("%.2f", loc.getZ())));
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1L);
						}
					}
				}
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + count + " &aspawners found."));
			}
		}
	}
	@EventHandler
	public void chooseSlot(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Spawner")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("SpawnerSlot")) {
						spawnerSlot.put(player.getUniqueId(), event.getSlot() - 9);
						gui.SpawnerEntity(player);
					}
					else if(item.hasKey("SpawnerItemDelete")) {
						if(event.getClick() == ClickType.LEFT) {
							spawnerSlot.put(player.getUniqueId(), event.getSlot() - 9);
							gui.SpawnerEntity(player);
						}
						if(event.getClick() == ClickType.RIGHT) {
							player.getInventory().setItem(event.getSlot() - 9, new ItemStack(Material.AIR));
							gui.SpawnerCreate(player);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseEntityType(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Spawner")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("EntityType")) {
						EntityType type = item.getObject("EntityType", EntityType.class);
						spawnerType.put(player.getUniqueId(), type);
						gui.SpawnerTier(player);
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseTier(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Spawner")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Tier")) {
						int tier = item.getInteger("Tier");
						spawnerTier.put(player.getUniqueId(), tier);
						this.finishSpawnerCreation(player);
						player.closeInventory();
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner created!"));
					}
				}
			}
		}
	}

	@EventHandler
	public void placeSpawner(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack i = event.getItem();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getSpawnerMode() == true) {
				event.setCancelled(true);
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("SpawnerPlace")) {
						if(event.getClickedBlock() != null) {
							Location loc = event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5);
							EntityType type = item.getObject("EntityTypeItem", EntityType.class);
							int tier = item.getInteger("Tier");
							new DFSpawner(loc, type, tier);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner placed!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou need to click a block."));
						}
					}
				}
			}
		}
	}
	public void finishSpawnerCreation(Player player) {
		ItemStack item = new ItemStack(Material.EGG);
		ItemMeta meta = item.getItemMeta();
		String ent = spawnerType.get(player.getUniqueId()).toString();
		ent = ent.toLowerCase();
		ent = ent.substring(0, 1).toUpperCase() + ent.substring(1);
		ent = ent.replace("_", " ");
		meta.setDisplayName(new CCT().colorize("&7Spawner"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Type: " + ent));
		lore.add(new CCT().colorize("&7Tier: " + spawnerTier.get(player.getUniqueId())));
		lore.add(new CCT().colorize("&7-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject("EntityTypeItem", spawnerType.get(player.getUniqueId()));
		i.setInteger("Tier", spawnerTier.get(player.getUniqueId()));
		i.setString("SpawnerPlace", "");
		i.setString("SpawnerItemDelete", "");
		player.getInventory().setItem(spawnerSlot.get(player.getUniqueId()), i.getItem());
		spawnerType.remove(player.getUniqueId());
		spawnerTier.remove(player.getUniqueId());
		spawnerSlot.remove(player.getUniqueId());
	}
	
	@EventHandler
	public void lootMenu(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(i != null) {
			NBTItem item = new NBTItem(i);
			if(item.hasKey("LootCreate")) {
				gui.LootCreate(player);
			}
			else if(item.hasKey("LootDelete")){
				boolean message = false;
				Location loc = event.getClickedBlock().getLocation();
				for(LootChest loot : lcManager.getLootList().values()) {
					if(loot != null) {
						if(loot.getLocation().distance(loc) <= 2.5) {
							lcManager.remove(loot.getUUID());
							message = true;
						}
					}
				}
				if(message == true) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest Removed!"));
				}
				else if(message == false) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo Loot Chest found."));
				}
			}
			else if(item.hasKey("LootSee")) {
				int count = 0;
				for(LootChest loot : lcManager.getLootList().values()) {
					if(loot != null) {
						if(player.getLocation().distance(loot.getLocation()) <= 40.0) {
							player.sendBlockChange(loot.getLocation(), Material.YELLOW_STAINED_GLASS.createBlockData());
							count++;
							new BukkitRunnable() {
								public void run() {
									Location loc = loot.getLocation();
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest Location: " + String.format("%.2f", loc.getX()) + " " + String.format("%.2f", loc.getY()) + " " + String.format("%.2f", loc.getZ())));
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1L);
						}
					}
				}
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + count + " &aloot chests found."));
			}
		}
	}
	@EventHandler
	public void chooseLootSlot(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Loot")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("LootSlot")) {
						lootSlot.put(player.getUniqueId(), event.getSlot() - 9);
						gui.LootRadius(player);
					}
					else if(item.hasKey("LootItemDelete")) {
						if(event.getClick() == ClickType.LEFT) {
							lootSlot.put(player.getUniqueId(), event.getSlot() - 9);
							gui.LootRadius(player);
						}
						if(event.getClick() == ClickType.RIGHT) {
							player.getInventory().setItem(event.getSlot() - 9, new ItemStack(Material.AIR));
							gui.LootCreate(player);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseRadiusType(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Loot")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Radius")) {
						int radius = item.getInteger("Radius");
						lootRadius.put(player.getUniqueId(), radius);
						gui.LootTier(player);
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseLootTier(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Loot")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Tier")) {
						int tier = item.getInteger("Tier");
						lootTier.put(player.getUniqueId(), tier);
						this.finishLootCreation(player);
						player.closeInventory();
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest created!"));
					}
				}
			}
		}
	}
	@EventHandler
	public void placeLoot(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getLootMode() == true) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("LootPlace")) {
						event.setCancelled(false);
						Location loc = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
						int radius = item.getInteger("RadiusItem");
						int tier = item.getInteger("Tier");
						new LootChest(loc, tier, radius);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest placed!"));
					}
				}
			}
		}
	}
	public void finishLootCreation(Player player) {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&7Loot Chest"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Radius: " + lootRadius.get(player.getUniqueId())));
		lore.add(new CCT().colorize("&7Tier: " + lootTier.get(player.getUniqueId())));
		lore.add(new CCT().colorize("&7-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setInteger("RadiusItem", lootRadius.get(player.getUniqueId()));
		i.setInteger("Tier", lootTier.get(player.getUniqueId()));
		i.setString("LootPlace", "");
		i.setString("LootItemDelete", "");
		player.getInventory().setItem(lootSlot.get(player.getUniqueId()), i.getItem());
		lootRadius.remove(player.getUniqueId());
		lootTier.remove(player.getUniqueId());
		lootSlot.remove(player.getUniqueId());
	}
	@EventHandler
	public void cancelClick(InventoryClickEvent event) {
		InventoryView inv = event.getView();
		if(inv.getTitle().contains("Examine") || inv.getTitle().contains("Info") || inv.getTitle().contains("Teleport") || inv.getTitle().contains("Punish")) {
			event.setCancelled(true);
		}
	}
	
}
