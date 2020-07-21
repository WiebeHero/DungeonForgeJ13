package me.WiebeHero.FlyTicket;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class FlyTicketEvents implements Listener{
	
	private FlyTicketManager flyTicketManager;
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	private ArrayList<UUID> cooldown;
	
	public FlyTicketEvents(FlyTicketManager flyTicketManager, DFFactionManager facManager, DFFactionPlayerManager facPlayerManager) {
		this.flyTicketManager = flyTicketManager;
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
		this.cooldown = new ArrayList<UUID>();
	}
	
	@EventHandler
	public void useFactionFlyTicket(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && item.getType() != Material.AIR) {
				NBTItem i = new NBTItem(item);
				if(i.hasKey("Fly Ticket")) {
					if(!this.flyTicketManager.containsFlyTicket(player.getUniqueId())) {
						DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
						if(facPlayer.getFactionId() != null) {
							long time = i.getLong("Duration");
							FlyTicket ticket = new FlyTicket(player.getUniqueId(), System.currentTimeMillis() + time);
							this.flyTicketManager.addFlyTicket(ticket);
							new BukkitRunnable() {
								public void run() {
									if(flyTicketManager.containsFlyTicket(player.getUniqueId())) {
										flyTicketManager.removeFlyTicket(player.getUniqueId());
									}
								}
							}.runTaskLaterAsynchronously(CustomEnchantments.getInstance(), time / 50);
							item.setAmount(item.getAmount() - 1);
							player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have activated a personal faction fly ticket!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't activate this ticket because you are not in a faction!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou already have a fly ticket active!"));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void attemptFly(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(!this.cooldown.contains(player.getUniqueId())) {
			this.cooldown.add(player.getUniqueId());
			new BukkitRunnable() {
				public void run() {
					if(cooldown.contains(player.getUniqueId())) {
						cooldown.remove(player.getUniqueId());
					}
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 5L);
			DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
			if(facPlayer.getFactionId() != null) {
				if(this.flyTicketManager.containsFlyTicket(player.getUniqueId())) {
					FlyTicket ticket = this.flyTicketManager.getFlyTicket(player.getUniqueId());
					DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
					if(faction.isInChunk(player)) {
						if(ticket.getFlyTime() > System.currentTimeMillis()) {
							player.setAllowFlight(true);
						}
						else {
							player.setFlying(false);
							player.setAllowFlight(false);
						}
					}
					else {
						player.setFlying(false);
						player.setAllowFlight(false);
					}
				}
			}
		}
	}
}
