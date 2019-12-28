package me.WiebeHero.APIs;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleAPI {
	public void sphere(Particle particle, Location loc, double r, int amount) {
		for (double i = 0; i <= Math.PI; i += Math.PI / amount) {
		   double radius = Math.sin(i) * r;
		   double y = Math.cos(i) * r;
		   for (double a = 0; a < Math.PI * 2; a+= Math.PI / amount) {
		      double x = Math.cos(a) * radius;
		      double z = Math.sin(a) * radius;
		      loc.add(x, y, z);
		      loc.getWorld().spawnParticle(particle, loc, 1, 0.0, 0.0, 0.0, 0.0);
		      loc.subtract(x, y, z);
		   }
		}
	}
}
