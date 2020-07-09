package me.WiebeHero.Party;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.WiebeHero.CustomEnchantments.CCT;

public class PartyEvents implements Listener{
	
	private PartyManager pManager;
	
	public PartyEvents(PartyManager pManager) {
		this.pManager = pManager;
	}
	
	@EventHandler
	public void damageFactionMember(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Player victim = (Player) event.getEntity();
			Party party = this.pManager.getPartyByPlayer(damager.getUniqueId());
			if(party != null) {
				if(party.containsPartyMember(victim.getUniqueId())) {
					event.setCancelled(true);
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't harm your own party members!"));
				}
			}
		}
		if(event.getDamager() instanceof Projectile  && event.getEntity() instanceof Player) {
			Projectile arrow = (Projectile) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				Player damager = (Player) arrow.getShooter();
				Player victim = (Player) event.getEntity();
				Party party = this.pManager.getPartyByPlayer(damager.getUniqueId());
				if(party != null) {
					if(party.containsPartyMember(victim.getUniqueId())) {
						event.setCancelled(true);
						damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't harm your own party members!"));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void quitParty(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		Party party = this.pManager.getPartyByPlayer(uuid);
		if(party != null) {
			UUID lead = party.getLeader();
			if(lead != null) {
				if(uuid.equals(lead)) {
					if(party.getPartyMembers().size() == 1) {
						this.pManager.removeParty(party.getUniqueId());
					}
					else {
						party.removePartyMember(uuid);
						UUID other = party.getPartyMembers().keySet().iterator().next();
						Player otherPlayer = Bukkit.getPlayer(other);
						party.addPartyMember(other, 3);
						for(UUID others : party.getPartyMembers().keySet()) {
							Player othersPlayer = Bukkit.getPlayer(others);
							if(!others.equals(other)) {
								othersPlayer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cleft the party! &6" + otherPlayer.getName() + " &cis now the leader of the party!"));
							}
							else {
								othersPlayer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are now the leader of the party because &6" + player.getName() + " &cleft the server!"));
							}
						}
					}
				}
				else {
					party.removePartyMember(uuid);
					for(UUID other : party.getPartyMembers().keySet()) {
						Player p = Bukkit.getPlayer(other);
						p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas left the party!"));
					}
				}
			}
		}
	}
}
