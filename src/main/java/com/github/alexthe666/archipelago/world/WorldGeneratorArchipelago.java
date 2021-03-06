package com.github.alexthe666.archipelago.world;

import com.github.alexthe666.archipelago.core.ModBlocks;
import com.github.alexthe666.archipelago.core.ModConfig;
import com.github.alexthe666.archipelago.core.ModFluids;
import com.github.alexthe666.archipelago.core.ModWorld;
import com.github.alexthe666.archipelago.util.PlantEntry;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGeneratorArchipelago implements IWorldGenerator {

    public static List<PlantEntry> flowersEntries = new ArrayList<>();
    public static List<PlantEntry> coralsEntries = new ArrayList<>();
    public static List<PlantEntry> kelpEntries = new ArrayList<>();

    public static BlockPos getCoralHeight(World world, BlockPos pos) {
        for (int y = 0; y < 256; y++) {
            if (y > 2) {
                BlockPos currentPosition = pos.add(0, y + 1, 0);
                if (world.getBlockState(currentPosition.up()).getBlock() == ModFluids.tropical_water && world.getBlockState(currentPosition).getMaterial() == Material.SAND) {
                    return currentPosition;
                }
            }
        }
        return pos;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == ModConfig.ARCHIPELAGO_DIMENSION_ID) {
            for (int i = 0; i < 16; i++) {
                BlockPos reefBlobPos = new BlockPos((chunkX * 16) + random.nextInt(16), 0, (chunkZ * 16) + random.nextInt(16));
                if (world.getBiomeGenForCoords(reefBlobPos) == ModWorld.tropicReef) {
                    new WorldGenBlockBlob(ModBlocks.coral_rock, random.nextInt(3) + 2).generate(world, random, getCoralHeight(world, reefBlobPos).down(3));
                }
            }
            for (int i = 0; i < 4; i++) {
                int x = (chunkX * 16) + random.nextInt(16);
                int z = (chunkZ * 16) + random.nextInt(16);
                new WorldGenArchipelagoPlant(flowersEntries.get(random.nextInt(flowersEntries.size()))).generate(world, random, world.getHeight(new BlockPos(x, 0, z)));
            }
            for (int i = 0; i < 7; i++) {
                int x = (chunkX * 16) + random.nextInt(16);
                int z = (chunkZ * 16) + random.nextInt(16);
                new WorldGenArchipelagoCoral(coralsEntries.get(random.nextInt(coralsEntries.size()))).generate(world, random, getCoralHeight(world, new BlockPos(x, 0, z)));
            }
            for (int i = 0; i < 4; i++) {
                int x = (chunkX * 16) + random.nextInt(16);
                int z = (chunkZ * 16) + random.nextInt(16);
                new WorldGenArchipelagoKelp(kelpEntries.get(random.nextInt(kelpEntries.size()))).generate(world, random, getCoralHeight(world, new BlockPos(x, 0, z)));
            }
            new WorldGenArchipelagoMangrove().generate(world, random, getCoralHeight(world, new BlockPos((chunkX * 16) + random.nextInt(16), 0, (chunkZ * 16) + random.nextInt(16))));
        }
    }
}
