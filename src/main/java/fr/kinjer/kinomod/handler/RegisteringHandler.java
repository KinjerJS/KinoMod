package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.init.BlocksMod;
import fr.kinjer.kinomod.init.FluidsMod;
import fr.kinjer.kinomod.init.ItemBlocksMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisteringHandler {
	
	public RegisteringHandler() {
		FluidsMod.registerFluids();
		
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		for(Item item : ItemsMod.items) {
			event.getRegistry().register(item);
		}
		/******************** ITEMBLOCKS ********************/
		for(ItemBlock item : ItemBlocksMod.items) {
			event.getRegistry().register(item);
		}
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for(Block block : BlocksMod.blocks) {
			event.getRegistry().register(block);
		}
		RenderHandler.registerCustomMeshesAndStats();
	}
	
}
