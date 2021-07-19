package fr.kinjer.kinomod.handler;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import fr.kinjer.kinomod.blocks.BlockKiniumFluid;
import fr.kinjer.kinomod.blocks.BlockKino;
import fr.kinjer.kinomod.fluids.KiniumFluid;
import fr.kinjer.kinomod.init.BlocksMod;
import fr.kinjer.kinomod.init.EntityInit;
import fr.kinjer.kinomod.init.FluidsMod;
import fr.kinjer.kinomod.init.ItemBlocksMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemTool;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegisteringHandler {
	
	private static Multimap<Class<?>, IForgeRegistryEntry<?>> entries = MultimapBuilder.hashKeys().arrayListValues().build();
	
	@SubscribeEvent
	public static void onRegistryEvent(RegistryEvent.Register event) {
		Class<?> type = event.getRegistry().getRegistrySuperType();
		
		if(entries.containsKey(type)) {
			Collection<IForgeRegistryEntry<?>> ourEntries = entries.get(type);
			for(IForgeRegistryEntry<?> entry : ourEntries)
				event.getRegistry().register(entry);
		}
	}
	
	public RegisteringHandler() { 
		FluidsMod.registerFluids(); 
		EntityInit.registerEntities();
		
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for(Block block : BlocksMod.blocks) {
			event.getRegistry().register(block);
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		for(Item item : ItemsMod.items) {
			event.getRegistry().register(item);
		}
		for(ItemBlock item : ItemBlocksMod.items) {
			event.getRegistry().register(item);
		}
		for (ItemArmor item : ItemsMod.itemsarmor) {
			event.getRegistry().register(item);
		}
		for (ItemTool item : ItemsMod.itemtool) {
			event.getRegistry().register(item);
		}
	}
	
//	@SubscribeEvent
 //   public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
 //       RegistrySounds.init();
 //       event.getRegistry().getRegistrySuperType();
 //       event.getRegistry();
 //   }
	
/*	 private <T extends IForgeRegistryEntry<T>> void fillRegistry(Class<T> registrySuperType, IForgeRegistry<T> forgeRegistry) {
	        List<?> entries = registry.getEntries(registrySuperType);
	        if(entries != null) {
	            entries.forEach((e) -> forgeRegistry.register((T) e));
	        }
	    }
*/}
