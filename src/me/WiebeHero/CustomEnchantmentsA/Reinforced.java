package me.WiebeHero.CustomEnchantmentsA;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import Skills.SkillJoin;
import me.WiebeHero.CustomMethods.MethodHealth;
import me.WiebeHero.CustomMethods.MethodItemStack;

public class Reinforced implements Listener{
	MethodHealth he = new MethodHealth();
	MethodItemStack it = new MethodItemStack(); 
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void armorSwitch1(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		ItemStack armorNew = event.getNewItem();
		ItemStack armorOld = event.getOldItem();
		if(it.loreContains(armorNew, "Reinforced") || it.loreContains(armorOld, "Reinforced")) {
			he.updateHealth(player);
		}
	}
}
