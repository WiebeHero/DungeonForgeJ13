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
		if(it.loreContains(armorNew, "Reinforced")) {
			int level = it.getLevelEnchant(armorNew, "Reinforced");
			double calc = join.getHHCalList().get(player.getUniqueId()) + 0.625 * level;
			join.getHHCalList().put(player.getUniqueId(), calc);
		}
		if(it.loreContains(armorOld, "Reinforced")) {
			int level = it.getLevelEnchant(armorNew, "Reinforced");
			double calc = join.getHHCalList().get(player.getUniqueId()) - 0.625 * level;
			if(calc < 20.00) {
				join.getHHCalList().put(player.getUniqueId(), 20.00);
			}
			else {
				join.getHHCalList().put(player.getUniqueId(), calc);
			}
		}
	}
}
