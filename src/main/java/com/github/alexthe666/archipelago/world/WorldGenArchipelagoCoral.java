package com.github.alexthe666.archipelago.world;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.github.alexthe666.archipelago.block.BlockShortCoral;
import com.github.alexthe666.archipelago.block.BlockTallPlant;
import com.github.alexthe666.archipelago.core.ModFluids;
import com.github.alexthe666.archipelago.util.PlantEntry;

public class WorldGenArchipelagoCoral extends WorldGenerator
{
	public PlantEntry plantType;

	public WorldGenArchipelagoCoral(PlantEntry plantEntry){
		this.plantType = plantEntry;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position){
		boolean flag = false;
		if(worldIn != null && plantType != null && plantType.block != null){
			for (int i = 0; i < 64; ++i){
				BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
				if (worldIn.isBlockLoaded(blockpos) && plantType.canSpawnIn(worldIn.getBiomeGenForCoords(blockpos)) && ((BlockShortCoral)plantType.block).canPlaceBlockAt(worldIn, blockpos)){
					worldIn.setBlockState(blockpos, plantType.block.getDefaultState(), 2);
					flag = true;
				}
			}
		}
		return flag;
	}
}