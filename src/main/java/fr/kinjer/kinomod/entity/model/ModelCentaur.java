package fr.kinjer.kinomod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


public class ModelCentaur extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer arm0;
	private final ModelRenderer arm1;
	private final ModelRenderer Main;
	private final ModelRenderer Body2;
	private final ModelRenderer TailA;
	private final ModelRenderer Leg1A;
	private final ModelRenderer Leg2A;
	private final ModelRenderer Leg3A;
	private final ModelRenderer Saddle;
	private final ModelRenderer Leg4A;
	private final ModelRenderer bb_main;
	private final ModelRenderer arm2_r1;
	private final ModelRenderer arm1_r1;

	public ModelCentaur() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -7.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 101, -9.0F, -2.0F, -5.0F, 18, 12, 8, 0.0F, true));
		body.cubeList.add(new ModelBox(body, 86, 68, -7.5F, 10.0F, -4.0F, 15, 3, 6, 0.5F, true));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -7.0F, -2.0F);
		head.cubeList.add(new ModelBox(head, 96, 79, -4.0F, -12.0F, -3.5F, 8, 10, 8, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 72, 104, -3.0F, -6.0F, -5.5F, 6, 5, 2, 0.0F, true));

		arm0 = new ModelRenderer(this);
		arm0.setRotationPoint(0.0F, -7.0F, 0.0F);
		arm0.cubeList.add(new ModelBox(arm0, 96, 98, 9.0F, -2.5F, -4.0F, 4, 16, 6, 0.0F, true));

		arm1 = new ModelRenderer(this);
		arm1.setRotationPoint(0.0F, -7.0F, 0.0F);
		arm1.cubeList.add(new ModelBox(arm1, 96, 98, -13.0F, -2.5F, -4.0F, 4, 16, 6, 0.0F, true));

		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, 24.0F, 6.0F);
		

		Body2 = new ModelRenderer(this);
		Body2.setRotationPoint(0.0F, -13.0F, 9.0F);
		Main.addChild(Body2);
		Body2.cubeList.add(new ModelBox(Body2, 0, 69, -7.0F, -8.0F, -20.0F, 14, 10, 22, 0.0F, true));

		TailA = new ModelRenderer(this);
		TailA.setRotationPoint(0.0F, -20.0F, 11.0F);
		Main.addChild(TailA);
		setRotationAngle(TailA, 0.5236F, 0.0F, 0.0F);
		TailA.cubeList.add(new ModelBox(TailA, 72, 68, -1.5F, 0.0F, -2.0F, 3, 14, 4, 0.0F, true));

		Leg1A = new ModelRenderer(this);
		Leg1A.setRotationPoint(-3.0F, -11.0F, 9.0F);
		Main.addChild(Leg1A);
		Leg1A.cubeList.add(new ModelBox(Leg1A, 72, 88, -3.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		Leg2A = new ModelRenderer(this);
		Leg2A.setRotationPoint(3.0F, -11.0F, 9.0F);
		Main.addChild(Leg2A);
		Leg2A.cubeList.add(new ModelBox(Leg2A, 73, 88, -1.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, true));

		Leg3A = new ModelRenderer(this);
		Leg3A.setRotationPoint(-3.0F, -11.0F, -9.0F);
		Main.addChild(Leg3A);
		Leg3A.cubeList.add(new ModelBox(Leg3A, 72, 88, -3.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		Saddle = new ModelRenderer(this);
		Saddle.setRotationPoint(0.0F, -22.0F, 2.0F);
		Main.addChild(Saddle);
		

		Leg4A = new ModelRenderer(this);
		Leg4A.setRotationPoint(3.0F, -11.0F, -9.0F);
		Main.addChild(Leg4A);
		Leg4A.cubeList.add(new ModelBox(Leg4A, 73, 88, -1.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, true));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		arm2_r1 = new ModelRenderer(this);
		arm2_r1.setRotationPoint(3.0F, -39.0F, -1.0F);
		bb_main.addChild(arm2_r1);
		setRotationAngle(arm2_r1, 0.0F, 0.0F, -0.1745F);
		arm2_r1.cubeList.add(new ModelBox(arm2_r1, 124, 100, -5.8838F, -7.4421F, -4.0F, 1, 9, 1, 0.0F, true));

		arm1_r1 = new ModelRenderer(this);
		arm1_r1.setRotationPoint(3.0F, -39.0F, -1.0F);
		bb_main.addChild(arm1_r1);
		setRotationAngle(arm1_r1, 0.0F, 0.0F, 0.2182F);
		arm1_r1.cubeList.add(new ModelBox(arm1_r1, 124, 100, -0.8838F, -6.4421F, -4.0F, 1, 9, 1, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		head.render(f5);
		arm0.render(f5);
		arm1.render(f5);
		Main.render(f5);
		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}