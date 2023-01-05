package net.dark_roleplay.just_utilities.impl;

import net.dark_roleplay.just_utilities.api.IModInterface;
import org.kohsuke.MetaInfServices;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;
import java.util.Optional;

@MetaInfServices
public class QuiltModProvider implements IModInterface {
	@Override
	public String[] getAvailableMods() {
		return QuiltLoader.getAllMods().stream().map(container -> container.metadata().id()).toArray(String[]::new);
	}

	@Override
	public Path[] getRootPathsForMod(String modid) {
		Optional<? extends ModContainer> mod = QuiltLoader.getModContainer(modid);

		if(!mod.isPresent())
			return new Path[0];

		return new Path[]{mod.get().rootPath()};
	}

	@Override
	public boolean isModLoaded(String modid) {
		return QuiltLoader.isModLoaded(modid);
	}

	@Override
	public boolean isValidVersion(String modid, String version) {
		//TODO
		return true;
	}

	@Override
	public void snapshotLoadingContext() {}

	@Override
	public void setLoadingContext(String modid) {}

	@Override
	public void resetLoadingContext() {}


	@Override
	public Path getGameDirectory() { return QuiltLoader.getGameDir(); }
}
