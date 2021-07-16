package fr.kinjer.kinomod.config;

import net.minecraft.entity.Entity;

public class VectorFromEntity {
	
	public double x;
    public double y;
    public double z;

    public VectorFromEntity() {
    }
    
    public VectorFromEntity(double d, double d1, double d2) {
        x = d;
        y = d1;
        z = d2;
    }
    
	public static VectorFromEntity fromEntity(Entity e) {
	        return new VectorFromEntity(e.posX, e.posY, e.posZ);
	    }
}