package fr.kinjer.kinomod.gen;

import java.util.Random;

import com.google.common.base.Predicate;

import fr.kinjer.kinomod.init.BlocksMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator{

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimension())
		{
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}
	
	private void generateEnd(World world, Random random, int x, int z) {
		this.addOreSpawn(BlocksMod.kinium_ore_end, Blocks.END_STONE, world, random, x, z, 16, 16, 3, 8, 4, 80);
		this.addOreSpawn(BlocksMod.balium_ore_end, Blocks.END_STONE, world, random, x, z, 16, 16, 3, 8, 4, 80);
		this.addOreSpawn(BlocksMod.seminium_ore_end, Blocks.END_STONE, world, random, x, z, 16, 16, 3, 8, 4, 80);
		this.addOreSpawn(BlocksMod.dalium_ore_end, Blocks.END_STONE, world, random, x, z, 16, 16, 3, 8, 4, 80);
	}

	private void generateSurface(World world, Random random, int x, int z) {
		this.addOreSpawn(BlocksMod.kinium_ore, Blocks.STONE, world, random, x, z, 16, 16, 3, 3, 4, 8);
		this.addOreSpawn(BlocksMod.balium_ore, Blocks.STONE, world, random, x, z, 16, 16, 3, 3, 4, 8);
		this.addOreSpawn(BlocksMod.seminium_ore, Blocks.STONE, world, random, x, z, 16, 16, 3, 3, 4, 8);
		this.addOreSpawn(BlocksMod.dalium_ore, Blocks.STONE, world, random, x, z, 16, 16, 3, 3, 4, 8);
	}
	
	private void generateNether(World world, Random random, int x, int z) {
		this.addOreSpawn(BlocksMod.kinium_ore_nether, Blocks.NETHERRACK, world, random, x, z, 16, 16, 3, 6, 4, 36);
		this.addOreSpawn(BlocksMod.balium_ore_nether, Blocks.NETHERRACK, world, random, x, z, 16, 16, 3, 6, 4, 36);
		this.addOreSpawn(BlocksMod.seminium_ore_nether, Blocks.NETHERRACK, world, random, x, z, 16, 16, 3, 6, 4, 36);
		this.addOreSpawn(BlocksMod.dalium_ore_nether, Blocks.NETHERRACK, world, random, x, z, 16, 16, 3, 6, 4, 36);
	
	}
	
	public void addOreSpawn(Block block, Block replace, World world, Random random, int blockXPos,
			int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
		this.addOreSpawn(block, block.getDefaultState(), replace, world, random, blockXPos, blockZPos, maxX, maxZ, maxVeinSize, chancesToSpawn, minY, maxY);
		
	}
	public void addOreSpawn(Block block, IBlockState metadata, Block target, World world, Random random, int blockXPos,
			int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
		for (int i = 0; i < chancesToSpawn; i++)
		{
			int posY = random.nextInt(128);
			if ((posY <= maxY) && (posY >= minY))
			{
				(new WorldGenMinable(metadata, maxVeinSize, new WorldGen.BlockPredicate(target))).generate(world, random,
						new BlockPos(blockXPos + random.nextInt(16), posY, blockZPos + random.nextInt(16)));
			}

		}

	}

	static class BlockPredicate implements Predicate<IBlockState>
	{
		static Block b;
	    private BlockPredicate(Block b)
	    {
	    	this.b = b;
	    }

	    public boolean apply(IBlockState p_apply_1_)
	    {
	        if (p_apply_1_ != null && p_apply_1_.getBlock() == b)
	        {
	            //BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType)p_apply_1_.getValue(BlockStone.VARIANT);
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
	}
	
}


