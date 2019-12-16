package me.WiebeHero.Moderation;

import java.util.HashMap;
import java.util.UUID;

public class StaffManager {
	
	public HashMap<UUID, Staff> staff;
	
	public StaffManager() {
		this.staff = new HashMap<UUID, Staff>();
	}
	public Staff get(UUID uuid) {
		return this.staff.get(uuid);
	}
	public void add(UUID uuid, Staff s) {
		this.staff.put(uuid, s);
	}
	public void remove(UUID uuid) {
		this.staff.remove(uuid);
	}
	public boolean contains(UUID uuid) {
		if(staff.containsKey(uuid)) {
			return true;
		}
		return false;
	}
}
