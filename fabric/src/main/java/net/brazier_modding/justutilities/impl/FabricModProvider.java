package net.brazier_modding.justutilities.impl;

import net.brazier_modding.justutilities.api.IModInterface;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.kohsuke.MetaInfServices;

import java.nio.file.Path;
import java.util.Optional;

@MetaInfServices
public class FabricModProvider implements IModInterface {
	@Override
	public String[] getAvailableMods() {
		return FabricLoader.getInstance().getAllMods().stream().map(container -> container.getMetadata().getId()).toArray(String[]::new);
	}

	@Override
	public Path[] getRootPathsForMod(String modid) {
		Optional<? extends ModContainer> mod = FabricLoader.getInstance().getModContainer(modid);

		if(!mod.isPresent())
			return new Path[0];

		return mod.get().getRootPaths().toArray(new Path[0]);
	}

	@Override
	public boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}

	@Override
	public boolean isValidVersion(String modid, String version) {
		//TODO
		return true;
	}
}
