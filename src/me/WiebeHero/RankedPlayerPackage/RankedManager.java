package me.WiebeHero.RankedPlayerPackage;

import java.util.HashMap;
import java.util.UUID;

public class RankedManager {
	public HashMap<UUID, RankedPlayer> ranked;
	public RankedManager() {
		this.ranked = new HashMap<UUID, RankedPlayer>();
	}
	public void add(UUID uuid, RankedPlayer p) {
		this.ranked.put(uuid, p);
	}
	public void remove(UUID uuid) {
		if(this.contains(uuid)) {
			this.ranked.remove(uuid);
		}
	}
	public boolean contains(UUID uuid) {
		if(this.ranked.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public RankedPlayer getRankedPlayer(UUID uuid) {
		if(this.contains(uuid)) {
			return this.ranked.get(uuid);
		}
		return null;
	}
}
