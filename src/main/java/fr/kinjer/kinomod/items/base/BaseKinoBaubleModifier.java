package fr.kinjer.kinomod.items.base;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public abstract class BaseKinoBaubleModifier extends BaseKinoBauble {
	public BaseKinoBaubleModifier(String name) {
		super(name);
	}

	@Override
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
		if(!player.world.isRemote) {
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			fillModifiers(attributes, stack);
			player.getAttributeMap().applyAttributeModifiers(attributes);
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		if(!player.world.isRemote) {
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			fillModifiers(attributes, stack);
			player.getAttributeMap().removeAttributeModifiers(attributes);
		}
	}

	abstract void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack);

}
