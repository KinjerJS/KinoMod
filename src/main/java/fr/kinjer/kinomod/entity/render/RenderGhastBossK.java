package fr.kinjer.kinomod.entity.render;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.EntityGhastBossK;
import fr.kinjer.kinomod.entity.model.ModelGhastBoss;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderGhastBossK extends RenderLiving<EntityGhastBossK> {
	private static final ResourceLocation GHAST_BOSS_TEXTURES = new ResourceLocation(KinoMod.MODID,
			"textures/entity/ghast_boss_kinium.png");
	private static final ResourceLocation GHAST_BOSS_SHOOTING_TEXTURES = new ResourceLocation(KinoMod.MODID,
			"textures/entity/ghast_boss_kinium_shooting.png");
	private static final ResourceLocation GHAST_BOSS_TEXTURES_1 = new ResourceLocation(KinoMod.MODID,
			"textures/entity/ghast_boss_kinium_1.png");
	private static final ResourceLocation GHAST_BOSS_SHOOTING_TEXTURES_1 = new ResourceLocation(KinoMod.MODID,
			"textures/entity/ghast_boss_kinium_shooting_1.png");
	private static final ResourceLocation GHAST_BOSS_TEXTURES_2 = new ResourceLocation(KinoMod.MODID,
			"textures/entity/ghast_boss_kinium_2.png");
	private static final ResourceLocation GHAST_BOSS_SHOOTING_TEXTURES_2 = new ResourceLocation(KinoMod.MODID,
			"textures/entity/ghast_boss_kinium_shooting_2.png");

	public RenderGhastBossK(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelGhastBoss(), 0.5F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityGhastBossK entity) {
		if (entity.isPhase(1))
			return entity.isAttacking() ? GHAST_BOSS_SHOOTING_TEXTURES_1 : GHAST_BOSS_TEXTURES_1;
		if (entity.isPhase(2))
			return entity.isAttacking() ? GHAST_BOSS_SHOOTING_TEXTURES_2 : GHAST_BOSS_TEXTURES_2;
		return entity.isAttacking() ? GHAST_BOSS_SHOOTING_TEXTURES : GHAST_BOSS_TEXTURES;
	}

	/**
	 * Allows the render to do state modifications necessary before the model is
	 * rendered.
	 */
	protected void preRenderCallback(EntityGhastBossK entitylivingbaseIn, float partialTickTime) {
		float f = 1.0F;
		float f1 = 4.5F;
		float f2 = 4.5F;
		GlStateManager.scale(5.5F, 5.5F, 5.5F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate(0.0D, 1.8D, 0.0D);
	}
}