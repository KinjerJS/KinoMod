package fr.kinjer.kinomod.entity.render;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.projectile.EntityBismuthBall;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBismuthBall extends RenderArrow<EntityBismuthBall> {
	
	public RenderBismuthBall(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBismuthBall entity){
		return new ResourceLocation(KinoMod.MODID, "textures/entity/projectile/bismuth_ball.png");
	}	
}
