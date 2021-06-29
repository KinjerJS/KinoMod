package fr.kinjer.kinomod.entity.render;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.EntityCentaur;
import fr.kinjer.kinomod.entity.model.ModelCentaur;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCentaur extends RenderLiving<EntityCentaur>{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(KinoMod.MODID, "textures/entity/centaur.png");

	public RenderCentaur(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelCentaur(), 0.5F);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCentaur entity) {
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityCentaur entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
