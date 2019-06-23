package Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class AttackSpeed implements Listener{
	HashMap<UUID, Double> speed = new HashMap<UUID, Double>();
	HashMap<UUID, Long> dur = new HashMap<UUID, Long>();
	SkillJoin join = new SkillJoin();
	ClassPride p = new ClassPride();
	ClassGreed g = new ClassGreed();
	public void attackSpeedRun(Player p) {
		new BukkitRunnable() {
			public void run() {
				ItemStack item = p.getInventory().getItemInMainHand();
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							if(item.getItemMeta().getLore().toString().contains("Attack Speed")) {
								String check1 = "";
								String check2 = "";
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Attack Speed")) {
										check1 = ChatColor.stripColor(s);
									}
									else if(s.contains("Attack Damage")) {
										check2 = ChatColor.stripColor(s);
									}
								}
								check1 = check1.replaceAll("[^\\d.]", "");
								double attackSpeed = Double.parseDouble(check1);
								check2 = check2.replaceAll("[^\\d.]", "");
								double attackDamage = Double.parseDouble(check2);
								double mult = join.getASCalList().get(p.getUniqueId());
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (mult + join.getASExtraList().get(p.getUniqueId())));
							}
							else {
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							}
						}
						else {
							p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
							p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
						}
					}
					else {
						p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
						p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					}
				}
				else {
					p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void attackSpeedItemChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player);
	}
	@EventHandler
	public void attackSpeedItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player);
	}
	@EventHandler
	public void attackSpeedItemPickup(@SuppressWarnings("deprecation") PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player);
	}
	@EventHandler
	public void attackSpeedInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			attackSpeedRun(player);
		}
	}
	@EventHandler
	public void attackSpeedSwitchHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player);
	}
}
