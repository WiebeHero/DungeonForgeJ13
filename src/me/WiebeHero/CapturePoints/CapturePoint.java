package me.WiebeHero.CapturePoints;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import me.WiebeHero.CustomEnchantments.CCT;

public class CapturePoint {
	
	private double captureRadius;
	private UUID capturedFac;
	private UUID captureId;
	private double xpMultiplier;
	private Location captureLocation;
	private int captureProgress;
	private BossBar progressBar;
	
	public CapturePoint(UUID captureId, UUID capturedFac, Location loc, int progress, double captureRadius, double xpMultiplier) {
		this.captureId = captureId;
		this.capturedFac = capturedFac;
		this.captureRadius = captureRadius;
		this.xpMultiplier = xpMultiplier;
		this.captureLocation = loc;
		this.captureProgress = progress;
		this.progressBar = Bukkit.getServer().createBossBar(new CCT().colorize("&aCapture Point"), BarColor.GREEN, BarStyle.SEGMENTED_10);
		this.progressBar.setProgress((double)this.captureProgress / 10.00);
	}
	
	public CapturePoint(UUID capturedFac, Location loc, double captureRadius, double xpMultiplier) {
		this.captureId = UUID.randomUUID();
		this.capturedFac = capturedFac;
		this.captureRadius = captureRadius;
		this.xpMultiplier = xpMultiplier;
		this.captureLocation = loc;
		this.progressBar = Bukkit.getServer().createBossBar(new CCT().colorize("&aCapture Point"), BarColor.GREEN, BarStyle.SEGMENTED_10);
	}
	
	public CapturePoint(Location loc, UUID capturedFac, double captureRadius) {
		this.captureId = UUID.randomUUID();
		this.captureLocation = loc;
		this.capturedFac = capturedFac;
		this.captureRadius = captureRadius;
		this.progressBar = Bukkit.getServer().createBossBar(new CCT().colorize("&aCapture Point"), BarColor.GREEN, BarStyle.SEGMENTED_10);
	}
	
	public CapturePoint(Location loc, double captureRadius, double xpMultiplier) {
		this.captureId = UUID.randomUUID();
		this.captureLocation = loc;
		this.captureRadius = captureRadius;
		this.xpMultiplier = xpMultiplier;
		this.progressBar = Bukkit.getServer().createBossBar(new CCT().colorize("&aCapture Point"), BarColor.GREEN, BarStyle.SEGMENTED_10);
	}
	
	public CapturePoint(double captureRadius) {
		this.captureRadius = captureRadius;
	}
	
	public void setCaptureRadius(double radius) {
		this.captureRadius = radius;
	}
	
	public double getCaptureRadius() {
		return this.captureRadius;
	}
	
	public void setCapturedId(UUID uuid) {
		this.capturedFac = uuid;
	}
	
	public UUID getCapturedId() {
		return this.capturedFac;
	}
	
	public void setXPMultiplier(double xpMultiplier) {
		this.xpMultiplier = xpMultiplier;
	}
	
	public double getXPMultiplier() {
		return this.xpMultiplier;
	}
	
	public UUID getUniqueId() {
		return this.captureId;
	}
	
	public void setCaptureLocation(Location loc) {
		this.captureLocation = loc;
	}
	
	public Location getCaptureLocation() {
		return this.captureLocation;
	}
	
	public void setCaptureProgress(int progress) {
		this.captureProgress = progress;
		this.progressBar.setProgress((double)this.captureProgress / 10.00);
	}
	
	public int getCaptureProgress() {
		return this.captureProgress;
	}
	
	public boolean isCaptured() {
		return this.capturedFac != null;
	}
	
	public BossBar getBossBar() {
		return this.progressBar;
	}
}
