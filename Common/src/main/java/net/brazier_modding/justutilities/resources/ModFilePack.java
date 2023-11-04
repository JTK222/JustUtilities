package net.brazier_modding.justutilities.resources;

import com.google.common.base.Joiner;
import net.brazier_modding.justutilities.JustUtilitiesConstants;
import net.minecraft.resources.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;

public class ModFilePack {

	private static Map<String, ModFilePack> MOD_FILE_PACKS = new HashMap<>();

	public static ModFilePack getPackForMod(String modId) {
		return MOD_FILE_PACKS.computeIfAbsent(modId, (modId2) -> new ModFilePack(modId, JustUtilitiesConstants.MOD_PROVIDER.getRootPathsForMod(modId2)));
	}

	private String modid;
	private Path[] modPath;

	public ModFilePack(String modid, Path[] modPath) {
		this.modid = modid;
		this.modPath = modPath;
		JustUtilitiesConstants.LOG.error("Creating Mod File Pack: {}", modid);
	}

	public Collection<ResourceLocation> getResources(String resourceNamespace, String resourcePath, int maxDepth, Predicate<String> filter) {
		Collection<ResourceLocation> resources = new HashSet<>();
		try{
			for(Path root : modPath){
				Path fixedDataRoot = root.resolve("fixed_data/" + resourceNamespace).toAbsolutePath();
				Path inputPath = fixedDataRoot.resolve(resourcePath);

				if(Files.exists(fixedDataRoot))
					Files.walk(inputPath)
							.map(fixedDataRoot::relativize)
							.filter(path -> path.getNameCount() <= maxDepth && !path.toString().endsWith(".mcmeta"))
							.filter(path -> filter.test(path.getFileName().toString()))
							.map(path -> new ResourceLocation(resourceNamespace, Joiner.on('/').join(path)))
							.forEach(resources::add);
			}
		}catch(IOException e){
			JustUtilitiesConstants.LOG.error(e);
		}

		for(ResourceLocation loc : resources){
			JustUtilitiesConstants.LOG.error("Resource Location {}: {}", resourceNamespace, loc);
		}
		return resources;
	}
	protected InputStream getResource(String resourcePath) throws IOException {
		JustUtilitiesConstants.LOG.error("Getting direct resource {}", resourcePath);
		try{
			for(Path root : modPath){
				Path input = root.resolve(resourcePath);
				if(Files.exists(input))
					return Files.newInputStream(input);
			}
		}catch(IOException e){
			JustUtilitiesConstants.LOG.error(e);
		}

		throw new FileNotFoundException("Can't find resource " + resourcePath + " for mod: " + modid);
	}

	public InputStream getResource(ResourceLocation location) throws IOException {
		return this.getResource("fixed_data/" + location.getNamespace() + "/" + location.getPath());
	}
}
