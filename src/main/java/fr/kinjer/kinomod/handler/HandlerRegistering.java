package fr.kinjer.kinomod.handler;

import java.util.Collection;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import fr.kinjer.kinomod.init.*;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HandlerRegistering {

	private static Multimap<Class<?>, IForgeRegistryEntry<?>> entries = MultimapBuilder.hashKeys().arrayListValues()
			.build();

	@SubscribeEvent
	public static void onRegistryEvent(RegistryEvent.Register event) {
		Class<?> type = event.getRegistry().getRegistrySuperType();

		if (entries.containsKey(type)) {
			Collection<IForgeRegistryEntry<?>> ourEntries = entries.get(type);
			for (IForgeRegistryEntry<?> entry : ourEntries)
				event.getRegistry().register(entry);
		}
	}

	public HandlerRegistering() {
		InitFluids.registerFluids();
		InitEntity.registerEntities();
		InitPotion.registerPotions();
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block block : InitBlocks.blocks) {
			event.getRegistry().register(block);
		}
	}

	@SubscribeEvent
	public void registerEnchant(RegistryEvent.Register<Enchantment> event) {

		event.getRegistry().registerAll(InitEnchantment.ENCHANTMENT.toArray(new Enchantment[0]));

	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : InitItems.items) {
			event.getRegistry().register(item);
		}
		for (ItemBlock item : InitItemBlocks.items) {
			event.getRegistry().register(item);
		}
		for (ItemArmor item : InitItems.itemsarmor) {
			event.getRegistry().register(item);
		}
		for (ItemTool item : InitItems.itemtool) {
			event.getRegistry().register(item);
		}
	}

//	@SubscribeEvent
	// public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
	// RegistrySounds.init();
	// event.getRegistry().getRegistrySuperType();
	// event.getRegistry();
	// }

	/*
	 * private <T extends IForgeRegistryEntry<T>> void fillRegistry(Class<T>
	 * registrySuperType, IForgeRegistry<T> forgeRegistry) { List<?> entries =
	 * registry.getEntries(registrySuperType); if(entries != null) {
	 * entries.forEach((e) -> forgeRegistry.register((T) e)); } }
	 */}
