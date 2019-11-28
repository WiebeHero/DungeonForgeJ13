package me.WiebeHero.Consumables;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public abstract class CommandFile implements Listener{
	void extraEvent() {};
	void activateConsumable(LivingEntity damager) {};
	void activateConsumable(LivingEntity damager, int level) {};
	void activateConsumable(LivingEntity damager, PlayerItemConsumeEvent event) {};
	void activateConsumable(LivingEntity damager, PlayerInteractEvent event) {};
	void registerRecipe() {};
	void registerExtraRecipe() {};
	void registerExtraExtraRecipe() {};
}
