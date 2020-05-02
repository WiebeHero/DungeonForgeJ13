package me.WiebeHero.Consumables;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public abstract class CommandFile implements Listener{
	void extraEvent() {};
	void activateConsumable(Player damager) {};
	void activateConsumable(Player damager, int level) {};
	void activateConsumable(Player damager, PlayerItemConsumeEvent event) {};
	void activateConsumable(Player damager, PlayerInteractEvent event) {};
	void registerRecipe() {};
	void registerExtraRecipe() {};
	void registerExtraExtraRecipe() {};
}
