package net.brazier_modding.justutilities.impl;

import net.brazier_modding.justutilities.api.IModInterface;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.kohsuke.MetaInfServices;

import java.nio.file.Path;
import java.util.Optional;

@MetaInfServices
public class ForgeModProvider implements IModInterface {

	@Override
	public String[] getAvailableMods() {
		return (ModList.get() == null ?
				LoadingModList.get().getMods() :
				ModList.get().getMods()
		).stream().map(info -> info.getModId()).toArray(String[]::new);
	}

	@Override
	public Path[] getRootPathsForMod(String modid) {
		IModFileInfo file = ModList.get() == null ? LoadingModList.get().getModFileById(modid) : ModList.get().getModFileById(modid);
		if(file == null)
			return new Path[0];

		return new Path[]{file.getFile().findResource("/")};
	}

	@Override
	public boolean isModLoaded(String modid) {
		return ModList.get().isLoaded(modid);
	}

	@Override
	public boolean isValidVersion(String modid, String versionRange) {
		Optional<? extends ModContainer> mod = ModList.get().getModContainerById(modid);

		if(!mod.isPresent())
			return false;

		try {
			VersionRange range = VersionRange.createFromVersionSpec(versionRange);
			return range.containsVersion(mod.get().getModInfo().getVersion());
		} catch (InvalidVersionSpecificationException e) {
			return false;
		}
	}

	private ModContainer prevLoadingContext;

	@Override
	public void snapshotLoadingContext(){
		this.prevLoadingContext = "minecraft".equals(ModLoadingContext.get().getActiveNamespace()) ? null : ModLoadingContext.get().getActiveContainer();
	}

	@Override
	public void setLoadingContext(String modid){
		ModContainer container = ModList.get() != null ?
				ModList.get().getModContainerById(modid).orElseThrow() :

				new EarlyModContainer(LoadingModList.get().getModFileById(modid) == null ? new DummyIModInfo(modid) : LoadingModList.get().getModFileById(modid).getMods().stream().filter(info -> info.getModId().equals(modid)).findFirst().orElseThrow());
		ModLoadingContext.get().setActiveContainer(container);
	}

	@Override
	public void resetLoadingContext(){
		ModLoadingContext.get().setActiveContainer(prevLoadingContext);
	}
}