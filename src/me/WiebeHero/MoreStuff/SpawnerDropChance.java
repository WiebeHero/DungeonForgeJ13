package me.WiebeHero.MoreStuff;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;

public class SpawnerDropChance {
	@EventHandler
	public void dropSpawner(BlockExplodeEvent event) {
		ArrayList<Block> blockList = new ArrayList<Block>(event.blockList());
		for(Block b : blockList) {
			if(b.getType() == Material.SPAWNER) {
				CreatureSpawner spawner = (CreatureSpawner) b;
				
			}
		}
	}
}
