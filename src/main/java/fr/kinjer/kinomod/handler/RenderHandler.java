package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.EntityCentaur;
import fr.kinjer.kinomod.entity.render.RenderCentaur;
import fr.kinjer.kinomod.init.BlocksMod;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
	
	public static void registerCustomMeshesAndStats() {
		setCustomState(BlocksMod.kinium_molten, "kinium_molten");
		setCustomState(BlocksMod.balium_molten, "balium_molten");
		setCustomState(BlocksMod.seminium_molten, "seminium_molten");
		setCustomState(BlocksMod.dalium_molten, "dalium_molten");
		
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
		RenderingRegistry.registerEntityRenderingHandler(EntityCentaur.class, new IRenderFactory<EntityCentaur>() {
			@Override
			public Render<? super EntityCentaur> createRenderFor(RenderManager manager) {
				return new RenderCentaur(manager);
			}
		});
	}

}
