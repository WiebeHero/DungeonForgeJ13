package me.WiebeHero.CustomEnchantmentsB;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class GrapplingHook implements Listener{
	public ArrayList<Player> cooldown = new ArrayList<Player>();
	public HashMap<Player, Boolean> prepared = new HashMap<Player, Boolean>();
	public HashMap<Player, Vector> vector = new HashMap<Player, Vector>();
	public HashMap<Player, Integer> levelBow = new HashMap<Player, Integer>();
	@EventHandler
	public void grapplingHookPrepare(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						String check = "";
						for(String s1 : player.getInventory().getItemInMainHand().getItemMeta().getLore()){
							if(s1.contains(ChatColor.stripColor("Grappling Hook"))) {
								check = ChatColor.stripColor(s1);
							}
						}
						if(check.contains("Grappling Hook")){
							check = check.replaceAll("[^\\d.]", "");
							int level = Integer.parseInt(check) - 1;
							if(!cooldown.contains(player)) {
								prepared.put(player, true);
								cooldown.add(player);
								levelBow.put(player, level);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void grapplingHookRegister(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(prepared.containsKey(player)) {
				if(prepared.get(player) == true) {
					vector.put(player, player.getLocation().getDirection());
				}
			}
		}
	}
	@EventHandler
	public void grapplingHookFire(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if(event.getEntity().getShooter() instanceof Player) {
				Player player = (Player) event.getEntity().getShooter();
				if(prepared.containsKey(player)) {
					if(prepared.get(player) == true) {
						player.setVelocity(new Vector(0, 0.5, 0));
						new BukkitRunnable() {
							public void run() {
								player.setVelocity(vector.get(player).normalize().multiply(player.getLocation().distance(arrow.getLocation()) / 15));
								vector.remove(player);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 3L);
						prepared.put(player, false);
						
						new BukkitRunnable() {
							public void run() {
								cooldown.remove(player);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1200 - 100 * levelBow.get(player));
					}
				}
			}
		}
	}
}
