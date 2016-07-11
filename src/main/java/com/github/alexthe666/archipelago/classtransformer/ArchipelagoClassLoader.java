package com.github.alexthe666.archipelago.classtransformer;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion("1.10.2")
@TransformerExclusions({ "com.github.alexthe666.archipelago.classtransformer." })
public class ArchipelagoClassLoader implements IFMLLoadingPlugin {
	@Override
	public String[] getASMTransformerClass() {
		return new String[] { ArchipelagoClassTransformer.class.getCanonicalName() };
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
