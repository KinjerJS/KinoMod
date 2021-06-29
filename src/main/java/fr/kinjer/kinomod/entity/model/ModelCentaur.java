package fr.kinjer.kinomod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCentaur extends ModelBase {
	private final ModelRenderer Centaur;
	private final ModelRenderer fullhead;
	private final ModelRenderer head;
	private final ModelRenderer cornes;
	private final ModelRenderer arm2_r1;
	private final ModelRenderer arm1_r1;
	private final ModelRenderer body;
	private final ModelRenderer arms;
	private final ModelRenderer arm0;
	private final ModelRenderer arm1;
	private final ModelRenderer mainhorse;
	private final ModelRenderer Body2;
	private final ModelRenderer TailA;
	private final ModelRenderer Legs;
	private final ModelRenderer Leg1A;
	private final ModelRenderer Leg2A;
	private final ModelRenderer Leg3A;
	private final ModelRenderer Leg4A;

	public ModelCentaur() {
		textureWidth = 128;
		textureHeight = 128;

		Centaur = new ModelRenderer(this);
		Centaur.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		fullhead = new ModelRenderer(this);
		fullhead.setRotationPoint(0.0F, -33.0F, 0.0F);
		Centaur.addChild(fullhead);
		

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 2.0F, -2.0F);
		fullhead.addChild(head);
		head.cubeList.add(new ModelBox(head, 96, 79, -4.0F, -12.0F, -3.5F, 8, 10, 8, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 72, 104, -3.0F, -6.0F, -5.5F, 6, 5, 2, 0.0F, true));

		cornes = new ModelRenderer(this);
		cornes.setRotationPoint(0.0F, 33.0F, 0.0F);
		fullhead.addChild(cornes);
		

		arm2_r1 = new ModelRenderer(this);
		arm2_r1.setRotationPoint(3.0F, -39.0F, -1.0F);
		cornes.addChild(arm2_r1);
		setRotationAngle(arm2_r1, 0.0F, 0.0F, -0.1745F);
		arm2_r1.cubeList.add(new ModelBox(arm2_r1, 124, 100, -5.8838F, -7.4421F, -4.0F, 1, 9, 1, 0.0F, true));

		arm1_r1 = new ModelRenderer(this);
		arm1_r1.setRotationPoint(3.0F, -39.0F, -1.0F);
		cornes.addChild(arm1_r1);
		setRotationAngle(arm1_r1, 0.0F, 0.0F, 0.2182F);
		arm1_r1.cubeList.add(new ModelBox(arm1_r1, 124, 100, -0.8838F, -6.4421F, -4.0F, 1, 9, 1, 0.0F, true));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -20.0F, -2.0F);
		Centaur.addChild(body);
		body.cubeList.add(new ModelBox(body, 0, 101, -9.0F, -2.0F, -5.0F, 18, 12, 8, 0.0F, true));
		body.cubeList.add(new ModelBox(body, 86, 68, -7.5F, 10.0F, -4.0F, 15, 3, 6, 0.5F, true));

		arms = new ModelRenderer(this);
		arms.setRotationPoint(0.0F, 0.0F, 0.0F);
		Centaur.addChild(arms);
		

		arm0 = new ModelRenderer(this);
		arm0.setRotationPoint(11.0F, -32.0F, 0.0F);
		arms.addChild(arm0);
		arm0.cubeList.add(new ModelBox(arm0, 96, 98, 9.0F, -2.5F, -4.0F, 4, 16, 6, 0.0F, true));

		arm1 = new ModelRenderer(this);
		arm1.setRotationPoint(-11.0F, -32.0F, 0.0F);
		arms.addChild(arm1);
		arm1.cubeList.add(new ModelBox(arm1, 96, 98, -13.0F, -2.5F, -4.0F, 4, 16, 6, 0.0F, true));

		mainhorse = new ModelRenderer(this);
		mainhorse.setRotationPoint(0.0F, 0.0F, 6.0F);
		Centaur.addChild(mainhorse);
		

		Body2 = new ModelRenderer(this);
		Body2.setRotationPoint(0.0F, -13.0F, -8.0F);
		mainhorse.addChild(Body2);
		Body2.cubeList.add(new ModelBox(Body2, 0, 69, -7.0F, -8.0F, -20.0F, 14, 10, 22, 0.0F, true));

		TailA = new ModelRenderer(this);
		TailA.setRotationPoint(0.0F, -20.0F, 11.0F);
		mainhorse.addChild(TailA);
		setRotationAngle(TailA, 0.5236F, 0.0F, 0.0F);
		TailA.cubeList.add(new ModelBox(TailA, 72, 68, -1.5F, 0.0F, -2.0F, 3, 14, 4, 0.0F, true));

		Legs = new ModelRenderer(this);
		Legs.setRotationPoint(0.0F, 0.0F, -6.0F);
		mainhorse.addChild(Legs);
		

		Leg1A = new ModelRenderer(this);
		Leg1A.setRotationPoint(-3.0F, -11.0F, 15.0F);
		Legs.addChild(Leg1A);
		Leg1A.cubeList.add(new ModelBox(Leg1A, 72, 88, -3.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		Leg2A = new ModelRenderer(this);
		Leg2A.setRotationPoint(3.0F, -11.0F, 15.0F);
		Legs.addChild(Leg2A);
		Leg2A.cubeList.add(new ModelBox(Leg2A, 73, 88, -1.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, true));
		
		Leg3A = new ModelRenderer(this);
		Leg3A.setRotationPoint(-3.0F, -11.0F, -3.0F);
		Legs.addChild(Leg3A);
		Leg3A.cubeList.add(new ModelBox(Leg3A, 72, 88, -3.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		Leg4A = new ModelRenderer(this);
		Leg4A.setRotationPoint(3.0F, -11.0F, -3.0F);
		Legs.addChild(Leg4A);
		Leg4A.cubeList.add(new ModelBox(Leg4A, 73, 88, -1.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, true));
	}

	public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Centaur.render(f5);
		fullhead.render(f5);
		head.render(f5);
		cornes.render(f5);
		arm1_r1.render(f5);
		arm2_r1.render(f5);
		body.render(f5);
		arms.render(f5);
		arm0.render(f5);
		arm1.render(f5);
		mainhorse.render(f5);
		Body2.render(f5);
		TailA.render(f5);
		Legs.render(f5);
		Leg1A.render(f5);
		Leg2A.render(f5);
		Leg3A.render(f5);
		Leg4A.render(f5);
		
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		this.Leg1A.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.Leg3A.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		
		this.Leg2A.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.Leg4A.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;
		
	}
}