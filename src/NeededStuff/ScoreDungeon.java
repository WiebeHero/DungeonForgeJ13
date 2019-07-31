package NeededStuff;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFactions;

public class ScoreDungeon implements Listener{
	public HashMap<UUID, Chunk> list = new HashMap<UUID, Chunk>();
	DFFactions fac = new DFFactions();
	@EventHandler
    public void playerJoin(PlayerMoveEvent event) { 
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		if(list.containsKey(player.getUniqueId())) {
			if(!list.get(uuid).equals(player.getChunk())) {
				CustomEnchantments.getInstance().registerNameTag(player);
				list.put(uuid, player.getChunk());
			}
		}
		else {
			list.put(uuid, player.getChunk());
		}
	}
}
