package fr.kinjer.kinomod.items;

import java.util.Set;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemSwordKino extends ItemTool {

	public ItemSwordKino(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn,
			Set<Block> effectiveBlocksIn) {
		super(74, -2.4F, materialIn, effectiveBlocksIn);
		setCreativeTab(KinoMod.tabKino);
		
		String name = "bismuth_sword";
		ItemsMod.itemtool.add((ItemTool) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return 1;
	}
}