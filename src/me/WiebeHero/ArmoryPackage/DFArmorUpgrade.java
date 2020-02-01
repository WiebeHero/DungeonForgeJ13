package me.WiebeHero.ArmoryPackage;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.EffectSkills;

public class DFArmorUpgrade implements Listener{
	public NovisEnchantmentGetting enchant = new NovisEnchantmentGetting();
	public String colorize(String msg)
    {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&')
                coloredMsg += '§';
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }
	EffectSkills sk = new EffectSkills();
	@EventHandler
	public void weapons(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		EntityDamageEvent ede = victim.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if(event.getEntity().getKiller() instanceof Player && (dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE)){
			Player damager = (Player) event.getEntity().getKiller();
			ItemStack items[] = damager.getInventory().getArmorContents();
			for(ItemStack i : items) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Upgradeable")) {
						if(item.hasKey("ArmorKey")) {
							int xp = item.getInteger("XP");
				    		int totalxpearned = 0;
				    		if(!(victim instanceof Player)) {
								Entity ent = NBTInjector.patchEntity(victim);
								NBTCompound comp = NBTInjector.getNbtData(ent);
								if(comp.hasKey("SpawnerUUID")) {
									int level = comp.getInteger("Level");
									int tier = comp.getInteger("Tier");
									if(tier == 0) {
										totalxpearned = xp + 1;
									}
									else {
										totalxpearned = xp + 5 + level;
									}
									//-----------------------------------------------------------------------------------------------------------------------------------------
									//XP Adding
									//-----------------------------------------------------------------------------------------------------------------------------------------
									
								}
								else {
									totalxpearned = xp + 1;
								}
							}
							else {
								DFPlayer dfVictim = new DFPlayer().getPlayer(victim);
								int level = dfVictim.getLevel();
								totalxpearned = level + xp;
							}
							int itemLevel = item.getInteger("Level");
		    				if(itemLevel != 15) {
			    				if(i.getType().toString().contains("HELMET")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.HEAD);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
			    				else if(i.getType().toString().contains("CHESTPLATE")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.CHEST);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
			    				else if(i.getType().toString().contains("LEGGINGS")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.LEGS);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
			    				else if(i.getType().toString().contains("BOOTS")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.FEET);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
					    	}
						}
				    }
				}
			}
		}
	}
}

