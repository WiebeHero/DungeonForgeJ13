package me.WiebeHero.APIs;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

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
	public ArrayList<Location> getSemiCircle(Location center, double radius, int amount, boolean clock)
    {
        World world = center.getWorld();
        double increment = Math.PI / amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0; i < amount; i++)
        {
            double angle = i * increment * (clock ? -1 : 1);
            double x = center.getX() + (radius * Math.cos((angle + Math.toRadians(center.getYaw() + (clock ? 180 : 0)))));
            double z = center.getZ() + (radius * Math.sin((angle + Math.toRadians(center.getYaw() + (clock ? 180 : 0)))));
            Location temp = new Location(world, x, center.getY(), z);
            Vector vec = this.rotateVector(center.getDirection(), 0, (float)angle);
            temp.add(vec);
            locations.add(temp);
        }
        return locations;
    }
	public final Vector rotateVector(Vector v, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = v.getX();
        initialY = v.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = v.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector(x, y, z);
	}
	public Location getLocationRelative(Location loc, double distance, int direction) {
		//The location is the location to get the new loc from
		//Distance is the distance away from the player
		//Direction is the value of do you want it to be right/left = 0 or front/back = 1
		final float newZ = (float)(loc.getZ() + (distance * Math.sin(Math.toRadians(loc.getYaw() + 90 * direction))));
		 
		final float newX = (float)(loc.getX() + (distance * Math.cos(Math.toRadians(loc.getYaw() + 90 * direction))));
		
		Location newLoc = loc.clone();
		newLoc.set(newX, loc.getY(), newZ);
		return newLoc;
	}
//	public void slash(Location loc, Particle particle, DustOptions op, int amount) {
//		float radius = 1.5F;
//		Location l = loc.clone();
//		for(double i = 45.00; i <= 90.00; i += 1.50) {
//			double x = (radius * Math.sin(i / 1.96));
//			double z = (radius * Math.cos(i));
//			l.add(x, 0, z);
//			l.getWorld().spawnParticle(particle, l, 1, op);
//			l.subtract(x, 0, z);
//		}
//	}
//	public void slash(Particle particle, Location loc, double length, int amount) {
//		for (double i = 0; i <= Math.PI; i += Math.PI / amount) {
//			double radius = Math.sin(i) * length;
//			for (double a = 0; a < Math.PI * 2; a+= Math.PI / amount) {
//		      double x = Math.cos(a) * radius;
//		      double z = Math.sin(a) * radius;
//		      loc.add(x, 0, z);
//		      loc.getWorld().spawnParticle(particle, loc, 1, 0.0, 0.0, 0.0, 0.0);
//		      loc.subtract(x, 0, z);
//		   }
//		}
//	}
//	public void slash(Location loc, Particle particle, DustOptions op, int amount) {
//		for (int degree = 0; degree < 180; degree++) {
//		    double radians = Math.toRadians(degree);
//		    double x = Math.cos(radians);
//		    double z = Math.sin(radians);
//		    loc.add(x,0,z);
//		    loc.getWorld().spawnParticle(Particle.REDSTONE, loc, amount, op);
//		    loc.subtract(x,0,z);
//		}
//	}
//	public void bezierCurveDisplay(Location l, Particle particle, DustOptions op)
//    {
//        Location p0 = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()).add(l.getDirection());
//        Vector otherSide = l.getDirection();
//        double yangle = Math.toRadians(60); // note that here we do have to convert to radians.
//        double yAxisCos = Math.cos(-yangle); // getting the cos value for the yaw.
//        double yAxisSin = Math.sin(-yangle); // getting the sin value for the yaw.
//        otherSide = this.rotateAroundAxisY(otherSide, yAxisCos, yAxisSin);
//        Location p1 = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
//        p1.setDirection(otherSide);
//        p1.add(otherSide);
//        Location p2 = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
//        otherSide = l.getDirection();
//        yangle = Math.toRadians(120); // note that here we do have to convert to radians.
//        yAxisCos = Math.cos(-yangle); // getting the cos value for the yaw.
//        yAxisSin = Math.sin(-yangle); // getting the sin value for the yaw.
//        otherSide = this.rotateAroundAxisY(otherSide, yAxisCos, yAxisSin);
//        p2.setDirection(otherSide);
//        p2.add(otherSide);
//        //these 3 locations were debugged and are valid.
//        Bukkit.broadcastMessage(p0.toString());
//        Bukkit.broadcastMessage(p1.toString());
//        Bukkit.broadcastMessage(p2.toString());
//        List<Location> curve = bezierCurve(150, p0, p1, p2);
//        for(int i = 0 ; i < curve.size(); i++)
//        {
//            Location point = curve.get(i);
//            point.getWorld().spawnParticle(particle, point, 1, op);
//        }
//    }
// 
//	public Vector rotateAroundAxisY(Vector v, double cos, double sin) {
//        double x = v.getX() * cos + v.getZ() * sin;
//        double z = v.getX() * -sin + v.getZ() * cos;
//        return v.setX(x).setZ(z);
//    }
//	
//    public Location bezierPoint(float t, Location p0, Location p1, Location p2)
//    {
//        float a = (1-t)*(1-t);
//        float b = 2*(1-t)*t;
//        float c = t*t;
// 
//        Location p = p0.clone().multiply(a).add(p1.clone().multiply(b)).add(p2.clone().multiply(c));
//        //System.out.println(p);
//        return p;
//    }
// 
//    public List<Location> bezierCurve(int segmentCount, Location p0, Location p1, Location p2)
//    {
//        List<Location> points = new ArrayList<Location>();
//        for(int i = 1; i < segmentCount; i++)
//        {
//            float t = i / (float) segmentCount;
//            points.add(bezierPoint(t, p0, p1, p2));
//        }
//        return points;
//    }
}
