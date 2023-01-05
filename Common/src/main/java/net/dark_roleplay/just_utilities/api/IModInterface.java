package net.dark_roleplay.just_utilities.api;

import java.nio.file.Path;

public interface IModInterface {

	String[] getAvailableMods();

	Path[] getRootPathsForMod(String modid);

	boolean isModLoaded(String modid);

	boolean isValidVersion(String modid, String versionRange);

	Path getGameDirectory();

	default void snapshotLoadingContext(){}

	default void setLoadingContext(String modid){}

	default void resetLoadingContext(){}
}
