package NeededStuff;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFactions;

public class ScoreDungeon implements Listener{
	public ArrayList<String> list = new ArrayList<String>();
	DFFactions fac = new DFFactions();
	@EventHandler
    public void playerJoin(PlayerMoveEvent event) { 
		Player player = event.getPlayer();
		if(!list.contains(player.getName())) {
			list.add(player.getName());
			CustomEnchantments.getInstance().registerNameTag(player);
			new BukkitRunnable() {
				@Override
				public void run() {
					list.remove(player.getName());
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 30L);
		}
	}
}
