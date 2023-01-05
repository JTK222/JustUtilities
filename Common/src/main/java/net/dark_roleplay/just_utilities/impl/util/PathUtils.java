package net.dark_roleplay.just_utilities.impl.util;

import net.dark_roleplay.just_utilities.api.IModInterface;

import java.nio.file.Path;

public class PathUtils {

	private static IModInterface modInterface = ServiceUtil.getService(IModInterface.class);

	public static Path getGameDirectory(){
		return modInterface.getGameDirectory();
	}

	public static Path getModDataDirectory(String namespace){
		return modInterface.getGameDirectory().resolve("mod_data").resolve(namespace);
	}

	public static Path getConfigDirectory(){
		return modInterface.getGameDirectory().resolve("configs");
	}
}
