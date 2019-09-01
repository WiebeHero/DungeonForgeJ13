package me.WiebeHero.CustomEnchantmentsA;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomMethods.MethodItemStack;
import me.WiebeHero.CustomMethods.NewAttribute;
import me.WiebeHero.Skills.EffectSkills;

public class Reinforced implements Listener{
	MethodItemStack it = new MethodItemStack(); 
	EffectSkills sk = new EffectSkills();
	NewAttribute at = new NewAttribute();
	@EventHandler
	public void armorSwitch1(PlayerArmorChangeEvent event) {
		ItemStack armorNew = event.getNewItem();
		if(armorNew != null) {
			if(armorNew.hasItemMeta()) {
				if(armorNew.getItemMeta().hasLore()) {
					String check = "";
					for(String s1 : armorNew.getItemMeta().getLore()){
						if(s1.contains(ChatColor.stripColor("Reinforced"))) {
							check = ChatColor.stripColor(s1);
						}
					}
					if(check.contains("Reinforced")){
						check = check.replaceAll("[^\\d.]", "");
						int level = Integer.parseInt(check);
						ItemMeta meta = armorNew.getItemMeta();
						if(meta.hasAttributeModifiers()) {
							if(meta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH) != null) {
								meta.removeAttributeModifier(Attribute.GENERIC_MAX_HEALTH);
							}
						}
						EquipmentSlot slot = EquipmentSlot.HEAD;
						if(armorNew.getType().toString().contains("HELMET")) {
							slot = EquipmentSlot.HEAD;
						}
						else if(armorNew.getType().toString().contains("CHESTPLATE")) {
							slot = EquipmentSlot.CHEST;
						}
						else if(armorNew.getType().toString().contains("LEGGINGS")) {
							slot = EquipmentSlot.LEGS;
						}
						else if(armorNew.getType().toString().contains("BOOTS")) {
							slot = EquipmentSlot.FEET;
						}
						meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, at.newModifier(Attribute.GENERIC_MAX_HEALTH, slot, 0.025 * level, Operation.ADD_SCALAR));
						armorNew.setItemMeta(meta);
					}
				}
			}
		}
	}
}
