package me.WiebeHero.CustomEnchantmentsA;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomMethods.MethodItemStack;
import me.WiebeHero.CustomMethods.MethodMovementSpeed;

public class Lightweight implements Listener{
	MethodMovementSpeed he = new MethodMovementSpeed();
	MethodItemStack it = new MethodItemStack(); 
	@EventHandler
	public void armorSwitch1(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		ItemStack armorNew = event.getNewItem();
		ItemStack armorOld = event.getOldItem();
		if(it.loreContains(armorNew, "Lightweight")) {
			int level = it.getLevelEnchant(armorNew, "Lightweight");
			double max = he.getSpeed(player) + 0.005 * level;
			he.setSpeed(player, max);
		}
		if(it.loreContains(armorOld, "Lightweight")) {
			int level = it.getLevelEnchant(armorNew, "Lightweight");
			double max = he.getSpeed(player) - 0.005 * level;
			he.setSpeed(player, max);
		}
	}
}