package com.github.alexthe666.archipelago.world;

import com.github.alexthe666.archipelago.util.PlantEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenArchipelagoCoral extends WorldGenerator {
    public PlantEntry plantType;

    public WorldGenArchipelagoCoral(PlantEntry plantEntry) {
        this.plantType = plantEntry;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        boolean placed = false;
        if (world != null && this.plantType != null && this.plantType.block != null) {
            for (int i = 0; i < 64; ++i) {
                BlockPos pos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
                if (world.isBlockLoaded(pos) && this.plantType.canSpawnIn(world.getBiomeGenForCoords(pos)) && this.plantType.block.canPlaceBlockAt(world, pos)) {
                    world.setBlockState(pos, this.plantType.block.getDefaultState(), 2);
                    placed = true;
                }
            }
        }
        return placed;
    }
}