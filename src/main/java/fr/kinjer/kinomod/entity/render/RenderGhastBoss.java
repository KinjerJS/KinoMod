package fr.kinjer.kinomod.entity.render;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.EntityGhastBoss;
import fr.kinjer.kinomod.entity.model.ModelGhastBoss;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGhastBoss extends RenderLiving<EntityGhastBoss>{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(KinoMod.MODID, "textures/entity/ghast_boss.png");

	public RenderGhastBoss(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelGhastBoss(), 0.5F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityGhastBoss entity) {
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityGhastBoss entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
