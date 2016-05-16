package com.github.alexthe666.archipelago.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.alexthe666.archipelago.Archipelago;
import com.github.alexthe666.archipelago.block.BlockTropicalWater;

public class ModFluids {

	public static Block tropical_water;
	public static Block tropical_water_seafoam;
	public static Fluid fluid_tropical_water;
	public static Fluid fluid_tropical_water_seafoam;

	public static void init(){
		fluid_tropical_water = new Fluid("tropical_water", new ResourceLocation("archipelago", "blocks/tropical_water_still"), new ResourceLocation("archipelago", "blocks/tropical_water_flowing"));
		FluidRegistry.registerFluid(fluid_tropical_water);
		fluid_tropical_water_seafoam = new Fluid("tropical_water_seafoam", new ResourceLocation("archipelago", "blocks/tropical_water_still_seafoam"), new ResourceLocation("archipelago", "blocks/tropical_water_flowing"));
		FluidRegistry.registerFluid(fluid_tropical_water_seafoam);
		tropical_water = new BlockTropicalWater(fluid_tropical_water, Material.water).setUnlocalizedName("archipelago.tropical_water");
		tropical_water_seafoam = new BlockTropicalWater(fluid_tropical_water_seafoam, Material.water).setUnlocalizedName("archipelago.tropical_water_seafoam");
		GameRegistry.registerBlock(tropical_water, "tropical_water");
		GameRegistry.registerBlock(tropical_water_seafoam, "tropical_water_seafoam");
		Archipelago.PROXY.renderFluids();
	}
}