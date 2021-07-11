package fr.kinjer.kinomod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGhastBoss extends ModelBase {
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
	private final ModelRenderer tentacles_3_r1;

	public ModelGhastBoss() {
		textureWidth = 128;
		textureHeight = 64;

		root = new ModelRenderer(this);
		root.setRotationPoint(-8.2F, 23.0F, 8.0F);
		

		tentacles = new ModelRenderer(this);
		tentacles.setRotationPoint(8.2F, 1.0F, -8.0F);
		root.addChild(tentacles);
		

		tentacles_0 = new ModelRenderer(this);
		tentacles_0.setRotationPoint(0.8F, -1.0F, -3.0F);
		tentacles.addChild(tentacles_0);
		tentacles_0.setTextureOffset(108, 41).addBox(-2.9009F, 0.7128F, -1.9912F, 5, 18, 5, true);

		tentacles_1 = new ModelRenderer(this);
		tentacles_1.setRotationPoint(-10.3F, -1.0F, -3.0F);
		tentacles.addChild(tentacles_1);
		tentacles_1.setTextureOffset(108, 36).addBox(-2.4048F, -0.1288F, -1.9912F, 5, 23, 5, true);

		tentacles_2 = new ModelRenderer(this);
		tentacles_2.setRotationPoint(-6.3F, -1.0F, -5.0F);
		tentacles.addChild(tentacles_2);
		tentacles_2.setTextureOffset(108, 42).addBox(-16.8008F, -0.3664F, 0.0088F, 5, 17, 5, true);

		tentacles_3 = new ModelRenderer(this);
		tentacles_3.setRotationPoint(6.3F, -1.0F, 0.0F);
		tentacles.addChild(tentacles_3);
		tentacles_3.setTextureOffset(108, 41).addBox(-4.2029F, 0.7128F, 5.4048F, 5, 18, 5, true);

		tentacles_4 = new ModelRenderer(this);
		tentacles_4.setRotationPoint(1.3F, -1.0F, 0.0F);
		tentacles.addChild(tentacles_4);
		tentacles_4.setTextureOffset(108, 32).addBox(-8.5989F, 0.0296F, 5.4048F, 5, 27, 5, true);

		tentacles_5 = new ModelRenderer(this);
		tentacles_5.setRotationPoint(-3.8F, -1.0F, 0.0F);
		tentacles.addChild(tentacles_5);
		tentacles_5.setTextureOffset(108, 36).addBox(-14.1028F, -0.1288F, 5.4048F, 5, 23, 5, true);

		tentacles_6 = new ModelRenderer(this);
		tentacles_6.setRotationPoint(3.8F, -1.0F, 5.0F);
		tentacles.addChild(tentacles_6);
		tentacles_6.setTextureOffset(108, 34).addBox(-5.9009F, -0.0496F, 10.8008F, 5, 25, 5, true);

		tentacles_7 = new ModelRenderer(this);
		tentacles_7.setRotationPoint(-1.3F, -1.0F, 5.0F);
		tentacles.addChild(tentacles_7);
		tentacles_7.setTextureOffset(108, 34).addBox(-11.4048F, -0.0496F, 10.8008F, 5, 25, 5, true);

		tentacles_8 = new ModelRenderer(this);
		tentacles_8.setRotationPoint(-6.3F, -1.0F, 5.0F);
		tentacles.addChild(tentacles_8);
		tentacles_8.setTextureOffset(108, 32).addBox(-16.8008F, 0.0296F, 10.8008F, 5, 27, 5, true);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.2F, -7.0F, 1.0F);
		root.addChild(body);
		body.setTextureOffset(0, 0).addBox(-16.0F, -24.0F, -17.0F, 32, 32, 32, true);

		horns = new ModelRenderer(this);
		horns.setRotationPoint(8.2F, 1.0F, -8.0F);
		root.addChild(horns);
		

		tentacles_4_r1 = new ModelRenderer(this);
		tentacles_4_r1.setRotationPoint(-7.081F, 1.0792F, 7.484F);
		horns.addChild(tentacles_4_r1);
		setRotationAngle(tentacles_4_r1, 0.0873F, 0.0436F, 0.1745F);
		tentacles_4_r1.setTextureOffset(105, 4).addBox(5.9802F, -39.4456F, -12.4752F, 5, 17, 5, true);

		tentacles_3_r1 = new ModelRenderer(this);
		tentacles_3_r1.setRotationPoint(-7.081F, 1.0792F, 7.484F);
		horns.addChild(tentacles_3_r1);
		setRotationAngle(tentacles_3_r1, 0.1309F, 0.0F, -0.2182F);
		tentacles_3_r1.setTextureOffset(105, 4).addBox(-11.0198F, -40.4456F, -11.4752F, 5, 17, 5, true);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		root.render(f5);
		tentacles_0.render(f5);
		tentacles_1.render(f5);
		tentacles_2.render(f5);
		tentacles_3.render(f5);
		tentacles_4.render(f5);
		tentacles_5.render(f5);
		tentacles_6.render(f5);
		tentacles_7.render(f5);
		tentacles_8.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}