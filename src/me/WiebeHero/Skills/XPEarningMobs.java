package me.WiebeHero.Skills;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class XPEarningMobs implements Listener{
	private DFScoreboard board;
	private DFPlayerManager dfManager;
	public XPEarningMobs(DFPlayerManager manager, DFScoreboard board) {
		this.dfManager = manager;
		this.board = board;
	}
	@EventHandler
	public void xpEarnMobs10(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player player = event.getEntity().getKiller();
				LivingEntity victim = event.getEntity();
				int finalXP = 0;
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
							i1 = new Random().nextInt(3) + 3 + 3 * levelMob;
						}
						if(tier == 1) {
							i1 = new Random().nextInt(30) + 90 + 1 * levelMob;
						}
						else if(tier == 2) {
							i1 = new Random().nextInt(45) + 110 + 2 * levelMob;
						}
						else if(tier == 3) {
							i1 = new Random().nextInt(60) + 135 + 3 * levelMob;
						}
						else if(tier == 4) {
							i1 = new Random().nextInt(75) + 165 + 4 * levelMob;
						}
						else if(tier == 5) {
							i1 = new Random().nextInt(90) + 200 + 5 * levelMob;
						}
						else {
							i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
						}
						finalXP = i1;
					}
				}
				else {
					DFPlayer df = dfManager.getEntity(victim);
					int otherLevel = df.getLevel();
					int i1 = 0;
					i1 = new Random().nextInt(7 * otherLevel) + 4 * otherLevel;
					finalXP = i1;
				}
				DFPlayerXpGainEvent ev = new DFPlayerXpGainEvent(player, finalXP, this.dfManager, this.board);
				Bukkit.getServer().getPluginManager().callEvent(ev);
			}
		}
	}
	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		event.setExpToDrop(0);
	}
	@EventHandler
	public void entityDie(EntityDeathEvent event) {
		event.setDroppedExp(0);
	}
}
