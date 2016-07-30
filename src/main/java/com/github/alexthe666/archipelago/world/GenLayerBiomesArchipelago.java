package com.github.alexthe666.archipelago.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import com.github.alexthe666.archipelago.core.ModWorld;

public class GenLayerBiomesArchipelago extends GenLayer {
    public static BiomeGenTropical[] volcanoBiomes = { ModWorld.ashField, ModWorld.volcano };
    public static BiomeGenTropical[] islandBiomes = { ModWorld.tropicGrassland, ModWorld.tropicShrubland, ModWorld.tropicJungle, ModWorld.dryPeaks, ModWorld.dryScrubland };
    public static BiomeGenTropical[] oceanBiomes = { ModWorld.tropicOcean, ModWorld.tropicShallows, ModWorld.tropicReef, ModWorld.tropicSeaGrassBed, ModWorld.tropicBlueHoles, ModWorld.tropicTrench, ModWorld.tropicKelpForest };
    private boolean isIsland;

    public GenLayerBiomesArchipelago(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int x, int z, int width, int height) {
        int[] ints = IntCache.getIntCache(width * height);
        for (int deltaZ = 0; deltaZ < height; deltaZ++) {
            for (int deltaX = 0; deltaX < width; deltaX++) {
                this.initChunkSeed(deltaX + x, deltaZ + z);
                ints[(deltaX + deltaZ * width)] = Biome.getIdForBiome(getBiome());
            }
        }
        return ints;
    }

    private Biome getBiome() {
        if (nextInt(3) == 0) {
            return nextInt(10) == 0 ? this.getRandomBiomeWeighted(volcanoBiomes) : this.getRandomBiomeWeighted(islandBiomes);
        } else {
            return this.getRandomBiomeWeighted(oceanBiomes);
        }
    }

    private BiomeGenTropical getRandomBiomeWeighted(BiomeGenTropical[] biomes) {
        int chance = 0;
        for (BiomeGenTropical biome : biomes) {
            chance += biome.getGenerationChance();
        }
        int chosen = nextInt(chance);
        chance = 0;
        for (BiomeGenTropical biome : biomes) {
            chance += biome.getGenerationChance();
            if (chance >= chosen) {
                return biome;
            }
        }
        return biomes[0];
    }
}
