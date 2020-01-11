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
import me.WiebeHero.Skills.DFPlayer;

public class DFShieldUpgrade implements Listener{
	
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
	@EventHandler
	public void weapons(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		EntityDamageEvent ede = victim.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if(event.getEntity().getKiller() instanceof Player && (dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE)){
			Player damager = (Player) event.getEntity().getKiller();
			ItemStack i = damager.getInventory().getItemInOffHand();
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Upgradeable")) {
					if(item.hasKey("ShieldKey")) {
						int xp = item.getInteger("XP");
			    		int totalxpearned = 0;
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
								if(tier == 0) {
									i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
								}
								if(tier == 1) {
									i1 = new Random().nextInt(50) + 50 + 2 * levelMob;
								}
								else if(tier == 2) {
									i1 = new Random().nextInt(70) + 70 + 3 * levelMob;
								}
								else if(tier == 3) {
									i1 = new Random().nextInt(90) + 90 + 4 * levelMob;
								}
								else if(tier == 4) {
									i1 = new Random().nextInt(110) + 110 + 5 * levelMob;
								}
								else if(tier == 5) {
									i1 = new Random().nextInt(130) + 130 + 6 * levelMob;
								}
								else {
									i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
								}
								totalxpearned = i1 + xp;
							}
						}
						else {
							DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
							int level = dfPlayer.getLevel();
							int i1 = 0;
							i1 = new Random().nextInt(7 * level) + 4 * level;
							totalxpearned = i1 + xp;
						}
						int itemLevel = item.getInteger("Level");
						if(itemLevel != 15) {
							DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.OFF_HAND);
							Bukkit.getPluginManager().callEvent(e);
				    	}
					}
			    }
			}
		}
	}
}

