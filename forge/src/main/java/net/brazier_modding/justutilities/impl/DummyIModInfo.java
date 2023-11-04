package net.brazier_modding.justutilities.impl;

import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.ForgeFeature;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DummyIModInfo implements IModInfo {
	private String modid;

	public DummyIModInfo(String modid) {
		this.modid = modid;
	}

	@Override
	public IModFileInfo getOwningFile() {
		return null;
	}

	@Override
	public String getModId() {
		return this.modid;
	}

	@Override
	public String getDisplayName() {
		return this.modid;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public ArtifactVersion getVersion() {
		return null;
	}

	@Override
	public List<? extends ModVersion> getDependencies() {
		return null;
	}

	@Override
	public List<? extends ForgeFeature.Bound> getForgeFeatures() {
		return null;
	}

	@Override
	public String getNamespace() {
		return null;
	}

	@Override
	public Map<String, Object> getModProperties() {
		return null;
	}

	@Override
	public Optional<URL> getUpdateURL() {
		return Optional.empty();
	}

	@Override
	public Optional<URL> getModURL() {
		return Optional.empty();
	}

	@Override
	public Optional<String> getLogoFile() {
		return Optional.empty();
	}

	@Override
	public boolean getLogoBlur() {
		return false;
	}

	@Override
	public IConfigurable getConfig() {
		return new IConfigurable(){

			@Override
			public <T> Optional<T> getConfigElement(String... key) {
				return Optional.empty();
			}

			@Override
			public List<? extends IConfigurable> getConfigList(String... key) {
				return new ArrayList<>();
			}
		};
	}
}
