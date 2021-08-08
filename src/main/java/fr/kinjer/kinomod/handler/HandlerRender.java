package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.EntityCentaur;
import fr.kinjer.kinomod.entity.EntityGhastBossD;
import fr.kinjer.kinomod.entity.projectile.EntityBismuthBall;
import fr.kinjer.kinomod.entity.projectile.EntityGhastBossDFireball;
import fr.kinjer.kinomod.entity.render.*;
import fr.kinjer.kinomod.init.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRender {
	
	public HandlerRender() {
		HandlerRender.registerEntityRenders();
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		HandlerRender.registerCustomMeshesAndStats();
	}
	
	public static void registerCustomMeshesAndStats() {
		setCustomState(InitBlocks.kinium_molten, "kinium_molten");
		setCustomState(InitBlocks.balium_molten, "balium_molten");
		setCustomState(InitBlocks.seminium_molten, "seminium_molten");
		setCustomState(InitBlocks.dalium_molten, "dalium_molten");
		
	}
	
	private static void setCustomState(Block block, String name) {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(KinoMod.MODID + ":" + name, "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(KinoMod.MODID + ":" + name, "fluid");
			}
		});
	}
	
	public static void registerEntityRenders() {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBismuthBall.class, RenderBismuthBall::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGhastBossDFireball.class, RenderGhastBossDFireball::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCentaur.class, new IRenderFactory<EntityCentaur>() {
			@Override
			public Render<? super EntityCentaur> createRenderFor(RenderManager manager) {
				return new RenderCentaur(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityGhastBossD.class, new IRenderFactory<EntityGhastBossD>() {
			@Override
			public Render<? super EntityGhastBossD> createRenderFor(RenderManager manager) {
				return new RenderGhastBossD(manager);
			}
		});
	}
}
