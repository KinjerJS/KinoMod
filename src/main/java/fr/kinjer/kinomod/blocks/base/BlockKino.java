package fr.kinjer.kinomod.blocks.base;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKino extends Block{
	
	public BlockKino(String name, Material material) {
		super(material);
		setCreativeTab(KinoMod.tabKino);
		InitBlocks.setBlockName(this, name);
		setHarvestLevel("pickaxe", 3);
	}
}
