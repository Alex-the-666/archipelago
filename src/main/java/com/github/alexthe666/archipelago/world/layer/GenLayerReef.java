package com.github.alexthe666.archipelago.world.layer;

import com.github.alexthe666.archipelago.core.ModWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerReef extends GenLayer {
    private static final int REEF = Biome.getIdForBiome(ModWorld.tropicReef);
    private static final int SHALLOWS = Biome.getIdForBiome(ModWorld.tropicShallows);

    public GenLayerReef(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] parentBiomes = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        int[] newBiomes = IntCache.getIntCache(areaWidth * areaHeight);
        for (int deltaZ = 0; deltaZ < areaHeight; ++deltaZ) {
            for (int deltaX = 0; deltaX < areaWidth; ++deltaX) {
                this.initChunkSeed((long) (deltaX + areaX), (long) (deltaZ + areaY));
                int biomeIndex = parentBiomes[deltaX + 1 + (deltaZ + 1) * (areaWidth + 2)];
                this.replace(parentBiomes, newBiomes, deltaX, deltaZ, areaWidth, biomeIndex, REEF);
            }
        }
        return newBiomes;
    }

    private void replace(int[] parentBiomes, int[] newBiomes, int deltaX, int deltaZ, int areaWidth, int parentBiome, int newBiome) {
        if (parentBiome == SHALLOWS) {
            newBiomes[deltaX + deltaZ * areaWidth] = parentBiome;
        } else {
            int neighbour1 = parentBiomes[deltaX + 1 + (deltaZ + 1 - 1) * (areaWidth + 2)];
            int neighbour2 = parentBiomes[deltaX + 1 + 1 + (deltaZ + 1) * (areaWidth + 2)];
            int neighbour3 = parentBiomes[deltaX + 1 - 1 + (deltaZ + 1) * (areaWidth + 2)];
            int neighbour4 = parentBiomes[deltaX + 1 + (deltaZ + 1 + 1) * (areaWidth + 2)];
            if ((neighbour1 != SHALLOWS && neighbour2 != SHALLOWS && neighbour3 != SHALLOWS && neighbour4 != SHALLOWS) || (!isBiomeOceanic(neighbour1) || !isBiomeOceanic(neighbour2) || !isBiomeOceanic(neighbour3) || !isBiomeOceanic(neighbour4))) {
                newBiomes[deltaX + deltaZ * areaWidth] = parentBiome;
            } else {
                newBiomes[deltaX + deltaZ * areaWidth] = newBiome;
            }
        }
    }
}