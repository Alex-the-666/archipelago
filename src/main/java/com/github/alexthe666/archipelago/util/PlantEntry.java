package com.github.alexthe666.archipelago.util;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class PlantEntry {

    public Block block;
    public int chancePerChunk;
    public boolean doublePlant;
    private List<Integer> biomesToSpawn = new ArrayList<>();

    public PlantEntry(Block block, int chancePerChunk, boolean doublePlant) {
        this.block = block;
        this.chancePerChunk = chancePerChunk;
        this.doublePlant = doublePlant;
    }

    public void addBiome(int biome) {
        this.biomesToSpawn.add(biome);
    }

    public boolean canSpawnIn(Biome biome) {
        return this.biomesToSpawn.contains(Biome.getIdForBiome(biome));
    }
}
