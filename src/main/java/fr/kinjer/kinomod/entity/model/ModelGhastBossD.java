package fr.kinjer.kinomod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGhastBossD extends ModelBase {
	private final ModelRenderer root;
	private final ModelRenderer tentacles;
	private final ModelRenderer tentacles_0;
	private final ModelRenderer tentacles_1;
	private final ModelRenderer tentacles_2;
	private final ModelRenderer tentacles_3;
	private final ModelRenderer tentacles_4;
	private final ModelRenderer tentacles_5;
	private final ModelRenderer tentacles_6;
	private final ModelRenderer tentacles_7;
	private final ModelRenderer tentacles_8;
	private final ModelRenderer body;
	private final ModelRenderer horns;
	private final ModelRenderer tentacles_4_r1;
	private final ModelRenderer tentacles_4_r1_r1;
	private final ModelRenderer tentacles_3_r1;

	public ModelGhastBossD() {
		textureWidth = 128;
		textureHeight = 64;

		root = new ModelRenderer(this);
		root.setRotationPoint(0.0F, 24.0F, 0.0F);

		tentacles = new ModelRenderer(this);
		tentacles.setRotationPoint(0.0F, 0.0F, 0.0F);
		root.addChild(tentacles);

		tentacles_0 = new ModelRenderer(this);
		tentacles_0.setRotationPoint(8.8F, -1.0F, -9.0F);
		tentacles.addChild(tentacles_0);
		tentacles_0.setTextureOffset(108, 41).addBox(-2.766F, -1.6646F, -2.2546F, 5, 18, 5, true);

		tentacles_1 = new ModelRenderer(this);
		tentacles_1.setRotationPoint(-10.3F, -5.0F, -3.0F);
		tentacles.addChild(tentacles_1);
		tentacles_1.setTextureOffset(108, 36).addBox(5.7301F, -2.5062F, -8.2546F, 5, 23, 5, true);

		tentacles_2 = new ModelRenderer(this);
		tentacles_2.setRotationPoint(-6.3F, -5.0F, -5.0F);
		tentacles.addChild(tentacles_2);
		tentacles_2.setTextureOffset(108, 42).addBox(-8.6659F, -2.7438F, -6.2546F, 5, 17, 5, true);

		tentacles_3 = new ModelRenderer(this);
		tentacles_3.setRotationPoint(6.3F, -1.0F, 0.0F);
		tentacles.addChild(tentacles_3);
		tentacles_3.setTextureOffset(108, 41).addBox(3.932F, -1.6646F, -0.8586F, 5, 18, 5, true);

		tentacles_4 = new ModelRenderer(this);
		tentacles_4.setRotationPoint(1.3F, -1.0F, 0.0F);
		tentacles.addChild(tentacles_4);
		tentacles_4.setTextureOffset(108, 32).addBox(-0.464F, -2.3478F, -0.8586F, 5, 27, 5, true);

		tentacles_5 = new ModelRenderer(this);
		tentacles_5.setRotationPoint(-3.8F, -1.0F, 0.0F);
		tentacles.addChild(tentacles_5);
		tentacles_5.setTextureOffset(108, 36).addBox(-5.9679F, -2.5062F, -0.8586F, 5, 23, 5, true);

		tentacles_6 = new ModelRenderer(this);
		tentacles_6.setRotationPoint(3.8F, -1.0F, 5.0F);
		tentacles.addChild(tentacles_6);
		tentacles_6.setTextureOffset(108, 34).addBox(2.234F, -2.427F, 4.5374F, 5, 25, 5, true);

		tentacles_7 = new ModelRenderer(this);
		tentacles_7.setRotationPoint(-1.3F, -1.0F, 5.0F);
		tentacles.addChild(tentacles_7);
		tentacles_7.setTextureOffset(108, 34).addBox(-3.2699F, -2.427F, 4.5374F, 5, 25, 5, true);

		tentacles_8 = new ModelRenderer(this);
		tentacles_8.setRotationPoint(-6.3F, -1.0F, 5.0F);
		tentacles.addChild(tentacles_8);
		tentacles_8.setTextureOffset(108, 32).addBox(-8.6659F, -2.3478F, 4.5374F, 5, 27, 5, true);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.1349F, -17.3774F, 1.7366F);
		root.addChild(body);
		body.setTextureOffset(0, 0).addBox(-16.0F, -16.0F, -16.0F, 32, 32, 32, true);

		horns = new ModelRenderer(this);
		horns.setRotationPoint(0.0F, 0.0F, 0.0F);
		root.addChild(horns);

		tentacles_4_r1 = new ModelRenderer(this);
		tentacles_4_r1.setRotationPoint(-7.081F, 1.0792F, 7.484F);
		horns.addChild(tentacles_4_r1);
		setRotationAngle(tentacles_4_r1, 0.0873F, 0.0436F, 0.1745F);

		tentacles_4_r1_r1 = new ModelRenderer(this);
		tentacles_4_r1_r1.setRotationPoint(15.2159F, -28.4566F, -13.7474F);
		tentacles_4_r1.addChild(tentacles_4_r1_r1);
		setRotationAngle(tentacles_4_r1_r1, 0.0436F, -0.0436F, 0.0F);
		tentacles_4_r1_r1.setTextureOffset(105, 4).addBox(-1.1008F, -15.3664F, -3.9912F, 5, 17, 5, true);

		tentacles_3_r1 = new ModelRenderer(this);
		tentacles_3_r1.setRotationPoint(-7.081F, 1.0792F, 7.484F);
		horns.addChild(tentacles_3_r1);
		setRotationAngle(tentacles_3_r1, 0.1309F, 0.0F, -0.2182F);
		tentacles_3_r1.setTextureOffset(105, 4).addBox(-2.8849F, -41.823F, -17.7386F, 5, 17, 5, true);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		tentacles.render(f5);
		body.render(f5);
		horns.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {

		this.tentacles_0.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 2) + 0.4F;
		this.tentacles_1.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 4) + 0.4F;
		this.tentacles_2.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 6) + 0.4F;
		this.tentacles_3.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 8) + 0.4F;
		this.tentacles_4.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 10) + 0.4F;
		this.tentacles_5.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 12) + 0.4F;
		this.tentacles_6.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 14) + 0.4F;
		this.tentacles_7.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 16) + 0.4F;
		this.tentacles_8.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 18) + 0.4F;

	}
}