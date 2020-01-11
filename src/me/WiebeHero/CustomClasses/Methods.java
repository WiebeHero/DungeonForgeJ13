package me.WiebeHero.CustomClasses;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import net.minecraft.server.v1_13_R2.DimensionManager;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;

public class Methods {
	public Player convertOfflinePlayer(String name) {
		UUID uuid = null;
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if(p.getName().equals(name)) {
				uuid = p.getUniqueId();
			}
		}
		if(uuid != null) {
			Location location = new Location(Bukkit.getWorld("DFWarzone-1"), 0, 0, 0, 0, 0);
			Player target = null;
			GameProfile profile = new GameProfile(uuid, name);
			MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
			EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(DimensionManager.OVERWORLD), profile, new PlayerInteractManager(server.getWorldServer(DimensionManager.OVERWORLD)));
			entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
			entity.world = ((CraftWorld) location.getWorld()).getHandle();
			target = entity == null ? null : (Player) entity.getBukkitEntity();
			if (target != null) {
			    target.loadData();
			    return target;
			}
			return target;
		}
		else {
			return null;
		}
	}
	public Player convertOfflinePlayer(UUID uuid) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		if(p != null) {
			Location location = new Location(Bukkit.getWorld("DFWarzone-1"), 0, 0, 0, 0, 0);
			Player target = null;
			GameProfile profile = new GameProfile(uuid, p.getName());
			MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
			EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(DimensionManager.OVERWORLD), profile, new PlayerInteractManager(server.getWorldServer(DimensionManager.OVERWORLD)));
			entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
			entity.world = ((CraftWorld) location.getWorld()).getHandle();
			target = entity == null ? null : (Player) entity.getBukkitEntity();
			if (target != null) {
			    target.loadData();
			    return target;
			}
			return target;
		}
		else {
			return null;
		}
	}
}
